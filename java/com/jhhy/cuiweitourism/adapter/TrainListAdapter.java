package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.util.List;

/**
 * Created by birney1 on 16/10/24.
 */
public class TrainListAdapter extends MyBaseAdapter {

    public TrainListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_train_list);
        }else{

        }
        return view;
    }

    class ViewHolder{
        private TextView tvTrainName;
        private TextView tvTrainFromTime;
        private TextView tvTrainFromStation;

        private TextView tvConsumingTime;
        private TextView tvArrivalTime;
        private TextView tvArrivalStation;

        private TextView tvTicketPrice;
        private TextView tvSeatType;
        private TextView tvTicketNumber;
    }
}
