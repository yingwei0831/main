package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/11/14.
 */
public class InnerTravelCommentImageGridAdapter extends MyBaseAdapter {

    public InnerTravelCommentImageGridAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_inner_comment_grid, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.iv_comment_grid);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        String url = (String) getItem(i);
        ImageLoaderUtil.getInstance(context).getImage(holder.imageView, url);
        ImageLoaderUtil.getInstance(context).setCallBack(new ImageLoaderUtil.ImageLoaderCallBack() {
            @Override
            public void refreshAdapter(Bitmap loadedImage) {
            }
        });
        return view;
    }

    class ViewHolder{
        private ImageView imageView;
    }
}
