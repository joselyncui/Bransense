package com.matrix.brainsense.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

/**
 * FileUtil
 * 
 * @version 1.0
 * @author Jolina Zhou
 */
public class FileUtil {

	/**
	 * download image
	 * 
	 * @param urlString
	 * @param name
	 * @return
	 */
	public static Bitmap downloadImage(String urlString, String name ,String urlChange) {

		URL url;
		String path = PathUtil.iconPath + name;
		if (ApplicationUtil.sdcExist() && isFileExist(path)) {
			return BitmapFactory.decodeFile(path);
		}
		try {
			String[] urlChanges=urlChange.split(" ");
			for(int i=0;i<urlChanges.length;i++){
				if(urlChanges[i].length()!=0){
					urlString=urlString.replace(urlChanges[i], URLEncoder.encode(urlChanges[i], "utf-8").replace("%2F", "/"));
				}
			}
			url=new URL(urlString);
			HttpURLConnection httpCon = (HttpURLConnection) url
					.openConnection();

			InputStream input = httpCon.getInputStream();

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead = 0;
			byte[] data = new byte[2048];

			while ((nRead = input.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			byte[] image = buffer.toByteArray();
			Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,
					image.length);
			if (ApplicationUtil.sdcExist() && !isFileExist(path)) {
				copyImageToCard(bitmap, name);
			}
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * check if the file is exist
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean isFileExist(String path) {

		File f = new File(path);
		return f.exists();

	}

	/**
	 * copy image to sdCard
	 * 
	 * @param bitmap
	 * @param name
	 */
	public static void copyImageToCard(Bitmap bitmap, String name) {

		String path = PathUtil.iconPath;
		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File file = new File(path, name);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (name.endsWith(".png")) {
				bitmap.compress(CompressFormat.PNG, 100, out);
			} else if (name.endsWith(".jpg")) {
				bitmap.compress(CompressFormat.JPEG, 100, out);
			}

			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * delete all files under the specified folder
	 * 
	 * @param path
	 * @return boolen
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		File to=new File(file.getAbsolutePath()+System.currentTimeMillis());
		file.renameTo(to);
		String[] tempList = to.list();
		path=to.getAbsolutePath();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				flag = true;
			}
		}
		to.delete();
		return flag;
	}
	
	/**
	 * copy files from folder a to folder b
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return boolen
	 */
	public static boolean copyFolder(String oldPath, String newPath) {
		try {
			File oldFile=new File(oldPath);
			if(!oldFile.exists())
				return true;
			if(getLargestSize(oldFile)>ApplicationUtil.sdcAvailableSize())
				return false;
			(new File(newPath)).mkdirs();
			String[] file = oldFile.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
					temp.delete();
				}
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
			delAllFile(oldPath);
			return true;
		} catch (Exception e) {
		}

		return false;

	}
	
	/**
	 * get the largest file's size in the folder
	 * 
	 * @param file
	 * @return long
	 */
	public static long getLargestSize(File file){
		long size=0;
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					long temp=getLargestSize(files[i]);
					if(size<=temp){
						size = temp;
					}	
				} else {
					if(size<=files[i].length()){
						size = files[i].length();
					}
				}
			}
		}
		return size;
	}
	
	public static long getFolderSize(File file) {
		long size = 0;
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					size = size + getFolderSize(files[i]);
				} else {
					size = size + files[i].length();
				}
			}
		}
		return size;
	}
	
	/**
	 * rename the folder
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static void rename(String oldPath,String newPath){
		File oldFile=new File(oldPath);
		File newFile=new File(newPath);
		oldFile.renameTo(newFile);
	}
	
}
