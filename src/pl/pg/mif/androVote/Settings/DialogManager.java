package pl.pg.mif.androVote.Settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogManager
{
	public static Dialog createIOError(Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("B��d podczas pr�by dost�pu do zasob�w konfiguracyjnych.")
		.setNeutralButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.dismiss();
	           }
	       });
		return builder.create();
	}
	public static Dialog createInvalidPortNumber(Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("Port serwera nie mo�e przekracza� 65535.")
		.setNeutralButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.dismiss();
	           }
	       });
		return builder.create();
	}
}
