package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.CityRecommend;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/23.
 */
public class HotRecommendGridViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<CityRecommend> list;
    private Context context;

    public HotRecommendGridViewAdapter(Context ct, List<CityRecommend> list) {
        this.context = ct;
        inflater = LayoutInflater.from(ct);
        this.list = list;
    }

    public void setData(List<CityRecommend> newList){
        this.list = newList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gridview_hot_recommend, null);
            holder.tvCityName = (TextView) convertView.findViewById(R.id.tv_item_gridview_hot_recommend);
            holder.ivRecommend = (ImageView) convertView.findViewById(R.id.iv_item_gridview_hot_recommend);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CityRecommend info = (CityRecommend) getItem(position);
        holder.tvCityName.setText(info.getCityName());
        ImageLoaderUtil.getInstance(context).displayImage(info.getCityImage(), holder.ivRecommend);
        return convertView;
    }

    class ViewHolder {
        ImageView ivRecommend;
        TextView tvCityName;
    }
}
