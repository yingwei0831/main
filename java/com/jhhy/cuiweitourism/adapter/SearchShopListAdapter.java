package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.Line;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by birney1 on 16/10/1.
 * 找商铺——>商铺线路列表
 */
public class SearchShopListAdapter extends MyBaseAdapter {

    public SearchShopListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_shop_line_list, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.iv_tab1_2_hot_activity);
            holder.tvTitle = (TextView)view.findViewById(R.id.tv_tab1_2_hot_activity_title);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_tab1_2_hot_activity_prirce);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Line line = (Line) getItem(i);
        if(line != null){
            holder.tvPrice.setText(line.getLinePrice());
            holder.tvTitle.setText(line.getLineTitle());
            ImageLoaderUtil.getInstance(context).displayImage(line.getLinePicPath(), holder.imageView);
        }
        return view;
    }

    public void setData(List<Line> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<Line> lists){
        this.list.addAll(lists);
        notifyDataSetChanged();
    }

    class ViewHolder{
        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvPrice;
    }
}
