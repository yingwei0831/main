package com.jhhy.cuiweitourism.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

/**
 * Created by jiahe008 on 2016/12/7.
 */
public class PlaneInquiryView extends LinearLayout {

    private ImageView ivTrash; //删除
    private TextView tvFromCity; //出发城市
    private TextView tvArrivalCity; //到达城市
    private ImageView ivExchange; //交换出发地和目的地
    private TextView tvFromTime; //出发时间

    public PlaneInquiryView(Context context) {
        super(context);
        init(context);
    }

    public PlaneInquiryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlaneInquiryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlaneInquiryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plane_inquery, null);
        ivTrash = (ImageView) view.findViewById(R.id.iv_delete_one_query);
        tvFromCity = (TextView) view.findViewById(R.id.tv_plane_from_city);
        tvArrivalCity = (TextView) view.findViewById(R.id.tv_plane_to_city);
        ivExchange = (ImageView) view.findViewById(R.id.iv_train_exchange);
        tvFromTime = (TextView) view.findViewById(R.id.tv_plane_from_time);
        addListener();
    }

    private void addListener() {
//        ivTrash.setOnClickListener(this);
//        tvFromCity.setOnClickListener(this);
//        tvArrivalCity.setOnClickListener(this);
//        ivExchange.setOnClickListener(this);
//        tvFromTime.setOnClickListener(this);
    }

}
