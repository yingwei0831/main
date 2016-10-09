package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahe008 on 2016/10/9.
 */
public class CarRentCityListAdapter extends MyBaseAdapter {

    public CarRentCityListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_car_rent_city, null);
            holder.tvAddressName = (TextView) view.findViewById(R.id.tv_address_name);
            holder.tvAddressDetail = (TextView) view.findViewById(R.id.tv_address_detail);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        ArrayList<String> list = (ArrayList<String>) getItem(i);
        holder.tvAddressName.setText(list.get(2));
        holder.tvAddressDetail.setText(list.get(3));
        return view;
    }

    public void setData(List list){
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder{
        private TextView tvAddressName;
        private TextView tvAddressDetail;
    }


}
