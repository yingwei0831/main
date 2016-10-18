package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HoutelPropertiesInfo;

import java.util.List;

/**
 * Created by jiahe008 on 2016/10/18.
 */
public class HotelSortListAdapter extends MyBaseAdapter {

    private int selection;
    private int type; //1:主，2:从

    private Drawable drawableLight; //类型1和3的图片
    private Drawable drawableDark; //类型1和3的图片

    public HotelSortListAdapter(Context ct, List list) {
        super(ct, list);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (type == 1) {
            if (null == view) {
                view = LayoutInflater.from(context).inflate(R.layout.inner_trip_first_listview_item, viewGroup, false);
                holder = new ViewHolder();
                holder.textView = (TextView) view.findViewById(R.id.tv_item_inner_trip_first_listview_item);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            HoutelPropertiesInfo hotelScreen = (HoutelPropertiesInfo) getItem(i);
            if (i == selection) {
                holder.textView.setTextColor(context.getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                holder.textView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            } else {
                holder.textView.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.textView.setBackgroundColor(context.getResources().getColor(R.color.colorInnerTravelPopLeftBg));
            }
            if (null != hotelScreen) {
                holder.textView.setText(hotelScreen.getAttrname());
            }
        } else if (type == 2) {
            if (null == view) {
                view = LayoutInflater.from(context).inflate(R.layout.inner_trip_days_listview_item, viewGroup, false);
                holder = new ViewHolder();
                holder.textView = (TextView) view.findViewById(R.id.tv_item_inner_trip_days_listview_item);
                view.setTag(holder);
                drawableLight = ContextCompat.getDrawable(context, R.drawable.unchecked);
                drawableDark = ContextCompat.getDrawable(context, R.drawable.check);
                drawableLight.setBounds(0, 0, drawableLight.getMinimumWidth(), drawableLight.getMinimumHeight());
                drawableDark.setBounds(0, 0, drawableDark.getMinimumWidth(), drawableDark.getMinimumHeight());
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (i == selection) {
                holder.textView.setTextColor(context.getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                holder.textView.setCompoundDrawables(null, null, drawableDark, null);
            } else {
                holder.textView.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.textView.setCompoundDrawables(null, null, drawableLight, null);
            }
            HoutelPropertiesInfo.Son tripDays = (HoutelPropertiesInfo.Son) getItem(i);
            if (null != tripDays) {
                holder.textView.setText(tripDays.getAttrname());
            }
        }
        return view;
    }


    public void setSelection(int selection) {
        this.selection = selection;
    }

    public void setType(int type) {
        this.type = type;
    }

    class ViewHolder {
        public TextView textView;
    }
}
