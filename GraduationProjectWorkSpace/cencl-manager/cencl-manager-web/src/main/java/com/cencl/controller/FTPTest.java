package com.cencl.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.cencl.common.utils.FtpUtil;

public class FTPTest {
	public void testFTPTest() throws Exception{
		//鍒涘缓涓�涓狥TPClient瀵硅薄
		FTPClient ftpClient = new FTPClient();
		
		//鍒涘缓FTP杩炴帴
		ftpClient.connect("192.168.1.223",21);

//		ftpClient.connect("192.168.43.188",21);
		
		//鐧诲綍FTP鏈嶅姟鍣紝浣跨敤鐢ㄦ埛鍚嶅拰瀵嗙爜
		ftpClient.login("ftpuser", "ftpuser");
		
		
		//璇诲彇鏈湴鏂囦欢
		FileInputStream fileInputStream = new FileInputStream(new File("E:\\9.png"));
		
		//淇敼涓婁紶鏂囦欢鐨勬牸寮�
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		//璁剧疆storeFile涓婁紶鐨勮矾寰�
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		
		//涓婁紶鏂囦欢(remote锛氭湇鍔″櫒绔瓨鏀剧殑鏂囦欢鍚�;local锛氫笂浼犳枃浠剁殑inputStream)
		ftpClient.storeFile("helloWorld.jpg", fileInputStream);
		
		//鍏抽棴杩炴帴
		ftpClient.logout();
		
	}
	
	public void testFTPUtils() throws Exception{
		//璇诲彇鏈湴鏂囦欢
		FileInputStream fileInputStream = new FileInputStream(new File("E:\\9.png"));
		
		FtpUtil.uploadFile("192.168.1.223", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "/2017/11/26", "hello.jpg", fileInputStream);
		
//		FtpUtil.uploadFile("192.168.43.188", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "/2017/11/26", "hello.jpg", fileInputStream);

		
	}
	
}
