package com.matrix.brainsense.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.matrix.brainsense.activity.BasicActivity;
import com.matrix.brainsense.activity.ChoiceActivity;
import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.activity.RegisterActivity;
import com.matrix.brainsense.activity.WelcomeActivity;
import com.matrix.brainsense.activity.XBMCActivity;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.HttpUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;

/**
 * Check user status
 * 
 * @author Jolina Zhou
 */
public class UserStatusTask extends AsyncTask<String, Integer, String> {

	private String mac;
	private String deviceId;

	private Context context;

	public UserStatusTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		mac = params[0];
		deviceId = params[1];
		String uri = HttpUtil.getBaseUri() + "user/user/status/" + mac + "/"
				+ deviceId;
		String result = HttpUtil.get(uri);
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		// Communication problems
		if (result.equals("-1")) {
			Toast.makeText(context, R.string.communication, Toast.LENGTH_LONG)
					.show();
			WelcomeActivity.exit_flag = true;
			checkWifi();
			return;
		}
		// register page
		if (result.equals("0")) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent mainIntent = new Intent(context,
							RegisterActivity.class);
					mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					context.startActivity(mainIntent);
					((Activity) context).finish();
					((Activity) context).overridePendingTransition(
							R.anim.in_from_right, R.anim.out_to_left);
				}
			}, 1000);
			return;
		}
		// download basic package
		if (result.equals("1")) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent mainIntent = new Intent(context, BasicActivity.class);
					mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					context.startActivity(mainIntent);
					((Activity) context).finish();
					((Activity) context).overridePendingTransition(
							R.anim.in_from_right, R.anim.out_to_left);
				}
			}, 1000);
			return;
		}
		// download xbmc
		if (result.equals("2")) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent mainIntent = new Intent(context, XBMCActivity.class);
					mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					context.startActivity(mainIntent);
					((Activity) context).finish();
					((Activity) context).overridePendingTransition(
							R.anim.in_from_right, R.anim.out_to_left);
				}
			}, 1000);
			return;
		}
		// download content
		if (result.equals("3") || result.equals("4")) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent mainIntent = null;
					if(ApplicationUtil.xbmcExist(context, "org.xbmc.xbmc")){
						mainIntent = new Intent(context,
								ChoiceActivity.class);
					}else if(!ApplicationUtil.xbmcExist(context, "org.xbmc.xbmc")){
						mainIntent = new Intent(context, XBMCActivity.class);
					}
					mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					context.startActivity(mainIntent);
					((Activity) context).finish();
					((Activity) context).overridePendingTransition(
							R.anim.in_from_right, R.anim.out_to_left);
					
				}
			}, 1000);
			return;
		}
		// MAC and DeviceId not match
		if (result.equals("5")) {
			ShowErrorMessageUtil.showErrorMessageWithExit(context,
					context.getString(R.string.deviceIdNotMatch));
			return;
		}
		// server exception
		ShowErrorMessageUtil.showErrorMessageWithExit(context,
				context.getString(R.string.serverException));
	}

	/**
	 * Check whether the device is connected to the Internet
	 * 
	 * @author Jolina Zhou
	 */
	private void checkWifi() {
		ConnectivityManager manger = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manger.getActiveNetworkInfo();
		boolean flag = (info != null && info.isConnected());
		if (!flag) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context, R.string.communication,
							Toast.LENGTH_LONG).show();
					checkWifi();
				}
			}, 4000);
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					WelcomeActivity.exit_flag = false;
					new UserStatusTask(context).execute(mac, deviceId);
				}
			}, 4000);
		}
	}

}
