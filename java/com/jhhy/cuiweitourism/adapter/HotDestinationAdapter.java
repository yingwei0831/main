package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.HotDestination;

import java.util.List;

/**
 * Created by birney1 on 16/9/12.
 */
public class HotDestinationAdapter extends MyBaseAdapter {

    public HotDestinationAdapter(Context ct, List list, Object view) {
        super(ct, list, view);
    }

    public HotDestinationAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }

    //    public void setData(List<HotDestination> newDest){
//        this.list = newDest;
//        notifyDataSetChanged();
//    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        HotDestViewHolder holder = null;
        if (view == null){
            holder = new HotDestViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_gridview_city_selection, null);
            holder.tvCityName = (TextView) view.findViewById(R.id.tv_item_gridview_city_selection);
            view.setTag(holder);
        }else{
            holder = (HotDestViewHolder) view.getTag();
        }

        HotDestination dest = (HotDestination) list.get(position);
        holder.tvCityName.setText(dest.getCityName());

        return view;
    }

    @Override
    public int getCount() {
       return super.getCount();
    }

    class HotDestViewHolder{
        private TextView tvCityName;
    }
}
