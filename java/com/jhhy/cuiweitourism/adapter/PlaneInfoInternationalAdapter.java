package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketDetailInternationalResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.PlaneListInternationalActivity;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.List;
import java.util.Locale;

/**
 * Created by jiahe008 on 2016/12/15.
 * 国际机票详情，国际机票订单详情
 */
public class PlaneInfoInternationalAdapter extends MyBaseAdapter {

    private String TAG = "PlaneInfoInternationalAdapter";
    private PlaneTicketInternationalInfo.PlaneTicketInternationalHFCabin cabin; //舱位类型
    private String travelType;
    private boolean typeOrder; //订单中类型 1
    private Drawable top;

    public PlaneInfoInternationalAdapter(Context ct, List list) {
        super(ct, list);
        top = ContextCompat.getDrawable(context, R.mipmap.arrow_right_green_single);
        top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
    }
    public void setOrderType(boolean typeOrder){
        this.typeOrder = typeOrder;
    }

    public PlaneInfoInternationalAdapter(Context ct, List list, PlaneTicketInternationalInfo.PlaneTicketInternationalHFCabin cabin) {
        super(ct, list);
        this.cabin = cabin;
    }

    public void setTravelType(String travelType){
        this.travelType = travelType;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_plane_international_info, null);
            holder.tvFromAirport = (TextView) view.findViewById(R.id.tv_plane_start_station_international);
            holder.tvArrivalAirport = (TextView) view.findViewById(R.id.tv_plane_end_station_international);
            holder.tvTimeConsuming = (TextView) view.findViewById(R.id.tv_plane_order_time_consuming_second_international);
            holder.tvCabinType = (TextView) view.findViewById(R.id.tv_plane_order_time_consuming_international);
            holder.tvFromTime = (TextView) view.findViewById(R.id.tv_plane_start_time_international);
            holder.tvArrivalTime = (TextView) view.findViewById(R.id.tv_plane_end_time_international);
            holder.tvPlaneInfo = (TextView) view.findViewById(R.id.tv_plane_order_plane_date);
            view.setTag(holder);
            if (typeOrder){
                holder.tvFromAirport.setTextColor(Color.BLACK);
                holder.tvArrivalAirport.setTextColor(Color.BLACK);
                holder.tvFromTime.setTextColor(Color.BLACK);
                holder.tvArrivalTime.setTextColor(Color.BLACK);
                holder.tvPlaneInfo.setTextColor(Color.BLACK);
                holder.tvCabinType.setCompoundDrawables(null, top, null, null);
            }
        }else{
            holder = (ViewHolder) view.getTag();
        }

        if (typeOrder){ //订单页中航段列表
            view.setBackgroundColor(Color.WHITE);
            PlaneTicketDetailInternationalResponse.InterFlightsBean flightItem = (PlaneTicketDetailInternationalResponse.InterFlightsBean) getItem(i); //航段
            holder.tvFromAirport.setText(String.format(Locale.getDefault(), "%s%s", flightItem.getBoardPoint(), flightItem.getBoardPointAT())); //起飞机场
            holder.tvArrivalAirport.setText(String.format(Locale.getDefault(), "%s%s", flightItem.getOffPoint(), flightItem.getOffPointAT())); //到达机场
            holder.tvTimeConsuming.setVisibility(View.INVISIBLE); //耗时
            holder.tvCabinType.setText(" "); //舱位类型
            holder.tvFromTime.setText(flightItem.getDepartureTime());
            holder.tvArrivalTime.setText(flightItem.getArrivalTime());
            holder.tvPlaneInfo.setText(String.format(Locale.getDefault(), "%s %s | %s | %s", flightItem.getCarrier(), flightItem.getFlightNo(), flightItem.getClassRank(), flightItem.getDepartureDate()));
        }else {
            PlaneTicketInternationalInfo.FlightInfo flightInfo = (PlaneTicketInternationalInfo.FlightInfo) getItem(i);
//        LogUtil.e(TAG, "flightInfo = " + flightInfo);
            PlaneTicketInternationalInfo.AircraftTypeInfo airline = PlaneListInternationalActivity.info.J.get(flightInfo.flightTypeCheck);
            LogUtil.e(TAG, "key = " + flightInfo.flightTypeCheck + ";   " + airline);
            String info;
            if (airline.airframe == null || airline.airframe.length() == 0) {
                info = String.format("%s%s | %s | %s", PlaneListInternationalActivity.info.A.get(flightInfo.airlineCompanyCheck).shortName, flightInfo.flightNumberCheck,
                        airline.typeName, Utils.getDateStrYMDE(flightInfo.fromDateCheck));
            } else {
                info = String.format("%s%s | %s (%s) | %s", PlaneListInternationalActivity.info.A.get(flightInfo.airlineCompanyCheck).shortName, flightInfo.flightNumberCheck,
                        airline.typeName, airline.airframe, Utils.getDateStrYMDE(flightInfo.fromDateCheck));
            }
            holder.tvPlaneInfo.setText(info);
            holder.tvFromAirport.setText(String.format(Locale.getDefault(), "%s%s",
                    PlaneListInternationalActivity.info.P.get(flightInfo.fromAirportCodeCheck).fullName, flightInfo.fromTerminal)); //起飞机场，起飞航站楼
            holder.tvArrivalAirport.setText(String.format(Locale.getDefault(), "%s%s",
                    PlaneListInternationalActivity.info.P.get(flightInfo.toAirportCodeCheck).fullName, flightInfo.toTermianl)); //到达机场，到达航站楼
//        holder.tvTimeConsuming.setText(Utils.getDiffMinuteStr(
//                String.format(Locale.getDefault(), "%s %s", flightInfo.fromDateCheck,  flightInfo.fromTimeCheck),
//                String.format(Locale.getDefault(), "%s %s", flightInfo.toDateCheck, flightInfo.toTimeCheck)));
            holder.tvTimeConsuming.setText(Utils.getPeriod(flightInfo.flightPeriod)); //耗时
            LogUtil.e(TAG, "舱位类型：" + cabin.passengerType.airportCabinType);
            String[] cabinType = cabin.passengerType.airportCabinType.split("/"); //去程/返程
            if ("RT".equals(travelType)) { //返程
                if (cabinType[1].length() == 1) {
                    holder.tvCabinType.setText(PlaneListInternationalActivity.info.R.get(cabinType[1])); //舱位类型 经济舱
                } else {
                    String[] cabinTypeS2 = cabinType[1].split(",");
                    if (cabinTypeS2.length > 1) {
                        holder.tvCabinType.setText(PlaneListInternationalActivity.info.R.get(cabinTypeS2[i])); //舱位类型 经济舱
                    } else {
                        holder.tvCabinType.setText(PlaneListInternationalActivity.info.R.get(cabinTypeS2[0])); //舱位类型 经济舱
                    }
                }
            } else { //去程
                if (cabinType[0].length() == 1) {
                    holder.tvCabinType.setText(PlaneListInternationalActivity.info.R.get(cabinType[0])); //舱位类型 经济舱
                } else {
                    String[] cabinTypeS1 = cabinType[0].split(",");
                    if (cabinTypeS1.length > 1) {
                        holder.tvCabinType.setText(PlaneListInternationalActivity.info.R.get(cabinTypeS1[i])); //舱位类型 经济舱
                    } else {
                        holder.tvCabinType.setText(PlaneListInternationalActivity.info.R.get(cabinTypeS1[0])); //舱位类型 经济舱
                    }
                }
            }
            holder.tvFromTime.setText(flightInfo.fromTimeCheck);
            holder.tvArrivalTime.setText(flightInfo.toTimeCheck);
        }
        return view;
    }

    class ViewHolder{
        private TextView tvFromAirport;
        private TextView tvArrivalAirport;
        private TextView tvTimeConsuming; //耗时
        private TextView tvCabinType;
        private TextView tvFromTime;
        private TextView tvArrivalTime;
        private TextView tvPlaneInfo;
    }
}
