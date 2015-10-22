package com.matrix.brainsense.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
/**
 * Quit
 * 
 * @version 1.0
 * @author Jolina Zhou
 */
public class QuitListener implements OnClickListener {
	private Context context;
	
	public QuitListener(Context context){
		this.context=context;
	}
	/* (non-Javadoc)
	 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(which==DialogInterface.BUTTON_POSITIVE){
			Intent intent = new Intent();  
	        intent.setAction("logOut");
	        context.sendBroadcast(intent);
	        System.exit(0);
		}else if(which==DialogInterface.BUTTON_NEGATIVE){
			dialog.dismiss();
		}
	}

}
