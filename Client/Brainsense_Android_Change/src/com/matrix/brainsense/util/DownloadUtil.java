package com.matrix.brainsense.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.content.Context;

/**
 * download file
 * 
 * @version 1.0
 * @author Jolina Zhou
 */
public class DownloadUtil {
	private static final int TIMEOUT = 10 * 1000;
	/**
	 * download file from the downUrl to saveUrl
	 * 
	 * @param downUrl
	 * @param saveUrl
	 * @return boolean,return true if download success else false
	 * @throws Exception
	 */
	public static long downloadUpdateFile(String downUrl, File saveFile,
			boolean isAppend ,Context context,String type) {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		long readLength = 0;
		URL url = null;
		
		HttpURLConnection con = null;
		try {
			downUrl=downUrl.replace(" ", "%20");
			url = new URL(downUrl);

			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(TIMEOUT);
			con.setReadTimeout(TIMEOUT);
			
			Map<String, List<String>> headers = con.getHeaderFields();
			if(con.getHeaderFields()!=null){
				if(con.getHeaderFields().get("Content-Disposition")==null)
					return -1;
				String fileName = headers.get("Content-Disposition").get(0)
						.split("; ")[1].split("=")[1];
				File file = new File(saveFile.getPath() + "/" + fileName);
				
				if(!SharedPfUtil.keyIsExist(context, type)){
					file.delete();
				}
				SharedPfUtil.setValue(context, type, saveFile.getPath() + "/" + fileName);
				
				inputStream = con.getInputStream();
				outputStream = new FileOutputStream(file, isAppend);
				byte buffer[] = new byte[1024];
				int readsize = 0;
				while ((readsize = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, readsize);
					readLength += readsize;
				}
			}else{
				return -2;
			}
		} catch (IOException e) {
			return -3;
		} finally {
			con.disconnect();
			try {
				if(outputStream!=null){
					outputStream.close();
				}
				if(inputStream!=null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return -3;
			}
		}

		return readLength;
	}
}
