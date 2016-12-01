package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by jiahe008 on 2016/11/30.
 */
public class TrainSeatTypeAdapter extends MyBaseAdapter {

    private int selection;

    public TrainSeatTypeAdapter(Context ct, List list) {
        super(ct, list);
    }

    public void setSelection(int selection){
        this.selection = selection;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_train_change_seat_type, null);
            holder = new ViewHolder();
            view.setTag(holder);
            holder.textView = (TextView) view.findViewById(R.id.tv_train_change_seat_type);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        TrainTicketDetailInfo.SeatInfo seatInfo = (TrainTicketDetailInfo.SeatInfo) list.get(i);
        if (seatInfo != null){
            holder.textView.setText(String.format(Locale.getDefault(), "%s ￥%s", seatInfo.seatName, seatInfo.floorPrice));
            if (i == selection) {
                holder.textView.setTextColor(context.getResources().getColor(R.color.colorActionBar));
            }else{
                holder.textView.setTextColor(Color.BLACK);
            }
        }
        return view;
    }

    class ViewHolder{
        private TextView textView;
    }
}
