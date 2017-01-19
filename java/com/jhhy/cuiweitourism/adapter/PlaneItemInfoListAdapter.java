package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;
import com.jhhy.cuiweitourism.ui.PlaneListInternationalActivity;

import java.util.List;

/**
 * Created by jiahe008 on 2016/10/25.
 */
public abstract class PlaneItemInfoListAdapter extends MyBaseAdapter implements OnItemTextViewClick {

    private int type; //1,国际机票; 否则国内机票

    public PlaneItemInfoListAdapter(Context ct, List list) {
        super(ct, list);
    }

    public void setType(int type){
        this.type = type;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_plane_seat_type, null);
            holder = new ViewHolder();
            holder.tvTypeSeat = (TextView) view.findViewById(R.id.tv_train_ticket_seat);
            holder.tvTicketPrice = (TextView) view.findViewById(R.id.tv_plane_ticket_price);
            holder.tvRefundNotice = (TextView) view.findViewById(R.id.tv_plane_ticket_refund);
            holder.btnReserveTicket = (Button) view.findViewById(R.id.btn_plane_ticket_reserve);
            holder.tvTaxPrice = (TextView) view.findViewById(R.id.tv_tax_price);
            holder.viewGreen = view.findViewById(R.id.view_green);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        final Button btnView = holder.btnReserveTicket;
        final int btnId = holder.btnReserveTicket.getId();
        holder.btnReserveTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemTextViewClick(i, btnView, btnId);
            }
        });

        final TextView tvRefundInfo = holder.tvRefundNotice;
        final int tvId = holder.tvRefundNotice.getId();
        holder.tvRefundNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemTextViewClick(i, tvRefundInfo, tvId);
            }
        });

        if (type == 1) {
            holder.tvTaxPrice.setVisibility(View.VISIBLE);
            PlaneTicketInternationalInfo.PlaneTicketInternationalHFCabin cabin = (PlaneTicketInternationalInfo.PlaneTicketInternationalHFCabin) getItem(i);

            String[] cabinTypes = cabin.passengerType.airportCabinType.split(",");
            StringBuffer sb = new StringBuffer();
            for (String cabinType1 : cabinTypes) {
                sb.append(PlaneListInternationalActivity.info.R.get(cabinType1)).append("|");
            }
            String cabinType =  sb.toString().substring(0, sb.length()-1);

            holder.tvTypeSeat.setText(cabinType);
            holder.tvTicketPrice.setText(String.format("￥%s", cabin.totalFare.taxTotal)); //票面价 cabin.baseFare.faceValueTotal ; 含税总价：hf.cabin.totalFare.taxTotal
            holder.tvTaxPrice.setText(String.format("税费：￥%s", cabin.passengerType.taxTypeCodeMap.get("XT").price)); //税费xxx; 含税总价；
        }else{
            holder.viewGreen.setVisibility(View.GONE);
            PlaneTicketInfoOfChina.SeatItemInfo seatItem = (PlaneTicketInfoOfChina.SeatItemInfo) getItem(i);
            holder.tvTypeSeat.setText(String.format("%s%s折", seatItem.seatMsg, seatItem.discount));
            holder.tvTicketPrice.setText(String.format("￥%s", seatItem.parPrice));
        }
        return view;
    }

    class ViewHolder{
        private TextView tvTypeSeat;
        private TextView tvTicketPrice;
        private TextView tvRefundNotice;
        private Button btnReserveTicket;
        private TextView tvTaxPrice;
        private View viewGreen;
    }
}
