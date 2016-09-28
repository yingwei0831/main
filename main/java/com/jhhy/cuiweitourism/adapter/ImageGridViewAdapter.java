package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.MyFileUtils;
import com.jhhy.cuiweitourism.view.MySquareImageView;

import java.io.File;
import java.util.List;

/**
 * Created by jiahe008 on 2016/9/9.
 */
public class ImageGridViewAdapter extends BaseAdapter {

    private String TAG = ImageGridViewAdapter.class.getSimpleName();
    private String IMG_PATH = Environment.getExternalStorageDirectory() + File.separator + "cuiweitourism/";

    private List<String> listImages;
    private Context context;
    private LayoutInflater inflater;

    public ImageGridViewAdapter(List<String> listImages, Context context) {
        this.listImages = listImages;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public String getItem(int i) {
        return listImages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageViewHolder holder = null;
        if(view == null){
            holder = new ImageViewHolder();
            view = inflater.inflate(R.layout.item_inner_travel_gridview, null);
            holder.imageView = (MySquareImageView) view.findViewById(R.id.iv_inner_travel_gridview_item);
            view.setTag(holder);
        }else{
            holder = (ImageViewHolder) view.getTag();
        }
        setIconData(context, holder.imageView, listImages.get(i));
        return view;
    }

    class ImageViewHolder{
        private MySquareImageView imageView;
    }

    private void setIconData(Context context, final ImageView ivIcon, String url) {
        LogUtil.v(TAG, "url = " + url);
        final String subString = url.substring(url.lastIndexOf("/") + 1);
        if (MyFileUtils.fileIsExists(IMG_PATH + subString)) {
            Bitmap iconBmp = BitmapFactory.decodeFile(IMG_PATH + subString);
            if (iconBmp != null) {
                ivIcon.setImageBitmap(iconBmp);
            }
        } else {
            ImageLoaderUtil imageLoader = ImageLoaderUtil.getInstance(context);
            imageLoader.getImage(ivIcon, url);
            imageLoader.setCallBack(new ImageLoaderUtil.ImageLoaderCallBack() {
                @Override
                public void refreshAdapter(Bitmap loadBitmap) {
                    LogUtil.v(TAG, "refreshAdapter");
//                    Bitmap iconBmp = BitmapFactory.decodeFile(Consts.IMG_PATH+iconPath);
//                    if(iconBmp!=null){
//                        ivUserIcon.setImageBitmap(iconBmp);
//                    }
                }
            });
        }
    }
}
