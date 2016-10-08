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
public class Tab1InnerTravelListViewAdapter extends BaseAdapter {

    private ArgumentOnClick argument;

    private List<Travel> mLists;
    private LayoutInflater inflater;

    private Context context;

    public Tab1InnerTravelListViewAdapter(Context context, List<Travel> mLists, ArgumentOnClick listener){
        this.mLists = mLists;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
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
            view = inflater.inflate(R.layout.tab1_inner_travel_listview_item, null);
            holder = new ViewHolder();
            holder.ivDestination = (ImageView) view.findViewById(R.id.iv_tab1_2_inner_travel);
            holder.tvTitle = (TextView)view.findViewById(R.id.tv_tab1_2_inner_travle_title);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_tab1_2_inner_travle_prirce);
            holder.tvType = (TextView) view.findViewById(R.id.tv_tab1_2_inner_travel_type);
            holder.tvArgument = (TextView) view.findViewById(R.id.tv_inner_travel_item_argument);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Travel travel = (Travel) getItem(position);

        final View finalConvertView = view;
        final int id = holder.tvArgument.getId();

        holder.tvArgument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(argument != null){
                    argument.goToArgument(view, viewGroup, position, id);
                }
            }
        });

        if(travel != null){
            //TODO
            if("跟团游".equals(travel.getType())){
                holder.tvType.setBackgroundColor(context.getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            }else if("自由游".equals(travel.getType())){
                holder.tvType.setBackgroundColor(context.getResources().getColor(R.color.colorFreedom));
            }
            holder.tvType.setText(travel.getType());
            holder.tvTitle.setText(travel.getTravelTitle());
            holder.tvPrice.setText(travel.getTravelPrice());
            ImageLoaderUtil.getInstance(context).displayImage(travel.getTravelIconPath(), holder.ivDestination);
        }
        return view;
    }

    public void setData(List<Travel> listNew){
        this.mLists = listNew;
        notifyDataSetChanged();
    }

    public void addData(List<Travel> listAdd){
        this.mLists.addAll(listAdd);
        notifyDataSetChanged();
    }

    class ViewHolder{
        private ImageView ivDestination;
        private TextView  tvTitle;
        private TextView  tvPrice;
        private TextView  tvType;
        private TextView  tvArgument;
    }

}
