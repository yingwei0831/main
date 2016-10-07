package com.jhhy.cuiweitourism.utils;

import android.widget.ImageView;

import com.jhhy.cuiweitourism.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by jiahe008 on 2016/7/22.
 * 最新jar包，未用
 */
public class MyImagerLoaderUtil {

    private DisplayImageOptions mOptions;

    private static ImageLoader mImageLoader;

    public static ImageLoader getInstance(){
        if(mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
        }
        return mImageLoader;
    }

    /**
     * 配置选项
     * @param radious 圆角图片的角度
     */
    public void initImageView(int radious) {
//        if(mOptions != null){
//            mOptions = null;
//        }
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 构建完成
    }

    /**
     * 下载并展示图片
     * @param url 图片的Url地址
     * @param imageView 承载图片的ImageView控件
     * @param options DisplayImageOptions配置文件
     */
    public void displayImage(String url, ImageView imageView, DisplayImageOptions options){
        mImageLoader.displayImage(url, imageView, options);
    }

}
