package com.jhhy.cuiweitourism.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOrderInternationalRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderDetailResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketOrderDetailResponse;

/**
 * Created by jiahe008 on 2016/9/23.
 */
public class TravelerInfoClass extends LinearLayout{
    private static final String TAG = InnerTravelDetailDescribeView.class.getSimpleName();

    private TextView tvName;
    private TextView tvMobile;
    private TextView tvID;
    private LinearLayout layoutId;

    public TravelerInfoClass(Context context) {
        this(context, null);
    }

    public TravelerInfoClass(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TravelerInfoClass(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.traveler_info, this);
        tvName = (TextView) rootView.findViewById(R.id.tv_order_traveler_name);
        tvMobile = (TextView) rootView.findViewById(R.id.tv_order_traveler_mobile);
        tvID = (TextView) rootView.findViewById(R.id.tv_order_traveler_id);
        layoutId = (LinearLayout) rootView.findViewById(R.id.layout_custom_id);
    }

    public void setShowView(UserContacts contacts){
        tvName.setText(contacts.getContactsName());
        tvMobile.setText(contacts.getContactsMobile());
        tvID.setText(contacts.getContactsIdCard());
    }
     public void setShowView(HotelOrderDetailResponse.CustomerBean contacts){
        tvName.setText(contacts.getName());
        tvMobile.setText(contacts.getMobile());
         layoutId.setVisibility(GONE);
    }
//    public void setShowView(PlaneTicketOrderInternationalRequest.PassengersBean contacts){
//        tvName.setText(contacts.getName()); //姓名
//        tvMobile.setText(contacts.getCardType()); //证件类型，证件号
//        tvID.setText(contacts.getCardNo()); //
//    }
}
