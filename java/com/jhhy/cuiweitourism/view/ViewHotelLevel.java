package com.jhhy.cuiweitourism.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

/**
 * Created by jiahe008 on 2016/10/17.
 */
public class ViewHotelLevel extends LinearLayout {

    private Context context;
    private View left;
    private View right;
    private ImageView ivCircle;
    private ImageView ivCircleMove;
    private TextView tvPrice;

    public ViewHotelLevel(Context context) {
        super(context);
        init(context);
    }

    public ViewHotelLevel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewHotelLevel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewHotelLevel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.item_price_arange, null);
        left = view.findViewById(R.id.view_hotel_left);
        right = view.findViewById(R.id.view_hotel_right);
        ivCircle = (ImageView) view.findViewById(R.id.iv_hotel_circle);
        tvPrice = (TextView) view.findViewById(R.id.tv_hotel_price_range);
        ivCircleMove = (ImageView) view.findViewById(R.id.iv_hotel_circle_move);
        addListener();
    }

    private void addListener() {
//        ivCircleMove.setOnTouchListener(this);
    }

    public void setStart(){
        left.setVisibility(GONE);
    }

    public void setEnd(){
        right.setVisibility(GONE);
    }




}
