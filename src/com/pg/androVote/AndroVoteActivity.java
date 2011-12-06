package com.pg.androVote;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroVoteActivity extends Activity {
	TextView mTextView;
	WebServiceConnector wsc;
	
	/** Here we string settings of our App in memory */
	private SettingsStorage sS = new SettingsStorage();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/** getting settings from 'secure' storage */
    	//String s = this.getPreferences(Context.MODE_PRIVATE).getString("serverAddress", "http://10.0.2.2:4567");
    	sS.setServerAddress("http://10.0.2.2:4567");
    	wsc = new WebServiceConnector(sS.getServerAddress());
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
        mTextView = (TextView) findViewById(R.id.textView1);
        wsc.Login("", "");
        VoteInfo vi = wsc.GetCurrentVoteInfo();
        
        mTextView.setText(vi.getDescription());
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    }
}
