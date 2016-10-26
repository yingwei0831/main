package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.util.List;

/**
 * Created by jiahe008 on 2016/10/26.
 */
public class PlaneListAdapter extends MyBaseAdapter {

    public PlaneListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_plane_list, null);
            holder.tvStartTime = (TextView) view.findViewById(R.id.tv_plane_start_time);
            holder.tvFromAirport = (TextView) view.findViewById(R.id.tv_plane_from_airport);
            holder.tvArrivalTime = (TextView) view.findViewById(R.id.tv_plane_arrival_time);
            holder.tvArrivalAirport = (TextView) view.findViewById(R.id.tv_plane_arrival_airport);
            holder.tvTicketPrice = (TextView) view.findViewById(R.id.tv_plane_ticket_price);
            holder.tvPlaneClass = (TextView) view.findViewById(R.id.tv_plane_ticket_class);
            holder.tvPlaneInfo = (TextView) view.findViewById(R.id.tv_plane_info);
            holder.tvTicketNum = (TextView) view.findViewById(R.id.tv_plane_ticket_number);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }


        return view;
    }

    class ViewHolder{
        private TextView tvStartTime;
        private TextView tvFromAirport;

        private TextView tvArrivalTime;
        private TextView tvArrivalAirport;

        private TextView tvTicketPrice;
        private TextView tvPlaneClass;  //舱位

        private TextView tvPlaneInfo;   //飞机信息
        private TextView tvTicketNum;   //机票数量
    }
}
