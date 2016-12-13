package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOrderInternational;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketOrderFetch;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/8.
 */
public class OrderEditContactsAdapter extends MyBaseAdapter {

    private OnItemTextViewClick listener;
    private int type;

    public OrderEditContactsAdapter(Context ct, List list, OnItemTextViewClick listener) {
        super(ct, list);
        this.listener = listener;
    }

    public void setType(int type){
        this.type = type;
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

        if (type == 1){
            PlaneTicketOrderInternational.PassengersBean item = (PlaneTicketOrderInternational.PassengersBean) getItem(i);
            if (item != null) {
                holder.tvName.setText(item.getName());
                holder.tvID.setText(item.getCardNo());
            }
        }else {
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
