package com.matrix.brainsense.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPfUtil
 * 
 * @version 1.0
 * @author Jolina Zhou
 */
@SuppressLint("InlinedApi")
public class SharedPfUtil {
	private static String KEY = "brainsense";

	/**
	 * get value from sharedpreferences according to the key
	 * 
	 * @param context
	 * @param key
	 * @return String, the value get from sharedpreferences
	 */
	public static String getValue(Context context, String key) {

		SharedPreferences sp = context.getSharedPreferences(KEY,
				Context.MODE_MULTI_PROCESS);
		return sp.getString(key, "");

	}

	/**
	 * set value to sharedpreferences
	 * 
	 * @param context
	 * @param key
	 *            , the name of sharedpreferences
	 * @param value
	 *            , the value to be saved
	 */
	public static <T> void setValue(Context context, String key, T value) {

		SharedPreferences sp = context.getSharedPreferences(KEY,
				Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.putString(key, String.valueOf(value));
		editor.commit();

	}

	/**
	 * remove value according the key from the sharedpreferences
	 * 
	 * @param context
	 * @param key
	 */
	public static void removeValue(Context context, String key) {

		SharedPreferences sp = context.getSharedPreferences(KEY,
				Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();

	}

	/**
	 * check if the key is exist
	 * 
	 * @param context
	 * @param key
	 */
	public static boolean keyIsExist(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(KEY,
				Context.MODE_MULTI_PROCESS);
		return sp.contains(key);
	}

	/**
	 * remove all value from the sharedpreferences
	 * 
	 * @param context
	 * 
	 */
	public static void removeAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(KEY,
				Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.clear().commit();

	}

	/**
	 * get size of the sharedpreferences
	 * 
	 * @param context
	 * 
	 */
	public static int shareSize(Context context) {
		SharedPreferences sp = context.getSharedPreferences(KEY,
				Context.MODE_MULTI_PROCESS);
		return sp.getAll().size();
	}

}
