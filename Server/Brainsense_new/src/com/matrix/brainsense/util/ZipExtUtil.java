package com.matrix.brainsense.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.like.likeutils.file.FileUtil;

public class ZipExtUtil {
	
	public static void unZip(String sourcePath, String targetPath, String name, String charset) 
			throws IOException{
		ZipFile zipFile = new ZipFile(sourcePath);
		Enumeration<ZipEntry> emu = (Enumeration<ZipEntry>) zipFile.getEntries();
		while(emu.hasMoreElements()){
			ZipEntry entry = emu.nextElement();
			if (entry.isDirectory()){
				FileUtil.createDir(targetPath + File.separator + entry.getName());
                continue;
            } else {
            	new File(targetPath + File.separator + entry.getName()).getParentFile().mkdirs();
            }
            BufferedInputStream bin = new BufferedInputStream(zipFile.getInputStream(entry));
            BufferedOutputStream bout = new BufferedOutputStream(
            		new FileOutputStream(targetPath + File.separator + entry.getName()));
            byte [] buf = new byte[1024 * 4];
            int len = 0;
            while((len = bin.read(buf))!=-1){
            	bout.write(buf,0,len);  
            }
            bout.flush();
            FileUtil.closeStream(bin, bout);
		}
		zipFile.close();
	}
	
}
