package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.Travel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/4.
 */
public class Tab1SearchRouteListViewAdapter extends BaseAdapter {

    private ArgumentOnClick argument;

    private List<Travel> mLists;
    private LayoutInflater inflater;

    public Tab1SearchRouteListViewAdapter(Context context, List<Travel> mLists){
        this.mLists = mLists;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<Travel> lists){
        this.mLists = lists;
        notifyDataSetChanged();
    }

    public void setOnArgumentClick(ArgumentOnClick listener){
        this.argument = listener;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.tab1_search_route_listview_item, null);
            holder = new ViewHolder();
            holder.tvType = (TextView) view.findViewById(R.id.tv_search_route_item_type);
            holder.ivDestination = (ImageView) view.findViewById(R.id.iv_tab1_2_search_route_destination);
            holder.tvTitle = (TextView)view.findViewById(R.id.tv_tab1_2_search_route_item_title);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_tab1_2_search_route_item_price);
            holder.tvArgument = (TextView) view.findViewById(R.id.layout_tab1_search_route_item_argument);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final View finalConvertView = view;
        Travel travel = (Travel) getItem(position);
        holder.tvPrice.setText(travel.getTravelPrice());
        holder.tvType.setText(travel.getType());
        final int id = holder.tvArgument.getId();
        holder.tvArgument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                argument.goToArgument(finalConvertView, viewGroup, position, id);
            }
        });

        if(travel != null){
            //TODO
        }
        return view;
    }

    class ViewHolder{
        private TextView tvType;
        private ImageView ivDestination;
        private TextView  tvTitle;
        private TextView  tvPrice;
        private TextView  tvArgument;
    }

}
