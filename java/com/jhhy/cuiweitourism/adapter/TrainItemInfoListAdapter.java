package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;

import java.util.List;

/**
 * Created by jiahe008 on 2016/10/25.
 */
public abstract class TrainItemInfoListAdapter extends MyBaseAdapter implements OnItemTextViewClick {

    public TrainItemInfoListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_train_seat_type, null);
            holder = new ViewHolder();
            holder.tvTypeSeat = (TextView) view.findViewById(R.id.tv_train_ticket_seat);
            holder.tvTicketPrice = (TextView) view.findViewById(R.id.tv_train_ticket_price);
            holder.tvTicketNum = (TextView) view.findViewById(R.id.tv_train_ticket_number);
            holder.btnReserveTicket = (Button) view.findViewById(R.id.btn_train_ticket_reserve);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        final Button btnView = holder.btnReserveTicket;
        final int btnId = holder.btnReserveTicket.getId();
        holder.btnReserveTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemTextViewClick(i, btnView, btnId);
            }
        });
        TrainTicketDetailInfo.SeatInfo seatInfo = (TrainTicketDetailInfo.SeatInfo) getItem(i);
        if (seatInfo != null){
            holder.tvTypeSeat.setText(seatInfo.seatName);
            holder.tvTicketPrice.setText(String.format("￥%s", seatInfo.floorPrice));
            if ("0".equals(seatInfo.seatCount)){
                holder.tvTicketNum.setText("无票");
                holder.btnReserveTicket.setClickable(false);
                holder.btnReserveTicket.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_reserve_not_border_selector));
            }else {
                holder.tvTicketNum.setText(String.format("%s张", seatInfo.seatCount));
                holder.btnReserveTicket.setClickable(true);
                holder.btnReserveTicket.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_reserve_border_selector));
            }
        }

        return view;
    }

    class ViewHolder{
        private TextView tvTypeSeat;
        private TextView tvTicketPrice;
        private TextView tvTicketNum;
        private Button btnReserveTicket;
    }
}
