package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.moudle.HotDestination;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/9/26.
 */
public class VisaHotCountryGridAdapter extends MyBaseAdapter {



    public VisaHotCountryGridAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_visa_main_hot_country, null);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_visa_country_img);
            holder.tvName = (TextView) view.findViewById(R.id.tv_visa_country_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        CityRecommend hotVisa = (CityRecommend) list.get(i);
        holder.tvName.setText(hotVisa.getCityName());
        ImageLoaderUtil.getInstance(context).displayImage(hotVisa.getCityImage(), holder.imageView);
        return view;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }
//    public void setData(List<CityRecommend> listHotCountry) {
//        this.list = listHotCountry;
//        notifyDataSetChanged();
//    }

    class ViewHolder{
        private ImageView imageView;
        private TextView tvName;
    }
}
