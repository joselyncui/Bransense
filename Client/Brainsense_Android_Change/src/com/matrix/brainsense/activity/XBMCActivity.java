package com.matrix.brainsense.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix.brainsense.task.ChangeStatusTask;
import com.matrix.brainsense.task.DownloadXBMCFTPSTask;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.DialogUtil;
import com.matrix.brainsense.util.FileUtil;
import com.matrix.brainsense.util.HttpUtil;
import com.matrix.brainsense.util.PathUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;
import com.matrix.brainsense.util.UIUnit;

/**
 * Download XBMC
 * 
 * @author Jolina Zhou
 */
public class XBMCActivity extends QuitActivity {
	private Button btn;
	private ProgressBar bar;
	private int backshape = 1;

	private long lastClickTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xbmc);

		bar = (ProgressBar) findViewById(R.id.progressbar);
		bar.setVisibility(View.INVISIBLE);

		btn = (Button) findViewById(R.id.downloadxbmc);
		btn.setOnClickListener(new DownloadListener());

		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int height = displayMetrics.heightPixels;
		int width = displayMetrics.widthPixels;
		if(width<height){
			int temp=height;
			height=width;
			width=temp;
		}

		// Set the header's layoutparams
		LinearLayout.LayoutParams header_params = new LinearLayout.LayoutParams(
				width, height);
		header_params.width = width;
		header_params.height = height / 7;
		TextView header = (TextView) findViewById(R.id.header);
		header.setLayoutParams(header_params);

		// Set the headerimg's layoutparams
		LinearLayout.LayoutParams headerimg_params = new LinearLayout.LayoutParams(
				width, height);
		headerimg_params.width = width * 4 / 5;
		headerimg_params.height = height / 9;
		headerimg_params.gravity = Gravity.CENTER;
		ImageView headerimg = (ImageView) findViewById(R.id.headerimg);
		headerimg.setLayoutParams(headerimg_params);

		LinearLayout.LayoutParams progressbar_params = new LinearLayout.LayoutParams(
				width, height);
		progressbar_params.width = width * 53 / 55;
		progressbar_params.height = height / 9;
		progressbar_params.topMargin = width * 6 / 55;
		progressbar_params.bottomMargin = width / 55;
		progressbar_params.gravity = Gravity.CENTER;
		((LinearLayout) findViewById(R.id.layoutprogressbar))
				.setLayoutParams(progressbar_params);

		LinearLayout.LayoutParams downloadbasebtn_params = new LinearLayout.LayoutParams(
				width, height);
		downloadbasebtn_params.width = width * 53 / 55;
		downloadbasebtn_params.height = height / 7;
		downloadbasebtn_params.topMargin = width / 55;
		downloadbasebtn_params.gravity = Gravity.CENTER;
		downloadbasebtn_params.bottomMargin = width / 55;
		btn.setLayoutParams(downloadbasebtn_params);

		LinearLayout.LayoutParams footer_params = new LinearLayout.LayoutParams(
				width, height);
		footer_params.width = width / 2;
		footer_params.height = height / 8;
		footer_params.gravity = Gravity.CENTER;
		footer_params.bottomMargin = width / 55;
		((ImageView) findViewById(R.id.footer)).setLayoutParams(footer_params);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.xbmclayout);
		UIUnit.getAllChildViews(linearLayout, width, height);

		
	}
	
	@Override
	protected void onPause() {
		if(ApplicationUtil.isApplicationBroughtToBackground(this)){
			Intent intent = new Intent();  
	        intent.setAction("back");
	        this.sendBroadcast(intent);
		}
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (ApplicationUtil.xbmcExist(this,"org.xbmc.xbmc")) {
			bar.setVisibility(View.INVISIBLE);
			btn.setBackgroundResource(R.drawable.shape);
			backshape = 3;
			btn.setText(R.string.nextButton);
			DialogUtil.dialogOneButton(this, getString(R.string.XBMCExist),
					getString(R.string.title), getString(R.string.ok),
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new ChangeStatusTask(XBMCActivity.this)
									.execute("xbmc");
							FileUtil.delAllFile(PathUtil.XBMCPath);
							Intent intent = new Intent(XBMCActivity.this,
									ChoiceActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(intent);
							XBMCActivity.this.finish();
						}
					});

		}
	}

	

	class DownloadListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (isFastDoubleClick())
				return;
			if (backshape == 1) {
				bar.setVisibility(View.VISIBLE);
				btn.setBackgroundResource(R.drawable.shape2);
				backshape = 2;
				btn.setText(R.string.downloadingButton);
				new GetXBMCPathTask().execute();
			} else if (backshape == 3) {
				Intent intent = new Intent(XBMCActivity.this,
						ChoiceActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				XBMCActivity.this.finish();
			}
		}
	}

	public boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	class GetXBMCPathTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String uri = HttpUtil.getBaseUri() + "user/xbmc";
			return HttpUtil.get(uri);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				if (result.equals("-1")) {
					Toast.makeText(XBMCActivity.this, R.string.communication,
							Toast.LENGTH_LONG).show();
					checkWifi();
				} else {
					try {
						JSONObject jsonXBMC = new JSONObject(result);
						String packagePath = jsonXBMC.getString("packagePath");
						bar.setVisibility(View.VISIBLE);
						new DownloadXBMCFTPSTask(getApplicationContext(), bar)
								.execute(packagePath.replace("\\", "/")
										+ "/xbmc.apk");
					} catch (JSONException e) {
						ShowErrorMessageUtil.showErrorMessageWithExit(
								XBMCActivity.this, XBMCActivity.this
										.getString(R.string.serverException));
					}
				}
			} else {
				ShowErrorMessageUtil.showErrorMessage(XBMCActivity.this,
						XBMCActivity.this.getString(R.string.noXBMC));
			}
		}

	}

	/**
	 * Check whether the device is connected to the Internet
	 * 
	 * @author Jolina Zhou
	 */
	private void checkWifi() {
		ConnectivityManager manger = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manger.getActiveNetworkInfo();
		boolean flag = (info != null && info.isConnected());
		if (!flag) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(XBMCActivity.this, R.string.communication,
							Toast.LENGTH_LONG).show();
					checkWifi();
				}
			}, 4000);
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					backshape = 1;
					btn.setBackgroundResource(R.drawable.shape);
					btn.setText(R.string.downLoadButton);
				}
			}, 4000);

		}
	}

}
