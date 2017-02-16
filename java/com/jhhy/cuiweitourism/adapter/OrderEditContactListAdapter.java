package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.FetchModel.InsuranceOrderRequest;

import java.util.List;

/**
 * Created by jiahe008_lvlanlan on 2017/2/16.
 */
public class OrderEditContactListAdapter extends MyBaseAdapter {

    private int count;

    public OrderEditContactListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_traveler, null);
            holder = new ViewHolder();
            holder.tvTravelerName = (TextView) view.findViewById(R.id.tv_travel_edit_order_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        if (i < list.size()){
            InsuranceOrderRequest.BeibrBean item = (InsuranceOrderRequest.BeibrBean) getItem(i);
            holder.tvTravelerName.setText(item.getTourername());
        }
        return view;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    class ViewHolder{
        private TextView tvTravelerName;
    }
}
