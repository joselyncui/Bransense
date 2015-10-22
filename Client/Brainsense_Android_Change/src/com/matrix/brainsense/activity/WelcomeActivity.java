package com.matrix.brainsense.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;

import com.matrix.brainsense.listener.QuitListener;
import com.matrix.brainsense.task.UpdateAPKTask;
import com.matrix.brainsense.task.UpdateDownloadTask;
import com.matrix.brainsense.task.UpdateXBMCTask;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.DialogUtil;
import com.matrix.brainsense.util.FileUtil;
import com.matrix.brainsense.util.PathUtil;
import com.matrix.brainsense.util.SharedPfUtil;

public class WelcomeActivity extends Activity {

	public static boolean exit_flag=false;
	public static boolean resume_flag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = this.getIntent();
		if (intent.getFlags() != 270532608) {
			Intent newIntent = new Intent(this, WelcomeActivity.class);
			newIntent.setFlags(270532608);
			this.finish();
			startActivity(newIntent);
			return;
		}

		setContentView(R.layout.activity_welcome);
		if(ApplicationUtil.xbmcExist(this,"org.xbmc.xbmc")){
			SharedPfUtil.removeValue(getApplicationContext(), "needUpdate");
		}

		checkWifi();
	}	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(resume_flag){
			resume_flag=false;
			FileUtil.rename(PathUtil.XBMCTempDataPath, PathUtil.XBMCDataPath);
			if(ApplicationUtil.xbmcExist(this,"org.xbmc.xbmc")){
				this.finish();
			}else{
				SharedPfUtil.setValue(getApplicationContext(), "needUpdate", true);
				new UpdateDownloadTask(this).execute(UpdateXBMCTask.packagePath
						.replace("\\", "/") + "/xbmc.apk");
			}
		}
		
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!exit_flag) {
				return false;
			} else {
				DialogUtil.dialogTwoButton(WelcomeActivity.this,
						getString(R.string.quit), getString(R.string.title),
						getString(R.string.positiveYes),
						getString(R.string.negativeNo), new QuitListener(this));
			}
		}
		return super.onKeyDown(keyCode, event);
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
			Toast.makeText(this, R.string.communication, Toast.LENGTH_LONG)
					.show();
			exit_flag=true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					checkWifi();
				}
			}, 4000);
		} else {
			exit_flag=false;
			new UpdateAPKTask(WelcomeActivity.this).execute();
		}
	}

}
