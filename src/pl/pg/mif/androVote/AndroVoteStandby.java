package pl.pg.mif.androVote;

import pl.pg.mif.androVote.WCFConnector.UserInfo;
import pl.pg.mif.androVote.WCFConnector.WCFConnector;
import pl.pg.mif.androVote.WCFConnector.Exceptions.BadLoginException;
import pl.pg.mif.androVote.WCFConnector.Exceptions.IOSettingsException;
import pl.pg.mif.androVote.WCFConnector.Exceptions.ServerConnectionException;
import java.util.ArrayList;
import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AndroVoteStandby extends Activity {
    /** Called when the activity is first created. */
	public static boolean admin = true;
	boolean lock = false;
	
    static final int USER_WAIT_SCREEN = 1;
    static final int USER_LOCK = 2;
    static final int ADMIN_WAIT_SCREEN = 3;
    
    ArrayList<vote> votes = generateVotes();
    ArrayList<vote> placedVotes;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int lay = admin == true ? R.layout.admin : R.layout.main;
        setContentView(lay);
        TextView t = (TextView) this.findViewById(R.id.textView1);
        t.setText("G³osowania: x/y //TODO");
        
    }
    
    //TODO: remove sample populate method
    private ArrayList<vote> generateVotes(){
    	ArrayList<vote> votes = new ArrayList<vote>();
    	for(int i =0; i<10; i++){
    		votes.add(new vote(i,"description number " + i));
    	}
		return votes;
    }
    
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch(id) {
        case ADMIN_WAIT_SCREEN:
        	
        	final ArrayList<vote> items = new ArrayList<vote>();
        	for(int i=0; i<votes.size(); i++){
        		if(votes.get(i).votePlaced == false)
        		items.add(votes.get(i));
        	}
        	String[] itemArray=new String[items.size()];
        	
        	for(int i=0; i<items.size(); i++){
        		itemArray[i] = items.get(i).description;
        	}
        	
        	AlertDialog.Builder admin_start_vote = new AlertDialog.Builder(this);
        	admin_start_vote.setTitle("Wybierz g³osowanie");
        	admin_start_vote.setItems(itemArray, new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int item) {
        	        Toast.makeText(getApplicationContext(), items.get(item).description, Toast.LENGTH_SHORT).show();
        	        votes.remove(item);
        	    }
        	});
        	dialog = admin_start_vote.create();
        	break;
        case USER_WAIT_SCREEN:
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage("Czy zablokowaæ aplikacje")
        	       .setCancelable(false)
        	       .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
            	    		showDialog(USER_LOCK);
        	           }
        	       })
        	       .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	                dialog.cancel();
        	           }
        	       });
        	dialog = builder.create();	
            break;
        case USER_LOCK:
        	AlertDialog.Builder alert = new AlertDialog.Builder(this);                 
        	alert.setTitle("Zablokowano")
        		.setMessage("Has³o :")
        		.setCancelable(false);

        	 // Set an EditText view to get user input   
        	 final EditText input = new EditText(this); 
        	 input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        	 alert.setView(input);
        	 alert.setPositiveButton(android.R.string.ok,
                     new Dialog.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface d, int which) {
                             //Do nothing here. We override the onclick so it wont shut window unless pin match
                         }
                     });

        	 dialog = alert.create();
        	 dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                 @Override
                 public void onShow(final DialogInterface dialog) {

                     Button b = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                     b.setOnClickListener(new View.OnClickListener() {

                         @Override
                         public void onClick(View view) {
                        	 String s = input.getText().toString();
                        	 //TODO get password here
                        	 if (!s.equals("1111")){
                        		 input.setText("");
                        	 } else {
                        		 input.setText("");
                        		 dialog.dismiss();
                        	 }
                         }
                     });
                 }
             });

        	break;
        default:
            dialog = null;
        }
        return dialog;
    }
    
    public void lockScreen(View view){
    	showDialog(1);
    }
    
    public void currentVote(View view){
    	showDialog(ADMIN_WAIT_SCREEN);
    }
}