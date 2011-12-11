package com.pg.androVote;

import java.io.IOException;

import com.pg.androVote.WCFConnector.VoteInfo;
import com.pg.androVote.WCFConnector.WCFConnector;
import com.pg.androVote.WCFConnector.BadLoginException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroVoteActivity extends Activity {
	TextView mTextView;
	WCFConnector wsc;
	
	/** Here we string settings of our App in memory */
	private SettingsStorage sS = new SettingsStorage();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/** getting settings from 'secure' storage */
    	//String s = this.getPreferences(Context.MODE_PRIVATE).getString("serverAddress", "http://10.0.2.2:4567");
    	sS.setServerAddress("http://10.0.2.2:4567");
    	wsc = new WCFConnector(sS.getServerAddress());
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
        mTextView = (TextView) findViewById(R.id.textView1);
        try
        {
        	wsc.Login("", "");
        }
        catch (BadLoginException e)
        {
        	
        }
        catch (IOException e)
        {
        	
        }
        VoteInfo vi = wsc.GetCurrentVoteInfo();
        
        mTextView.setText(vi.getDescription());
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    }
}
