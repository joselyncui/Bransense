package com.matrix.brainsense.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.matrix.brainsense.activity.RegisterActivity;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.HttpUtil;

/**
 * Gather device information
 * 
 * @author Jolina Zhou
 */
public class GatherInforTask extends AsyncTask<String, Integer, String> {
	private Context context;

	private String mac;
	private String cpu;
	private String os;
	private String memory;
	private String sdCard;

	public GatherInforTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		mac = ApplicationUtil.getMACaddress(context);
		cpu = String.valueOf(ApplicationUtil.cpuCore());
		os = ApplicationUtil.getAndroidVersion();
		memory = String.valueOf(ApplicationUtil.getRomAvailableSize()
				/ (1024 * 1024));
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			sdCard = String.valueOf(ApplicationUtil.sdcAvailableSize()
					/ (1024 * 1024));
		} else {
			sdCard = "0";
		}
		String uri = HttpUtil.getBaseUri() + "user/userhardware?macAdd=" + mac
				+ "&cpu=" + cpu + "&os=" + os + "&memory=" + memory
				+ "&sdcard=" + sdCard;
		String result = HttpUtil.post(uri);

		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		RegisterActivity.REGISTER_FLAG = true;
	}

}
