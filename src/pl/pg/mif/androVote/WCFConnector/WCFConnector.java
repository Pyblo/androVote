package pl.pg.mif.androVote.WCFConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


public class WCFConnector {

	/** Public API */

	
	/** Creates WebServiceConnector instance
	 * 
	 * @param url Address of configured voting server.
	 */
	public WCFConnector(String url)
	{
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}
	
	/** Authenticates into server
	 * 
	 * @param login		user's login
	 * @param password	user's password
	 * @throws BadLoginException 
	 */
	public String Login(String login, String password) throws IOException, BadLoginException
	{
		String ret;
		this.AddParam("login", login);
		this.AddParam("password", password);
		try {
        	this.Execute(WCFConnector.RequestMethod.GET, "LoginUser");
        } catch (Exception e) {
        	e.printStackTrace();
        }
		ret = this.getResponse();
		
		// I'm parsing result
		// first I need strip xml tags
		// next I'm getting JSON object and test wheter was error,
		// if yes: throw exception
		// if not: return string with user id
		String return_string = "";
		JSONObject js;
        try {
			js = new JSONObject(this.SanitizeMessage(ret));
			if (js.getBoolean("isError") == true)
			{
				throw new BadLoginException();
			}
			return_string= js.getString("id");
		} catch (JSONException e) {
			e.printStackTrace();	
		}
        
        return return_string;
	}
	
	/** Terminates session and removes all auth tokens (if present) */
	public void Logout()
	{
		// empty stub, because there's not such method in WCF api (10.01.2012)
	}
	
	/** Vote action
	 * Sends vote to server as authenticated user: warning - there must be
	 * active election.
	 * @param voteId	number representing selected option
	 */
	public void Vote(int voteId)
	{
	}
	
	/** 
	 * Returns current election info 
	 * 
	 * @return Method returns null if there's no 
	 * active voting or VoteInfo if there's started voting.
	 */
	public VoteInfo GetCurrentVoteInfo()
	{
		// accessing method GetActiveVotingId
		String ret;
		try {
        	this.Execute(WCFConnector.RequestMethod.GET, "GetActiveVotingId");
        } catch (Exception e) {
        	e.printStackTrace();
        }
		ret = this.getResponse();
		
		// parsing answer
		JSONObject js;
		int votingId = -2;
        try {
			js = new JSONObject(this.SanitizeMessage(ret));
			votingId = js.getInt("id");
		} catch (JSONException e) {
			e.printStackTrace();	
		}
        
        if (votingId < 0){
        	// there's no current voting
        	return null;
        } else {
        	this.AddParam("id", Integer.toString(votingId));
        	try {
            	this.Execute(WCFConnector.RequestMethod.GET, "GetVotingById");
            } catch (Exception e) {
            	e.printStackTrace();
            }
    		ret = this.getResponse();
    		
    		// here we should have JSON object with info about current voting
    		// (surrounded by ugly xml tags)
    		VoteInfo vi = new VoteInfo();
    		try{
    			js = new JSONObject(this.SanitizeMessage(ret));
    		} catch (JSONException e) {
    			e.printStackTrace();	
    		}
        	return vi;
        }
		
	}

	// end of public API
	
	private VoteInfo vi = null;
	
	/**
	 * Rippoff xml tags from message and returns pure JSON object.
	 * @param Message information returned by WCF.
	 * @return
	 */
	private String SanitizeMessage(String message)
	{
		int start_pos = message.indexOf('{');
		int stop_pos  = message.lastIndexOf('}');
		return message.substring(start_pos, stop_pos + 1);
	}
	
	// DON'T TOUCH!!! ///////////////////////////////////////////////////////
	
	/* 
	 * from http://lukencode.com/2010/04/27/calling-web-services-in-android-using-httpclient/ 
	 * For now: just stable API
	 */
	
	private enum RequestMethod
	{
		GET,
		POST
	}
	
	private ArrayList <NameValuePair> params;
	private ArrayList <NameValuePair> headers;

	private String url;

	private int responseCode;
	private String message;

	private String response;

	private String getResponse() {
		return response;
	}

//	private String getErrorMessage() {
//		return message;
//	}
//
//	private int getResponseCode() {
//		return responseCode;
//	}

	private void AddParam(String name, String value)
	{
		params.add(new BasicNameValuePair(name, value));
	}

	private void AddHeader(String name, String value)
	{
		headers.add(new BasicNameValuePair(name, value));
	}
	
	private void Execute(RequestMethod method, String function) throws Exception
	{
		switch(method) {
		case GET:
		{
			//add parameters
			String combinedParams = "";
			if(!params.isEmpty()){
				combinedParams += "?";
				for(NameValuePair p : params)
				{
					String paramString = p.getName() + "='" + URLEncoder.encode(p.getValue() + "'","UTF-8");
					if(combinedParams.length() > 1)
					{
						combinedParams  +=  "&" + paramString;
					}
					else
					{
						combinedParams += paramString;
					}
				}
			}

			HttpGet request = new HttpGet(url + function + combinedParams);

			//add headers
			for(NameValuePair h : headers)
			{
				request.addHeader(h.getName(), h.getValue());
			}

			executeRequest(request, url);
			break;
		}
		case POST:
		{
			HttpPost request = new HttpPost(url);

			//add headers
			for(NameValuePair h : headers)
			{
				request.addHeader(h.getName(), h.getValue());
			}

			if(!params.isEmpty()){
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}

			executeRequest(request, url);
			break;
		}
		}
	}

	private void executeRequest(HttpUriRequest request, String url)
	{
		HttpClient client = new DefaultHttpClient();

		HttpResponse httpResponse;

		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);

				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e)  {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
	}

	// reads stream from socket and writes it to the resulting string
	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
