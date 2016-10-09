package com.jhhy.cuiweitourism.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.Tab1GridViewAdapter;
import com.jhhy.cuiweitourism.biz.Tab1RecommendBiz;
import com.jhhy.cuiweitourism.moudle.ADInfo;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.ui.CarRentSelectTypeActivity;
import com.jhhy.cuiweitourism.ui.CitySelectionActivity;
import com.jhhy.cuiweitourism.ui.HotActivityListActivity;
import com.jhhy.cuiweitourism.ui.InnerActivity4;
import com.jhhy.cuiweitourism.ui.PersonalizedCustomActivity;
import com.jhhy.cuiweitourism.ui.SearchRouteActivity;
import com.jhhy.cuiweitourism.ui.SearchShopActivity;
import com.jhhy.cuiweitourism.ui.StartActivityEditActivity;
import com.jhhy.cuiweitourism.ui.VisaMainActivity;
import com.jhhy.cuiweitourism.ui.easemob.EasemobLoginActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1Fragment extends Fragment implements XScrollView.IXScrollViewListener, GestureDetector.OnGestureListener, View.OnClickListener, ArgumentOnClick {

    private static final String TAG = Tab1Fragment.class.getSimpleName();

    //  轮播图片
    private List<ADInfo> infos = new ArrayList<ADInfo>();

    private ViewFlipper flipper;
    private LinearLayout layoutPoint;

    private ImageView[] indicators; // 轮播图片数组
    private int currentPosition = 0; // 轮播当前位置

    private static final int FLING_MIN_DISTANCE = 100;
    private static final int FLING_MIN_VELOCITY = 0;
    private GestureDetector mGestureDetector; // MyScrollView的手势
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private boolean isScrolling = false; // 滚动框是否滚动着
    private int time = 4000; // 默认轮播时间
    private final int WHEEL = 100; // 转动
    private final int WHEEL_WAIT = 101; // 等待

    private TextView tvLocationCity; //显示当前选择的城市
    private PhoneBean selectCity; //选择的城市

    private TextView tvInnerTravel; //国内游
    private TextView tvOutsideTravel; //出境游
    private TextView tvStartActivity; //发起活动
    private TextView tvRentCar; //租车
    private TextView tvVisa; //签证

    private TextView tvSearchRoute; //找路线
    private RelativeLayout layoutPersionalizedCustom; //热门活动
    private RelativeLayout layoutHotActivity; //热门活动

    private MyGridView gridViewRecommend; //热门推荐
    private List<Travel> lists = new ArrayList<>(); //热门推荐数据(全部)
    private Tab1GridViewAdapter adapter; //热门推荐适配器
    private List<Travel> listsInner = new ArrayList<>(); //热门推荐数据(全部)
    private List<Travel> listsOutside = new ArrayList<>(); //热门推荐数据(全部)
    private List<Travel> listsNearby = new ArrayList<>(); //热门推荐数据(全部)

    //热门推荐底部indicator
    private TextView tvIndicatorAllBottom;
    private TextView tvIndicatorInnerBottom;
    private TextView tvIndicatorOutsideBottom;
    private TextView tvIndicatorNearbyBottom;
    private int indicator = -1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.i(TAG, "-------------------handleMessage---------------------");
            switch (msg.what){
                case Consts.MESSAGE_TAB1_RECOMMEND:
                    if(msg.arg1 == 0){
                        ToastUtil.show(getContext(), "获取数据失败");
                    }else if(msg.arg1 == 1){
//                        ToastUtil.show(getContext(), "请求成功");
                        List<Travel> listNew = (List<Travel>) msg.obj;
                        if(listNew != null) {
                            lists = listNew;
                            adapter.setData(lists);
                        }else{
                            ToastUtil.show(getContext(), "没有热门推荐城市");
                        }
                    }
                    break;
                case Consts.MESSAGE_TAB1_RECOMMEND_INNER:
                    if(msg.arg1 == 0){
                        ToastUtil.show(getContext(), "获取数据失败");
                    }else if(msg.arg1 == 1){
//                        ToastUtil.show(getContext(), "请求成功");
                        List<Travel> listNew = (List<Travel>) msg.obj;
                        if(listNew != null) {
                            listsInner = listNew;
                            adapter.setData(listsInner);
                        }else{
                            ToastUtil.show(getContext(), "没有国内游推荐城市");
                        }
                    }
                    break;
                case Consts.MESSAGE_TAB1_RECOMMEND_OUTSIDE:
                    if(msg.arg1 == 0){
                        ToastUtil.show(getContext(), "获取数据失败");
                    }else if(msg.arg1 == 1){
//                        ToastUtil.show(getContext(), "请求成功");
                        List<Travel> listNew = (List<Travel>) msg.obj;
                        if(listNew != null) {
                            listsOutside = listNew;
                            adapter.setData(listsOutside);
                        }else{
                            ToastUtil.show(getContext(), "没有出境游推荐地点");
                        }
                    }
                    break;
                case Consts.MESSAGE_TAB1_RECOMMEND_NEARBY:
                    if(msg.arg1 == 0){
                        ToastUtil.show(getContext(), "获取数据失败");
                    }else if(msg.arg1 == 1){
//                        ToastUtil.show(getContext(), "请求成功");
                        List<Travel> listNew = (List<Travel>) msg.obj;
                        if(listNew != null) {
                            listsNearby = listNew;
                            adapter.setData(listsNearby);
                        }else{
                            ToastUtil.show(getContext(), "没有推荐的周边游地点");
                        }
                    }
                    break;
            }
        }
    };

    public Tab1Fragment() {
        // Required empty public constructor
    }

    public static Tab1Fragment newInstance(String param1, String param2) {
        Tab1Fragment fragment = new Tab1Fragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        LogUtil.i(TAG, "======onAttach======");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "======onCreate======");
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }
    private Context mContext;
    private XScrollView mScrollView;
    private View content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LogUtil.i(TAG, "======onCreateView======");

        View view = inflater.inflate(R.layout.my_x_scroll_view, container, false);
        setupView(view);
        addListener();
        getData(0);
        return view;
    }

    private void getData(int type) {
        if(type == 0) {
            //此处获取全部
            Tab1RecommendBiz biz = new Tab1RecommendBiz(getContext(), handler);
            biz.getRecommendForYou("", "", Consts.MESSAGE_TAB1_RECOMMEND);
        }else if(1 == type){ //国内游
            Tab1RecommendBiz biz = new Tab1RecommendBiz(getContext(), handler);
            biz.getRecommendForYou("1", "", Consts.MESSAGE_TAB1_RECOMMEND_INNER);
        }else if(2 == type){ //出境游
            Tab1RecommendBiz biz = new Tab1RecommendBiz(getContext(), handler);
            biz.getRecommendForYou("2", "", Consts.MESSAGE_TAB1_RECOMMEND_OUTSIDE);
        }else if(3 == type){ //周边游
            Tab1RecommendBiz biz = new Tab1RecommendBiz(getContext(), handler);
            biz.getRecommendForYou("", "北京", Consts.MESSAGE_TAB1_RECOMMEND_NEARBY);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(TAG, "======onActivityCreated======");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i(TAG, "======onStart======");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "======onResume======");
    }

    //悬浮导航的隐藏显示
private RelativeLayout  layoutTitle; //顶部“标题栏”，求高度
private LinearLayout    layoutTabRecommendForYou; //GridView的导航
private LinearLayout    layoutTabRecommendForYou2; //顶部GridView的悬浮导航
    private TextView tvIndicatorAllTop; //全部
    private TextView tvIndicatorInnerTop; //国内游
    private TextView tvIndicatorOutsideTop; //国外游
    private TextView tvIndicatorNearbyTop; //周边游

    private TextView tvSearchrShop; //找商铺

    private void setupView(View view) {
layoutTitle = (RelativeLayout) view.findViewById(R.id.layout_title_tab1);
layoutTabRecommendForYou2 = (LinearLayout) view.findViewById(R.id.layout_tab_recommend_for_you_2);
        tvIndicatorAllTop       = (TextView) view.findViewById(R.id.tv_tab1_indicator_all_top);
        tvIndicatorInnerTop     = (TextView) view.findViewById(R.id.tv_tab1_indicator_inner_top);
        tvIndicatorOutsideTop   = (TextView) view.findViewById(R.id.tv_tab1_indicator_outside_top);
        tvIndicatorNearbyTop    = (TextView) view.findViewById(R.id.tv_tab1_indicator_nearby_top);

        mScrollView = (XScrollView)view.findViewById(R.id.scroll_view);
        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(true);
        mScrollView.setAutoLoadEnable(true);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(Utils.getCurrentTime());

        content = LayoutInflater.from(getActivity()).inflate(R.layout.my_x_scroll_view_content, null);

        if (null != content) {
            tvLocationCity = (TextView) content.findViewById(R.id.tv_search_bar_main_left_location); //当前城市

            tvInnerTravel = (TextView) content.findViewById(R.id.tv_tab1_inner_travel); //国内游
            tvOutsideTravel = (TextView) content.findViewById(R.id.tv_tab1_outside); //出境游

            tvStartActivity = (TextView) content.findViewById(R.id.tv_tab1_start_activity); //发起活动
            tvRentCar = (TextView) content.findViewById(R.id.tv_tab1_rent_car); //发起活动
            tvVisa = (TextView) content.findViewById(R.id.tv_tab1_visa); //签证

            tvSearchRoute = (TextView) content.findViewById(R.id.tv_tab1_search_route_activity); //找路线
            tvSearchrShop = (TextView) content.findViewById(R.id.tv_tab1_search_shop); //找路线

            layoutPersionalizedCustom = (RelativeLayout) content.findViewById(R.id.layout_personalized_custom); //个性定制
            layoutHotActivity = (RelativeLayout) content.findViewById(R.id.layout_hot_activity); //热门活动

layoutTabRecommendForYou = (LinearLayout) content.findViewById(R.id.layout_tab_recommend_for_you); //底部GridView的导航栏
            tvIndicatorAllBottom    = (TextView) content.findViewById(R.id.tv_tab1_recommend_all_bottom); //全部
            tvIndicatorInnerBottom  = (TextView) content.findViewById(R.id.tv_tab1_recommend_inner_bottom); //国内游
            tvIndicatorOutsideBottom = (TextView) content.findViewById(R.id.tv_tab1_recommend_outside_bottom); //国外游
            tvIndicatorNearbyBottom = (TextView) content.findViewById(R.id.tv_tab1_recommend_nearby_bottom); //周边游

            gridViewRecommend = (MyGridView) content.findViewById(R.id.fragment_home_lv);
            gridViewRecommend.setFocusable(false);
            gridViewRecommend.setFocusableInTouchMode(false);

            adapter = new Tab1GridViewAdapter(mContext, lists, this);
            gridViewRecommend.setAdapter(adapter);

            mGestureDetector = new GestureDetector(getActivity(), this);
        }
        mScrollView.setView(content);
        final int titleHeight = layoutTitle.getHeight();
        mScrollView.setOnXScrollChangedI(new XScrollView.onXScrollChangedI() {
            @Override
            public void onXScrollChangedImpl(int l, int t, int oldl, int oldt) {

//                LogUtil.i(TAG, "-------onXScrollChangedImpl--------- l = " + l + ", t = " + t + ", oldl = " + oldl + "，oldt = " +oldt);
                int[] s = new int[2];
                layoutTabRecommendForYou.getLocationOnScreen(s);
                int statusHeight = Utils.getStatusBarHeight(getContext());
//                LogUtil.i(TAG, "s: x = " + s[0] + ", y = " + s[1]);
//                LogUtil.i(TAG, "statusHeight = " + statusHeight + ", titleHeight = " + titleHeight);
                if(statusHeight + titleHeight >= s[1]){
                    layoutTabRecommendForYou2.setVisibility(View.VISIBLE);
                }else{
                    layoutTabRecommendForYou2.setVisibility(View.GONE);
                }
            }
        });

    }

    private void addListener() {
        tvLocationCity.setOnClickListener(this);
        tvInnerTravel.setOnClickListener(this);
        tvOutsideTravel.setOnClickListener(this);
        tvStartActivity.setOnClickListener(this);
        tvRentCar.setOnClickListener(this);
        tvVisa.setOnClickListener(this);
        layoutPersionalizedCustom.setOnClickListener(this);
        layoutHotActivity.setOnClickListener(this);
        tvSearchRoute.setOnClickListener(this);
        tvSearchrShop.setOnClickListener(this);
        tvIndicatorAllBottom.setOnClickListener(this);
        tvIndicatorInnerBottom.setOnClickListener(this);
        tvIndicatorOutsideBottom.setOnClickListener(this);
        tvIndicatorNearbyBottom.setOnClickListener(this);
        tvIndicatorAllTop   .setOnClickListener(this);
        tvIndicatorInnerTop .setOnClickListener(this);
        tvIndicatorOutsideTop.setOnClickListener(this);
        tvIndicatorNearbyTop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab1_inner_travel: //国内游
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                InnerActivity4.actionStart(getContext(), bundle);
                break;
            case R.id.tv_tab1_outside: //出境游
                Bundle bundleOut = new Bundle();
                bundleOut.putInt("type", 2);
                InnerActivity4.actionStart(getContext(), bundleOut);
                break;
            case R.id.tv_tab1_start_activity: //发起活动
                StartActivityEditActivity.actionStart(getContext(), null);
                break;
            case R.id.tv_tab1_rent_car: //租车
                Bundle bundleCar = new Bundle();
                bundleCar.putString("city", tvLocationCity.getText().toString());
                CarRentSelectTypeActivity.actionStart(getContext(), bundleCar);
                break;
            case R.id.tv_tab1_visa: //签证
                VisaMainActivity.actionStart(getContext(), null);
                break;
            case R.id.layout_personalized_custom: //个性定制
                PersonalizedCustomActivity.actionStart(getContext(), null);
                break;
            case R.id.layout_hot_activity: //热门活动
                Bundle bundleHotActivity = new Bundle();
                bundleHotActivity.putSerializable("selectCity", selectCity);
                HotActivityListActivity.actionStart(getContext(), bundleHotActivity);
                break;
            case R.id.tv_tab1_search_route_activity: //找路线
                SearchRouteActivity.actionStart(getContext(), null);
                break;
            case R.id.tv_tab1_search_shop: //找商铺
                SearchShopActivity.actionStart(getContext(), null);
                break;
            case R.id.tv_tab1_recommend_all_bottom: //Indicator 全部
            case R.id.tv_tab1_indicator_all_top:
//                gridViewRecommend.setSelection(0);
                LogUtil.e(TAG, "indicator = " + indicator);
//                if(0 == indicator)  return;
//                indicator = 0;
                changeIndicator(0);
                if(lists != null && lists.size() != 0){
                    adapter.setData(lists);
                    return;
                }
                getData(0);
                break;
            case R.id.tv_tab1_recommend_inner_bottom: //Indicator 国内游
            case R.id.tv_tab1_indicator_inner_top:
                LogUtil.e(TAG, "indicator = " + indicator);
//                if(1 == indicator)  return;
//                indicator = 1;
                changeIndicator(1);
                if(listsInner != null && listsInner.size() != 0){
                    adapter.setData(listsInner);
                    return;
                }
                getData(1);
                break;
            case R.id.tv_tab1_recommend_outside_bottom: //Indicator 国外游
            case  R.id.tv_tab1_indicator_outside_top:
                LogUtil.e(TAG, "indicator = " + indicator);
                changeIndicator(2);
                if(listsOutside != null && listsOutside.size() != 0){
                    adapter.setData(listsOutside);
                    return;
                }
//                if(2 == indicator)  return;
//                indicator = 2;
                getData(2);
                break;
            case R.id.tv_tab1_recommend_nearby_bottom: //Indicator 周边游
            case R.id.tv_tab1_indicator_nearby_top:
                LogUtil.e(TAG, "indicator = " + indicator);
//                if(3 == indicator)  return;
//                indicator = 3;
                changeIndicator(3);
                if(listsNearby != null && listsNearby.size() != 0){
                    adapter.setData(listsNearby);
                    return;
                }
                getData(3);
                break;
            case R.id.tv_search_bar_main_left_location: //顶部搜索栏 选择地址
//                CitySelectionActivity.actionStart(getContext(), null);
                Intent intentCity = new Intent(getContext(), CitySelectionActivity.class);
                Bundle bundleCity = new Bundle();
                bundleCity.putString("currentCity", tvLocationCity.getText().toString());
                intentCity.putExtras(bundleCity);
                startActivityForResult(intentCity, Consts.REQUEST_CODE_SELECT_CITY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Consts.REQUEST_CODE_SELECT_CITY){ //选择城市
            if (resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                selectCity = (PhoneBean) bundle.getSerializable("city");
                tvLocationCity.setText(selectCity.getName());
            }

        }
    }

    private void changeIndicator(int type){
            tvIndicatorAllBottom.setTextColor(getResources().getColor(android.R.color.black));
            tvIndicatorAllBottom.setBackground(null);
            tvIndicatorInnerBottom.setTextColor(getResources().getColor(android.R.color.black));
            tvIndicatorInnerBottom.setBackground(null);
            tvIndicatorOutsideBottom.setTextColor(getResources().getColor(android.R.color.black));
            tvIndicatorOutsideBottom.setBackground(null);
            tvIndicatorNearbyBottom.setTextColor(getResources().getColor(android.R.color.black));
            tvIndicatorNearbyBottom.setBackground(null);

            tvIndicatorAllTop.setTextColor(getResources().getColor(android.R.color.black));
            tvIndicatorAllTop.setBackground(null);
            tvIndicatorInnerTop.setTextColor(getResources().getColor(android.R.color.black));
            tvIndicatorInnerTop.setBackground(null);
            tvIndicatorOutsideTop.setTextColor(getResources().getColor(android.R.color.black));
            tvIndicatorOutsideTop.setBackground(null);
            tvIndicatorNearbyTop.setTextColor(getResources().getColor(android.R.color.black));
            tvIndicatorNearbyTop.setBackground(null);

//        for(int i = 0; i < 4; i++){
            if (type == 0){
                tvIndicatorAllBottom.setTextColor(getResources().getColor(android.R.color.white));
                tvIndicatorAllBottom.setBackground(getResources().getDrawable(R.drawable.bg_tab1_radiobutton_recommend_for_you));
                tvIndicatorAllTop.setTextColor(getResources().getColor(android.R.color.white));
                tvIndicatorAllTop.setBackground(getResources().getDrawable(R.drawable.bg_tab1_radiobutton_recommend_for_you));
            }else if(1 == type){
                tvIndicatorInnerBottom.setTextColor(getResources().getColor(android.R.color.white));
                tvIndicatorInnerBottom.setBackground(getResources().getDrawable(R.drawable.bg_tab1_radiobutton_recommend_for_you));
                tvIndicatorInnerTop.setTextColor(getResources().getColor(android.R.color.white));
                tvIndicatorInnerTop.setBackground(getResources().getDrawable(R.drawable.bg_tab1_radiobutton_recommend_for_you));
            }else if(2 == type){
                tvIndicatorOutsideBottom.setTextColor(getResources().getColor(android.R.color.white));
                tvIndicatorOutsideBottom.setBackground(getResources().getDrawable(R.drawable.bg_tab1_radiobutton_recommend_for_you));
                tvIndicatorOutsideTop.setTextColor(getResources().getColor(android.R.color.white));
                tvIndicatorOutsideTop.setBackground(getResources().getDrawable(R.drawable.bg_tab1_radiobutton_recommend_for_you));
            }else if(3 == type){
                tvIndicatorNearbyBottom.setTextColor(getResources().getColor(android.R.color.white));
                tvIndicatorNearbyBottom.setBackground(getResources().getDrawable(R.drawable.bg_tab1_radiobutton_recommend_for_you));
                tvIndicatorNearbyTop.setTextColor(getResources().getColor(android.R.color.white));
                tvIndicatorNearbyTop.setBackground(getResources().getDrawable(R.drawable.bg_tab1_radiobutton_recommend_for_you));
            }
//        }
    }

    /**
     * 讨价还价
     * @param view      layout布局
     * @param viewGroup
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
        Intent intent = new Intent(getContext(), EasemobLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mScrollView.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        mScrollView.stopLoadMore();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
//        LogUtil.i(TAG, "=========================================单击事件！");
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        LogUtil.i(TAG, "++++++++ onScroll +++++++++");
        LogUtil.i(TAG, "distanceX = " + distanceX + ", distanceY = " + distanceY);
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
            LogUtil.i(TAG, "==============开始向左滑动了================");
            showNextView();
        }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
            LogUtil.i(TAG, "==============开始向右滑动了================");
            showPreviousView();
        }
        return false;
    }

    private void showNextView() {
        LogUtil.i(TAG, "========showNextView=======向左滑动=======");
        isScrolling = true;
        flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out));
        flipper.showNext();
        currentPosition++;
        if(currentPosition == flipper.getChildCount()){
            dianUnselect(currentPosition-1);
            currentPosition = 0;
            dianSelect(currentPosition);
        }else{
            dianUnselect(currentPosition-1);
            dianSelect(currentPosition);
        }
        releaseTime = System.currentTimeMillis();
        isScrolling = false;
        LogUtil.i(TAG, "==============第"+currentPosition+"页==========");
    }

    private void showPreviousView() {
        // TODO Auto-generated method stub
//        LogUtil.i(TAG, "========showPreviousView=======向右滑动=======");
        isScrolling = true;
//		thread.suspend();
        dianSelect(currentPosition);
        flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out));
        flipper.showPrevious();
        currentPosition--;
        if(currentPosition == -1){
            dianUnselect(currentPosition+1);
            currentPosition = flipper.getChildCount()-1;
            dianSelect(currentPosition);
        }else{
            dianUnselect(currentPosition+1);
            dianSelect(currentPosition);
        }
        releaseTime = System.currentTimeMillis();
        isScrolling = false;
//		LogUtil.i(TAG, "==============第"+currentPage+"页==========");
//		thread.resume();
    }

    /**
     * 对应被选中的点的图片
     * @param id
     */
    private void dianSelect(int id) {
        indicators[id].setImageResource(R.drawable.icon_point_pre);
    }
    /**
     * 对应未被选中的点的图片
     * @param id
     */
    private void dianUnselect(int id){
        indicators[id].setImageResource(R.drawable.icon_point);
    }


}
