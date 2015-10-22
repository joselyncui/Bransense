package com.matrix.brainsense.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix.brainsense.task.BasePathTask;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.HttpUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;
import com.matrix.brainsense.util.UIUnit;

/**
 * Download base package
 * 
 * @author Jolina Zhou
 */
public class BasicActivity extends QuitActivity {

	private Spinner language;
	private Spinner type;
	private String[] languages = null;
	private String[] types = null;

	private List<String> languageList = new ArrayList<String>();
	private Map<String, String> languageIdMap = new HashMap<String, String>();
	private List<String> typeList = new ArrayList<String>();
	private Map<String, String> typeIdMap = new HashMap<String, String>();

	private Button btn;
	private ProgressBar bar;

	public static int btn_flag = 1;
	public static boolean resume_flag = false;

	private long lastClickTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic);
		
		language = (Spinner) findViewById(R.id.languages);
		type = (Spinner) findViewById(R.id.types);

		new BaseLanguageTask().execute();

		bar = (ProgressBar) findViewById(R.id.progressbar);
		bar.setVisibility(View.INVISIBLE);

		btn = (Button) findViewById(R.id.downloadbase);
		btn.setOnClickListener(new DownloadListener());

		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int height = displayMetrics.heightPixels;
		int width = displayMetrics.widthPixels;
		if (width < height) {
			int temp = height;
			height = width;
			width = temp;
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

		LinearLayout.LayoutParams title_params = new LinearLayout.LayoutParams(
				width, height);
		title_params.width = width * 5 / 8;
		title_params.height = height / 12;
		title_params.topMargin = width / 55;
		title_params.leftMargin = width / 55;
		((TextView) findViewById(R.id.titlelanguage))
				.setLayoutParams(title_params);

		LinearLayout.LayoutParams language_params = new LinearLayout.LayoutParams(
				width, height);
		language_params.height = height / 9;
		language_params.gravity = Gravity.CENTER;
		language_params.bottomMargin = height / 55;
		language_params.topMargin = width / 55;
		language.setLayoutParams(language_params);

		LinearLayout.LayoutParams progressbar_params = new LinearLayout.LayoutParams(
				width, height);
		progressbar_params.width = width * 53 / 55;
		progressbar_params.height = height / 9;
		progressbar_params.topMargin = width / 55;
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
		footer_params.topMargin = width / 55;
		footer_params.gravity = Gravity.CENTER;
		footer_params.bottomMargin = width / 55;
		((ImageView) findViewById(R.id.footer)).setLayoutParams(footer_params);

		((TextView) findViewById(R.id.titletype)).setLayoutParams(title_params);
		LinearLayout.LayoutParams type_params = new LinearLayout.LayoutParams(
				width, height);
		type_params.height = height / 9;
		type_params.gravity = Gravity.CENTER;
		type_params.bottomMargin = height / 55;
		type_params.topMargin = width / 55;
		type.setLayoutParams(type_params);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.basiclayout);
		UIUnit.getAllChildViews(linearLayout, width, height);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		if (btn_flag == 2 && resume_flag) {
			resume_flag = false;
			btn_flag = 1;
			btn.setBackgroundResource(R.drawable.shape);
			btn.setText(R.string.downLoadButton);
		}
		super.onResume();
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
	
	/**
	 * Get language list and type list
	 * 
	 * @author Jolina Zhou
	 */
	class BaseLanguageTask extends AsyncTask<String, Integer, String> {
		private String languageResult;
		private String typeResult;

		@Override
		protected String doInBackground(String... params) {

			String uriLanguage = HttpUtil.getBaseUri() + "user/languages";
			languageResult = HttpUtil.get(uriLanguage);
			String uriType = HttpUtil.getBaseUri() + "user/types";
			typeResult = HttpUtil.get(uriType);
			if (languageResult.equals("-1") || typeResult.equals("-1"))
				return "-1";
			return "0";
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("-1")) {
				checkWifi();
				return;
			}
			getLanguage();//parse language json
			getType();//parse type json
		}

		private void getLanguage() {
			if (languageResult.equals("null")) {
				languages = new String[] { getString(R.string.chooseLanguage) };
			} else {
				try {
					JSONObject jsonRsesult = new JSONObject(languageResult);
					JSONArray jsonList = jsonRsesult.getJSONArray("language");
					for (int i = 0; i < jsonList.length(); i++) {
						JSONObject jsonLanguage = (JSONObject) jsonList.get(i);
						parseLanguage(jsonLanguage);
					}
				} catch (JSONException e) {
					try {
						parseLanguage(new JSONObject(languageResult)
								.getJSONObject("language"));
					} catch (JSONException e1) {
						ShowErrorMessageUtil.showErrorMessage(
								BasicActivity.this,
								getString(R.string.serverException));
					}
				}
				String strLanguage = getString(R.string.chooseLanguage);
				for (String set : languageList) {
					strLanguage = strLanguage + "," + set;
				}
				languages = strLanguage.split(",");
			}
			
			final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
					BasicActivity.this, android.R.layout.simple_spinner_item,
					languages);
			adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			language.setAdapter(adapter1);
			language.setVisibility(View.VISIBLE);
			language.setSelection(0);
		}

		private void parseLanguage(JSONObject jsonLanguage)
				throws JSONException {
			String name = jsonLanguage.getString("name");
			String id = jsonLanguage.getString("languageId");
			languageList.add(name);
			languageIdMap.put(name, id);
		}

		private void getType() {
			if (typeResult.equals("null")) {
				types = new String[] { getString(R.string.chooseType) };
			} else {
				try {
					JSONObject jsonRsesult = new JSONObject(typeResult);
					JSONArray jsonList = jsonRsesult.getJSONArray("type");
					for (int i = 0; i < jsonList.length(); i++) {
						JSONObject jsonLanguage = (JSONObject) jsonList.get(i);
						parseType(jsonLanguage);
					}
				} catch (JSONException e) {
					try {
						parseType(new JSONObject(typeResult)
								.getJSONObject("type"));
					} catch (JSONException e1) {
						ShowErrorMessageUtil.showErrorMessage(
								BasicActivity.this,
								getString(R.string.serverException));
					}
				}
				String strType = getString(R.string.chooseType);
				for (String set : typeList) {
					strType = strType + "," + set;
				}
				types = strType.split(",");
			}
			final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
					BasicActivity.this, android.R.layout.simple_spinner_item,
					types);
			adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			type.setAdapter(adapter2);
			type.setVisibility(View.VISIBLE);
			type.setSelection(0);
		}

		private void parseType(JSONObject jsonLanguage) throws JSONException {
			String name = jsonLanguage.getString("name");
			String id = jsonLanguage.getString("typeId");
			typeList.add(name);
			typeIdMap.put(name, id);
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
			Toast.makeText(this, R.string.communication, Toast.LENGTH_LONG)
					.show();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					checkWifi();
				}
			}, 4000);
		} else {
			new BaseLanguageTask().execute();
		}
	}
	
	/**
	 * Download button OnClickListener
	 * 
	 * @author Jolina Zhou
	 */
	class DownloadListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (isFastDoubleClick())
				return;
			if (btn_flag == 1) {
				if (language.getSelectedItemPosition() != 0
						&& type.getSelectedItemPosition() != 0) {
					btn_flag = 2;
					String languageId = languageIdMap.get(language
							.getSelectedItem().toString());
					String typeId = typeIdMap.get(type.getSelectedItem()
							.toString());
					btn.setBackgroundResource(R.drawable.shape2);
					btn.setText(R.string.installButton);
					bar.setIndeterminate(true);
					bar.setVisibility(View.VISIBLE);
					new GetBasePackagePathTask().execute(languageId, typeId);
				} else {
					ShowErrorMessageUtil.showErrorMessage(BasicActivity.this,
							getString(R.string.noChooseLanguage));
				}
				return;
			}
			if (btn_flag == 3) {
				//download success
				btn_flag = 4;
				Intent intent = new Intent(BasicActivity.this,
						XBMCActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				BasicActivity.this.finish();
				return;
			}
		}

	}

	
	/**
	 * Get base package path
	 * 
	 * @author Jolina Zhou
	 */
	class GetBasePackagePathTask extends AsyncTask<String, Integer, String> {
		private String languageId;
		private String typeId;

		@Override
		protected String doInBackground(String... params) {
			languageId = params[0];
			typeId = params[1];
			String uri = HttpUtil.getBaseUri() + "user/base/" + languageId
					+ "/" + typeId;
			return HttpUtil.get(uri);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("-1")) {
				checkWifi();
				return;
			}
			getBasePackage(result);//parse base package json
		}

		private void getBasePackage(String result) {
			if (result.equals("null")) {
				ShowErrorMessageUtil.showErrorMessage(BasicActivity.this,
						getString(R.string.noPackage));
				btn_flag = 1;
				btn.setBackgroundResource(R.drawable.shape);
				btn.setText(R.string.downLoadButton);
				bar.setVisibility(View.INVISIBLE);
			} else {
				try {
					JSONObject basePackage = new JSONObject(result);
					if(!basePackage.has("packagePath")){
						//no base package of this language and type
						ShowErrorMessageUtil.showErrorMessage(BasicActivity.this,
								getString(R.string.noPackage));
						btn_flag = 1;
						btn.setBackgroundResource(R.drawable.shape);
						btn.setText(R.string.downLoadButton);
						bar.setVisibility(View.INVISIBLE);
						return;
					}
					
					String packagePath = basePackage.getString("packagePath").replace("\\", "/");
					String packageSize = basePackage.getString("size");
					new BasePathTask(BasicActivity.this, bar, btn)
							.execute(packagePath, languageId + "_" + typeId,
									packageSize);
				} catch (JSONException e) {
					ShowErrorMessageUtil.showErrorMessage(
							BasicActivity.this,
							getString(R.string.serverException));
					btn_flag = 1;
					btn.setBackgroundResource(R.drawable.shape);
					btn.setText(R.string.downLoadButton);
					bar.setVisibility(View.INVISIBLE);
				}

			}
		}
		
		/**
		 * Check whether the device is connected to the Internet
		 * 
		 * @author Jolina Zhou
		 */
		private void checkWifi() {
			ConnectivityManager manger = (ConnectivityManager) BasicActivity.this
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manger.getActiveNetworkInfo();
			boolean flag = (info != null && info.isConnected());
			if (!flag) {
				Toast.makeText(BasicActivity.this, R.string.communication, Toast.LENGTH_LONG)
						.show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						checkWifi();
					}
				}, 4000);
			} else {
				new GetBasePackagePathTask().execute(languageId, typeId);
			}
		}

	}

	//Judge double-click events
	public boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
