package com.matrix.brainsense.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

/***
 * ApplicationUtil
 * 
 * @author Jolina Zhou
 */
@SuppressWarnings("deprecation")
public class ApplicationUtil {

	/**
	 * get package name
	 * 
	 * @param context
	 * @return String, package path
	 */
	@SuppressLint("SdCardPath")
	public static String getPackageName(Context context) {
		PackageInfo packageInfo = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "/data/data/" + packageInfo.packageName;
	}

	/**
	 * check whether the sdCard is exist
	 * 
	 * @return Boolean
	 */
	public static boolean sdcExist() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * get the versionCode of this application
	 * 
	 * @param context
	 * @return String,version
	 */
	public static int getVersion(Context context) {
		PackageInfo packageInfo = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo.versionCode;
	}
	
	/**
	 * get the versionName of this application
	 * 
	 * @param context
	 * @return String,version
	 */
	public static String getVersionName(Context context) {
		PackageInfo packageInfo = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo.versionName;
	}

	/**
	 * get sdcard path
	 * 
	 * @return String, sdcard path
	 */
	public static String sdcPath() {
		File sdc = Environment.getExternalStorageDirectory();
		return sdc.getPath();
	}

	/**
	 * get device id
	 * 
	 * @param context
	 * @return String, device id
	 */
	public static String DeviceId(Context context) {
		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		UUID deviceUUID = new UUID(
				("" + android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID))
						.hashCode(),
				((long) ("" + telephonyMgr.getDeviceId()).hashCode() << 32)
						| ("" + telephonyMgr.getSimSerialNumber()).hashCode());

		return deviceUUID.toString();
	}

	/**
	 * get AvailMemory
	 * 
	 * @param context
	 * @return String Memory
	 */
	public static String availMemory(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		return Formatter.formatFileSize(context, mi.availMem);
	}

	/**
	 * get total Memory
	 * 
	 * @param context
	 * @return String Memory
	 */
	public static String totalMemory(Context context) {
		long totalMemory = 0;
		try {
			FileReader localFileReader = new FileReader("/proc/meminfo");
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			totalMemory = Integer.valueOf(
					localBufferedReader.readLine().split("\\s+")[1]).intValue() * 1024;
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return Formatter.formatFileSize(context, totalMemory);
	}

	/**
	 * get Rom Total size
	 * 
	 * @param context
	 * @return String Rom
	 */
	@SuppressLint("NewApi")
	public static Long getRomTotalSize(Context context) {
		File path = Environment.getDataDirectory();
		return path.getTotalSpace();
	}

	/**
	 * get Rom Available size
	 * 
	 * @param context
	 * @return String Rom
	 */
	@SuppressLint("NewApi")
	public static Long getRomAvailableSize() {
		File path = Environment.getDataDirectory();
		return path.getUsableSpace();
	}

	/**
	 * get sdCard total size
	 * 
	 * @param context
	 * @return String sdcSize
	 */
	public static Long sdcSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs statfs = new StatFs(path.getPath());
		long blocSize = statfs.getBlockSize();
		long totalBlocks = statfs.getBlockCount();
		return blocSize * totalBlocks;

	}

	/**
	 * get sdCard Available size
	 * 
	 * @param context
	 * @return String sdcAvailableSize
	 */
	public static Long sdcAvailableSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs statfs = new StatFs(path.getPath());
		long blocSize = statfs.getBlockSize();
		long availaBlock = statfs.getAvailableBlocks();
		return blocSize * availaBlock;

	}

	/**
	 * get cpuCore
	 * 
	 * @return int cpuCore
	 */
	public static int cpuCore() {
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}
		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new CpuFilter());
			return files.length;
		} catch (Exception e) {
			return 1;
		}
	}

	/**
	 * get Android Version
	 * 
	 * @return String Version
	 */
	public static String getAndroidVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * get device MAC address
	 * 
	 * @param systemService
	 * @return String MACaddress
	 */
	public static String getMACaddress(Context context) {
		
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();

	}
	
	/**
	 * Clean cache
	 * 
	 * @param context
	 */
	public static void deleteCache(Context context){
		if (context.getCacheDir() != null && context.getCacheDir().exists() && context.getCacheDir().isDirectory()) {
            for (File item : context.getCacheDir().listFiles()) {
                item.delete();
            }
        }
	}
	
	public static int getAppVersion(Context context,String appName){
		PackageManager manager = context.getApplicationContext().getPackageManager();
		List<PackageInfo> list = manager
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo info : list) {
			if (info.packageName.equals(appName)) {
				return info.versionCode;
			}
		}
		return -1;
	}
	
	/**
	 * check the application is in background or not
	 * @param context
	 * @return
	 */
	public static boolean isApplicationBroughtToBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
	
	
	/**
	 * Judge weather xbmc is exist
	 * 
	 * @param packageName
	 * @return boolean, true if exist else false
	 */
	public static boolean xbmcExist(Context context,String packageName) {
		PackageManager packageManager = context.getApplicationContext().getPackageManager();
		boolean flag = false;

		List<ApplicationInfo> list = packageManager.getInstalledApplications(0);
		for (ApplicationInfo info : list) {
			if (info.packageName.equals(packageName)) {
				flag = true;
			}
		}
		return flag;
	}
	
}
