package com.matrix.brainsense.broadcast;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.matrix.brainsense.task.LogOutTask;
import com.matrix.brainsense.task.UpdateXBMCTask;
import com.matrix.brainsense.util.ApplicationUtil;

/**
 * Open mobile start service, uninstall and remove watchdogVersion
 * 
 * @author Jolina Zhou
 */
public class MyBroadcast extends BroadcastReceiver {
	private boolean flag = true;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("logOut")) {
			new LogOutTask(context).execute();
		}
		if (intent.getAction().equals("back")) {
			if (flag) {
				flag = false;
				checkLogout(context);
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) { 
			if(intent.getDataString().contains("org.xbmc.xbmc")){
				UpdateXBMCTask.uninstall_finsh_flag=true;
			}
		}

	}

	private void checkLogout(Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> list = manager.getRunningAppProcesses();
		boolean exist = false;
		for (RunningAppProcessInfo rapi : list) {
			if (rapi.processName.equals(context.getPackageName())) {
				exist = true;
			}
		}
		if (!exist) {
			new LogOutTask(context).execute();
		} else {
			if (ApplicationUtil.isApplicationBroughtToBackground(context)) {
				flag = true;
				Intent intent = new Intent();
				intent.setAction("back");
				context.sendBroadcast(intent);
			} else {
				flag = true;
			}
		}
	}
}
