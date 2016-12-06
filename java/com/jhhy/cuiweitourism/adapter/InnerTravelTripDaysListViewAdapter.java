package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/11.
 */
public class InnerTravelTripDaysListViewAdapter extends MyBaseAdapter {

    private int mPosition; //选中位置
    private int type; //国际机票筛选，添加type

    private Drawable drawableLight; //类型1和3的图片
    private Drawable drawableDark; //类型1和3的图片

    public InnerTravelTripDaysListViewAdapter(Context ct, List list) {
        super(ct, list);
        drawableLight = ContextCompat.getDrawable(context, R.drawable.unchecked);
        drawableDark = ContextCompat.getDrawable(context, R.drawable.check);
        drawableLight.setBounds(0, 0, drawableLight.getMinimumWidth(), drawableLight.getMinimumHeight());
        drawableDark.setBounds(0, 0, drawableDark.getMinimumWidth(), drawableDark.getMinimumHeight());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            InnerTravelTripDaysViewHolder holder = null;
            if (null == view) {
                view = LayoutInflater.from(context).inflate(R.layout.inner_trip_days_listview_item, viewGroup, false);
                holder = new InnerTravelTripDaysViewHolder();
                holder.tvTripDays = (TextView) view.findViewById(R.id.tv_item_inner_trip_days_listview_item);
                view.setTag(holder);
            } else {
                holder = (InnerTravelTripDaysViewHolder) view.getTag();
            }
            if (i == mPosition) {
                holder.tvTripDays.setTextColor(context.getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                holder.tvTripDays.setCompoundDrawables(null, null, drawableDark, null);
            } else {
                holder.tvTripDays.setTextColor(context.getResources().getColor(android.R.color.black));
                holder.tvTripDays.setCompoundDrawables(null, null, drawableLight, null);
            }
        if (type == 1){ //起飞时间，string
            String time = (String) getItem(i);
            if (null != time) holder.tvTripDays.setText(time);
        }else if (type == 2){ //机场
            PlaneTicketInternationalInfo.AirportInfo airportInfo = (PlaneTicketInternationalInfo.AirportInfo) getItem(i);
            if (null != airportInfo) holder.tvTripDays.setText(airportInfo.fullName);
        }else if (type == 3){ //机型
            PlaneTicketInternationalInfo.AircraftTypeInfo aircraftTypeInfo = (PlaneTicketInternationalInfo.AircraftTypeInfo) getItem(i);
            if (null != aircraftTypeInfo) {
                String name = aircraftTypeInfo.typeName;
                if (name != null && name.length() != 0) {
                    holder.tvTripDays.setText(name);
                }else{
                    holder.tvTripDays.setText(aircraftTypeInfo.typeCode);
                }
            }
        }else if (type == 4){ //航空公司
            PlaneTicketInternationalInfo.AirlineCompanyInfo airlineCompanyInfo = (PlaneTicketInternationalInfo.AirlineCompanyInfo) getItem(i);
            if (null != airlineCompanyInfo) {
                String name = airlineCompanyInfo.companyName;
                if (name != null && name.length() != 0) {
                    holder.tvTripDays.setText(name);
                }else{
                    holder.tvTripDays.setText(airlineCompanyInfo.airlineCompanyCode);
                }
            }
        }
        else {
            String tripDays = (String) getItem(i);
            if (null != tripDays) holder.tvTripDays.setText(tripDays);
        }
        return view;
    }

    public void setSelectPosition(int position) {
        mPosition = position;
    }

    public void setType(int type){
        this.type = type;
    }

    class InnerTravelTripDaysViewHolder{
        private TextView tvTripDays;
    }
}
