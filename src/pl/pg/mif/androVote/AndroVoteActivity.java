package pl.pg.mif.androVote;

import pl.pg.mif.androVote.WCFConnector.UserInfo;
import pl.pg.mif.androVote.WCFConnector.WCFConnector;
import pl.pg.mif.androVote.WCFConnector.Exceptions.BadLoginException;
import pl.pg.mif.androVote.WCFConnector.Exceptions.IOSettingsException;
import pl.pg.mif.androVote.WCFConnector.Exceptions.ServerConnectionException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AndroVoteActivity extends Activity {

	private Button zaloguj;
	private Button zamknij;
	private Button ustawienia;
	private EditText login;
	private EditText haslo;
	private String logintxt;
	private String haslotxt;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		this.zamknij = (Button) this.findViewById(R.id.button3);
		this.zamknij.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		this.zaloguj = (Button) this.findViewById(R.id.button1);
		this.zaloguj.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub

				login = (EditText) findViewById(R.id.editText1);
				logintxt = login.getText().toString();
				haslo = (EditText) findViewById(R.id.editText2);
				haslotxt = haslo.getText().toString();

			}
		});
		this.ustawienia = (Button) this.findViewById(R.id.button2);
		this.ustawienia.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
        
        WCFConnector wcf = null; 
        try{
        	wcf = new WCFConnector(this);
        } catch (IOSettingsException e)
        {
        	
        }
        
        if (wcf != null)
        {
	        try {
	        	wcf.Login(logintxt, haslotxt);
			} catch (ServerConnectionException e) {
				e.printStackTrace();
			} catch (BadLoginException e) {
				e.printStackTrace();
			}
        } else {
        }
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    }
    
    public void Ustawienia_click(View view)
    {
    	Intent i = new Intent(this, pl.pg.mif.androVote.SettingsActivity.class);
    	startActivity(i);
    }
}
