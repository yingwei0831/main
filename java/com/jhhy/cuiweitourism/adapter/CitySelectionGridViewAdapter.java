package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/23.
 */
public class CitySelectionGridViewAdapter extends MyBaseAdapter {

    private LayoutInflater inflater;

    public CitySelectionGridViewAdapter(Context ct, List<String> list) {
        super(ct, list);
        inflater = LayoutInflater.from(ct);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gridview_city_selection, null);
            holder.tvCityName = (TextView) convertView.findViewById(R.id.tv_item_gridview_city_selection);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String info = (String) getItem(position);
        holder.tvCityName.setText(info);
        return convertView;
    }

    class ViewHolder {
        TextView tvCityName;
    }
}
