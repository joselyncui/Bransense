package com.matrix.brainsense.task;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.HttpUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;

public class UpdateAPKTask extends AsyncTask<String, Integer, String> {

	private Context context;
	
	public UpdateAPKTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		String uri = HttpUtil.getBaseUri() + "user/watchdog";
		return HttpUtil.get(uri);
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			if (result.equals("-1")) {
				Toast.makeText(context, R.string.communication,
						Toast.LENGTH_LONG).show();
				checkWifi();
			} else {
				try {
					JSONObject jsonXBMC = new JSONObject(result);
					String packagePath = jsonXBMC.getString("packagePath");
					String version = jsonXBMC.getString("versionCode");
					if (ApplicationUtil.getVersion(context)>=Integer.parseInt(version)) {
						new UpdateXBMCTask(context).execute();
					} else {
						new UpdateDownloadTask(context).execute(packagePath.replace(
								"\\", "/") + "/watchdog.apk");
					}
				} catch (JSONException e) {
					ShowErrorMessageUtil.showErrorMessageWithExit(context,
							context.getString(R.string.serverException));
				}
			}
		} else {
			ShowErrorMessageUtil.showErrorMessage(context,
					context.getString(R.string.noWatchdog));
		}
	}
	
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
					new UpdateAPKTask(context).execute();
				}
			}, 4000);
		}
	}

}
