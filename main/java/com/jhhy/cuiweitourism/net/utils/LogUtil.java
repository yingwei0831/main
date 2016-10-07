package com.jhhy.cuiweitourism.net.utils;


import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogUtil {
	
	private final String PATH = ""+ Environment.getExternalStorageDirectory().getPath();

	//true 发布版本。
	static boolean isRelease = false;
	public static void i(String tag, String msg) {
		if (isRelease){//存入日志文件
//			saveString(tag, msg);
			return;
		}else{
			Log.i(tag, msg);
		}
	}
	
	/**
	 * 将日志存入文件
	 * @param tag	日志标签
	 * @param msg	日志内容
	 */
	private static void saveString(String tag, String msg) {
		// TODO Auto-generated method stub
		String name = "";
		String path = getSDPath();
		if(path == null) return;
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File logFile = null;
		if(file.isDirectory()){
			logFile = new File(file, name);
		}
		if(logFile == null){
			return;
		}
		FileOutputStream fos = null;
		try {
			 fos = new FileOutputStream(new File(file, name));
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(fos == null){
			return;
		}
		byte[] buffer = new byte[1024];
		try {
			while( buffer.length != 0){
				fos.write(buffer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 获取手机SD卡目录
	 * @return
	 */
	public static String getSDPath() {
		boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (hasSDCard) {
			return Environment.getExternalStorageDirectory().toString();
		} else {
			return Environment.getDownloadCacheDirectory().toString();
		}
	}
	public static void i(String tag, Object msg) {
		i(tag, String.valueOf(msg));
	}
	
	public static void v(String tag, String msg){
		if(isRelease){
			return;
		}else{
			Log.v(tag, msg);
		}
	}
	
	public static void v(String tag, Object msg){
		v(tag, String.valueOf(msg));
	}
	
	public static void w(String tag, String msg){
		if(isRelease){
			return;
		}else{
			Log.w(tag, msg);
		}
	}
	
	public static void w(String tag, Object msg){
		w(tag, String.valueOf(msg));
	}
	
	public static void d(String tag, String msg){
		if(isRelease){
			return;
		}else{
			Log.d(tag, msg);
		}
	}
	
	public static void d(String tag, Object msg){
		d(tag, String.valueOf(msg));
	}
	
	public static void e(String tag, String msg){
		if(isRelease){
			return;
		}else{
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, Object msg){
		e(tag, String.valueOf(msg));
	}

}
