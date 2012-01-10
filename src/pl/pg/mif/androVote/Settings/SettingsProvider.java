package pl.pg.mif.androVote.Settings;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import android.content.Context;

public class SettingsProvider
{
	private String settingsFileName = "androVote.ini";
	private Context context;	
	private String serverAddress = "pg.serv";
	private int serverPort = 666;
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public SettingsProvider(Context context) throws IOException
	{
		this.context = context;
		try 
		{
			loadSettings();
		} 
		catch (FileNotFoundException e) { }
	}
	public void saveSettings() throws IOException
	{
		FileOutputStream fos = context.openFileOutput(settingsFileName, Context.MODE_PRIVATE);
		fos.write(("ServerAddress=" + serverAddress + "\n").getBytes());
		fos.write(("ServerPort=" + Integer.toString(serverPort)).getBytes());
		fos.close();
	}
	private void loadSettings() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(context.openFileInput(settingsFileName))));
		HashMap<String,String> settingsMap = new HashMap<String, String>();
		String line;
		while ((line = br.readLine()) != null)
		{
			String[] res = line.split("=");
			settingsMap.put(res[0], res[1]);
		}
		if(settingsMap.containsKey("ServerAddress")) serverAddress = settingsMap.get("ServerAddress");
		if(settingsMap.containsKey("ServerPort")) serverPort = Integer.parseInt(settingsMap.get("ServerPort"));
		br.close();
	}
}