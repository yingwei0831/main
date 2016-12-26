package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.model.LatLng;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListResponse;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by jiahe008 on 2016/8/29.
 */
public class HotelListAdapter extends MyBaseAdapter {

    private String[] location;

    public HotelListAdapter(Context ct, List list) {
        super(ct, list);
        SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(context);
        location = sp.getLocation();
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

        HotelListResponse.HotelBean hotelInfo = (HotelListResponse.HotelBean) getItem(i);
        if(hotelInfo != null){
            //TODO
            holder.tvTitle.setText(hotelInfo.getName());
            String type = "经济/客栈";
            if ("0".equals(hotelInfo.getStartLevel()) || "1".equals(hotelInfo.getStartLevel()) || "2".equals(hotelInfo.getStartLevel())){
                type = "经济/客栈";
            }else if ("3".equals(hotelInfo.getStartLevel())){
                type = "三星/舒适";
            }else if ("4".equals(hotelInfo.getStartLevel())){
                type = "四星/高档";
            }else if ("5".equals(hotelInfo.getStartLevel())){
                type = "五星/豪华";
            }
            holder.tvType.setText(type);
            holder.tvPrice.setText(hotelInfo.getMinPrice());
            LatLng latLngFrom = new LatLng(Double.parseDouble(hotelInfo.getBaidulat()), Double.parseDouble(hotelInfo.getBaidulon())); //百度和高德转换坐标
            CoordinateConverter converter  = new CoordinateConverter();
            // CoordType.GPS 待转换坐标类型
            converter.from(CoordinateConverter.CoordType.BAIDU);
            // sourceLatLng待转换坐标点 LatLng类型
            converter.coord(latLngFrom);
            // 执行转换操作
            LatLng desLatLng = converter.convert();

            LatLng latLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
            float distance = AMapUtils.calculateLineDistance(latLng, desLatLng) / 1000; //计算距离

            holder.tvDistance.setText(String.format(Locale.getDefault(), "%.2f", distance));
            holder.tvAddress.setText(hotelInfo.getTraffic()); //显示地址
            ImageLoaderUtil.getInstance(context).displayImage(hotelInfo.getImgUrl(), holder.imageView);
        }
        return view;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }

    public void addData(List list){
        this.list.addAll(list);
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
