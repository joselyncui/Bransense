package com.matrix.brainsense.task;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;

import javax.net.ssl.SSLException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.matrix.brainsense.activity.BasicActivity;
import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.util.FTPSUtil;
import com.matrix.brainsense.util.FTPUtil;
import com.matrix.brainsense.util.PathUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;

public class UpdateDownloadTask extends AsyncTask<String, Integer, String> {

	private Context context;
	private ProgressDialog progressDialog;

	public static long totalSize = 0;
	public static long currentSize = 0;

	private String downloadPath;
	private String localPath;

	public UpdateDownloadTask(Context context) {
		super();
		this.context = context;
	}

	public UpdateDownloadTask(Context context, ProgressDialog progressDialog) {
		super();
		this.context = context;
		this.progressDialog = progressDialog;
	}

	@Override
	protected String doInBackground(String... params) {
		downloadPath = params[0];
		localPath = PathUtil.XBMCPath + downloadPath;
		File file = new File(localPath.substring(0, localPath.lastIndexOf("/")));
		if (!file.exists()) {
			file.mkdirs();
		}
		publishProgress(0);
		try {
			try {
				if (FTPSUtil.connect(PathUtil.ftpIp, PathUtil.ftpPort,
						PathUtil.ftpName, PathUtil.ftpPassword)) {
					currentSize = FTPSUtil.getLocalFolderSize(file);
					totalSize = FTPSUtil.getFolderSize(downloadPath);
					if (totalSize == 0) {
						return "1";
					}
					FTPSUtil.downloadFile(downloadPath, localPath, true);
				} else {
					return "1";
				}
			} catch (SSLException e) {
				if (FTPUtil.connect(PathUtil.ftpIp, PathUtil.ftpPort,
						PathUtil.ftpName, PathUtil.ftpPassword)) {
					currentSize = FTPUtil.getLocalFolderSize(file);
					totalSize = FTPUtil.getFolderSize(downloadPath);
					if (totalSize == 0) {
						return "1";
					}
					FTPUtil.downloadFile(downloadPath, localPath, true);
				} else {
					return "1";
				}
			}

		} catch (ConnectException e) {
			e.printStackTrace();
			return "-1";
		} catch (SocketException e) {
			e.printStackTrace();
			return "-1";
		} catch (IOException e) {
			e.printStackTrace();
			if (e.getMessage() != null) {
				if (e.getMessage().equals(
						"write failed: ENOSPC (No space left on device)")) {
					BasicActivity.resume_flag = true;
					return "3";
				}
			}
			return "-1";
		} finally {
			try {
				FTPSUtil.disconnect();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return "0";
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			if (localPath.contains("xbmc")) {
				progressDialog.setMessage(context
						.getString(R.string.updateXBMC));
			} else if (localPath.contains("watchdog")) {
				progressDialog.setMessage(context
						.getString(R.string.updateWatchdog));
			}
		}
		progressDialog.show();
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.equals("-1")) {
				checkWifi();
				return;
			}
			if (result.equals("0")) {
				progressDialog.dismiss();
				File apkfile = new File(localPath);
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
						"application/vnd.android.package-archive");
				context.startActivity(i);
				System.exit(0);
				return;
			}
			if (result.equals("1")) {
				progressDialog.dismiss();
				ShowErrorMessageUtil.showErrorMessageWithExit(context,
						context.getString(R.string.downloadFailed));
				return;
			}
			// sdCard is not enough
			if (result.equals("3")) {
				progressDialog.dismiss();
				ShowErrorMessageUtil.showErrorMessageWithExit(context,
						context.getString(R.string.sdcard));
				return;
			}
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
					progressDialog.dismiss();
					Toast.makeText(context, R.string.communication,
							Toast.LENGTH_LONG).show();
					checkWifi();
				}
			}, 4000);
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					new UpdateDownloadTask(context, progressDialog)
							.execute(downloadPath);
				}
			}, 4000);
		}
	}

	
}
