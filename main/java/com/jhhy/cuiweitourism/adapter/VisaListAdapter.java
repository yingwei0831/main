package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.VisaHotCountry;
import com.jhhy.cuiweitourism.moudle.VisaHotCountryCity;

import java.util.List;

/**
 * Created by jiahe008 on 2016/9/27.
 */
public class VisaListAdapter extends MyBaseAdapter {

    public VisaListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_visa_city_list, null);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_visa_title);
            holder.tvPeriod = (TextView) view.findViewById(R.id.tv_visa_period);
            holder.tvLocation = (TextView) view.findViewById(R.id.tv_visa_location);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_visa_price);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        VisaHotCountryCity city = (VisaHotCountryCity) getItem(i);
        holder.tvTitle.setText(city.getTitle());
        holder.tvPeriod.setText(city.getHandleDay());
        holder.tvLocation.setText(city.getCityName());
        holder.tvPrice.setText(city.getPrice());
        return view;
    }

    public void setData(List<VisaHotCountryCity> listCity){
        this.list = listCity;
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tvTitle;
        private TextView tvPeriod;
        private TextView tvLocation;
        private TextView tvPrice;
    }
}
