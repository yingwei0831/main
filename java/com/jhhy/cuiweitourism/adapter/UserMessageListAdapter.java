package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.MemberCenterMsgInfo;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.ArrayList;
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
            holder.line = view.findViewById(R.id.item_comment_line);
//            holder.tvComment = (TextView) view.findViewById(R.id.tv_content_comment);
//            holder.tvName = (TextView) view.findViewById(R.id.tv_message_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        MemberCenterMsgInfo info = (MemberCenterMsgInfo) getItem(i);
        if (info != null){
            ImageLoaderUtil.getInstance(context).displayImage(info.getLitpic(), holder.ivIcon);
            holder.tvContent.setText(info.getTitle());
           if (info.getPl() == null || info.getPl().size() == 0){
               holder.line.setVisibility(View.GONE);
           }else{
               holder.line.setVisibility(View.VISIBLE);
               for (int j = 0; j < info.getPl().size(); j++) {
                   MemberCenterMsgInfo.comment comment = info.getPl().get(i);
                   View commentView = LayoutInflater.from(context).inflate(R.layout.item_message_comment, null);
                   TextView tvName = (TextView) commentView.findViewById(R.id.tv_message_name);
                   TextView tvComment = (TextView) commentView.findViewById(R.id.tv_content_comment);
                   tvComment.setText(comment.getContent());
                   ((ViewGroup)view).addView(commentView);
               }
           }
        }
        return view;
    }

    public void setData(ArrayList<MemberCenterMsgInfo> mList) {
        this.list = mList;
        notifyDataSetChanged();
    }

    class ViewHolder {
//        private TextView tvAddTime;
        private ImageView ivIcon;
        private TextView tvContent;
        private View line;
//        private TextView tvName;
//        private TextView tvComment;
    }
}
