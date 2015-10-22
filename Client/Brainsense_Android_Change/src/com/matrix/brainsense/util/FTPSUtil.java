package com.matrix.brainsense.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class FTPSUtil {

	private static FTPSClient ftpClient;
	public static long currentSize = 0;

	public static boolean connect(String hostName, int port, String username,
			String password) throws SocketException, IOException {
		ftpClient = new FTPSClient(false);
		ftpClient.connect(hostName, port);
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.setSoTimeout(6000);
		if (ftpClient.login(username, password)) {
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				return true;
			}
		}
		disconnect();
		return false;
	}

	public static void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	public static void deleteFile(String remoteFilePath) throws IOException {
		ftpClient.deleteFile(remoteFilePath);
	}

	public static void deleteFolder(String remoteFolderPath) throws IOException {
		FTPFile[] files = ftpClient.listFiles(new String(remoteFolderPath
				.getBytes("UTF-8"), "iso-8859-1"));
		for (int i = 0; i < files.length; i++) {
			String tmpRemoteFolderPath = remoteFolderPath + File.separator
					+ files[i].getName();
			if (files[i].isFile()) {
				deleteFile(tmpRemoteFolderPath);
			} else {
				deleteFolder(tmpRemoteFolderPath);
			}
		}
		ftpClient.removeDirectory(remoteFolderPath);
	}

	private static void CreateDirecroty(String remotePath) throws IOException {
		if (remotePath.equals("/")) {
			return;
		}
		String[] allPath = remotePath.split("/");
		for (int i = 0; i < allPath.length; i++) {
			StringBuilder tmpPath = new StringBuilder();
			for (int j = 0; j <= i; j++) {
				tmpPath.append("/");
				tmpPath.append(allPath[j]);
			}
			ftpClient.makeDirectory(tmpPath.toString());
			tmpPath.delete(0, tmpPath.length());
		}
	}

	public static void uploadFile(String remoteFolderPath, String localFilePath)
			throws IOException {
		CreateDirecroty(remoteFolderPath);
		File localFile = new File(localFilePath);
		FileInputStream in = new FileInputStream(localFile);
		ftpClient.changeWorkingDirectory(remoteFolderPath);
		ftpClient.setBufferSize(1024);
		ftpClient.storeFile(localFile.getName(), in);
		in.close();
	}

	public static void uploadFolder(String remoteFolderPath,
			String localFolderPath) throws IOException {
		CreateDirecroty(remoteFolderPath);
		File localFolderFile = new File(localFolderPath);
		File[] files = localFolderFile.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				uploadFile(remoteFolderPath, file.getAbsolutePath());
			} else {
				String tmpRemoteFolderPath = remoteFolderPath + File.separator
						+ file.getName();
				String tmpLocalFolderPath = localFolderPath + File.separator
						+ file.getName();
				uploadFolder(tmpRemoteFolderPath, tmpLocalFolderPath);
			}
		}
	}

	public static void downloadFolder(String remoteFolderPath,
			String localFolderPath) throws IOException {
		new File(localFolderPath).mkdirs();
		String remoteFolderPathTmp;
		String localFolderPathTmp;
		FTPFile[] files = ftpClient.listFiles(new String(remoteFolderPath
				.getBytes("UTF-8"), "iso-8859-1"));
		for (int i = 0; i < files.length; i++) {
			FTPFile file = files[i];
			if (file.isDirectory()) {
				localFolderPathTmp = localFolderPath + File.separator
						+ file.getName();
				new File(localFolderPathTmp).mkdirs();
				remoteFolderPathTmp = remoteFolderPath + File.separator
						+ file.getName();
				downloadFolder(remoteFolderPathTmp, localFolderPathTmp);
			} else {
				downloadFile(
						remoteFolderPath + File.separator + file.getName(),
						localFolderPath, false);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void downloadFolderByDir(String zipPath, String localPath,
			String remotePath) throws IOException {
		new File(localPath).mkdirs();
		ZipFile zipFile = new ZipFile(zipPath, "gbk");
		Enumeration emu = zipFile.getEntries();
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			if (entry.isDirectory()) {
				new File((localPath + entry.getName())).mkdirs();
				continue;
			}
			File file = new File(localPath + entry.getName());
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			downloadFile(remotePath + entry.getName(),
					localPath + entry.getName(), true);

		}
		zipFile.close();
	}

	public static void downloadFile(String remoteFilePath,
			String localFilePath, boolean flag) throws IOException {
		FTPFile[] files = ftpClient.listFiles(new String(remoteFilePath
				.getBytes("UTF-8"), "iso-8859-1"));
		if (files.length != 1) {
			throw new FileNotFoundException();
		}
		long remoteFileSize = files[0].getSize();
		if (!flag) {
			File file = new File(localFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			localFilePath = localFilePath + File.separator + files[0].getName();
		}
		File localFile = new File(localFilePath);
		if (localFile.exists()) {
			continueDownload(remoteFilePath, remoteFileSize, localFile);
		} else {
			newFileDownload(remoteFilePath, localFile);
		}

	}

	private static void newFileDownload(String remote, File f)
			throws FileNotFoundException, IOException,
			UnsupportedEncodingException {
		OutputStream out = new FileOutputStream(f);
		InputStream in = ftpClient.retrieveFileStream(new String((remote)
				.getBytes("UTF-8"), "iso-8859-1"));
		byte[] bytes = new byte[1024];
		int c;
		while ((c = in.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			currentSize = currentSize + c;
		}
		in.close();
		out.close();
		try {
			ftpClient.completePendingCommand();
		} catch (Exception e) {

		}

	}

	private static void continueDownload(String remote, long lRemoteSize,
			File localFile) throws FileNotFoundException, IOException,
			UnsupportedEncodingException {
		long localSize = localFile.length();
		if (localSize >= lRemoteSize) {
			return;
		}
		FileOutputStream out = new FileOutputStream(localFile, true);
		ftpClient.setRestartOffset(localSize);
		InputStream in = ftpClient.retrieveFileStream(new String((remote)
				.getBytes("UTF-8"), "iso-8859-1"));
		byte[] bytes = new byte[1024];
		int c;
		while ((c = in.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localSize += c;
			currentSize = currentSize + c;
		}
		in.close();
		out.close();
		try {
			ftpClient.completePendingCommand();
		} catch (Exception e) {

		}
	}

	public static long getFolderSize(String remoteFolderPath)
			throws UnsupportedEncodingException, IOException {
		long size = 0;
		FTPFile[] files = ftpClient.listFiles(new String(remoteFolderPath
				.getBytes("UTF-8"), "iso-8859-1"));
		for (FTPFile file : files) {
			String tmpRemoteFolderPath = remoteFolderPath + File.separator
					+ file.getName();
			if (file.isDirectory()) {
				size += getFolderSize(tmpRemoteFolderPath);
			} else {
				size += file.getSize();
			}
		}
		return size;
	}

	public static long getLocalFolderSize(File file) {
		long size = 0;
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					size=size+getLocalFolderSize(files[i]);
				}
			}else{
				size=size+file.length();
			}
		}
		currentSize = size;
		return size;
	}
}
