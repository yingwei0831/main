package com.jhhy.cuiweitourism.popupwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
public class PopupWindowVisaMaterial extends PopupWindow implements View.OnClickListener {

    private Activity activity;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;

    private EditText etMail;
    private View parent;


    private String TAG = PopupWindowVisaMaterial.class.getSimpleName();

    public PopupWindowVisaMaterial(final Activity activity, View parent, final Handler handler) {
        this.activity = activity;
        this.parent = parent;

        View view = View.inflate(activity, R.layout.popup_visa_deliver_material, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
//        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));
        setAnimationStyle(R.style.anim_menu_bottombar);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
//        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        showAsDropDown(parent, 0, -Utils.getScreenHeight(activity));
        update();

        Button sendMaterial = (Button) view.findViewById(R.id.btn_send_material);

        sendMaterial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String mail = etMail.getText().toString();
                if (TextUtils.isEmpty(mail)){
                    ToastCommon.toastShortShow(activity, null, "输入不能为空");
                    return;
                }
                if (Utils.checkEmail(mail)){
                    VisaBiz biz = new VisaBiz(activity, handler);
                    biz.doSentMaterialToMyMail(MainActivity.user.getUserId());
                }else{
                    ToastCommon.toastShortShow(activity, null, "邮件格式不正确");
                }
            }
        });

        tv1 = (TextView) view.findViewById(R.id.tv_type1);
        tv2 = (TextView) view.findViewById(R.id.tv_type2);
        tv3 = (TextView) view.findViewById(R.id.tv_type3);
        tv4 = (TextView) view.findViewById(R.id.tv_type4);
        tv5 = (TextView) view.findViewById(R.id.tv_type5);

        etMail = (EditText) view.findViewById(R.id.et_mail_address);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        tv1.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_unselected_corner_border));
        tv2.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_unselected_corner_border));
        tv3.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_unselected_corner_border));
        tv4.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_unselected_corner_border));
        tv5.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_unselected_corner_border));
        switch (view.getId()){
            case R.id.tv_type1:
                tv1.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_type2:
                tv2.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_type3:
                tv3.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_type4:
                tv4.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_type5:
                tv5.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_et_city_selected_corner_border));
                break;
        }
    }


}
