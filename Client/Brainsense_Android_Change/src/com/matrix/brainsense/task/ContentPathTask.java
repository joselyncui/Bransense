package com.matrix.brainsense.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class ContentPathTask extends
		AsyncTask<List<Map<String, String>>, Integer, String> {
	private Context context;
	private ProgressBar progressBar;
	private Button btn;

	private String mac;
	private String localPathContent;
	private String pathContent;

	private String oldResult;
	private String newResult;
	private List<Map<String, String>> remotePath = new ArrayList<Map<String, String>>();

	public ContentPathTask(Context context, ProgressBar progressBar, Button btn) {
		super();
		this.context = context;
		this.progressBar = progressBar;
		this.btn = btn;
	}

	@Override
	protected String doInBackground(List<Map<String, String>>... params) {
		mac = ApplicationUtil.getMACaddress(context);
		remotePath = params[0];
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

	@SuppressWarnings("unchecked")
	private void checkPath() {
		if (!oldResult.equals("null")) {
			try {
				JSONObject jsonUser = new JSONObject(oldResult);
				localPathContent = jsonUser.getString("localPathContent");
			} catch (JSONException e) {
				// server exception
				ShowErrorMessageUtil.showErrorMessageWithExit(context,
						context.getString(R.string.serverException));
			}
		}
		if (!newResult.equals("null")) {
			try {
				JSONObject jsonPath = new JSONObject(newResult);
				pathContent = jsonPath.getString("contentPath");
			} catch (JSONException e) {
				// server exception
				ShowErrorMessageUtil.showErrorMessageWithExit(context,
						context.getString(R.string.serverException));
			}
		}
		// you have never download base package before
		if (localPathContent.trim().equals("")) {
			new ChangePathTask().execute();
			return;
		}
		// package path has been changed
		if (!localPathContent.equals(pathContent)) {
			new CopyFileTask().execute();
			return;
		}
		// package path not be changed
		if (localPathContent.equals(pathContent)) {
			new DownloadContentDataFTPSTask(context, progressBar, btn,
					ApplicationUtil.sdcPath() + pathContent)
					.execute(remotePath);
			return;
		}
	}

	/**
	 * Check whether the device is connected to the Internet
	 * 
	 * @author Jolina Zhou
	 */
	@SuppressWarnings("unchecked")
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
			new ContentPathTask(context, progressBar, btn).execute(remotePath);
		}
	}

	class ChangePathTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String uri = HttpUtil.getBaseUri()
					+ "user/localpackagepath?macAdd=" + mac + "&type=content";
			String result = HttpUtil.put(uri);
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(String result) {
			if (result.equals("-1")) {
				checkChangeWifi();
				return;
			}
			if (result.equals("true")) {
				new DownloadContentDataFTPSTask(context, progressBar, btn,
						ApplicationUtil.sdcPath() + pathContent)
						.execute(remotePath);
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
			return FileUtil
					.copyFolder(ApplicationUtil.sdcPath() + localPathContent,
							ApplicationUtil.sdcPath() + pathContent);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				new ChangePathTask().execute();
			}
		}

	}

}
