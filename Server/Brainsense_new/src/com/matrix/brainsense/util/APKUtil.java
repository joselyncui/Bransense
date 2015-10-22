package com.matrix.brainsense.util;

import com.sinaapp.msdxblog.apkUtil.entity.ApkInfo;

public class APKUtil {
	
	public static int getApkVersionCode(String path, String apkPath){
		String version = null;
		try {
			com.sinaapp.msdxblog.apkUtil.utils.ApkUtil apkUtil = new com.sinaapp.msdxblog.apkUtil.utils.ApkUtil();
			apkUtil.setmAaptPath(path + "/apkTool/aapt");
			ApkInfo apkInfo = apkUtil.getApkInfo(apkPath);
			version = apkInfo.getVersionCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.valueOf(version);
	}
	
	public static String getApkVersionName(String path, String apkPath){
		String version = null;
		try {
			com.sinaapp.msdxblog.apkUtil.utils.ApkUtil apkUtil = new com.sinaapp.msdxblog.apkUtil.utils.ApkUtil();
			apkUtil.setmAaptPath(path + "/apkTool/aapt");
			ApkInfo apkInfo = apkUtil.getApkInfo(apkPath);
			version = apkInfo.getVersionName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;
	}
	
}
