package com.jhhy.cuiweitourism.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;


import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class  ImageLoaderUtil {
	
	private static final String TAG = ImageLoaderUtil.class.getSimpleName();
	private static ImageLoaderUtil instance;// = new ImageLoaderUtil();
	private Context mContext;
	private ImageLoaderCallBack mCallback;

	public static String IMG_PATH = "";

	/**
	 * 构造方法
	 * @param context
	 */
	private ImageLoaderUtil(Context context) {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			IMG_PATH = Environment.getExternalStorageDirectory()+ File.separator + "cuiweitourism/"; //下载图片路径
		}else{
			return;
		}
		File cachePath = new File(IMG_PATH);

		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
		 		.defaultDisplayImageOptions(options)
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(100)// 缓存一百张图片
				.writeDebugLogs().discCache(new UnlimitedDiscCache(cachePath)) //自定义缓存路径
				.imageDownloader(new BaseImageDownloader(context, 15 * 1000, 30 * 1000)) //connectTimeout (5 s), readTimeout (30 s)超时时间
				.build();
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 获取ImageLoaderUtil实例
	 * @param context
	 * @return
	 */
	public static ImageLoaderUtil getInstance(Context context) {
		if (instance == null) {
			synchronized (context) {
				if (instance == null)
					instance = new ImageLoaderUtil(context);
			}
		}
		return instance;
	}

	/**
	 * 获取图片：本地或网络
	 * @param imageres
	 * @param imagePath
	 */
	public void getImage(ImageView imageres, String imagePath) { //http://61.51.110.209:8080/ok/ARTICLE_IMG/IMG_20150819_121647.JPEG
		// mAttacher = new PhotoViewAttacher(imageres);

		String mImagePath = imagePath;
		if(checkImage(imagePath)){//加载本地图片
			File file = new File(imagePath); //http:/61.51.110.209:8080/ok/ARTICLE_IMG/IMG_20150819_121647.JPEG
			String fileName = file.getName(); //IMG_20150819_121647.JPEG
			mImagePath = "file://" + IMG_PATH + fileName; //file:///storage/emulated/0/ARTICLE_IMG/IMG_20150819_121647.JPEG
		}

		ImageLoader.getInstance().displayImage(mImagePath, imageres, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// progressBar.setVisibility(View.VISIBLE);
			}
			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
				case IO_ERROR:
					message = "下载错误";
					break;
				case DECODING_ERROR:
					message = "图片无法显示";
					break;
				case NETWORK_DENIED:
					message = "网络有问题，无法下载";
					break;
				case OUT_OF_MEMORY:
					message = "图片太大无法显示";
					break;
				case UNKNOWN:
					message = "未知的错误";
					break;
				}
				LogUtil.e(TAG, "下载失败，图片地址："+imageUri+"，失败原因："+message);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				LogUtil.i(TAG, "下载完成，图片地址："+imageUri);
				saveBMP2SD(imageUri, loadedImage);
				mCallback.refreshAdapter(loadedImage);
			}
		});
	}

	/**
	 * 检测图片文件是否存在
	 */
	private boolean checkImage(String imagePath) {//http://61.51.110.209:8080/ok/ARTICLE_IMG/IMG_20150819_121647.JPEG
		boolean isexsit =true;
		File file = new File(imagePath);//http:/61.51.110.209:8080/ok/ARTICLE_IMG/IMG_20150819_121647.JPEG
		String fileName = file.getName();//IMG_20150819_121647.JPEG
		File f = new File(IMG_PATH + fileName);// /storage/emulated/0/ARTICLE_IMG/IMG_20150819_121647.JPEG
		isexsit = f.exists();//true
		return isexsit;
	}

	/**
	 * 将网络下载来的图片存入SD卡
	 */
	protected void saveBMP2SD(String imagePath, Bitmap loadedImage) {
		LogUtil.e(TAG, "loadedImage = "+loadedImage +", imagePath = " + imagePath);
		if(!imagePath.contains("http"))return;
		File file =new File(imagePath);
		String fileName = file.getName();
		File f = new File(IMG_PATH + fileName);
		FileOutputStream fOut = null;
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		if(!f.exists())
			try {
			fOut = new FileOutputStream(f);
			loadedImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(fOut!=null)
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setCallBack(ImageLoaderCallBack callback){
		mCallback = callback;
	}
	
	public interface ImageLoaderCallBack{
		public void refreshAdapter(Bitmap loadedImage);
	}


	public void displayImage(String url, ImageView imageView){
		ImageLoader.getInstance().displayImage(url, imageView);
	}

	public static void setIconData(Context context, final ImageView ivIcon, String url) {
		LogUtil.i(TAG, "url = "+url);
		if(url == null || url.length() == 0)	return;
		final String subString = url.substring(url.lastIndexOf("/")+1);
		if(MyFileUtils.fileIsExists(IMG_PATH + subString)){
			Bitmap iconBmp = BitmapFactory.decodeFile(IMG_PATH + subString);
			if(iconBmp!=null){
				ivIcon.setImageBitmap(iconBmp);
			}
		}else{
//			ImageLoader.getInstance().destroy();
			ImageLoaderUtil imageLoader = ImageLoaderUtil.getInstance(context);
			imageLoader.getImage(ivIcon, url);
			imageLoader.setCallBack(new ImageLoaderUtil.ImageLoaderCallBack() {
				@Override
				public void refreshAdapter(Bitmap loadedImage) {
                    ivIcon.setImageBitmap(loadedImage);
				}
			});
		}
	}
}
