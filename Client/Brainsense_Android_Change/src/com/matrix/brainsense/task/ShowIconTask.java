package com.matrix.brainsense.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.matrix.brainsense.util.FileUtil;
/**
 * Display icons of package choices
 * 
 * @version 1.0
 * @author Jolina Zhou
 */
public class ShowIconTask extends AsyncTask<String, Integer, Bitmap> {

	ImageView image = null;
	String name = "";

	public ShowIconTask(ImageView image, String name) {
		this.image = image;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		image.setVisibility(View.GONE);
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Bitmap doInBackground(String... urls) {
		String uri=urls[0].replace("\\", "/");
		return FileUtil.downloadImage(uri, name,urls[1]);
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		image.setVisibility(View.VISIBLE);
		if (bitmap == null) {
			return;
		}
		image.setImageBitmap(bitmap);
	}
	 

}
