package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/10.
 */
public class HotelImageViewAdapter extends MyBaseAdapter {

    public HotelImageViewAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_hotel_image, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.picture);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
//        String url = list.get(i);
//        LogUtil.e("url", url);
//        Picasso.with(context)
//                .load(url)
//                .into(viewHolder.imageView);
//        ViewCompat.setTransitionName(viewHolder.imageView, url);
        return view;
    }


    public class ViewHolder {
        public ImageView imageView;
    }
}
