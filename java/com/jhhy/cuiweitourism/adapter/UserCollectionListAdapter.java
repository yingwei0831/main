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
import com.jhhy.cuiweitourism.moudle.Collection;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.List;

/**
 * Created by birney1 on 16/9/25.
 * 我的收藏
 */
public abstract class UserCollectionListAdapter extends MyBaseAdapter implements ArgumentOnClick{

    private String TAG = UserCollectionListAdapter.class.getSimpleName();

    private boolean check;
    private boolean visible;

    public UserCollectionListAdapter(Context ct, List list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_my_collection, null);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_collection_dest);
            holder.radioButton = (RadioButton) view.findViewById(R.id.rb_collection_select);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_my_collection_title);
            holder.tvPrice = (TextView) view.findViewById(R.id.tv_my_collection_price);
            view.setTag(holder);
        } else {
            holder = (CollectionViewHolder) view.getTag();
        }
        Collection coll = (Collection) getItem(i);
        ImageLoaderUtil.getInstance(context).displayImage(coll.getColLitpic(), holder.imageView);
        holder.tvTitle.setText(coll.getColTitle());
        holder.tvPrice.setText(coll.getColPrice());
        final int viewId = holder.radioButton.getId();
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToArgument(view, viewGroup, i, viewId);
            }
        });
        if (visible){
            holder.radioButton.setVisibility(View.VISIBLE);
            if (coll.isSelection()){
                holder.radioButton.setChecked(true);
            }else{
                holder.radioButton.setChecked(false);
            }
        }else{
            coll.setSelection(false);
            holder.radioButton.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }
    //    public void setData(List<Collection> lists) {
//        this.list = lists;
//        notifyDataSetChanged();
//    }

    class CollectionViewHolder{
        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvPrice;
        private RadioButton radioButton;
    }
}
