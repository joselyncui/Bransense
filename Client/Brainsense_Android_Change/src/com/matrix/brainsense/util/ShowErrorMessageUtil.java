package com.matrix.brainsense.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;

import com.matrix.brainsense.activity.R;

public class ShowErrorMessageUtil {
	
	public static void showErrorMessage(Context context,String errorMessage){
		dialog(context,errorMessage);
	}
	
	public static void showErrorMessageWithExit(Context context,String errorMessage){
		dialogWithExit(context,errorMessage);
	}
	
	public static void dialog(Context context,String errorMessage) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(errorMessage);
		builder.setTitle(R.string.title);
		builder.setNeutralButton(R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setCancelable(false);
		AlertDialog ad = builder.create();
		ad.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
		ad.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		
		ad.show();
	}
	
	public static void dialogWithExit(Context context,String errorMessage) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(errorMessage);
		builder.setTitle(R.string.title);
		builder.setNeutralButton(R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
			}
		});
		builder.setCancelable(false);
		AlertDialog ad = builder.create();
		ad.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
		ad.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		
		ad.show();
	}
	
	public static void toast(Context context,String errorMessage){
		Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
	}
}
