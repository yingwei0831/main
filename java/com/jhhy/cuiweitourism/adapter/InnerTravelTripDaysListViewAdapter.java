package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
public class InnerTravelTripDaysListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<String> list;
    private int mPosition; //选中位置

    private Drawable drawableLight; //类型1和3的图片
    private Drawable drawableDark; //类型1和3的图片


    public InnerTravelTripDaysListViewAdapter(Context context, List<String> list) {
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
            InnerTravelTripDaysViewHolder holder = null;
            if (null == view) {
                view = inflater.inflate(R.layout.inner_trip_days_listview_item, viewGroup, false);
                holder = new InnerTravelTripDaysViewHolder();
                holder.tvTripDays = (TextView) view.findViewById(R.id.tv_item_inner_trip_days_listview_item);
                view.setTag(holder);
                drawableLight = ContextCompat.getDrawable(context, R.drawable.unchecked);
                drawableDark = ContextCompat.getDrawable(context, R.drawable.check);
                drawableLight.setBounds(0, 0, drawableLight.getMinimumWidth(), drawableLight.getMinimumHeight());
                drawableDark.setBounds(0, 0, drawableDark.getMinimumWidth(), drawableDark.getMinimumHeight());
            } else {
                holder = (InnerTravelTripDaysViewHolder) view.getTag();
            }
            if (i == mPosition) {
                holder.tvTripDays.setTextColor(context.getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                holder.tvTripDays.setCompoundDrawables(null, null, drawableDark, null);
            } else {
                holder.tvTripDays.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.tvTripDays.setCompoundDrawables(null, null, drawableLight, null);
            }
            String tripDays = (String) getItem(i);
            if (null != tripDays) holder.tvTripDays.setText(tripDays);
        return view;
    }

    public void setData(List<String> list){
        this.list = list;
    }

    public void setSelectPosition(int position) {
        mPosition = position;
    }

    class InnerTravelTripDaysViewHolder{
        private TextView tvTripDays;

        private TextView tvStartTime1;
        private TextView tvStartTime2;
    }
}
