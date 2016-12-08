package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.util.List;

/**
 * Created by jiahe008 on 2016/9/27.
 */
public class VisaConnectionListAdapter extends MyBaseAdapter {

    private int selection = 0;

    public VisaConnectionListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_visa_select_city, null);
            holder.tvName = (TextView) view.findViewById(R.id.tv_area_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        String area = (String) list.get(i);
        holder.tvName.setText(area);
        if (selection == i){
            holder.tvName.setBackgroundColor(Color.WHITE);
        }else{
            holder.tvName.setBackgroundColor(context.getResources().getColor(R.color.colorInnerTravelPopLeftBg));
        }
        return view;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }
//    public void setData(List<ClassifyArea> area){
//        this.list = area;
//        notifyDataSetChanged();
//    }

    public void setSelection(int selection){
        this.selection = selection;
    }

    class  ViewHolder{
        private TextView tvName;
    }
}
