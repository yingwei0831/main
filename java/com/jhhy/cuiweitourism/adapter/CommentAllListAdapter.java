package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.IOnItemClickListener;
import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndMoreCommentInfo;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.CircleImageView;
import com.jhhy.cuiweitourism.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahe008 on 2016/10/12.
 */
public abstract class CommentAllListAdapter extends MyBaseAdapter implements IOnItemClickListener {

    private ImageGridViewAdapter adapter;

    public CommentAllListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_comment_list, null);
            holder.tvNameOther = (TextView) view.findViewById(R.id.tv_item_comment_user_name);
            holder.tvCommentTime = (TextView) view.findViewById(R.id.tv_item_comment_time);
            holder.civIconOther = (CircleImageView) view.findViewById(R.id.civ_item_comment_user_portrait);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_item_comment_title);
            holder.gvImages = (MyGridView) view.findViewById(R.id.gv_images_pic);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        ForeEndMoreCommentInfo comment = (ForeEndMoreCommentInfo) getItem(i);
        if (comment != null){
            holder.tvTitle.setText(comment.getContent());
            holder.tvNameOther.setText(comment.getNickname());
            holder.tvCommentTime.setText(Utils.getTimeStrYMD(Long.parseLong(comment.getAddtime()) * 1000));
            ImageLoaderUtil.getInstance(context).displayImage(comment.getFace(), holder.civIconOther);
            adapter = new ImageGridViewAdapter(context,comment.getPllist());
            holder.gvImages.setAdapter(adapter);
        }

        holder.gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                onItemClickI(adapterView, view, position, id, i);
            }
        });
        return view;
    }

    public void setData(ArrayList<ForeEndMoreCommentInfo> array) {
        this.list = array;
        notifyDataSetChanged();
    }

    class ViewHolder{
        private CircleImageView civIconOther; //头像
        private TextView tvNameOther; //名称
        private TextView tvCommentTime; //时间
        private TextView tvTitle; //发布的评价内容
        private MyGridView gvImages; //评价的图片，最多显示3张图片，第四章图片显示文字
    }
}
