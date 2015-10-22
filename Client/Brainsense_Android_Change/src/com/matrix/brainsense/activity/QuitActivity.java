package com.matrix.brainsense.activity;

import android.app.Activity;
import android.view.KeyEvent;

import com.matrix.brainsense.listener.QuitListener;
import com.matrix.brainsense.util.DialogUtil;

public class QuitActivity extends Activity {
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DialogUtil.dialogTwoButton(QuitActivity.this,
					getString(R.string.quit), getString(R.string.title),
					getString(R.string.positiveYes),
					getString(R.string.negativeNo), new QuitListener(this));
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
