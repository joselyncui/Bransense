package com.matrix.brainsense.task;

import android.content.Context;
import android.os.AsyncTask;

import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.HttpUtil;

public class ChangeStatusTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private String mac;
	
	public ChangeStatusTask(Context context) {
		this.context = context;
	}
	@Override
	protected String doInBackground(String... params) {
		mac = ApplicationUtil.getMACaddress(context);
		String uri=HttpUtil.getBaseUri()+"user/user/status?macAdd="+mac+"&type="+params[0];
		String result=HttpUtil.put(uri);
		return result;
	}

}
