package com.jhhy.cuiweitourism.circleviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * @param context
	 * @param url
     * @return
     */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(R.layout.view_banner, null);
//		ImageLoader.getInstance().displayImage(url, imageView);
//		ImageLoaderUtil.getInstance(context).getImage(imageView, url);
		ImageLoaderUtil.getInstance(context).displayImage(url, imageView);
		return imageView;
	}
}
