package pl.pg.mif.androVote;

import java.io.IOException;

import pl.pg.mif.androVote.WCFConnector.BadLoginException;
import pl.pg.mif.androVote.WCFConnector.WCFConnector;

import pl.pg.mif.androVote.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AndroVoteActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tv = (TextView) findViewById(R.id.main_test_textview);
        WCFConnector wcf = new WCFConnector("http://10.0.2.2:23323/RW_GLOSOWANIE_WcfDataService.svc/");
        try {
			tv.setText(wcf.Login("kak", "kaka"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLoginException e) {
			// TODO Auto-generated catch block
			tv.setText(e.message);
			e.printStackTrace();
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
