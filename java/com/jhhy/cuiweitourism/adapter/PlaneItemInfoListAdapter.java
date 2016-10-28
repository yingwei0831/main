package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;

import java.util.List;

/**
 * Created by jiahe008 on 2016/10/25.
 */
public abstract class PlaneItemInfoListAdapter extends MyBaseAdapter implements OnItemTextViewClick {

    public PlaneItemInfoListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_plane_seat_type, null);
            holder = new ViewHolder();
            holder.tvTypeSeat = (TextView) view.findViewById(R.id.tv_train_ticket_seat);
            holder.tvTicketPrice = (TextView) view.findViewById(R.id.tv_plane_ticket_price);
            holder.tvRefundNotice = (TextView) view.findViewById(R.id.tv_plane_ticket_refund);
            holder.btnReserveTicket = (Button) view.findViewById(R.id.btn_plane_ticket_reserve);
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

        final TextView tvRefundInfo = holder.tvRefundNotice;
        final int tvId = holder.tvRefundNotice.getId();
        holder.tvRefundNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemTextViewClick(i, tvRefundInfo, tvId);
            }
        });

        PlaneTicketInfoOfChina.SeatItemInfo seatItem = (PlaneTicketInfoOfChina.SeatItemInfo) getItem(i);
        holder.tvTypeSeat.setText(String.format("%s%s折", seatItem.seatMsg, seatItem.discount));
        holder.tvTicketPrice.setText(seatItem.parPrice);

        return view;
    }

    class ViewHolder{
        private TextView tvTypeSeat;
        private TextView tvTicketPrice;
        private TextView tvRefundNotice;
        private Button btnReserveTicket;
    }
}
