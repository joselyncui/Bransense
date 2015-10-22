package com.matrix.brainsense.task;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;

import javax.net.ssl.SSLException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.matrix.brainsense.activity.BasicActivity;
import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.util.FTPSUtil;
import com.matrix.brainsense.util.FTPUtil;
import com.matrix.brainsense.util.PathUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;
import com.matrix.brainsense.util.ZipFileUtil;

public class DownLoadBaseDataFTPSTask extends
		AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressBar progressBar;
	private Button btn;
	private String size;

	private long totalSize = 0;

	private String remotePath;
	private String pathBase;
	private String zipPath;

	private int progress = 0;
	private boolean thread_flag = false;

	public DownLoadBaseDataFTPSTask() {
		super();
	}

	public DownLoadBaseDataFTPSTask(Context context, ProgressBar progressBar,
			Button btn) {
		super();
		this.context = context;
		this.progressBar = progressBar;
		this.btn = btn;
	}

	@Override
	protected String doInBackground(String... params) {
		remotePath = params[0];
		pathBase = params[1];
		size = params[2];
		zipPath = pathBase + "/packages"
				+ remotePath.substring(remotePath.lastIndexOf("/"));
		try {
			try {
				if (FTPSUtil.connect(PathUtil.ftpIp, PathUtil.ftpPort,
						PathUtil.ftpName, PathUtil.ftpPassword)) {
					totalSize=FTPSUtil.getFolderSize(remotePath);
					if (totalSize == 0) {
						return "false";
					}
					FTPSUtil.getLocalFolderSize(new File(zipPath));
					if (!thread_flag) {
						thread_flag = true;
						Thread myThread = new myThread();
						myThread.start();
					}
					FTPSUtil.downloadFile(remotePath, pathBase + "/packages",
							false);
				} else {
					return "false";
				}
			} catch (SSLException e) {
				if (e.getMessage() != null) {
					if (e.getMessage().contains(
							"502 SSL/TLS authentication not allowed")) {
						if (FTPUtil.connect(PathUtil.ftpIp, PathUtil.ftpPort,
								PathUtil.ftpName, PathUtil.ftpPassword)) {
							totalSize=FTPUtil.getFolderSize(remotePath);
							if (totalSize == 0) {
								return "false";
							}
							FTPUtil.getLocalFolderSize(new File(zipPath));
							if (!thread_flag) {
								thread_flag = true;
								Thread myThread = new myThread();
								myThread.start();
							}
							FTPUtil.downloadFile(remotePath, pathBase
									+ "/packages", false);

						} else {
							return "false";
						}
					}
					if (e.getMessage().contains("Connection timed out"))
						return "-1";
				}
				return "-1";
			}
		} catch (ConnectException e) {
			return "-1";
		} catch (SSLException e) {

		} catch (SocketException e) {
			e.printStackTrace();
			return "-1";
		} catch (IOException e) {
			e.printStackTrace();
			if (e.getMessage() != null) {
				if (e.getMessage().equals(
						"write failed: ENOSPC (No space left on device)")) {
					BasicActivity.resume_flag = true;
					return "1";
				}
			}
			return "-1";
		} finally {
			try {
				FTPSUtil.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "0";
	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			// download failed
			if (result.equals("false")) {
				thread_flag=false;
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.downloadFailed));
				return;
			}
			// Communication problems
			if (result.equals("-1")) {
				thread_flag=false;
				checkWifi();
				return;
			}
			// sdCard is not enough
			if (result.equals("1")) {
				thread_flag=false;
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.sdcard));
				return;
			}
			// download the base package zip file success
			if (result.equals("0")) {
				thread_flag=false;
				// unzip the zipfile
				publishProgress(progress);
				new unZipFileTask().execute();
				return;
			}
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		progressBar.setIndeterminate(false);
		progressBar.setProgress(0);
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (progressBar != null) {
			progressBar.setProgress(values[0]);
		}
	}

	class unZipFileTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				Thread.sleep(1000);
				return ZipFileUtil.unZip(zipPath, pathBase + "/", context);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result == null) {
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.installFailed));
				progressBar.setVisibility(View.INVISIBLE);
				BasicActivity.btn_flag = 1;
				btn.setText(R.string.downLoadButton);
				btn.setBackgroundResource(R.drawable.shape);
			} else if (result) {
				new ChangeStatusTask(context).execute("base");
				progressBar.setVisibility(View.INVISIBLE);
				BasicActivity.btn_flag = 3;
				btn.setText(R.string.nextButton);
				btn.setBackgroundResource(R.drawable.shape);

			} else {
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.sdcard));
			}
			super.onPostExecute(result);
		}

	}

	class myThread extends Thread {
		@Override
		public void run() {
			while (true) {
				progress = (int) ((FTPSUtil.currentSize * 1.0 / totalSize) * 10000);
				publishProgress(progress);
				if (progress == 10000 || (!thread_flag)) {
					break;
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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
					new DownLoadBaseDataFTPSTask(context, progressBar, btn)
							.execute(remotePath, pathBase, size);
				}
			}, 4000);
		}
	}
}
