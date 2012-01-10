package pl.pg.mif.androVote.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pl.pg.mif.androVote.R;

public class VoteDialog {
	Context context;
	Dialog dialog;
	String type;
	AlertDialog.Builder builder;
	AlertDialog ad;
	boolean dialogResult = false;
	
	public VoteDialog(Context cont,String typ) {
		
		this.context = cont;
		this.dialog = new Dialog(this.context);
		this.type = typ;
		
		if(typ=="error")// alerty bledow
		{
			this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.dialog.setContentView(R.layout.alert_dialog);
			ImageView image = (ImageView) this.dialog.findViewById(R.id.image);
			image.setImageResource(R.drawable.err_ico);
			
			Button closeButton = (Button)this.dialog.findViewById(R.id.Close);
			
			closeButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					dialog.dismiss();
					// TODO Auto-generated method stub
					
				}
			});
		}
		else if(typ=="warning")// ostrzeżenia
		{
			this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.dialog.setContentView(R.layout.alert_dialog);
			ImageView image = (ImageView) this.dialog.findViewById(R.id.image);
			image.setImageResource(R.drawable.warning_ico);
			
			Button closeButton = (Button)this.dialog.findViewById(R.id.Close);
			
			closeButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					dialog.dismiss();
					// TODO Auto-generated method stub
					
				}
			});
		}
		else if(typ=="confirm")// ostrzeżenia
		{
			this.builder = new AlertDialog.Builder(this.context);
			this.builder.setCancelable(false);
			this.builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   	dialogResult = true;
		                dialog.dismiss();
		           }
		    });
			this.builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   	dialogResult = false;
		                dialog.dismiss();
		           }
		    });
		}
	}
	
	//dodaj tytul
	//public void set_title(String title){
	//	this.dialog.setTitle(title);
	//}

	//ustaw wiadomosc
	public void set_message(String mess){
		if(this.type=="confirm")
		{
			this.builder.setMessage(mess);
		}
		else
		{
			TextView text = (TextView) this.dialog.findViewById(R.id.text);
			text.setText(mess);
		}
	}

	public void show_dialog(){
		if(this.type=="confirm")
		{
			this.ad = this.builder.create();
			this.ad.show();
		}
		else
		{
			this.dialog.show();
		}
	}
}
