package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/11.
 */
public class InnerTravelFirstListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<String> list;
    private int mPosition;

    public InnerTravelFirstListViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        InnerTravelTripFirstViewHolder holder = null;
        if(null == view){
            view = inflater.inflate(R.layout.inner_trip_first_listview_item, viewGroup, false);
            holder = new InnerTravelTripFirstViewHolder();
            holder.tvTripDays = (TextView) view.findViewById(R.id.tv_item_inner_trip_first_listview_item);
            view.setTag(holder);
        }else {
            holder = (InnerTravelTripFirstViewHolder) view.getTag();
        }
        String tripDays = (String) getItem(i);
        if(i == mPosition){
            holder.tvTripDays.setTextColor(context.getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            holder.tvTripDays.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        }else{
            holder.tvTripDays.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.tvTripDays.setBackgroundColor(context.getResources().getColor(R.color.colorInnerTravelPopLeftBg));
        }
        if(null != tripDays)    holder.tvTripDays.setText(tripDays);
        return view;
    }

    public void setSelectPosition(int position) {
        mPosition = position;
    }

    public String getCurrentPositionItem() {
        return list.get(mPosition);
    }

    class InnerTravelTripFirstViewHolder{
        private TextView tvTripDays;
    }
}
