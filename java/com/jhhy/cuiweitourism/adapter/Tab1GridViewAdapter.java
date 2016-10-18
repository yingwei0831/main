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
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/4.
 */
public class Tab1GridViewAdapter extends BaseAdapter {

    private ArgumentOnClick argument;

    private List<Travel> mLists;
    private LayoutInflater inflater;
    private Context context;

    public Tab1GridViewAdapter(Context context, List<Travel> mLists, ArgumentOnClick listener){
        this.mLists = mLists;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.argument = listener;
    }

    public void setData(List<Travel> lists){
        this.mLists = lists;
        notifyDataSetChanged();
    }

//    public void setArgumentOnClick(ArgumentOnClick listener){
//        this.argument = listener;
//    }

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
            view = inflater.inflate(R.layout.tab1_travel_gridview_item, null);
            holder = new ViewHolder();
            holder.ivDestination = (ImageView) view.findViewById(R.id.iv_tab1_travel_gridview_destination);
            holder.tvTitle = (TextView)view.findViewById(R.id.tv_tab1_travel_gridview_title);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_tab1_price);
            holder.tvArgument = (TextView) view.findViewById(R.id.tv_tab1_travel_gridview_argument);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final View finalConvertView = view;
        Travel travel = (Travel) getItem(position);
        holder.tvPrice.setText(travel.getTravelPrice());
        final int id = holder.tvArgument.getId();

        holder.tvArgument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                argument.goToArgument(finalConvertView, viewGroup, position, id);
            }
        });

        if(travel != null){
            //TODO
            holder.tvTitle.setText(travel.getTravelTitle());
            holder.tvPrice.setText(travel.getTravelPrice());
//            ImageLoaderUtil.getInstance(context).displayImage(travel.getTravelIconPath(), holder.ivDestination);
        }
        return view;
    }

    class ViewHolder{
        private ImageView ivDestination;
        private TextView  tvTitle;
        private TextView  tvPrice;
        private TextView  tvArgument;
    }

}
