package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.VisaType;

import java.util.List;

/**
 * Created by jiahe008 on 2016/9/29.
 */
public class VisaTypeListAdapter extends MyBaseAdapter {

    private int selection = 0;

    public VisaTypeListAdapter(Context ct, List list) {
        super(ct, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_text_view, null);
            holder.tvType = (TextView) view.findViewById(R.id.tv_text);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        String visaType = (String) list.get(i);
        holder.tvType.setText(visaType);
        if (i == selection){
            holder.tvType.setTextColor(context.getResources().getColor(R.color.colorActionBar));
        }else{
            holder.tvType.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        notifyDataSetChanged();
    }

    public void setSelection(int position){
        this.selection = position;
    }

    class ViewHolder {
        private TextView tvType;
    }
}
