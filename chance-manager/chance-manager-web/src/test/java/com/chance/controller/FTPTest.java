package com.chance.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.chance.common.utils.FtpUtil;

public class FTPTest {
	@Test
	public void testFtpClient() throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.25.129", 21);
		ftpClient.login("ftpuser", "ftpuser");
		// 设置上传路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		// 修改文件上传格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

		// 设置本地文件流
		FileInputStream inputStream = new FileInputStream(new File("E:\\壁纸\\0cf5d1c707a3a832207d4c7dc27beeae.jpg"));
		// 上传文件，服务器端文档名，上传文档的inputStream
		ftpClient.storeFile("1.jpg", inputStream);
		ftpClient.logout();
	}

	@Test
	public void testFtpUtil() throws Exception {
		// 设置本地文件流
		FileInputStream inputStream = new FileInputStream(new File("E:\\壁纸\\0cf5d1c707a3a832207d4c7dc27beeae.jpg"));
		FtpUtil.uploadFile("192.168.25.129", 21, "ftpuser", "ftpuser", 
				"/home/ftpuser/www/images", "/TempImg", "1.jpg", inputStream);
	}
}
