package com.jhhy.cuiweitourism.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.UserContacts;

/**
 * Created by jiahe008 on 2016/9/23.
 */
public class TravelerInfoClass extends LinearLayout{
    private static final String TAG = InnerTravelDetailDescribeView.class.getSimpleName();

    private TextView tvName;
    private TextView tvMobile;
    private TextView tvID;

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
    }

    public void setShowView(UserContacts contacts){
        tvName.setText(contacts.getContactsName());
        tvMobile.setText(contacts.getContactsMobile());
        tvID.setText(contacts.getContactsIdCard());
    }
}
