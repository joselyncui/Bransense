package com.matrix.brainsense.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint("DefaultLocale")
@SuppressWarnings("rawtypes")
public class ZipFileUtil {
	private static final int BUFFER = 1024;

	/**
	 * ZipDecompressing
	 * 
	 * @param fileName
	 * @param filePath
	 * @throws Exception
	 */
	public static boolean unZip(String fileName, String filePath,Context context) throws Exception {
		if(getSize(fileName, filePath)>ApplicationUtil.sdcAvailableSize()){
			return false;
		}
		String oldPath=filePath+"/plugin."+fileName.substring(fileName.lastIndexOf("/")+1, fileName.lastIndexOf("."));
		File oldFile=new File(oldPath);
		if(oldFile.exists()){
			FileUtil.delAllFile(oldPath);
		}
		org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(fileName,"gbk");
		Enumeration emu = zipFile.getEntries();
		while (emu.hasMoreElements()) {
			org.apache.tools.zip.ZipEntry entry = (org.apache.tools.zip.ZipEntry) emu.nextElement();
			if (entry.isDirectory()) {
				new File((filePath + entry.getName())).mkdirs();
				continue;
			}
			BufferedInputStream bis = new BufferedInputStream(
					zipFile.getInputStream(entry));
			File file = new File(filePath + entry.getName());
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);

			byte[] buf = new byte[BUFFER];
			int len = 0;
			while ((len = bis.read(buf, 0, BUFFER)) != -1) {
				fos.write(buf, 0, len);
			}
			bos.flush();
			bos.close();
			bis.close();

		}
		zipFile.close();
		File file = new File(fileName);
		File newFile =new File(fileName.replace(".zip", System.currentTimeMillis()+".zip"));
		if (file.exists()) {
			file.renameTo(newFile);
			newFile.delete();
		}
		return true;
	}

	
	@SuppressWarnings("resource")
	@SuppressLint("DefaultLocale")
	public static long getZipSize(String fileName, String filePath,
			String searchFileName){
		ZipFile zipFile = null;
		long size=0;
		try {
			zipFile = new ZipFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration emu = zipFile.entries();
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			if (entry.getName().toLowerCase().indexOf(searchFileName) != -1) {
				if (entry.getName().contains(".zip")) {
					size+=entry.getCompressedSize();
				}
			}
		}
		return size;
	}
	
	@SuppressWarnings("resource")
	@SuppressLint("DefaultLocale")
	public static long getSize(String fileName, String filePath){
		ZipFile zipFile = null;
		long size=0;
		try {
			zipFile = new ZipFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration emu = zipFile.entries();
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			size+=entry.getCompressedSize();
		}
		return size;
	}
	
	

}
