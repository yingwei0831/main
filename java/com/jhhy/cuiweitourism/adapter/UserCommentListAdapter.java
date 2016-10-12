package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.MemberCenterRemarkInfo;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.CircleImageView;

import java.util.ArrayList;
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
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_content_be_title);
            holder.tvMyContent = (TextView) view.findViewById(R.id.tv_my_comment_content);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        MemberCenterRemarkInfo info = (MemberCenterRemarkInfo) list.get(i);
        if (info != null){
            holder.tvCommentTime.setText(Utils.getTimeStrYMD(Long.parseLong(info.getAddtime()) * 1000));
            holder.tvTitle.setText(info.getTitle());
            holder.tvMyContent.setText(info.getContent());
            holder.tvNameOther.setText(info.getSuppliername());
            ImageLoaderUtil.getInstance(context).displayImage(info.getLitpic(), holder.ivTitleImage);
            ImageLoaderUtil.getInstance(context).displayImage(info.getLogo(), holder.civIconOther);
        }
        return view;
    }

    public void setData(ArrayList<MemberCenterRemarkInfo> mList) {
        this.list = mList;
        notifyDataSetChanged();
    }

    class ViewHolder {
        private CircleImageView civIconOther; //谁回复我的——>谁发的
        private TextView tvNameOther;
        private TextView tvCommentTime;
        private TextView tvTitle; //谁发的什么内容
        private ImageView ivTitleImage; //谁发的什么内容
        private TextView tvMyContent; //我评论的什么内容
//        private TextView tvCommentTimeTop; //点评顶部时间
    }
}
