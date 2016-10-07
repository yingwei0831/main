package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.fragment.OrdersAllFragment;
import com.jhhy.cuiweitourism.fragment.OrdersWaitCommentFragment;
import com.jhhy.cuiweitourism.fragment.OrdersWaitPayFragment;
import com.jhhy.cuiweitourism.fragment.OrdersWaitRefundFragment;
import com.jhhy.cuiweitourism.popupwindows.AllOrdersPopupWindow;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class Tab4AllOrdersActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layoutTitle;
    private TextView tvTitle;
    private int[]   typeTitles    =   new     int[]{  R.string.orders_all, R.string.orders_travel, R.string.orders_air,
                                                R.string.orders_train, R.string.orders_hotel, R.string.orders_rent_car,
                                                R.string.orders_activity, R.string.orders_visa};
    private Drawable drawableRightDown;
    private Drawable drawableRightTop;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] mTitlesF = new String[]{"全部", "待付款", "待点评", "待退款"};
    private List<Fragment> mContent = new ArrayList<>(4);

    private OrdersPagerAdapter mAdapter;

    private int position = -1; //1：全部；2：代付款；3：待点评；4：待退款

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.activity_tab4_all_orders);
        setupView();
        addListener();
        initData();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
    }

    private void setupView() {
        layoutTitle = (RelativeLayout) findViewById(R.id.layout_title_inner_travel);
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(typeTitles[selection]);
        drawableRightDown = ContextCompat.getDrawable(Tab4AllOrdersActivity.this, R.mipmap.arrow_tran_down);
        drawableRightDown.setBounds(0, 0, drawableRightDown.getMinimumWidth(), drawableRightDown.getMinimumHeight());
        drawableRightTop = ContextCompat.getDrawable(Tab4AllOrdersActivity.this, R.mipmap.arrow_tran_top);
        drawableRightTop.setBounds(0, 0, drawableRightTop.getMinimumWidth(), drawableRightTop.getMinimumHeight());

        tvTitle.setCompoundDrawables(null, null, drawableRightDown, null);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout_orders);
        mViewPager = (ViewPager)findViewById(R.id.viewpager_orders);

    }

    private void addListener() {
        tvTitle.setOnClickListener(this);
    }

    private OrdersAllFragment f1;
    private OrdersWaitPayFragment f2;
    private OrdersWaitCommentFragment f3;
    private OrdersWaitRefundFragment f4;

    private void initData() {
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        f1 = OrdersAllFragment.newInstance(mTitlesF[0], type);
        f2 = OrdersWaitPayFragment.newInstance(mTitlesF[1], type);
        f3 = OrdersWaitCommentFragment.newInstance(mTitlesF[2], type);
        f4 = OrdersWaitRefundFragment.newInstance(mTitlesF[3], type);

        mContent.add(f1);
        mContent.add(f2);
        mContent.add(f3);
        mContent.add(f4);

        mAdapter = new OrdersPagerAdapter(getSupportFragmentManager(), mTitlesF, mContent);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        //TabLayout加载viewpager,setupWithViePager必须在ViewPager.setAdapter()之后调用
        mTabLayout.setupWithViewPager(mViewPager);
        LogUtil.e(TAG, "------------ position = " + position + "----------------");
        mViewPager.setCurrentItem(position, true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_inner_travel:
                tvTitle.setCompoundDrawables(null, null, drawableRightTop, null);
                if (popOrderType == null) {
                    popOrderType = new AllOrdersPopupWindow(Tab4AllOrdersActivity.this, layoutTitle);
                    setupPopView();
                    addPopListener();
                }else {
                    if (popOrderType.isShowing()) {
                        popOrderType.dismiss();
                    } else {
                        LogUtil.e(TAG, "------ selection before = " + selection + "-----------");
                        popOrderType.refreshData(selection);
                        popOrderType.showAsDropDown(layoutTitle, 0, 0);
                    }
                }
                break;
        }
    }

    private void setupPopView() {

    }
    private String TAG = getClass().getSimpleName();

    private void addPopListener() {
        popOrderType.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                LogUtil.e(TAG, "------------------onDismiss--------------");
                tvTitle.setCompoundDrawables(null, null, drawableRightDown, null);
                int newSelection = popOrderType.getCheckButton();
                LogUtil.e(TAG, "------ selection after = " + selection + "-----------");
                if (selection == newSelection){

                }else{
                    tvTitle.setText(typeTitles[selection]);
                    selection = newSelection;
                    switch (selection){
                        case 0:
                            type = "0";
                            break;
                        case 1:
                            type = "1";
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            type = "2";
                            break;
                        case 5:
                            type = "3";
                            break;
                        case 6:
                            type = "202";
                            break;
                        case 7:
                            type = "8";
                            break;
                    }
                    f1.getData(type);
                    f2.getData(type);
                    f3.getData(type);
                    f4.getData(type);
                }
            }
        });
    }

    private AllOrdersPopupWindow popOrderType; //选择订单类型的
    private int selection = 0;
    private String type = "0"; //0.全部订单、1.线路、2.酒店、3租车、8签证、14私人定制、202活动

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, Tab4AllOrdersActivity.class);
        if(bundle != null){
            intent.putExtras( bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
