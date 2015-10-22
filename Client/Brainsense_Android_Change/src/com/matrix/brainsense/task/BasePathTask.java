package com.matrix.brainsense.task;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.FileUtil;
import com.matrix.brainsense.util.HttpUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;

public class BasePathTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressBar progressBar;
	private Button btn;

	private String mac;
	private String localPathBase;
	private String pathBase;
	private String totalSize;

	private String oldResult;
	private String newResult;
	private String remotePath;

	private String language;

	public BasePathTask(Context context, ProgressBar progressBar, Button btn) {
		this.context = context;
		this.progressBar = progressBar;
		this.btn = btn;
	}

	@Override
	protected String doInBackground(String... params) {
		mac = ApplicationUtil.getMACaddress(context);
		remotePath = params[0];
		language = params[1];
		totalSize = params[2];
		String uriOld = HttpUtil.getBaseUri() + "user/localpackagepath/" + mac;
		String uriNew = HttpUtil.getBaseUri() + "user/packagepath";
		oldResult = HttpUtil.get(uriOld);
		newResult = HttpUtil.get(uriNew);
		String result = "";
		if (oldResult.equals("-1") || newResult.equals("-1")) {
			result = "-1";
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		if (result.equals("-1")) {
			checkWifi();
			return;
		}
		checkPath();
	}

	private void checkPath() {
		if (!oldResult.equals("null")) {
			try {
				JSONObject jsonUser = new JSONObject(oldResult);
				localPathBase = jsonUser.getString("localPathBase");
			} catch (JSONException e) {
				// server exception
				ShowErrorMessageUtil.showErrorMessageWithExit(context,
						context.getString(R.string.serverException));
			}
		}
		if (!newResult.equals("null")) {
			try {
				JSONObject jsonPath = new JSONObject(newResult);
				pathBase = jsonPath.getString("basePath");
			} catch (JSONException e) {
				// server exception
				ShowErrorMessageUtil.showErrorMessageWithExit(context,
						context.getString(R.string.serverException));
			}
		}

		// you have never download base package before
		if (localPathBase.trim().equals("")) {
			new ChangePathTask().execute();
			return;
		}
		// package path has been changed
		if (!localPathBase.equals(pathBase)) {
			new CopyFileTask().execute();
			return;
		}
		// package path not be changed
		if (localPathBase.equals(pathBase)) {
			new DownLoadBaseDataFTPSTask(context, progressBar, btn)
					.execute(remotePath, ApplicationUtil.sdcPath() + pathBase,
							totalSize);
			return;
		}
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
			new BasePathTask(context, progressBar, btn).execute(remotePath,
					language, totalSize);
		}
	}

	class ChangePathTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String uri = HttpUtil.getBaseUri()
					+ "user/localpackagepath?macAdd=" + mac + "&type=base";
			String result = HttpUtil.put(uri);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("-1")) {
				checkChangeWifi();
				return;
			}
			if (result.equals("true")) {
				new DownLoadBaseDataFTPSTask(context, progressBar, btn)
						.execute(remotePath, ApplicationUtil.sdcPath()
								+ pathBase, totalSize);
				return;
			}
			// server exception
			ShowErrorMessageUtil.showErrorMessageWithExit(context,
					context.getString(R.string.serverException));
		}
		
		private void checkChangeWifi() {
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
				new ChangePathTask().execute();
			}
		}

	}

	class CopyFileTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			return FileUtil.copyFolder(ApplicationUtil.sdcPath()
					+ localPathBase, ApplicationUtil.sdcPath() + pathBase);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				new ChangePathTask().execute();
			}
		}

	}
}
