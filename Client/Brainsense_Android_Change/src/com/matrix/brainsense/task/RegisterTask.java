package com.matrix.brainsense.task;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.matrix.brainsense.activity.BasicActivity;
import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.activity.RegisterActivity;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.HttpUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;

/**
 * Gather user information
 * 
 * @author Jolina Zhou
 */
public class RegisterTask extends AsyncTask<String, Integer, String> {
	private String mac;
	private String name;
	private String email;
	private String keycode;
	private String deviceId;

	private Context context;

	public RegisterTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		name = params[0];
		email = params[1];
		keycode = params[2];
		mac = ApplicationUtil.getMACaddress(context);
		deviceId = ApplicationUtil.DeviceId(context);

		String uri = null;
		try {
			uri = HttpUtil.getBaseUri() + "user/user?macAdd=" + mac
					+ "&name=" + URLEncoder.encode(name,"utf-8") + "&email=" + email + "&keyCode=" + keycode
					+ "&deviceId=" + deviceId;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = HttpUtil.post(uri);

		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		// Communication problems
		if (result.equals("-1")) {
			checkWifi();
			return;
		}
		// key code is not exist;
		if (result.equals("0")) {
			ShowErrorMessageUtil.showErrorMessage(context,
					context.getString(R.string.noKeyCode));
			RegisterActivity.REGISTER_FLAG = true;
			return;
		}
		// key code has been used
		if (result.equals("1")) {
			ShowErrorMessageUtil.showErrorMessage(context,
					context.getString(R.string.keyCodeRepeat));
			RegisterActivity.REGISTER_FLAG = true;
			return;
		}
		// key code is not match
		if (result.equals("2")) {
			ShowErrorMessageUtil.showErrorMessage(context,
					context.getString(R.string.keyCodeNotMatch));
			RegisterActivity.REGISTER_FLAG = true;
			return;
		}
		// success
		if (result.equals("3")) {
			new GatherInforTask(context).execute();
			Intent mainIntent = new Intent(context, BasicActivity.class);
			mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(mainIntent);
			((Activity) context).finish();
			return;
		}
		// server exception
		ShowErrorMessageUtil.showErrorMessage(context,
				context.getString(R.string.serverException));
		RegisterActivity.REGISTER_FLAG = true;
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
			Toast.makeText(context, R.string.communication, Toast.LENGTH_LONG)
					.show();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					checkWifi();
				}
			}, 4000);
		} else {
			RegisterActivity.REGISTER_FLAG = true;
		}
	}

}
