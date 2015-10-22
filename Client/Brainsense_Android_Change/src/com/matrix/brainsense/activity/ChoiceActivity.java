package com.matrix.brainsense.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix.brainsense.task.ContentPathTask;
import com.matrix.brainsense.task.ShowIconTask;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.FileUtil;
import com.matrix.brainsense.util.HttpUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;
import com.matrix.brainsense.util.UIUnit;

/**
 * Select content
 * 
 * @author Jolina Zhou
 */
public class ChoiceActivity extends QuitActivity {

	public static final String IMAGE = "image";
	public static final String CHECKIMAGE = "checkimage";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";

	private Spinner countrySpinner;
	private Spinner categorySpinner;

	private String[] countries = null;
	private String[] categories = null;

	private ProgressDialog progressDialog;

	private Map<String, String> countryMap = new HashMap<String, String>();
	private Map<String, String> categoryMap = new HashMap<String, String>();
	private Map<String, Bitmap> bitMap = new HashMap<String, Bitmap>();

	private List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();

	private String country_Id = "0";
	private String category_Id = "0";

	private ListView mlistview;
	private MyAdapter myAdapter;
	private ProgressBar bar;
	private Button btn;

	private long allPackageSize = 0;
	private long freeSpace = 300 * 1024 * 1024;

	public static int btn_flag = 0;

	private long lastClickTime;

	private DisplayMetrics displayMetrics;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);

		countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);

		new CountryAndCategoryListTask().execute();

		countrySpinner.setOnItemSelectedListener(new CountrySpinnerListener());

		categorySpinner
				.setOnItemSelectedListener(new CategorySpinnerListener());

		mlistview = (ListView) findViewById(R.id.packagelist);

		myAdapter = new MyAdapter(this, contentList);
		mlistview.setAdapter(myAdapter);
		mlistview.setOnItemClickListener(new MlistviewListener());

		bar = (ProgressBar) findViewById(R.id.progressbar);
		bar.setVisibility(View.INVISIBLE);

		btn = (Button) findViewById(R.id.download);
		btn.setOnClickListener(new DownloadListener());

		displayMetrics = new DisplayMetrics();
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
		((TextView) findViewById(R.id.radio_1)).setLayoutParams(title_params);
		((TextView) findViewById(R.id.radio_2)).setLayoutParams(title_params);

		LinearLayout.LayoutParams spinner_params = new LinearLayout.LayoutParams(
				width, height);
		spinner_params.height = height / 9;
		spinner_params.gravity = Gravity.CENTER;
		spinner_params.bottomMargin = height / 55;
		spinner_params.topMargin = width / 55;
		countrySpinner.setLayoutParams(spinner_params);
		categorySpinner.setLayoutParams(spinner_params);

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

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.choicelayout);
		UIUnit.getAllChildViews(linearLayout, width, height);

	}

	@Override
	protected void onPause() {
		if (ApplicationUtil.isApplicationBroughtToBackground(this)) {
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

	public int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	class MyAdapter extends BaseAdapter {
		private Context context;
		private List<Map<String, Object>> mDataSource;

		public MyAdapter(Context context, List<Map<String, Object>> list) {
			this.context = context;
			this.mDataSource = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mDataSource == null) {
				return 0;
			}
			return mDataSource.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			if (mDataSource == null) {
				return new HashMap<String, Object>();
			}
			return mDataSource.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			if (convertView == null) {
				view = newView(position, parent);
			} else {
				view = convertView;
			}
			bindView(view, position);
			return view;
		}

		private View newView(int position, ViewGroup parent) {
			View view = null;
			LayoutInflater mInflater = LayoutInflater.from(context);
			ViewHolder mViewHolder = new ViewHolder();
			view = mInflater.inflate(R.layout.listview_items, parent, false);
			mViewHolder.imageView = (ImageView) view.findViewById(R.id.image);
			mViewHolder.titleView = (TextView) view.findViewById(R.id.title);
			mViewHolder.descriptionView = (TextView) view
					.findViewById(R.id.description);
			mViewHolder.checkImageView = (ImageView) view
					.findViewById(R.id.checkimage);
			view.setTag(mViewHolder);
			return view;
		}

		private void bindView(View view, int position) {
			final Map<String, Object> data = mDataSource.get(position);
			Bitmap image = (Bitmap) data.get(IMAGE);
			String str = (String) data.get(TITLE);
			String str2 = (String) data.get(DESCRIPTION);
			int checkimage = (Integer) data.get(CHECKIMAGE);
			final ViewHolder mViewHolder = (ViewHolder) view.getTag();
			mViewHolder.titleView.setText(str);
			mViewHolder.descriptionView.setText(str2);
			mViewHolder.checkImageView.setImageResource(checkimage);
			if (image == null) {
				String uri = HttpUtil.ImageUri() + data.get("iconPath");
				uri = uri.replace(" ", "%20").replace("\\", "/");
				int index = (uri.split("/").length) - 1;
				String imageName = uri.split("/")[index];
				new ShowIconTask(mViewHolder.imageView, imageName).execute(uri,
						str);
			} else {
				mViewHolder.imageView.setImageBitmap(image);
			}
		}

	}

	static class ViewHolder {
		ImageView imageView;
		ImageView checkImageView;
		TextView titleView;
		TextView descriptionView;
	}

	/**
	 * Get all countries and categories
	 * 
	 * @author Jolina Zhou
	 */
	class CountryAndCategoryListTask extends AsyncTask<String, Integer, String> {
		private String countryResult;
		private String categoryResult;

		@Override
		protected String doInBackground(String... params) {
			String uriCountry = HttpUtil.getBaseUri() + "user/countries";
			countryResult = HttpUtil.get(uriCountry);
			String uriCategory = HttpUtil.getBaseUri() + "user/categories";
			categoryResult = HttpUtil.get(uriCategory);
			if (countryResult.equals("-1") || categoryResult.equals("-1"))
				return "-1";
			return "0";
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("-1")) {
				Toast.makeText(ChoiceActivity.this, R.string.communication,
						Toast.LENGTH_LONG).show();
				checkWifi();
				return;
			}
			getCountries(countryResult);// parse country JSONArray
			getCategories(categoryResult);// parse category JSONArray
		}
	}

	private void getCountries(String result) {
		if (result.equals("null")) {
			countries = new String[] { "ALL" };
		} else {
			try {
				JSONObject jsonResult = new JSONObject(result);
				JSONArray jsonCountries = jsonResult.getJSONArray("country");
				for (int i = 0; i < jsonCountries.length(); i++) {
					JSONObject jsonCountry = (JSONObject) jsonCountries.get(i);
					parseCountries(jsonCountry);
				}
			} catch (JSONException e) {
				try {
					parseCountries(new JSONObject(result)
							.getJSONObject("country"));
				} catch (JSONException e1) {
					ShowErrorMessageUtil.showErrorMessage(ChoiceActivity.this,
							getString(R.string.serverException));
				}
			}
			Set<String> countrySet = countryMap.keySet();
			String strCountry = "ALL";
			for (String set : countrySet) {
				strCountry = strCountry + "," + set;
			}
			countries = strCountry.split(",");
		}

		final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, countries);
		countryAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		countrySpinner.setAdapter(countryAdapter);
		countrySpinner.setVisibility(View.VISIBLE);
		countrySpinner.setSelection(0);

	}

	// parse country JSONObject
	private void parseCountries(JSONObject jsonCountry) throws JSONException {
		String countryId = jsonCountry.getString("countryId");
		String countryName = jsonCountry.getString("name");
		countryMap.put(countryName, countryId);
	}

	private void getCategories(String result) {
		if (result.equals("null")) {
			categories = new String[] { "ALL" };
		} else {
			try {
				JSONObject jsonResult = new JSONObject(result);
				JSONArray jsonCountries = jsonResult.getJSONArray("category");
				for (int i = 0; i < jsonCountries.length(); i++) {
					JSONObject jsonCountry = (JSONObject) jsonCountries.get(i);
					parseCategories(jsonCountry);
				}
			} catch (JSONException e) {
				try {
					parseCategories(new JSONObject(result)
							.getJSONObject("category"));
				} catch (JSONException e1) {
					ShowErrorMessageUtil.showErrorMessage(ChoiceActivity.this,
							getString(R.string.serverException));
				}
			}
			Set<String> categorySet = categoryMap.keySet();
			String strCategory = "ALL";
			for (String set : categorySet) {
				strCategory = strCategory + "," + set;
			}
			categories = strCategory.split(",");
		}

		final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, categories);
		categoryAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(categoryAdapter);
		categorySpinner.setVisibility(View.VISIBLE);
		categorySpinner.setSelection(0);

	}

	// parse category JSONObject
	private void parseCategories(JSONObject jsonCategory) throws JSONException {
		String categoryId = jsonCategory.getString("categoryId");
		String categoryName = jsonCategory.getString("name");
		categoryMap.put(categoryName, categoryId);
	}

	/**
	 * Get content list by country id and category id
	 * 
	 * @author Jolina Zhou
	 */
	class ContentListTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String uri = HttpUtil.getBaseUri() + "user/contentpackages/"
					+ params[0] + "/" + params[1];
			String result = HttpUtil.get(uri);
			return result;
		}

		@Override
		protected void onPreExecute() {
			if (progressDialog == null) {
				progressDialog = new ProgressDialog(ChoiceActivity.this);
				progressDialog.setCancelable(false);
			}
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("-1")) {
				Toast.makeText(ChoiceActivity.this, R.string.communication,
						Toast.LENGTH_LONG).show();
				return;
			}
			getContent(result);// parse content JSONArray
		}

	}

	private void getContent(String result) {
		contentList.clear();
		if (result.equals("null")) {
			Toast.makeText(this, R.string.noPackage, Toast.LENGTH_LONG).show();
		} else {
			try {
				JSONObject jsonResult = new JSONObject(result);
				JSONArray jsonContents = jsonResult
						.getJSONArray("contentPackage");
				for (int i = 0; i < jsonContents.length(); i++) {
					JSONObject jsonContent = (JSONObject) jsonContents.get(i);
					parseContent(jsonContent);
				}
			} catch (JSONException e) {
				try {
					parseContent(new JSONObject(result)
							.getJSONObject("contentPackage"));
				} catch (JSONException e1) {
					ShowErrorMessageUtil.showErrorMessage(ChoiceActivity.this,
							getString(R.string.serverException));
				}
			}
		}
		progressDialog.dismiss();
		ViewGroup.LayoutParams params = mlistview.getLayoutParams();
		params.height = (Dp2Px(getApplicationContext(), 73))
				* (contentList.size());
		mlistview.setLayoutParams(params);
		myAdapter.notifyDataSetChanged();
	}

	// parse content JSONObject
	private void parseContent(JSONObject jsonContent) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CHECKIMAGE, R.drawable.check_false);
		map.put(TITLE, jsonContent.getString("name"));
		map.put(DESCRIPTION, jsonContent.getString("description"));
		map.put("packagePath",
				jsonContent.getString("packagePath").replace("\\", "/"));
		map.put("packageId", jsonContent.getString("disPackageId"));
		map.put("size", jsonContent.getString("size"));
		map.put("iconPath", jsonContent.getString("iconPath"));
		String imageUri = HttpUtil.ImageUri()
				+ jsonContent.getString("iconPath");
		imageUri = imageUri.replace(" ", "%20").replace("\\", "/");
		int index = (imageUri.split("/").length) - 1;
		String imageName = jsonContent.getString("name").replace("/", "_")
				+ "_" + imageUri.split("/")[index];
		map.put("imageName", imageName);
		new GetIconTask(imageName).execute(imageUri,
				jsonContent.getString("name"));
		map.put(IMAGE, bitMap.get(imageName));
		contentList.add(map);
	}

	/**
	 * Display icons of package choices
	 * 
	 * @author Jolina Zhou
	 */
	class GetIconTask extends AsyncTask<String, Integer, Bitmap> {

		String name = "";

		public GetIconTask(String name) {
			this.name = name;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected Bitmap doInBackground(String... urls) {
			String uri = urls[0].replace("\\", "/");
			return FileUtil.downloadImage(uri, name, urls[1]);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			getBitMap(name, bitmap);
		}
	}

	public void getBitMap(String key, Bitmap bit) {
		bitMap.put(key, bit);
	}

	/**
	 * Country spinner item selected listener
	 * 
	 * @author Jolina Zhou
	 */
	class CountrySpinnerListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (countryMap.containsKey(countries[arg2])) {
				country_Id = countryMap.get(countries[arg2]);
			} else {
				country_Id = "0";
			}
			contentList.clear();
			ViewGroup.LayoutParams params = mlistview.getLayoutParams();
			params.height = (Dp2Px(getApplicationContext(), 70)) * 1 + 30;
			mlistview.setLayoutParams(params);
			myAdapter.notifyDataSetChanged();
			resetSize();
			new ContentListTask().execute(country_Id, category_Id);

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Category spinner item selected listener
	 * 
	 * @author Jolina Zhou
	 */
	class CategorySpinnerListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (categoryMap.containsKey(categories[arg2])) {
				category_Id = categoryMap.get(categories[arg2]);
			} else {
				category_Id = "0";
			}
			contentList.clear();
			ViewGroup.LayoutParams params = mlistview.getLayoutParams();
			params.height = (Dp2Px(getApplicationContext(), 70)) * 1 + 30;
			mlistview.setLayoutParams(params);
			myAdapter.notifyDataSetChanged();
			resetSize();
			new ContentListTask().execute(country_Id, category_Id);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}

	}

	/**
	 * content list item click listener
	 * 
	 * @author Jolina Zhou
	 */
	class MlistviewListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Map<String, Object> item = contentList.get(position);
			int checkimage = (Integer) item.get(CHECKIMAGE);
			if (checkimage == R.drawable.check_false) {
				allPackageSize = allPackageSize
						+ Long.parseLong(item.get("size").toString());
				if (allPackageSize > (ApplicationUtil.sdcAvailableSize() - freeSpace)) {
					ShowErrorMessageUtil.showErrorMessage(ChoiceActivity.this,
							getString(R.string.contentLimit));
					allPackageSize = allPackageSize
							- Long.parseLong(item.get("size").toString());
					return;
				}

				for (Map<String, Object> map : contentList) {
					map.put(IMAGE, bitMap.get(map.get("imageName")));
				}
				item.put(CHECKIMAGE, R.drawable.check_true);
				btn_flag = 1;
				btn.setBackgroundResource(R.drawable.shape);

			} else {
				allPackageSize = allPackageSize
						- Long.parseLong(item.get("size").toString());
				item.put(CHECKIMAGE, R.drawable.check_false);
				if (allPackageSize == 0) {
					btn_flag = 0;
					btn.setBackgroundResource(R.drawable.shape2);
				}
			}
			myAdapter.notifyDataSetChanged();
		}

	}

	/**
	 * Download button click listener
	 * 
	 * @author Jolina Zhou
	 */
	class DownloadListener implements OnClickListener {

		@SuppressWarnings("unchecked")
		@Override
		public void onClick(View v) {
			if (isFastDoubleClick())
				return;
			if (btn_flag == 1) {
				countrySpinner.setEnabled(false);
				categorySpinner.setEnabled(false);
				mlistview.setEnabled(false);
				List<Map<String, String>> remotePathList = new ArrayList<Map<String, String>>();
				for (Map<String, Object> data : contentList) {
					if (R.drawable.check_true == Integer.parseInt(data.get(
							CHECKIMAGE).toString())) {
						Map<String, String> pathMap = new HashMap<String, String>();
						pathMap.put("name", data.get(TITLE).toString());
						pathMap.put("path", data.get("packagePath").toString());
						pathMap.put("size", data.get("size").toString());
						remotePathList.add(pathMap);
					}
				}
				bar.setVisibility(View.VISIBLE);
				bar.setIndeterminate(true);
				bar.setProgress(0);
				btn.setBackgroundResource(R.drawable.shape2);
				btn_flag = 2;
				btn.setText("Installing");
				new ContentPathTask(ChoiceActivity.this, bar, btn)
						.execute(remotePathList);
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
					Toast.makeText(ChoiceActivity.this, R.string.communication,
							Toast.LENGTH_LONG).show();
					checkWifi();
				}
			}, 4000);
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					new CountryAndCategoryListTask().execute();
				}
			}, 4000);
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

	public void refresh(String co_id, String ca_id) {
		new ContentListTask().execute(co_id, ca_id);
	}

	public void resetSize() {
		allPackageSize = 0;
		btn_flag = 0;
		btn.setBackgroundResource(R.drawable.shape2);
	}
}
