package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HomePageCustomListInfo;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/4.
 */
public class PersonalizedCustomGridViewAdapter extends BaseAdapter {

    private List<HomePageCustomListInfo> mLists;
    private LayoutInflater inflater;
    private Context context;

    public PersonalizedCustomGridViewAdapter(Context context, List<HomePageCustomListInfo> mLists){
        this.mLists = mLists;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    public void setData(List<HomePageCustomListInfo> lists){
        this.mLists = lists;
        notifyDataSetChanged();
    }

    public void addData(List<HomePageCustomListInfo> lists){
        this.mLists.addAll(lists);
        notifyDataSetChanged();
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
            view = inflater.inflate(R.layout.item_personalized_custom_gridview, null);
            holder = new ViewHolder();
            holder.ivDestination = (ImageView) view.findViewById(R.id.iv_custom_destination);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_custom_price);
            holder.tvTitle = (TextView)view.findViewById(R.id.tv_custom_title);
            holder.tvDestinationName = (TextView)view.findViewById(R.id.tv_custom_destination_name);
            holder.tvDays = (TextView) view.findViewById(R.id.tv_personalized_custom_account);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        HomePageCustomListInfo travel = (HomePageCustomListInfo) getItem(position);

        if(travel != null){
            //TODO
            holder.tvPrice.setText(travel.getPrice());
            holder.tvDays.setText(travel.getDays());
            holder.tvDestinationName.setText(travel.getMdd());
            holder.tvTitle.setText(travel.getTitle());
            ImageLoaderUtil.getInstance(context).displayImage(travel.getLitpic(), holder.ivDestination);
        }
        return view;
    }

    class ViewHolder{
        private ImageView ivDestination;
        private TextView  tvTitle;
        private TextView  tvPrice;
        private TextView  tvDestinationName;
        private TextView  tvDays;
    }

}
