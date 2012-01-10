package com.pg.androVote.WCFConnector;

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


/** from http://lukencode.com/2010/04/27/calling-web-services-in-android-using-httpclient/ 
 * For now: just stable API
 */

public class WCFConnector {

	/** Public API */
	public enum RequestMethod
	{
		GET,
		POST
	}
	
	/** Creates WebServiceConnector instance
	 * 
	 * @param url voting server address
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
	 */
	public String Login(String login, String password) throws IOException, BadLoginException
	{
		// need total rework
		String ret;
		this.AddParam("login", login);
		this.AddParam("password", password);
		try {
        	this.Execute(WCFConnector.RequestMethod.GET, "LoginUser");
        } catch (Exception e) {
        	e.printStackTrace();
        }

		ret = this.getResponse();
        try {
			JSONObject js = new JSONObject(this.SanitizeMessage(ret));
		} catch (JSONException e) {
			e.printStackTrace();	
		}
        return ret;
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
	
	/** Returns current election info */
	public VoteInfo GetCurrentVoteInfo()
	{
		return vi;
	}

	// end of public API
	
	private VoteInfo vi = null;
	
	private String SanitizeMessage(String message)
	{
		return message;
		
	}
	
	// DON'T TOUCH!!! ///////////////////////////////////////////////////////
	private ArrayList <NameValuePair> params;
	private ArrayList <NameValuePair> headers;

	private String url;

	private int responseCode;
	private String message;

	private String response;

	private String getResponse() {
		return response;
	}

	private String getErrorMessage() {
		return message;
	}

	private int getResponseCode() {
		return responseCode;
	}

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
