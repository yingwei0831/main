package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.VisaHotCountry;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/9/26.
 */
public class VisaHotAnyCountryGridAdapter extends MyBaseAdapter {

    public VisaHotAnyCountryGridAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_visa_main_hot_any, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.iv_visa_country_any_img);
            holder.title = (TextView) view.findViewById(R.id.tv_visa_any_title);
            holder.price = (TextView) view.findViewById(R.id.tv_visa_hot_price);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        VisaHotCountry country = (VisaHotCountry) list.get(i);
        holder.title.setText(country.getTitle());
        holder.price.setText(country.getPrice());
        ImageLoaderUtil.getInstance(context).displayImage(country.getLitpic(), holder.imageView);
        return view;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }
//    public void setData(List<VisaHotCountry> lists){
//        this.list = lists;
//        notifyDataSetChanged();
//    }

    class ViewHolder{
        private ImageView imageView;
        private TextView title;
        private TextView price;
    }
}
