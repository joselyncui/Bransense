package com.matrix.brainsense.task;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.activity.WelcomeActivity;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.FileUtil;
import com.matrix.brainsense.util.HttpUtil;
import com.matrix.brainsense.util.PathUtil;
import com.matrix.brainsense.util.SharedPfUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;

public class UpdateXBMCTask extends AsyncTask<String, Integer, String> {
	private Context context;

	private String mac;
	private String deviceId;

	public static String packagePath;

	public static boolean uninstall_finsh_flag = false;

	public UpdateXBMCTask(Context context) {
		super();
		this.context = context;
		mac = ApplicationUtil.getMACaddress(context);
		deviceId = ApplicationUtil.DeviceId(context);
	}

	@Override
	protected String doInBackground(String... params) {
		String uri = HttpUtil.getBaseUri() + "user/xbmc";
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
					packagePath = jsonXBMC.getString("packagePath");
					String version = jsonXBMC.getString("versionCode");
					if (ApplicationUtil.getAppVersion(context, "org.xbmc.xbmc") == -1) {
						if(SharedPfUtil.keyIsExist(context.getApplicationContext(), "needUpdate")){
							new UpdateDownloadTask(context).execute(packagePath
									.replace("\\", "/") + "/xbmc.apk");
						}else{
							new DeviceStatusTask(context).execute(mac, deviceId);
						}
					} else if (ApplicationUtil.getAppVersion(context,
							"org.xbmc.xbmc") == Integer.parseInt(version)) {
						new DeviceStatusTask(context).execute(mac, deviceId);
					} else if (ApplicationUtil.getAppVersion(context,
							"org.xbmc.xbmc") > Integer.parseInt(version)) {
						FileUtil.rename(PathUtil.XBMCDataPath, PathUtil.XBMCTempDataPath);
						uninstallAPK(context, "org.xbmc.xbmc");
						WelcomeActivity.resume_flag = true;
					} else if (ApplicationUtil.getAppVersion(context,
							"org.xbmc.xbmc") < Integer.parseInt(version)) {
						new UpdateDownloadTask(context).execute(packagePath
								.replace("\\", "/") + "/xbmc.apk");
					}
				} catch (JSONException e) {
					ShowErrorMessageUtil.showErrorMessageWithExit(context,
							context.getString(R.string.serverException));
				}
			}
		} else {
			ShowErrorMessageUtil.showErrorMessage(context,
					context.getString(R.string.noXBMC));
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
					new UpdateXBMCTask(context).execute();
				}
			}, 4000);
		}
	}

	/**
	 * uninstall apk file
	 * 
	 * @param packagName
	 */
	public static void uninstallAPK(Context context, String packageName) {

		Uri uri = Uri.parse("package:" + packageName);
		Intent intent = new Intent(Intent.ACTION_DELETE, uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

}
