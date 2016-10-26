package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by birney1 on 16/10/24.
 */
public class TrainListAdapter extends MyBaseAdapter {

    private String TAG = TrainListAdapter.class.getSimpleName();

    public TrainListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_train_list, null);
            holder.tvTrainName = (TextView) view.findViewById(R.id.tv_ticket_item_train_name);
            holder.tvTrainFromTime = (TextView) view.findViewById(R.id.tv_ticket_item_train_start_time);
            holder.tvTrainFromStation = (TextView) view.findViewById(R.id.tv_ticket_item_train_start_station);

            holder.tvConsumingTime = (TextView) view.findViewById(R.id.tv_ticket_item_train_consuming_time);
            holder.tvArrivalTime = (TextView) view.findViewById(R.id.tv_ticket_item_train_arrival_time);
            holder.tvArrivalStation = (TextView) view.findViewById(R.id.tv_ticket_item_train_arrival_name);

            holder.tvTicketPrice = (TextView) view.findViewById(R.id.tv_ticket_item_train_price);
            holder.tvSeatType = (TextView) view.findViewById(R.id.tv_ticket_item_train_seat_type);
            holder.tvTicketNumber = (TextView) view.findViewById(R.id.tv_ticket_item_train_ticket_number);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        TrainTicketDetailInfo info = (TrainTicketDetailInfo) getItem(i);
        if (info != null) {
            holder.tvTrainName.setText(info.trainNum);
            holder.tvTrainFromTime.setText(info.departureTime);
            holder.tvTrainFromStation.setText(info.departureStation);
            holder.tvConsumingTime.setText(Utils.getDuration(info.duration));
            holder.tvArrivalTime.setText(info.arrivalTime);
            holder.tvArrivalStation.setText(info.arrivalStation);
            ArrayList<TrainTicketDetailInfo.SeatInfo> ticketKinds = info.seatInfoArray;
            Collections.sort(ticketKinds);
            TrainTicketDetailInfo.SeatInfo seatInfo = ticketKinds.get(0);
            holder.tvTicketPrice.setText(String.format("￥%s", seatInfo.floorPrice));
            holder.tvSeatType.setText(seatInfo.seatName);
            if (Integer.parseInt(info.numberOfRemainingSeats) == 0){
                holder.tvTicketNumber.setText("无票");
                holder.tvTicketNumber.setTextColor(Color.RED);
            }else if (Integer.parseInt(info.numberOfRemainingSeats) <= 9){
                holder.tvTicketNumber.setText(String.format("仅剩%s张", info.numberOfRemainingSeats));
                holder.tvTicketNumber.setTextColor(Color.RED);
            } else {
                holder.tvTicketNumber.setText(String.format("%s张", info.numberOfRemainingSeats));
                holder.tvTicketNumber.setTextColor(Color.GRAY);
            }
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
