package com.jhhy.cuiweitourism.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.AllOrdersPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class Tab3Fragment extends Fragment implements View.OnClickListener {

    private String TAG = Tab3Fragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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

    private OrdersAllFragment f1;
    private OrdersWaitPayFragment f2;
    private OrdersWaitCommentFragment f3;
    private OrdersWaitRefundFragment f4;

    private AllOrdersPopupWindow popOrderType; //选择订单类型的
    private int selection = 0;
    private String type = "0"; //0.全部订单、1.线路、2.酒店、3租车、8签证、14私人定制、202活动

    public Tab3Fragment() {
        // Required empty public constructor
    }

    public static Tab3Fragment newInstance(String param1, String param2) {
        Tab3Fragment fragment = new Tab3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab4_all_orders, container, false);
        setupView(view);
        addListener();
        initData();
        return view;
    }

    private void setupView(View view) {
        layoutTitle = (RelativeLayout) view.findViewById(R.id.layout_title_inner_travel);
        tvTitle = (TextView) view.findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(typeTitles[0]);
        ivTitleLeft = (ImageView) view.findViewById(R.id.title_main_tv_left_location);
        ivTitleLeft.setVisibility(View.GONE);

        drawableRightDown = ContextCompat.getDrawable(getContext(), R.mipmap.arrow_tran_down);
        drawableRightDown.setBounds(0, 0, drawableRightDown.getMinimumWidth(), drawableRightDown.getMinimumHeight());
        drawableRightTop = ContextCompat.getDrawable(getContext(), R.mipmap.arrow_tran_top);
        drawableRightTop.setBounds(0, 0, drawableRightTop.getMinimumWidth(), drawableRightTop.getMinimumHeight());

        tvTitle.setCompoundDrawables(null, null, drawableRightDown, null);

        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout_orders);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_orders);
    }

    private void addListener() {
        tvTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_inner_travel:
                tvTitle.setCompoundDrawables(null, null, drawableRightTop, null);
                if (popOrderType == null) {
                    popOrderType = new AllOrdersPopupWindow(getActivity(), layoutTitle);
                    addPopListener();
                }else {
                    if (popOrderType.isShowing()) {
                        popOrderType.dismiss();
                    } else {
                        popOrderType.refreshData(selection);
                        popOrderType.showAsDropDown(layoutTitle, 0, 0);
                    }
                }
                break;
        }
    }

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

        mAdapter = new OrdersPagerAdapter(getFragmentManager(), mTitlesF, mContent);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        //TabLayout加载viewpager,setupWithViePager必须在ViewPager.setAdapter()之后调用
        mTabLayout.setupWithViewPager(mViewPager);
//        LogUtil.e(TAG, "------------ position = " + position + "----------------");
//        mViewPager.setCurrentItem(position, true);
    }

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

}
