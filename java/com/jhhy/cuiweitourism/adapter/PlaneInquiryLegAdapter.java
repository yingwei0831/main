package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.PlaneInquiry;

import java.util.List;
import java.util.Locale;

/**
 * Created by jiahe008 on 2017/1/14.
 */
public class PlaneInquiryLegAdapter extends MyBaseAdapter {

    public PlaneInquiryLegAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_inquiry_leg, null);
            holder = new ViewHolder();
            holder.tvNumber = (TextView) view.findViewById(R.id.tv_inquiry_number);
            holder.tvInquiryLeg = (TextView) view.findViewById(R.id.tv_inquiry_leg);
            holder.tvTime = (TextView) view.findViewById(R.id.tv_inquiry_time);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        PlaneInquiry item = (PlaneInquiry) getItem(i);
        if (item != null){
            holder.tvNumber.setText(String.format(Locale.getDefault(), "第%d程", i+1));
            holder.tvInquiryLeg.setText(String.format(Locale.getDefault(), "%s—%s", item.getFromCity().getName(), item.getArrivalCity().getName()));
            holder.tvTime.setText(item.getFromDate());
        }
        return view;
    }

    class ViewHolder{
        private TextView tvNumber;
        private TextView tvInquiryLeg;
        private TextView tvTime;
    }
}
