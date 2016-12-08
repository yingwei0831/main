package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.ShopRecommend;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by birney1 on 16/9/29.
 */
public class SearchShopGridAdapter extends MyBaseAdapter {

    public SearchShopGridAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_shop_recommend, null);
            holder = new ViewHolder();
            holder.ivShopLogo = (ImageView) view.findViewById(R.id.iv_shop_logo);
            holder.tvShopName = (TextView) view.findViewById(R.id.tv_shop_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        ShopRecommend shop = (ShopRecommend) getItem(i);
        holder.tvShopName.setText(shop.getName());
        ImageLoaderUtil.getInstance(context).displayImage(shop.getLitpic(), holder.ivShopLogo);
        return view;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }

    //    public void setData(List<ShopRecommend> lists) {
//        this.list = lists;
//        notifyDataSetChanged();
//    }

    public void addData(List<ShopRecommend> lists){
        this.list.addAll(lists);
        notifyDataSetChanged();
    }

    class ViewHolder{
        private ImageView ivShopLogo;
        private TextView tvShopName;
    }
}
