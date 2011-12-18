package com.pg.androVote;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroVoteActivity extends Activity {
	TextView mTextView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mTextView = (TextView) findViewById(R.id.textView1);
        mTextView.setText("Hello androVote!");
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    }
}
