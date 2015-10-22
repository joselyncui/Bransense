package com.matrix.brainsense.task;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.matrix.brainsense.activity.BasicActivity;
import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.util.FTPSUtil;
import com.matrix.brainsense.util.FTPUtil;
import com.matrix.brainsense.util.PathUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;

public class DownloadXBMCTask extends AsyncTask<String, Integer, String> {

	private Context context;
	private ProgressBar progressBar;

	public static long totalSize = 0;
	public static long currentSize = 0;

	private String downloadXBMCPath;
	private String localXBMCPath;

	private boolean thread_flag = false;

	public DownloadXBMCTask(Context context, ProgressBar progressBar) {
		this.context = context;
		this.progressBar = progressBar;
	}

	@Override
	protected String doInBackground(String... params) {
		downloadXBMCPath = params[0];
		localXBMCPath = PathUtil.XBMCPath
				+ downloadXBMCPath.substring(0,
						downloadXBMCPath.lastIndexOf("/"));
		try {
			if (FTPUtil.connect(PathUtil.ftpIp, PathUtil.ftpPort,
					PathUtil.ftpName, PathUtil.ftpPassword)) {
				currentSize = FTPUtil
						.getLocalFolderSize(new File(localXBMCPath));
				totalSize = FTPUtil.getFolderSize(downloadXBMCPath);
				if (totalSize == 0) {
					return "1";
				}
				if(!thread_flag){
					thread_flag=true;
					Thread myThread = new myThread();
					myThread.start();
				}
				
				FTPUtil.downloadFile(downloadXBMCPath, localXBMCPath, false);
			} else {
				return "1";
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
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.equals("-1")) {
				thread_flag = false;
				checkWifi();
				return;
			}
			if (result.equals("0")) {
				File apkfile = new File(localXBMCPath + "/XBMC.apk");
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
						"application/vnd.android.package-archive");
				context.startActivity(i);
				return;
			}
			if (result.equals("1")) {
				thread_flag = false;
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.downloadFailed));
				return;
			}
			// sdCard is not enough
			if (result.equals("3")) {
				thread_flag = false;
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.sdcard));
				return;
			}
		}

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (progressBar != null) {
			progressBar.setProgress(values[0]);
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
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					new DownloadXBMCTask(context, progressBar)
							.execute(downloadXBMCPath);
				}
			}, 4000);
		}
	}

	class myThread extends Thread {
		@Override
		public void run() {
			while (true) {
				int progress = (int) ((FTPUtil.currentSize * 1.0 / totalSize) * 10000);
				publishProgress(progress);
				if (progress == 10000 || (!thread_flag)) {
					break;
				}
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
