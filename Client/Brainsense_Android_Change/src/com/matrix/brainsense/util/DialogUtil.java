package com.matrix.brainsense.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.WindowManager;

/***
 * DialogUtil
 * 
 * @version 1.0
 * @author Jolina Zhou
 */
public class DialogUtil {

	/**
	 * show dialog with the neutral button
	 * 
	 * @param context
	 * @param message
	 * @param title
	 * @param neutral
	 * @param Listener
	 */
	public static void dialogOneButton(Context context, String message,
			String title, String neutral, OnClickListener Listener) {
		
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		if (Listener == null) {
			builder.setNeutralButton(neutral, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		} else {
			builder.setNeutralButton(neutral, Listener);
		}
		builder.setCancelable(false);
		builder.create().show();
		
	}

	/**
	 * show dialog with the positive and negative button
	 * 
	 * @param context
	 * @param message
	 * @param title
	 * @param positive
	 * @param negative
	 * @param Listener
	 */
	public static void dialogTwoButton(Context context, String message,
			String title, String positive, String negative,
			OnClickListener Listener) {
		
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setPositiveButton(positive, Listener);
		builder.setNegativeButton(negative, Listener);
		builder.setCancelable(false);
		builder.create().show();
		
		
		
	}

	/**
	 * show dialog with a set of radio buttons
	 * 
	 * @param context
	 * @param title
	 * @param strings
	 * @param positive
	 * @param negative
	 * @param listener
	 */
	public static void dialogRadio(Context context, String title,
			String[] strings, String positive, String negative,
			OnClickListener listener) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setSingleChoiceItems(strings, 0, listener);
		builder.setPositiveButton(positive, listener);
		builder.setNegativeButton(negative, listener);
		builder.setCancelable(false);
		builder.create().show();
		
	}

	/**
	 * show dialog with a set of items
	 * 
	 * @param context
	 * @param title
	 * @param items
	 * @param neutral
	 * @param Listener
	 */
	public static void dialogItem(Context context, String title,
			String[] items, String neutral, OnClickListener Listener) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setItems(items, null);
		if (Listener == null) {
			builder.setNeutralButton(neutral, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		} else {
			builder.setNeutralButton(neutral, Listener);
		}
		builder.setCancelable(false);
		builder.create().show();

	}
	
	public static void dialogTwoButtonSystem(Context context, String message,
			String title, String positive, String negative,
			OnClickListener Listener) {
		
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		builder.setPositiveButton(positive, Listener);
		builder.setNegativeButton(negative, Listener);
		builder.setCancelable(false);
		AlertDialog ad = builder.create();
		ad.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
		ad.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		ad.setCanceledOnTouchOutside(false);
		ad.show();
	}
	
	public static void dialogOneButtonSys(Context context, String message,
			String title, String neutral, OnClickListener Listener) {
		
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle(title);
		if (Listener == null) {
			builder.setNeutralButton(neutral, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		} else {
			builder.setNeutralButton(neutral, Listener);
		}
		builder.setCancelable(false);
		AlertDialog ad = builder.create();
		ad.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
		ad.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		ad.setCanceledOnTouchOutside(false);
		ad.show();
		
	}
	
}
