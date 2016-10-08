package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.util.List;

/**
 * Created by jiahe008 on 2016/9/8.
 */
public class Tab2LeftListViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    private int selection;

    public Tab2LeftListViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LeftViewHolder holder = null;
        if(view == null){
            holder = new LeftViewHolder();
            view = inflater.inflate(R.layout.item_tab2_left_listview, null);
            holder.tvAreaName = (TextView) view.findViewById(R.id.tv_tab2_left_item);
            view.setTag(holder);
        }else{
            holder = (LeftViewHolder) view.getTag();
        }
        if (i == selection){
            holder.tvAreaName.setBackgroundResource(android.R.color.white);
            holder.tvAreaName.setTextColor(context.getResources().getColor(R.color.colorActionBar));
        }else{
            holder.tvAreaName.setBackgroundResource(R.color.colorBgTab2LeftNormal);
            holder.tvAreaName.setTextColor(context.getResources().getColor(android.R.color.black));
        }
        holder.tvAreaName.setText(list.get(i));
        return view;
    }

    public void setData(List<String> listLeft) {
        this.list = listLeft;
        notifyDataSetChanged();
    }

    public void setSelection(int selection){
        this.selection = selection;
    }

    class LeftViewHolder{
        private TextView tvAreaName;
    }
}
