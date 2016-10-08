package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.view.CircleImageView;

import java.util.List;

/**
 * Created by birney1 on 16/9/29.
 */
public class UserCommentListAdapter extends MyBaseAdapter {

    public UserCommentListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_my_comment, null);
            holder = new ViewHolder();
            holder.civIconOther = (CircleImageView) view.findViewById(R.id.civ_item_my_comment_user_portrait);
            holder.tvNameOther = (TextView) view.findViewById(R.id.tv_item_my_comment_user_name);
            holder.tvCommentTime = (TextView) view.findViewById(R.id.tv_comment_time);
            holder.ivTitleImage = (ImageView) view.findViewById(R.id.iv_content_be_comment);
            holder.tvContent = (TextView) view.findViewById(R.id.tv_content_be_comment);
            holder.tvMyContent = (TextView) view.findViewById(R.id.tv_my_comment_content);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    class ViewHolder {
        private CircleImageView civIconOther; //谁回复我的——>谁发的
        private TextView tvNameOther;
        private TextView tvCommentTime;
        private ImageView ivTitleImage; //谁发的什么内容
        private TextView tvContent; //谁发的什么内容
        private TextView tvMyContent; //我评论的什么内容
    }
}
