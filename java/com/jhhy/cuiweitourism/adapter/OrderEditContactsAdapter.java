package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOrderInternationalRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketOrderFetch;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/8.
 */
public class OrderEditContactsAdapter extends MyBaseAdapter {

    private OnItemTextViewClick listener;
    private int type;

    private int number; //为了酒店选择联系人

    public OrderEditContactsAdapter(Context ct, List list, OnItemTextViewClick listener) {
        super(ct, list);
        this.listener = listener;
    }

    public void setType(int type){
        this.type = type;
    }

    public void setCount(int count){
        this.number = count;
    }

    @Override
    public int getCount() {
        if (type == 3){
            return number;
        }else {
            return super.getCount();
        }
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_train_contact, null);
            holder = new ViewHolder();
            holder. ivTrash = (ImageView) view.findViewById(R.id.iv_train_trash);
            holder. ivDetail = (ImageView) view.findViewById(R.id.iv_contact_view_detail);
            holder.tvName = (TextView) view.findViewById(R.id.tv_contact_name);
            holder.tvID = (TextView) view.findViewById(R.id.tv_contact_card_id);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final View clickViewT = holder.ivTrash;
        holder.ivTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemTextViewClick(i, clickViewT, 0);
            }
        });
        final View clickViewD = holder.ivDetail;
        holder.ivDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemTextViewClick(i, clickViewD, 0);
            }
        });

        final View clickViewName = holder.tvName;
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemTextViewClick(i, clickViewName, 0);
            }
        });
        if (type == 1){
            PlaneTicketOrderInternationalRequest.PassengersBean item = (PlaneTicketOrderInternationalRequest.PassengersBean) getItem(i);
            if (item != null) {
                holder.tvName.setText(item.getName());
                holder.tvID.setText(item.getCardNo());
            }
        }else if (type == 3){ //酒店
            if (i < list.size()){
                HotelOrderRequest.RoomBean.PassengerBean hotelPassenger = (HotelOrderRequest.RoomBean.PassengerBean) getItem(i);
                if (hotelPassenger != null){
                    holder.tvName.setText(hotelPassenger.getName());
                }
            }else{
                holder.tvName.setHint(context.getString(R.string.hotel_passenger_name_hint));
                holder.tvName.setText("");
            }
            holder.tvID.setVisibility(View.GONE);
        }
        else {
            TrainTicketOrderFetch.TicketInfo info = (TrainTicketOrderFetch.TicketInfo) getItem(i);
            if (info != null) {
                holder.tvName.setText(info.getPsgName());
                holder.tvID.setText(info.getCardNo());
            }
        }
        return view;
    }

    class ViewHolder{
        private ImageView ivTrash;
        private ImageView ivDetail;
        private TextView tvName;
        private TextView tvID;
    }
}
