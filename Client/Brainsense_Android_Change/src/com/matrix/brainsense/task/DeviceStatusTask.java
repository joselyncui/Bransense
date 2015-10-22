package com.matrix.brainsense.task;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.activity.WelcomeActivity;
import com.matrix.brainsense.util.HttpUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;

/**
 * Check device status
 * 
 * @author Jolina Zhou
 */
public class DeviceStatusTask extends AsyncTask<String, Integer, String> {
	private Context context;

	private String mac;
	private String deviceId;

	public DeviceStatusTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		mac = params[0];
		deviceId = params[1];
		String uri = HttpUtil.getBaseUri() + "user/device/status/" + mac;
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
		// authorized or device is not exist
		if (result.equals("0") || result.equals("4")) {
			new UserStatusTask(context).execute(mac, deviceId);
			return;
		}
		// not authorized
		if (result.equals("1")) {
			ShowErrorMessageUtil.showErrorMessageWithExit(context,
					context.getString(R.string.refused));
			return;
		}
		// waiting for approval in X days
		if (result.equals("2")) {
			new UserStatusTask(context).execute(mac, deviceId);
			return;
		}
		// waiting for approval out of X days
		if (result.equals("3")) {
			ShowErrorMessageUtil.showErrorMessageWithExit(context,
					context.getString(R.string.review));
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
					new DeviceStatusTask(context).execute(mac, deviceId);
				}
			}, 4000);
		}
	}

}
