package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.PlaneInquiry;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/8.
 */
public class PlaneInquiryAdapter extends MyBaseAdapter {

    private String TAG = "PlaneInquiryAdapter";

    private OnItemTextViewClick listener;

    public PlaneInquiryAdapter(Context ct, List list, OnItemTextViewClick listener) {
        super(ct, list);
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_plane_inquery, null);
            holder = new ViewHolder();
            holder.tvInquiryNo = (TextView) view.findViewById(R.id.tv_plane_inquiry_no);
            holder.ivTrash      =  (ImageView) view.findViewById(R.id.iv_delete_one_query);
            holder.tvFromCity   =  (TextView) view.findViewById(R.id.tv_plane_from_city);
            holder.tvArrivalCity =  (TextView) view.findViewById(R.id.tv_plane_to_city);
            holder.ivExchangeCity =  (ImageView) view.findViewById(R.id.iv_train_exchange);
            holder.tvFromDate   =  (TextView) view.findViewById(R.id.tv_plane_from_time);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final View ivT = holder.ivTrash;
        holder.ivTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemTextViewClick(position, ivT, 0);
            }
        });
        final View tvF = holder.tvFromCity;
        holder.tvFromCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemTextViewClick(position, tvF, 0);
            }
        });
        final View tvA = holder.tvArrivalCity;
        holder.tvArrivalCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemTextViewClick(position, tvA, 0);
            }
        });
        final View ivE = holder.ivExchangeCity;
        holder.ivExchangeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemTextViewClick(position, ivE, 0);
            }
        });
        final View ivD = holder.tvFromDate;
        holder.tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemTextViewClick(position, ivD, 0);
            }
        });

        PlaneInquiry item = (PlaneInquiry) getItem(position);
        LogUtil.e(TAG, "item = " + item);
        if (item != null){
            if (item.fromCity != null){
                holder.tvFromCity.setText(item.fromCity.getName());
            }else{
                holder.tvFromCity.setText(context.getString(R.string.tab4_account_certification_gender_notice));
            }
            if (item.arrivalCity != null){
                holder.tvArrivalCity.setText(item.arrivalCity.getName());
            }else{
                holder.tvArrivalCity.setText(context.getString(R.string.tab4_account_certification_gender_notice));
            }
            if (item.fromDate != null){
                holder.tvFromDate.setText(item.fromDate);
            }else{
                holder.tvFromDate.setText(context.getString(R.string.tab4_account_certification_gender_notice));
            }
            holder.tvInquiryNo.setText(String.valueOf(position + 1));
        }
        return view;
    }

    class ViewHolder{
        private TextView tvInquiryNo; //1
        private ImageView ivTrash; //delete
        private TextView tvFromCity; //出发地
        private TextView tvArrivalCity; //目的地
        private ImageView ivExchangeCity; //交换出发地，目的地
        private TextView tvFromDate; //出发时间
    }
}
