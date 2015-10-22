package com.matrix.brainsense.task;

import android.content.Context;
import android.os.AsyncTask;

import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.HttpUtil;

public class LogOutTask extends AsyncTask<String, Integer, String> {
	private Context context;
	
	public LogOutTask(Context context) {
		this.context = context;
	}
	

	@Override
	protected String doInBackground(String... params) {
		String mac = ApplicationUtil.getMACaddress(context);
		String uri = HttpUtil.getBaseUri() + "user/user/logout/" + mac;
		return HttpUtil.put(uri);
	}
	

	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			// log out error
			if (result.equals("false")||result.equals("-1")) {
				new LogOutTask(context).execute();
				return;
			}
		}
		super.onPostExecute(result);
	}

}
