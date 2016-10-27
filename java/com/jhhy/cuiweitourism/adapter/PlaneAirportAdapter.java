package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.ui.CitySelectionActivity;
import com.jhhy.cuiweitourism.view.PinnedSectionListView;

import java.util.ArrayList;
import java.util.HashMap;


public class PlaneAirportAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private LayoutInflater layoutInflater;
    /**
     * 数据集
     */
    private ArrayList<PlaneTicketCityInfo> list;
    /**
     * 首字母
     */
    public HashMap<String, Integer> map_IsHead;

    public PlaneAirportAdapter(Context context, ArrayList<PlaneTicketCityInfo> list, HashMap<String, Integer> map_IsHead) {
        this.list = list;
        this.map_IsHead = map_IsHead;
        layoutInflater = LayoutInflater.from(context);
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }
    
    //实现自定义listview的接口
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == CitySelectionActivity.TITLE;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        switch (getItemViewType(i)) {
            case CitySelectionActivity.ITEM:
                if (view == null) {
                    viewHolder = new ViewHolder();
                    view = layoutInflater.inflate(R.layout.item_phone_item, null);
                    viewHolder.txt = (TextView) view.findViewById(R.id.item_phone_txt_name);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }

                //设置名字
                viewHolder.txt.setText(list.get(i).getName());
                break;
            case CitySelectionActivity.TITLE:
                if (view == null) {
                    viewHolder = new ViewHolder();
                    view = layoutInflater.inflate(R.layout.item_phone_title, null);
                    viewHolder.txt = (TextView) view.findViewById(R.id.item_phone_txt_head);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }
                //设置标题
                viewHolder.txt.setText(list.get(i).headChar);
                break;
        }

        return view;
    }


    private class ViewHolder {
        private TextView txt;
    }

}
