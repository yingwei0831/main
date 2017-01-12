package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.Line;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.PlaneListActivity;
import com.jhhy.cuiweitourism.ui.PlaneListInternationalActivity;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/10/26.
 */
public class PlaneListAdapter extends MyBaseAdapter {

    private String TAG = "PlaneListAdapter";
    private int type; //1：国内机票  2：国外机票
    private int priceType; //2:票价+税费；1:含税票价
    private int traveltype; //1:返程 0:单程
    private int typeMultiply; //转机

    private PlaneTicketCityInfo fromCity;
    private PlaneTicketCityInfo toCity;

    public PlaneListAdapter(Context ct, List list, PlaneTicketCityInfo fromCity, PlaneTicketCityInfo toCity, int type){
        super(ct, list);
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.type = type;
    }

    public void setTraveltype(int traveltype){
        this.traveltype = traveltype;
    }

    public void setPriceType(int priceType){
        this.priceType = priceType;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_plane_list_return, null);
            holder.tvStartTime = (TextView) view.findViewById(R.id.tv_plane_start_time);
            holder.tvFromAirport = (TextView) view.findViewById(R.id.tv_plane_from_airport);
            holder.tvArrivalTime = (TextView) view.findViewById(R.id.tv_plane_arrival_time);
            holder.tvArrivalAirport = (TextView) view.findViewById(R.id.tv_plane_arrival_airport);
            holder.tvTicketPrice = (TextView) view.findViewById(R.id.tv_plane_ticket_price);
            holder.tvPlaneClass = (TextView) view.findViewById(R.id.tv_plane_ticket_class);
            holder.tvPlaneInfo = (TextView) view.findViewById(R.id.tv_plane_info);
            holder.tvArrivalType = (TextView) view.findViewById(R.id.tv_plane_arrival_type);
            holder.tvConsumingTime = (TextView) view.findViewById(R.id.tv_plane_consuming_time);
//            holder.tvTicketNum = (TextView) view.findViewById(R.id.tv_plane_ticket_number);

            holder.layoutReturnTop = (RelativeLayout) view.findViewById(R.id.layout_return_top);
            holder.layoutReturnBottom = (LinearLayout) view.findViewById(R.id.layout_return_bottom);
//            holder.layoutReturnTop.setVisibility(View.GONE);
//            holder.layoutReturnBottom.setVisibility(View.GONE);

            holder.tvStartTimeReturn = (TextView) view.findViewById(R.id.tv_plane_start_time_return);
            holder.tvFromAirportReturn = (TextView) view.findViewById(R.id.tv_plane_from_airport_return);
            holder.tvArrivalTimeReturn = (TextView) view.findViewById(R.id.tv_plane_arrival_time_return);
            holder.tvArrivalAirportReturn = (TextView) view.findViewById(R.id.tv_plane_arrival_airport_return);
//            holder.tvTicketPriceReturn = (TextView) view.findViewById(R.id.tv_plane_ticket_price_return);
//            holder.tvPlaneClassReturn = (TextView) view.findViewById(R.id.tv_plane_ticket_class_return);
            holder.tvPlaneInfoReturn = (TextView) view.findViewById(R.id.tv_plane_info_return);
            holder.tvArrivalTypeReturn = (TextView) view.findViewById(R.id.tv_plane_arrival_type_return);
            holder.tvConsumingTimeReturn = (TextView) view.findViewById(R.id.tv_plane_consuming_time_return);
            holder.tvTicketNum = (TextView) view.findViewById(R.id.tv_plane_ticket_number_return);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        if (type == 1) { //国内机票
            PlaneTicketInfoOfChina.FlightInfo flight = (PlaneTicketInfoOfChina.FlightInfo) getItem(i);
            if (flight != null) {
                holder.tvStartTime.setText(String.format("%s:%s", flight.depTime.substring(0, 2), flight.depTime.substring(2)));
                holder.tvFromAirport.setText(fromCity.getAirportname());
                holder.tvArrivalTime.setText(String.format("%s:%s", flight.arriTime.substring(0, 2), flight.arriTime.substring(2)));
                holder.tvArrivalAirport.setText(toCity.getAirportname());
                ArrayList<PlaneTicketInfoOfChina.SeatItemInfo> seatItems = flight.getSeatItems();
                Collections.sort(seatItems);
                PlaneTicketInfoOfChina.SeatItemInfo seat = seatItems.get(0);
                holder.tvTicketPrice.setText(String.format("￥%s", seat.getParPrice()));
                holder.tvPlaneClass.setText(seat.getSeatMsg());
                holder.tvPlaneInfo.setText(String.format("%s %s", seat.getFlightNo(), flight.planeType));
            }
        }else if (type == 2){ //国际机票
            PlaneTicketInternationalInfo.PlaneTicketInternationalF flight = (PlaneTicketInternationalInfo.PlaneTicketInternationalF) getItem(i);
            if (flight != null){

                PlaneTicketInternationalInfo.PlaneTicketInternationalHF hf = PlaneListInternationalActivity.info.HMap.get(flight.F);

                LogUtil.e(TAG, "舱位代码：" + hf.cabin.passengerType.airportCabinType); // 舱位代码：B,B,F   E/E,B,B
                String[] cabinTypes = hf.cabin.passengerType.airportCabinType.split("/");
                LogUtil.e(TAG, "舱位代码：size = " + cabinTypes.length + "; " + Arrays.toString(cabinTypes));

                if (traveltype == 1){ //有返程
                    LogUtil.e(TAG, "cabinTypes[1] = " +cabinTypes[1]);
                    holder.layoutReturnTop.setVisibility(View.VISIBLE);
                    holder.layoutReturnBottom.setVisibility(View.VISIBLE);
                    PlaneTicketInternationalInfo.PlaneTicketInternationalFS s2 = flight.S2;

                    holder.tvStartTimeReturn.setText(s2.fromTime);
                    holder.tvArrivalTimeReturn.setText(s2.toTime);
                    if ("0".equals(s2.transferFrequency)) { //直达
                        holder.tvArrivalTypeReturn.setText(context.getString(R.string.plane_flight_single));
                        holder.tvPlaneInfoReturn.setText(String.format("%s%s | %s",
                                s2.flightInfos.get(0).flightNumberCheck,
                                PlaneListInternationalActivity.info.J.get(s2.flightInfos.get(0).flightTypeCheck).typeName,
                                PlaneListInternationalActivity.info.R.get(cabinTypes[1]))); //飞机/舱位 MU5003空客A320(J)|经济舱(R)
                    } else { //中转
                        holder.tvArrivalTypeReturn.setText(context.getString(R.string.plane_flight_unsingle));
                        String[] cabinTypeS2 = cabinTypes[1].split(",");
                        StringBuffer sb = new StringBuffer();
                        for (String str: cabinTypeS2){
                            sb.append(" ").append(PlaneListInternationalActivity.info.R.get(str)).append(" |");
                        }
                        holder.tvPlaneInfoReturn.setText(String.format("%s%s |%s",
                                s2.flightInfos.get(0).flightNumberCheck,
                                PlaneListInternationalActivity.info.J.get(s2.flightInfos.get(0).flightTypeCheck).typeName,
                                sb.toString().substring(0, sb.toString().lastIndexOf(" ")))); //飞机/舱位 MU5003空客A320(J)|经济舱(R) 中转显示全部舱位类型
                    }
//                    holder.tvConsumingTimeReturn.setText(Utils.getDiffMinuteStr(String.format("%s %s", s2.fromDate, s2.fromTime), String.format("%s %s", s2.toDate, s2.toTime))); //耗时
                    holder.tvConsumingTimeReturn.setText(Utils.getPeriod(s2.flightPeriodTotal)); //耗时 分钟—>xx时xx分
                    holder.tvFromAirportReturn.setText(String.format("%s%s", PlaneListInternationalActivity.info.P.get(s2.fromAirportCode).fullName, s2.fromAirportName)); //起飞机场/航站楼
                    holder.tvArrivalAirportReturn.setText(String.format("%s%s", PlaneListInternationalActivity.info.P.get(s2.toAirportCode).fullName, s2.toAirportName)); //起飞机场/航站楼
                }else{
                    holder.layoutReturnTop.setVisibility(View.GONE);
                    holder.layoutReturnBottom.setVisibility(View.GONE);
                }
                //单程
                holder.tvArrivalType.setVisibility(View.VISIBLE);
                holder.tvConsumingTime.setVisibility(View.VISIBLE);
                PlaneTicketInternationalInfo.PlaneTicketInternationalFS s1 = flight.S1;

                holder.tvStartTime.setText(s1.fromTime);
                holder.tvArrivalTime.setText(s1.toTime);
                if ("0".equals(s1.transferFrequency)) { //直达
                    holder.tvArrivalType.setText(context.getString(R.string.plane_flight_single));
                    holder.tvPlaneInfo.setText(String.format("%s%s | %s",
                            s1.flightInfos.get(0).flightNumberCheck,
                            PlaneListInternationalActivity.info.J.get(s1.flightInfos.get(0).flightTypeCheck).typeName,
                            PlaneListInternationalActivity.info.R.get(cabinTypes[0]))); //飞机/舱位 MU5003空客A320(J)|经济舱(R)
                } else { //中转
                    holder.tvArrivalType.setText(context.getString(R.string.plane_flight_unsingle));
                    String[] cabinTypeS1 = cabinTypes[0].split(",");
                    StringBuffer sb = new StringBuffer();
                    for (String str: cabinTypeS1){
                        sb.append(" ").append(PlaneListInternationalActivity.info.R.get(str)).append(" |");
                    }
                    holder.tvPlaneInfo.setText(String.format("%s%s |%s",
                            s1.flightInfos.get(0).flightNumberCheck,
                            PlaneListInternationalActivity.info.J.get(s1.flightInfos.get(0).flightTypeCheck).typeName,
                            sb.toString().substring(0, sb.toString().length() - 2))); //飞机/舱位 MU5003空客A320(J)|经济舱(R) 中转,取第一个显示
                }
//                holder.tvConsumingTime.setText(Utils.getDiffMinuteStr(String.format("%s %s", s1.fromDate, s1.fromTime), String.format("%s %s", s1.toDate, s1.toTime))); //耗时
                holder.tvConsumingTime.setText(Utils.getPeriod(s1.flightPeriodTotal));
                holder.tvFromAirport.setText(String.format("%s%s", PlaneListInternationalActivity.info.P.get(s1.fromAirportCode).fullName, s1.fromAirportName)); //起飞机场/航站楼
                holder.tvArrivalAirport.setText(String.format("%s%s", PlaneListInternationalActivity.info.P.get(s1.toAirportCode).fullName, s1.toAirportName)); //起飞机场/航站楼

                if (priceType == 1) { //含税总价
                    holder.tvTicketPrice.setText(String.format("￥%s", hf.cabin.totalFare.taxTotal));
                    holder.tvPlaneClass.setText(context.getString(R.string.plane_flight_tax));
                } else { //税价+票价
                    holder.tvTicketPrice.setText(String.format("￥%s", hf.cabin.baseFare.faceValueTotal)); //票面价 ; 含税总价：hf.cabin.totalFare.taxTotal
                    holder.tvPlaneClass.setText(String.format("税费:￥%s", hf.cabin.passengerType.taxTypeCodeMap.get("XT").price)); //税费xxx; 含税总价；
                }
                if (Integer.parseInt(hf.cabin.passengerType.cabinCount) >= 9){
                    holder.tvTicketNum.setVisibility(View.INVISIBLE);
                }else {
                    holder.tvTicketNum.setVisibility(View.VISIBLE);
                    holder.tvTicketNum.setText(String.format(Locale.getDefault(), "%s张", hf.cabin.passengerType.cabinCount));
                }
            }
        }
        return view;
    }

    class ViewHolder{
        private TextView tvStartTime;
        private TextView tvFromAirport;

        private TextView tvArrivalTime;
        private TextView tvArrivalAirport;

        private TextView tvTicketPrice;
        private TextView tvPlaneClass;  //舱位

        private TextView tvPlaneInfo;   //飞机信息
        private TextView tvTicketNum;   //机票数量

        private TextView tvArrivalType; //直达、中转
        private TextView tvConsumingTime; //耗时

        private RelativeLayout layoutReturnTop;
        private LinearLayout layoutReturnBottom;
        private TextView tvStartTimeReturn;
        private TextView tvFromAirportReturn;

        private TextView tvArrivalTimeReturn;
        private TextView tvArrivalAirportReturn;

//        private TextView tvTicketPriceReturn;
//        private TextView tvPlaneClassReturn;  //舱位

        private TextView tvPlaneInfoReturn;   //飞机信息
//        private TextView tvTicketNumReturn;   //机票数量

        private TextView tvArrivalTypeReturn; //直达、中转
        private TextView tvConsumingTimeReturn; //耗时


    }
}
