package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaHotInfo;

import java.util.List;
import java.util.Locale;

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
        VisaHotInfo city = (VisaHotInfo) getItem(i);
        holder.tvTitle.setText(city.getVisaName());
        holder.tvPeriod.setText(String.format(Locale.getDefault(), "办理时长预计送签后的%s个工作日", city.getVisaTime()));
        holder.tvLocation.setText(String.format(Locale.getDefault(), "%s领区", city.getVisaAddress()));
        holder.tvPrice.setText(city.getVisaPrice());
        return view;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tvTitle;
        private TextView tvPeriod;
        private TextView tvLocation;
        private TextView tvPrice;
    }
}
