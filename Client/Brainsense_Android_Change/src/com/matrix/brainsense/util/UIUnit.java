package com.matrix.brainsense.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class UIUnit {
	public static void getAllChildViews(View view,int width,int height) {

		if (view instanceof ViewGroup) {

			ViewGroup vp = (ViewGroup) view;
			int count=vp.getChildCount();
			
			if(count==1){
				getAllChildViews(vp.getChildAt(0), width, height);
			}else{
			
				for (int i = 0; i < vp.getChildCount(); i++) {
					
					View v=vp.getChildAt(i);
					
					if(v instanceof LinearLayout||v instanceof RelativeLayout||v instanceof FrameLayout||v instanceof TableLayout||v instanceof ScrollView||v instanceof ListView){
						ViewGroup v2=(ViewGroup)v;
						int count2=v2.getChildCount();
						if(count2==1){
							setTheViewTextSize(v2.getChildAt(0),width,height);
						}else{
							getAllChildViews(v, width, height);
						}
					}else{
						setTheViewTextSize(v, width, height);
					}
				}
			}

		}

	}
	
	public static void setTheViewTextSize(View v, int width, int height){
		if(v instanceof TextView){
			((TextView) v).setTextSize(adjustFontSize(width, height));
		}else if(v instanceof EditText){
			((EditText) v).setTextSize(adjustFontSize(width, height));
		}else if(v instanceof Button){
			((Button) v).setTextSize(adjustFontSize(width, height));
		}
	}
	
	public static int adjustFontSize(int screenWidth, int screenHeight){  
	      
	    if (screenWidth <= 240) {        // 240X320 
	        return 8;  
	  
	    }else if (screenWidth <= 320){   // 320X480 
	        return 10;  
	  
	    }else if (screenWidth <= 480){   // 480X800 or 480X854  
	        return 12;  
	  
	    }else if (screenWidth <= 540){   // 540X960   
	        return 14;  
	          
	    }else if(screenWidth <= 800){    // 800X1280   
	        return 16;  
	          
	    }else{                          // big than 800X1280  
	        return 18;  
	          
	    }  
	}
}
