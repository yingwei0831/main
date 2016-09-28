package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.UserIcon;

import java.util.List;

/**
 * Created by birney1 on 16/9/24.
 */
public class UserIconListAdapter extends MyBaseAdapter {

    public UserIconListAdapter(Context ct, List list, Object view) {
        super(ct, list, view);
    }

    public UserIconListAdapter(Context ct, List list) {
        super(ct, list);
    }

    public void setData(List<UserIcon> icons){
        list = icons;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_my_tourism_cion, null);
            holder = new ViewHolder();
            holder.tvContent = (TextView) view.findViewById(R.id.tv_item_my_tourism_coin);
            holder.tvScore = (TextView) view.findViewById(R.id.tv_item_my_tourism_score);
            holder.tvAddTime = (TextView) view.findViewById(R.id.tv_item_my_tourim_time);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        UserIcon icon = (UserIcon) getItem(i);
        holder.tvContent.setText(icon.getContent());
        holder.tvScore.setText(icon.getScore());
        holder.tvAddTime.setText(icon.getAddtime());
        return view;
    }

    class ViewHolder{
        private TextView tvContent;
        private TextView tvAddTime;
        private TextView tvScore;

    }
}
