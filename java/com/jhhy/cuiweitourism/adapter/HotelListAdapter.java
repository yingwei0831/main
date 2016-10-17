package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahe008 on 2016/8/29.
 */
public class HotelListAdapter extends MyBaseAdapter {

    public HotelListAdapter(Context ct, List list, Object view) {
        super(ct, list, view);
    }

    public HotelListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_hotel_list, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.iv_item_hotel_list);
            holder.tvTitle = (TextView)view.findViewById(R.id.tv_item_hotel_name);
            holder.tvType = (TextView) view.findViewById(R.id.tv_hotel_type);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_item_hotel_list_money_unit);
            holder.tvAddress = (TextView) view.findViewById(R.id.tv_hotel_address);
            holder.tvDistance = (TextView) view.findViewById(R.id.tv_item_hotel_list_distance);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        HotelListInfo hotelInfo = (HotelListInfo) getItem(i);
        if(hotelInfo != null){
            //TODO
            holder.tvTitle.setText(hotelInfo.getTitle());
            holder.tvType.setText(hotelInfo.getTypes());
            holder.tvPrice.setText(hotelInfo.getPrice());
            holder.tvAddress.setText(hotelInfo.getAddress());
//            holder.tvDistance.setText(hotelInfo.getLat(), hotelInfo.getLng());
            ImageLoaderUtil.getInstance(context).displayImage(hotelInfo.getLitpic(), holder.imageView);
        }

        return view;
    }

    public void setData(ArrayList<HotelListInfo> listHotel) {
        this.list = listHotel;
        notifyDataSetChanged();
    }

    class ViewHolder{
        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvType;
        private TextView tvPrice;
        private TextView tvAddress;
        private TextView tvDistance;
    }
}
