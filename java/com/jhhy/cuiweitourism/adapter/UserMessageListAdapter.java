package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.util.List;

/**
 * Created by birney1 on 16/9/29.
 */
public class UserMessageListAdapter extends MyBaseAdapter {

    public UserMessageListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_my_message, null);
            holder = new ViewHolder();
            holder.ivIcon = (ImageView) view.findViewById(R.id.iv_content_icon);
            holder.tvContent = (TextView) view.findViewById(R.id.tv_content_message);
            holder.tvComment = (TextView) view.findViewById(R.id.tv_content_comment);
            holder.tvName = (TextView) view.findViewById(R.id.tv_message_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    class ViewHolder {
        private ImageView ivIcon;
        private TextView tvContent;
        private TextView tvName;
        private TextView tvComment;
    }
}
