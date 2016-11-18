package com.jhhy.cuiweitourism.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.fragment.OrdersAllFragment;
import com.jhhy.cuiweitourism.fragment.OrdersWaitCommentFragment;
import com.jhhy.cuiweitourism.fragment.OrdersWaitPayFragment;
import com.jhhy.cuiweitourism.fragment.OrdersWaitRefundFragment;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.popupwindows.AllOrdersPopupWindow;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class Tab4AllOrdersActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layoutTitle;
    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private int[]   typeTitles    =   new     int[]{  R.string.orders_all, R.string.orders_travel, R.string.orders_air,
                                                R.string.orders_train, R.string.orders_hotel, R.string.orders_rent_car,
                                                R.string.orders_activity, R.string.orders_visa};
    private Drawable drawableRightDown;
    private Drawable drawableRightTop;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] mTitlesF = new String[]{"全部","待付款","待点评","待退款"};
    private List<Fragment> mContent = new ArrayList<>(4);

    private OrdersPagerAdapter mAdapter;

    private int position = -1; //1：全部；2：代付款；3：待点评；4：待退款

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.activity_tab4_all_orders);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setupView();
        addListener();
        initData();
        registReceiver();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
    }

    private void setupView() {
        layoutTitle = (RelativeLayout) findViewById(R.id.layout_title_inner_travel);
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(typeTitles[selection]);
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

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
        ivTitleLeft.setOnClickListener(this);
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
        mViewPager.setCurrentItem(position, true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_title_inner_travel:
                tvTitle.setCompoundDrawables(null, null, drawableRightTop, null);
                if (popOrderType == null) {
                    popOrderType = new AllOrdersPopupWindow(Tab4AllOrdersActivity.this, layoutTitle);
                    addPopListener();
                } else {
                    if (popOrderType.isShowing()) {
                        popOrderType.dismiss();
                    } else {
                        LogUtil.e(TAG, "------ selection before = " + selection + "-----------");
                        popOrderType.showAsDropDown(layoutTitle, 0, 0);
                        popOrderType.refreshData(selection);
                    }
                }
                break;
        }
    }

    private String TAG = getClass().getSimpleName();

    private void addPopListener() {
        popOrderType.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                LogUtil.e(TAG, "------------------onDismiss--------------");
                tvTitle.setCompoundDrawables(null, null, drawableRightDown, null);
                int newSelection = popOrderType.getCheckButton();
                LogUtil.e(TAG, "------ selection after = " + newSelection + "-----------");
                if (selection == newSelection){

                }else{
                    tvTitle.setText(typeTitles[selection]);
                    selection = newSelection;
                    switch (selection){
                        case 0:
                            type = "0";
                            tvTitle.setText(typeTitles[0]);
                            break;
                        case 1:
                            type = "1";
                            tvTitle.setText(typeTitles[1]);
                            break;
                        case 2:
                            tvTitle.setText(typeTitles[2]);
                            break;
                        case 3:
                            tvTitle.setText(typeTitles[3]);
                            break;
                        case 4:
                            type = "2";
                            tvTitle.setText(typeTitles[4]);
                            break;
                        case 5:
                            type = "3";
                            tvTitle.setText(typeTitles[5]);
                            break;
                        case 6:
                            type = "202";
                            tvTitle.setText(typeTitles[6]);
                            break;
                        case 7:
                            type = "8";
                            tvTitle.setText(typeTitles[7]);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private OrderUpdateReceiver receiver;

    private void registReceiver() {
        receiver = new OrderUpdateReceiver();
        IntentFilter filter = new IntentFilter(Consts.ACTION_ORDER_UPDATE);
        registerReceiver(receiver, filter);
    }

    class OrderUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Consts.ACTION_ORDER_UPDATE.equals(intent.getAction())){
                f1.getData(type);
                f2.getData(type);
                f3.getData(type);
                f4.getData(type);
            }
        }
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, Tab4AllOrdersActivity.class);
        if(bundle != null){
            intent.putExtras( bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
