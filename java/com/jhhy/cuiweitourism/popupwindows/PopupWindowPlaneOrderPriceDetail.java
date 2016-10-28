package com.jhhy.cuiweitourism.popupwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class PopupWindowPlaneOrderPriceDetail extends PopupWindow {

    private Activity activity;

    private View parent;
    private TextView tvFlightInfo;  //航班飞机信息
    private TextView tvPriceAdult;  //成人票
    private TextView tvPriceAdditional; //机建+燃油
    private TextView tvPriceInsurance;  //保险
    private TextView tvNumberAdult;     //成人x 0人
    private TextView tvNumberAdditional;    //x 0人
    private TextView tvNumberInsurance;     //x 0份
    private TextView tvNumberPassenger;     //乘机人数：x 0

    private String TAG = PopupWindowPlaneOrderPriceDetail.class.getSimpleName();

    public PopupWindowPlaneOrderPriceDetail(final Activity activity, View parent, int height) {
        this.activity = activity;
        this.parent = parent;

        View view = View.inflate(activity, R.layout.popup_plane_order_price_detail, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins_plane));
//        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));
        setAnimationStyle(R.style.anim_menu_bottombar);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
//        showAtLocation(parent, Gravity.BOTTOM, 0, height);
//        showAsDropDown(parent, 0, -300);
        update();

        setupView(view);
    }

    private void setupView(View view) {
        tvFlightInfo = (TextView) view.findViewById(R.id.tv_plane_info);
        tvPriceAdult = (TextView) view.findViewById(R.id.tv_plane_price_adult);
        tvPriceAdditional = (TextView) view.findViewById(R.id.tv_plane_price_additional);
        tvPriceInsurance = (TextView) view.findViewById(R.id.tv_plane_price_insurance);
        tvNumberAdult = (TextView) view.findViewById(R.id.tv_plane_number_adult);
        tvNumberAdditional = (TextView) view.findViewById(R.id.tv_plane_number_additional);
        tvNumberInsurance = (TextView) view.findViewById(R.id.tv_plane_number_insurance);
        tvNumberPassenger = (TextView) view.findViewById(R.id.tv_plane_number_passenger);
    }

    public void refreshVie(){

    }



}
