package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.CustomActivity;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by birney1 on 16/9/25.
 * 我的收藏
 */
public abstract class UserReleaseListAdapter extends MyBaseAdapter
        implements ArgumentOnClick
{

    private String TAG = UserReleaseListAdapter.class.getSimpleName();

    private boolean check;
    private boolean visible;

    public UserReleaseListAdapter(Context ct, List list) {
        super(ct, list);
    }

    public void setVisible(boolean visible){
        this.visible = visible;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        CollectionViewHolder holder = null;
        if (view == null){
            holder = new CollectionViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_my_release, null);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_release_dest);
            holder.radioButton = (RadioButton) view.findViewById(R.id.rb_release_select);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_my_release_title);
            holder.tvReleaseTime = (TextView) view.findViewById(R.id.tv_my_release_time);
            view.setTag(holder);
        } else {
            holder = (CollectionViewHolder) view.getTag();
        }
        CustomActivity custActy = (CustomActivity) getItem(i);
        ImageLoaderUtil.getInstance(context).displayImage(custActy.getActivityLitpic(), holder.imageView);
        holder.tvTitle.setText(custActy.getActivityTitle());
        holder.tvReleaseTime.setText(custActy.getActyAddTime());
        final int viewId = holder.radioButton.getId();
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToArgument(view, viewGroup, i, viewId);
            }
        });
        if (visible){
            holder.radioButton.setVisibility(View.VISIBLE);
            if (custActy.isSelection()){
                holder.radioButton.setChecked(true);
            }else{
                holder.radioButton.setChecked(false);
            }
        }else{
            custActy.setSelection(false);
            holder.radioButton.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }
//    public void setData(List<CustomActivity> lists) {
//        this.list = lists;
//        notifyDataSetChanged();
//    }

    class CollectionViewHolder{
        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvReleaseTime;
        private RadioButton radioButton;
    }
}
