package com.matrix.brainsense.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matrix.brainsense.task.RegisterTask;
import com.matrix.brainsense.util.ApplicationUtil;
import com.matrix.brainsense.util.FileUtil;
import com.matrix.brainsense.util.PathUtil;
import com.matrix.brainsense.util.ShowErrorMessageUtil;
import com.matrix.brainsense.util.UIUnit;

/**
 * Gather device/user information
 * 
 * @author Jolina Zhou
 */
public class RegisterActivity extends QuitActivity {
	public static boolean REGISTER_FLAG=true;
	
	private EditText name;
	private EditText email;
	private EditText code1;
	private EditText code2;
	private EditText code3;
	private EditText code4;
	private Button register;

	private String username;
	private String useremail;
	private String keycode;
	private String keycode1;
	private String keycode2;
	private String keycode3;
	private String keycode4;

	private List<EditText> txts = new ArrayList<EditText>();
	private List<Integer> nums = new ArrayList<Integer>();
	
	private long lastClickTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		name = (EditText) findViewById(R.id.editname);
		email = (EditText) findViewById(R.id.editemail);
		code1 = (EditText) findViewById(R.id.code1);
		code2 = (EditText) findViewById(R.id.code2);
		code3 = (EditText) findViewById(R.id.code3);
		code4 = (EditText) findViewById(R.id.code4);

		code1.addTextChangedListener(new TextChangeListener(code1, 1));
		code2.addTextChangedListener(new TextChangeListener(code2, 2));
		code3.addTextChangedListener(new TextChangeListener(code3, 3));
		code4.addTextChangedListener(new TextChangeListener(code4, 4));

		code1.setOnKeyListener(new KeyListener(code1, 1));
		code2.setOnKeyListener(new KeyListener(code2, 2));
		code3.setOnKeyListener(new KeyListener(code3, 3));
		code4.setOnKeyListener(new KeyListener(code4, 4));

		txts.add(code1);
		txts.add(code2);
		txts.add(code3);
		txts.add(code4);

		nums.add(8);
		nums.add(4);
		nums.add(4);
		nums.add(4);

		register = (Button) findViewById(R.id.Register);

		register.setOnClickListener(new Register());
				
		FileUtil.delAllFile(PathUtil.dataPath);
		
		
		DisplayMetrics displayMetrics=new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int height=displayMetrics.heightPixels;
		int width=displayMetrics.widthPixels;
		if(width<height){
			int temp=height;
			height=width;
			width=temp;
		}
		
		//Set the header's layoutparams
		LinearLayout.LayoutParams header_params = new LinearLayout.LayoutParams(width, height);
		header_params.width = width;
		header_params.height = height/7;
        TextView header=(TextView) findViewById(R.id.header);
        header.setLayoutParams(header_params);
        
        
        //Set the headerimg's layoutparams
        LinearLayout.LayoutParams headerimg_params = new LinearLayout.LayoutParams(width, height);
        headerimg_params.width = width*4/5;
        headerimg_params.height = height/9;
        headerimg_params.gravity=Gravity.CENTER;
        ImageView headerimg=(ImageView) findViewById(R.id.headerimg);
        headerimg.setLayoutParams(headerimg_params);
        
        LinearLayout.LayoutParams layouttitle_params = new LinearLayout.LayoutParams(width, height);
        layouttitle_params.width = width/8;
        layouttitle_params.height = height/12;
        layouttitle_params.topMargin=width/55;
        layouttitle_params.leftMargin=width/55;
        ((TextView)findViewById(R.id.titlename)).setLayoutParams(layouttitle_params);
        ((TextView)findViewById(R.id.titleemail)).setLayoutParams(layouttitle_params);
        
        LinearLayout.LayoutParams layouttitle_params2 = new LinearLayout.LayoutParams(width, height);
        layouttitle_params2.width = width/6;
        layouttitle_params2.height = height/12;
        layouttitle_params2.topMargin=width/55;
        layouttitle_params2.leftMargin=width/55;
        ((TextView)findViewById(R.id.titlekeycode)).setLayoutParams(layouttitle_params2);
        
        LinearLayout.LayoutParams layoutname_params = new LinearLayout.LayoutParams(width, height);
        layoutname_params.width = width*4/5;
        layoutname_params.height = height/12;
        layoutname_params.topMargin=width/55;
        LinearLayout layoutname=(LinearLayout) findViewById(R.id.layoutname);
        layoutname.setLayoutParams(layoutname_params);
        
        LinearLayout.LayoutParams layoutemail_params = new LinearLayout.LayoutParams(width, height);
        layoutemail_params.width = width*4/5;
        layoutemail_params.height = height/12;
        layoutemail_params.topMargin=width/55;
        LinearLayout layoutemail=(LinearLayout) findViewById(R.id.layoutemail);
        layoutemail.setLayoutParams(layoutemail_params);
        
        LinearLayout.LayoutParams layoutcode_params = new LinearLayout.LayoutParams(width, height);
        layoutcode_params.width = width*1/6;
        layoutcode_params.height = height/12;
        layoutcode_params.topMargin=width/55;
        layoutcode_params.leftMargin=width/55;
        layoutcode_params.rightMargin=width/55;
        ((LinearLayout)findViewById(R.id.layoutcode1)).setLayoutParams(layoutcode_params);
        LinearLayout.LayoutParams layoutcode_params2 = new LinearLayout.LayoutParams(width, height);
        layoutcode_params2.width = width*1/10;
        layoutcode_params2.height = height/12;
        layoutcode_params2.topMargin=width/55;
        layoutcode_params2.leftMargin=width/55;
        layoutcode_params2.rightMargin=width/55;
        ((LinearLayout)findViewById(R.id.layoutcode2)).setLayoutParams(layoutcode_params2);
        ((LinearLayout)findViewById(R.id.layoutcode3)).setLayoutParams(layoutcode_params2);
        LinearLayout.LayoutParams layoutcode_params3 = new LinearLayout.LayoutParams(width, height);
        layoutcode_params3.width = width*1/10;
        layoutcode_params3.height = height/12;
        layoutcode_params3.topMargin=width/55;
        layoutcode_params3.leftMargin=width/55;
        ((LinearLayout)findViewById(R.id.layoutcode4)).setLayoutParams(layoutcode_params3);
        
        LinearLayout.LayoutParams registerbtn_params = new LinearLayout.LayoutParams(width, height);
        registerbtn_params.width = width*53/55;
        registerbtn_params.height = height/7;
        registerbtn_params.topMargin=width/55;
        registerbtn_params.leftMargin=width/55;
        registerbtn_params.rightMargin=width/55;
        registerbtn_params.bottomMargin=width/55;
        register.setLayoutParams(registerbtn_params);
        
        
        LinearLayout.LayoutParams footer_params = new LinearLayout.LayoutParams(width, height);
        footer_params.width = width/2;
        footer_params.height = height/8;
        footer_params.topMargin=width/55;
        footer_params.leftMargin=width*9/40;
        footer_params.rightMargin=width*9/40;
        footer_params.bottomMargin=width/55;
        ((ImageView)findViewById(R.id.footer)).setLayoutParams(footer_params);
        
        
        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.registlayout);
        UIUnit.getAllChildViews(linearLayout,width,height);

		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
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

	private final class TextChangeListener implements TextWatcher {
		EditText editText = null;
		int point = 0;

		public TextChangeListener(EditText editText, int point) {
			this.editText = editText;
			this.point = point;
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			if (editText.getText().length() < nums.get(point - 1)) {
				return;
			}
			if (editText.getText().length() == nums.get(point - 1)
					&& point != txts.size()) {
				txts.get(point).setEnabled(true);
				txts.get(point).requestFocus();
				txts.get(point)
						.setSelection(txts.get(point).getText().length());
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	}

	private final class KeyListener implements OnKeyListener {
		EditText editText;
		int point;

		public KeyListener(EditText editText, int point) {
			this.editText = editText;
			this.point = point;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.View.OnKeyListener#onKey(android.view.View, int,
		 * android.view.KeyEvent)
		 */
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (editText.getText().length() == nums.get(point - 1)
					&& keyCode != 67 && point != 4) {
				txts.get(point).setEnabled(true);
				txts.get(point).requestFocus();
			}
			if (editText.getText().length() == 0 && keyCode == 67 && point != 1) {
				txts.get(point - 2).setEnabled(true);
				txts.get(point - 2).requestFocus();
				txts.get(point - 2).setSelection(
						txts.get(point - 2).getText().length());
			}
			return false;
		}

	}

	class Register implements OnClickListener {

		@Override
		public void onClick(View v) {
			if((!REGISTER_FLAG)||isFastDoubleClick())
				return;
			
			REGISTER_FLAG=false;
			
			username = name.getText().toString().trim();
			useremail = email.getText().toString().trim();
			keycode1 = code1.getText().toString().trim();
			keycode2 = code2.getText().toString().trim();
			keycode3 = code3.getText().toString().trim();
			keycode4 = code4.getText().toString().trim();
			keycode = keycode1 + "-" + keycode2 + "-" + keycode3 + "-"
					+ keycode4;

			if (username.trim().length() == 0 || useremail.trim().length() == 0
					|| keycode.trim().length() == 3) {
				ShowErrorMessageUtil.showErrorMessage(RegisterActivity.this,
						getString(R.string.registerNull));
				REGISTER_FLAG=true;
				return;
			}
			if (!checkEmail(useremail)) {
				ShowErrorMessageUtil.showErrorMessage(RegisterActivity.this,
						getString(R.string.emailFormat));
				REGISTER_FLAG=true;
				return;
			}
			if (keycode1.trim().length() != 8 || keycode2.trim().length() != 4
					|| keycode4.trim().length() != 4
					|| keycode4.trim().length() != 4
					|| keycode.trim().length() != 23) {
				ShowErrorMessageUtil.showErrorMessage(RegisterActivity.this,
						getString(R.string.keyCodeLength));
				REGISTER_FLAG=true;
				return;
			}
			new RegisterTask(RegisterActivity.this).execute(username,useremail,keycode);
		}

	}

	/**
	 * Check whether the email format is correct
	 * 
	 * @param email
	 * @return boolean
	 */
	public boolean checkEmail(String email) {

		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public boolean isFastDoubleClick(){
		long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 1000) {   
            return true;   
        }  
        lastClickTime = time;   
        return false;  
	}

}
