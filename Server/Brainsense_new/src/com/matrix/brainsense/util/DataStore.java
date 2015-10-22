package com.matrix.brainsense.util;

import java.io.File;
import java.util.ResourceBundle;

public class DataStore {
	
	private static ResourceBundle rb = ResourceBundle.getBundle("FTPPath");
	
	private static String FTP_URL = null;
	private static String FTP_PATH = null;
	private static String FTP_USERNAME = null;
	private static String FTP_PASSWORD = null;
	
	public static String getFtpUrl(){
		if(FTP_URL == null) {
			FTP_URL = rb.getString("ftp_url");
		}
		return FTP_URL;
	}
	
	public static String getFtpPath(){
		if(FTP_PATH == null) {
			FTP_PATH = rb.getString("ftp_path");
		}
		return FTP_PATH;
	}
	
	public static String getFtpUsername(){
		if(FTP_USERNAME == null){
			FTP_USERNAME = rb.getString("username");
		}
		return FTP_USERNAME;
	}
	
	public static String getFtpPassword(){
		if(FTP_PASSWORD == null){
			FTP_PASSWORD = rb.getString("password");
		}
		return FTP_PASSWORD;
	}
	
	public static String getXBMCName(){
		return "xbmc.apk";
	}
	
	// XBMC relative path: /XBMC/xbmc.apk
	public static String getRelativeXBMCPath(String versionName){
		return File.separator + "XBMC" + File.separator + versionName;
	}
	
	// XBMC save path: D:/.../XBMC/xbmc.apk
	public static String getXBMCPath(String versionName){
		return getFtpPath() + getRelativeXBMCPath(versionName);
	}
	
	
	public static String getTmpStructurePath(String path) {
		return new File(path).getParentFile().getAbsolutePath() + File.separator + "tmp";
	}
	
	public static String getStructurePath(String path){
		File file = new File(path);
		return file.getParentFile().getAbsolutePath() + File.separator + file.getName() + ".zip";
	}
	
	public static String getRelativeWatchdogPath(String version){
		return File.separator + "Watchdog" + File.separator + version;
	}
	
	public static String getWatchdogName(){
		return "watchdog.apk";
	}
	
	public static String getWatchdogPath(String version){
		return getFtpPath() + getRelativeWatchdogPath(version);
	}
	
}
