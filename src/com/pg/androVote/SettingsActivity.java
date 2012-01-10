package com.pg.androVote;

import java.io.IOException;
import com.pg.androVote.R;
import com.pg.androVote.settings.DialogManager;
import com.pg.androVote.settings.SettingsProvider;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends Activity {
	protected SettingsProvider settings;
	
	static final int DIALOG_IO_ERROR = 0;
	static final int DIALOG_INVALID_PORT_NUM = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        try {
			settings = new SettingsProvider(this);
		} catch (IOException e) {
			e.printStackTrace();
			showDialog(DIALOG_IO_ERROR);
		}
        if(settings == null)
        {
			finish();
        }
        else
        {
        	initEdit();
        }
    }
    
    protected void initEdit()
    {
    	((EditText)this.findViewById(R.id.editText1)).setText(settings.getServerAddress());
    	((EditText)this.findViewById(R.id.editText2)).setText(Integer.toString(settings.getServerPort()));
    }
    
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch(id) 
        {
	        case DIALOG_IO_ERROR:
	            dialog = DialogManager.createIOError(this);
	            break;
	        case DIALOG_INVALID_PORT_NUM:
	            dialog = DialogManager.createInvalidPortNumber(this);
	            break;
	        default:
	            dialog = null;
        }
        return dialog;
    }
    
    protected boolean commitSettings()
    {
    	settings.setServerAddress(((EditText)findViewById(R.id.editText1)).getText().toString());
    	int port = Integer.parseInt((String)((EditText)findViewById(R.id.editText2)).getText().toString());
    	if(port > 65535)
    	{
    		showDialog(DIALOG_INVALID_PORT_NUM);
    		return false;
    	}
    	if(port < 0)
    	{
    		showDialog(DIALOG_INVALID_PORT_NUM);
    		return false;
    	}
    	settings.setServerPort(port);
    	try {
			settings.saveSettings();
		} catch (IOException e) {
			showDialog(DIALOG_IO_ERROR);
			e.printStackTrace();
			return false;
		}
    	return true;
    }
    
    public void OK_click(View view)
    {
    	if(commitSettings())
    	{
			finish();
    	}
    }
    
    public void Zastosuj_click(View view)
    {
    	commitSettings();
    	initEdit();
    }
    
    public void Anuluj_click(View view)
    {
    	finish();
    }
}