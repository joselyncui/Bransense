package com.matrix.brainsense.util;

public class PathUtil {
	// // FTP Like
	// public static String ftpIp = "192.168.1.80";
	// public static int ftpPort = 21;
	// public static String ftpName = "like";
	// public static String ftpPassword = "like";

	// // FTP cloud
	// public static String ftpIp = "115.29.15.61";
	// public static int ftpPort = 21;
	// public static String ftpName = "Brainsense";
	// public static String ftpPassword = "Brainsense2014";

	// FTP IL cloud
	public static String ftpIp = "212.150.144.25";
	public static int ftpPort = 21;
	public static String ftpName = "matrixglobal";
	public static String ftpPassword = "12345678";

	// client file path
	public static String dataPath = ApplicationUtil.sdcPath() + "/Brainsense";
	public static String XBMCPath = ApplicationUtil.sdcPath()
			+ "/Brainsense/download";
	public static String iconPath = ApplicationUtil.sdcPath()
			+ "/Brainsense/icon";
	public static String XBMCDataPath = ApplicationUtil.sdcPath()
			+ "/Android/data/org.xbmc.xbmc";
	public static String XBMCTempDataPath = ApplicationUtil.sdcPath()
			+ "/Android/data/org.xbmc.xbmc.temp";
}
