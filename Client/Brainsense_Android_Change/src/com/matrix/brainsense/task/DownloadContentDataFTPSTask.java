package com.matrix.brainsense.task;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.matrix.brainsense.activity.BasicActivity;
import com.matrix.brainsense.activity.ChoiceActivity;
import com.matrix.brainsense.activity.R;
import com.matrix.brainsense.util.FTPSUtil;
import com.matrix.brainsense.util.FTPUtil;
import com.matrix.brainsense.util.PathUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;
import com.matrix.brainsense.util.ZipFileUtil;

public class DownloadContentDataFTPSTask extends
		AsyncTask<List<Map<String, String>>, Integer, String> {

	private Context context;
	private ProgressBar progressBar;
	private Button btn;
	private String pathContent;

	private long totalSize;

	private String path;
	private String zipPath;

	private int progress = 0;
	private boolean thread_flag = false;

	private List<Map<String, String>> remotePath = new ArrayList<Map<String, String>>();

	public DownloadContentDataFTPSTask(Context context,
			ProgressBar progressBar, Button btn, String pathContent) {
		super();
		this.context = context;
		this.progressBar = progressBar;
		this.btn = btn;
		this.pathContent = pathContent;
	}

	@Override
	protected String doInBackground(List<Map<String, String>>... params) {
		remotePath = params[0];
		if (remotePath.size() == 0) {
			return "2";
		}
		path = remotePath.get(0).get("path");
		zipPath = pathContent + "/packages"
				+ path.substring(path.lastIndexOf("/"));
		try {
			try {
				if (FTPSUtil.connect(PathUtil.ftpIp, PathUtil.ftpPort,
						PathUtil.ftpName, PathUtil.ftpPassword)) {
					totalSize=FTPSUtil.getFolderSize(path);
					if (totalSize == 0) {
						return "false";
					}
					FTPSUtil.getLocalFolderSize(new File(zipPath));
					if (!thread_flag) {
						thread_flag = true;
						Thread myThread = new myThread();
						myThread.start();
					}
					FTPSUtil.downloadFile(path, pathContent + "/packages",
							false);
				} else {
					return "false";
				}
			} catch (SSLException e) {
				if (e.getMessage() != null) {
					if (e.getMessage().contains(
							"502 SSL/TLS authentication not allowed"))
						if (FTPUtil.connect(PathUtil.ftpIp, PathUtil.ftpPort,
								PathUtil.ftpName, PathUtil.ftpPassword)) {
							totalSize=FTPUtil.getFolderSize(path);
							if (totalSize == 0) {
								return "false";
							}
							FTPUtil.getLocalFolderSize(new File(zipPath));
							if (!thread_flag) {
								thread_flag = true;
								Thread myThread = new myThread();
								myThread.start();
							}
							FTPUtil.downloadFile(path, pathContent
									+ "/packages", false);
						} else {
							return "false";
						}
					if (e.getMessage().contains("Connection timed out"))
						return "-1";
				}
				return "-1";
			}
		} catch (ConnectException e) {
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
					return "1";
				}
			}
			return "-1";
		} finally {
			try {
				FTPSUtil.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "0";
	}

	@Override
	protected void onPreExecute() {
		progressBar.setIndeterminate(false);
		super.onPreExecute();
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (progressBar != null) {
			progressBar.setProgress(values[0]);
		}
	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			// download failed
			if (result.equals("false")) {
				thread_flag=false;
				((Activity) context).findViewById(R.id.countrySpinner)
						.setEnabled(true);
				((Activity) context).findViewById(R.id.categorySpinner)
						.setEnabled(true);
				ChoiceActivity.btn_flag = 0;
				btn.setBackgroundResource(R.drawable.shape2);
				btn.setText(R.string.downLoadButton);
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.downloadFailed));
				return;
			}
			// Download all package finished
			if (result.equals("2")) {
				thread_flag=false;
				reset();
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.finish));

				new ChangeStatusTask(context).execute("content");
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
			// download the current package zip success
			if (result.equals("0")) {
				// unzip current package zip file
				thread_flag=false;
				new unZipFileTask().execute();
				return;

			}
		}
		super.onPostExecute(result);
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

	class unZipFileTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				Thread.sleep(1000);
				return ZipFileUtil.unZip(zipPath, pathContent + "/", context);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Boolean result) {
			if (result == null) {
				reset();
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.installFailed));
			} else if (result) {
				remotePath.remove(0);
				progressBar.setProgress(0);
				new DownloadContentDataFTPSTask(context, progressBar, btn,
						pathContent).execute(remotePath);

			} else {
				ShowErrorMessageUtil.showErrorMessage(context,
						context.getString(R.string.sdcard));
			}
			super.onPostExecute(result);
		}

	}

	private void reset() {
		ChoiceActivity choice = (ChoiceActivity) context;
		Spinner countrySpinner = (Spinner) choice
				.findViewById(R.id.countrySpinner);
		int co_id = countrySpinner.getSelectedItemPosition();
		countrySpinner.setEnabled(true);
		countrySpinner.setSelection(0);
		Spinner categorySpinner = (Spinner) choice
				.findViewById(R.id.categorySpinner);
		int ca_id = categorySpinner.getSelectedItemPosition();
		categorySpinner.setEnabled(true);
		categorySpinner.setSelection(0);
		ListView mlistview = (ListView) choice.findViewById(R.id.packagelist);
		choice.resetSize();
		if (ca_id == 0 && co_id == 0) {
			choice.refresh(String.valueOf(co_id), String.valueOf(ca_id));
		}
		mlistview.setEnabled(true);
		progressBar.setVisibility(View.INVISIBLE);
		progressBar.setProgress(0);
		btn.setText(R.string.downLoadButton);
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
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					new DownloadContentDataFTPSTask(context, progressBar, btn,
							pathContent).execute(remotePath);
				}
			}, 4000);
		}
	}

}
