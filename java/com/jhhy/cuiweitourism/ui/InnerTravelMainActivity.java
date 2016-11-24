package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotDestinationAdapter;
import com.jhhy.cuiweitourism.adapter.HotDestinationGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.HotRecommendGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.Tab1GridViewAdapter;
import com.jhhy.cuiweitourism.biz.ExchangeBiz;
import com.jhhy.cuiweitourism.biz.InnerTravelMainBiz;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.moudle.ADInfo;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.moudle.HotDestination;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.net.biz.ForeEndActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndAdvertise;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndAdvertisingPositionInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.easemob.EasemobLoginActivity;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.just.sun.pricecalendar.ToastCommon;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

public class InnerTravelMainActivity extends BaseActionBarActivity implements XScrollView.IXScrollViewListener, GestureDetector.OnGestureListener, ArgumentOnClick, View.OnTouchListener {

    private String TAG = InnerTravelMainActivity.class.getSimpleName();

    private MyGridView gvHotDestination; //热门目的地
    private List<HotDestination> listHotDestination = new ArrayList<>();
    private HotDestinationAdapter hotDestAdapter;
//    private LinearLayout layoutDestContainer; //热门目的地

    private MyGridView gvHotRecommend; //热门推荐
    private List<CityRecommend> listHotRecommend = new ArrayList<>();
    private HotRecommendGridViewAdapter hotRecomAdapter;
    private TextView tvHotRecommendExchange; //热门推荐，换一换

//    private List<String> titles = new ArrayList<>();

    private GestureDetector mGestureDetector; // mScrollView的手势
    private LinearLayout layoutIndicatorTop; //顶部悬浮导航栏
    private TextView tvIndicatorTopFollow; //顶部跟团游
    private View viewIndicatorTopFollow;
    private TextView tvIndicatorTopFreedom; //顶部自由游
    private View viewIndicatorTopFreedom;
//    private RelativeLayout layoutTitle; //顶部Title
    private XScrollView mScrollView;
    private View content;

    private LinearLayout layoutIndicator;//底部GridView的导航栏(跟团游，自由游)
    private TextView tvFollow; //跟团游,导航按钮
    private TextView tvFreedom; //自由游,导航按钮
    private View viewFollow; //绿色条,跟团游
    private View viewFreedom; //绿色条,自由游
    private MyGridView gridViewFollow; //底部Gridview:自由游/跟团游数据
    private Tab1GridViewAdapter gvAdapter;

    private List<Travel> listsFollow = new ArrayList<>(); //跟团游
    private List<Travel> listsFreedom = new ArrayList<>(); //自由游
    private List<Travel> lists = new ArrayList<>(); //填充数据使用

    private int type; // 1：国内游，2：出境游
    private PhoneBean selectCity;

    private int page = 1; //此处有分页
    private int attr = 1; //1:跟团游，142:自由游
    private boolean refresh = true;
    private boolean loadMore;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_INNER_FOLLOW: //attr: 1—>跟团游，142—>自由游
                    if (msg.arg1 == 1){
                        List<Travel> follow = (List<Travel>) msg.obj;
                        if (follow == null || follow.size() == 0){
                            ToastUtil.show(getApplicationContext(), "没有更多数据");
                            resetValue();
                        }else{
                            if (refresh){
                                refresh = false;
                                if (attr == 1) {
                                    listsFollow = follow;
                                    lists = listsFollow;
                                }else if (attr == 142){
                                    listsFreedom = follow;
                                    lists = listsFreedom;
                                }
                                gvAdapter.setData(lists);
                                mScrollView.stopRefresh();
                            }
                            if (loadMore){
                                loadMore = false;
                                if (attr == 1) {
                                    listsFollow.addAll(follow);
                                    lists = listsFollow;
                                }else if (attr == 142){
                                    listsFreedom.addAll(follow);
                                    listsFreedom = follow;
                                }
                                gvAdapter.setData(lists);
                                mScrollView.stopLoadMore();
                            }
                        }
                    }else{
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                        resetValue();
                    }
                    break;
                case Consts.MESSAGE_INNER_TRAVEL_HOT_DESTINATION:
                    if (msg.arg1 == 1){
                        List<HotDestination> listDest = (List<HotDestination>) msg.obj;
                        if (listDest == null || listDest.size() == 0){
                            ToastUtil.show(getApplicationContext(), "没有热门目的地");
                        }else{
                            if (listDest.size() > 12){
                                listHotDestination = listDest.subList(0, 12);
                            }else {
                                listHotDestination = listDest;
                            }
                            setListViewHeightBasedOnChildren(gvHotDestination);
                            hotDestAdapter.setData(listHotDestination);
//                            setContainerData();
                        }
                    }else{
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                    }
                    break;
                case Consts.MESSAGE_EXCHANGE:
                    exchange = false;
                    if (msg.arg1 == 1){
                        List<CityRecommend> listDest = (List<CityRecommend>) msg.obj;
                        if (listDest == null || listDest.size() == 0){
                            ToastUtil.show(getApplicationContext(), "没有热门推荐");
                        }else{
                            listHotRecommend = listDest;
                            hotRecomAdapter.setData(listHotRecommend);
                        }
                    }else{
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                    }
                    break;
                case WHEEL:
                    if(flipper.getChildCount() != 0){
                        if(!isScrolling){
                            //向前滑向后滑
                            showNextView();
                        }
                    }
                    releaseTime = System.currentTimeMillis();
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, Consts.TIME_PERIOD);
                    break;
                case WHEEL_WAIT:
                    if(flipper.getChildCount() != 0){
                        handler.removeCallbacks(runnable);
                        handler.postDelayed(runnable, Consts.TIME_PERIOD);
                    }
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                    break;
            }
        }
    };

//    private int COLUMNS = 4; //列

//    private void setContainerData() {
//
//        layoutDestContainer.removeAllViews();
//        int row = 0;// 计算需要行数
//        if ((listHotDestination.size() % COLUMNS) == 0) {
//            row = listHotDestination.size() / COLUMNS;
//        } else {
//            row = (listHotDestination.size() / COLUMNS) + 1;
//        }
//
//        for (int i = 0; i < row; i++) {
//
//            LinearLayout rowLayout = new LinearLayout(getApplicationContext());
//
//            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//            rowLayout.setWeightSum(COLUMNS);
//
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            addActivityTag(i, rowLayout);
//
//            rowLayout.setLayoutParams(params);
//
//            layoutDestContainer.addView(rowLayout);
//        }
//    }

//    /**
//     * 添加每行数据
//     * @param row 行
//     * @param layout
//     */

//    private void addActivityTag(int row, LinearLayout layout) {
//
//        for (int i = 0; i < COLUMNS; i++) {
//
//            final int position = (row * COLUMNS + i);
//
//            TextView textView = new TextView(this);
//            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
//            tvParams.weight = 1;// 此处weight必须置为1
//            tvParams.bottomMargin = 10;
//            if (i == 1 || i == 2){ //左边，右边 margin
//                tvParams.leftMargin = 5;
//                tvParams.rightMargin = 5;
//            }
//            if (i == 0){ //右边 margin
//                tvParams.rightMargin = 5;
//            }
//            if (i == 3){ //左边 margin
//                tvParams.leftMargin = 5;
//            }
//            textView.setLayoutParams(tvParams);
//            if (position < listHotDestination.size()) {
//                textView.setText(listHotDestination.get(position).getCityName());
//                textView.setBackgroundResource(R.drawable.bg_et_city_unselected_corner_border);
//                textView.setTextSize(16f);
//                textView.setGravity(Gravity.CENTER);
//                textView.setTextColor(getResources().getColor(android.R.color.black));
//                textView.setPadding(0, 6, 0, 6);
//
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        HotDestination item = listHotDestination.get(position);
//                        String cityId = item.getCityId();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("cityId", cityId);
//                        bundle.putString("cityName", item.getCityName());
//                        InnerTravelCityActivity.actionStart(getApplicationContext(), bundle);
//                    }
//                });
//            }else{
//                textView.setVisibility(View.INVISIBLE);
//            }
//            layout.addView(textView);
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_inner_travel_main);
        getData();
        super.onCreate(savedInstanceState);
        getInternetData();
        getBannerData();
        getTravelData();
    }

    private void getTravelData() {
        InnerTravelMainBiz biz = new InnerTravelMainBiz(getApplicationContext(), handler, Consts.MESSAGE_INNER_FOLLOW);
        biz.getInnerTravelData(String.valueOf(type), String.valueOf(attr), String.valueOf(page));
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");
        selectCity = (PhoneBean) bundle.getSerializable("selectCity");
        if (selectCity == null){
            selectCity = new PhoneBean();
            selectCity.setCity_id("20");
            selectCity.setName("北京");
        }
    }

    private void getInternetData() {
        //热门目的地推荐
        InnerTravelMainBiz biz = new InnerTravelMainBiz(getApplicationContext(), handler, Consts.MESSAGE_INNER_TRAVEL_HOT_DESTINATION);
        biz.getHotDestination(String.valueOf(type));
        //热门推荐，换一换
        exchangeHotRecommend();
    }

    @Override
    protected void setupView() {
        super.setupView();
        imageUrls.add("drawable://" + R.drawable.ic_empty);
        if (type == 1){
            tvTitle.setText("国内游");
        }else if(type == 2){
            tvTitle.setText("出境游");
        }

//        layoutTitle = (RelativeLayout) findViewById(R.id.layout_inner_travel_main_title);
        layoutIndicatorTop      = (LinearLayout) findViewById(R.id.layout_inner_travel_main_indicator_top);
        tvIndicatorTopFollow    = (TextView) findViewById(R.id.tv_travel_main_indicator_top_follow);
        tvIndicatorTopFreedom   = (TextView) findViewById(R.id.tv_travel_main_indicator_top_freedom);
        viewIndicatorTopFollow  = findViewById(R.id.view_travel_main_indicator_top_follow);
        viewIndicatorTopFreedom = findViewById(R.id.view_travel_main_indicator_top_freedom);

        mScrollView = (XScrollView)findViewById(R.id.scroll_view);
        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(true);
        mScrollView.setAutoLoadEnable(false);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(Utils.getCurrentTime());

        content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_inner_travel_main_content, null);
        if (null != content) {
            View ivSearchLeft = content.findViewById(R.id.iv_title_search_left);
            ivSearchLeft.setVisibility(View.GONE);
            TextView etSearchText = (TextView) content.findViewById(R.id.edit_search);
            etSearchText.setHint("输入你想去的地方");
            etSearchText.setOnClickListener(this);

//            layoutDestContainer = (LinearLayout) content.findViewById(R.id.layout_dest_container); //热门目的地
//            layoutDestContainer.setVisibility(View.VISIBLE);
            gvHotDestination = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_hot); //热门目的地

            gvHotRecommend = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_recommend); //热门推荐
            tvHotRecommendExchange = (TextView) content.findViewById(R.id.tv_hot_recommend_exchange); //热门推荐，换一换

            layoutIndicator = (LinearLayout) content.findViewById(R.id.layout_inner_travel_main_indicator); //底部GridView的导航栏
            tvFollow = (TextView) content.findViewById(R.id.tv_inner_travel_main_follow);
            tvFreedom = (TextView) content.findViewById(R.id.tv_inner_travel_main_freedom);
            viewFollow = content.findViewById(R.id.indicator_follow);
            viewFreedom = content.findViewById(R.id.indicator_freedom);

            gridViewFollow = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_content); //底部GridView
            gridViewFollow.setFocusable(false);
            gridViewFollow.setFocusableInTouchMode(false);

            lists = listsFollow;
            gvAdapter = new Tab1GridViewAdapter(getApplicationContext(), lists, this);
            gridViewFollow.setAdapter(gvAdapter);

            mGestureDetector = new GestureDetector(getApplicationContext(), this);

            flipper = (ViewFlipper)content.findViewById(R.id.viewflipper);
            layoutPoint =(LinearLayout)content.findViewById(R.id.layout_indicator_point);

            addImageView(imageUrls.size());
            addIndicator(imageUrls.size());
            setIndicator(currentPosition);

            flipper.setOnTouchListener(this);

            dianSelect(currentPosition);
            MyScrollView myScrollView = (MyScrollView)content.findViewById(R.id.viewflipper_myScrollview);
            myScrollView.setGestureDetector(mGestureDetector);
        }
        mScrollView.setView(content);

        /**
         * 此处是为了顶部导航栏的显示和隐藏
         */
        mScrollView.setOnXScrollChangedI(new XScrollView.onXScrollChangedI() {
            @Override
            public void onXScrollChangedImpl(int l, int t, int oldl, int oldt) {
                int[] s = new int[2];
                layoutIndicator.getLocationOnScreen(s);
                int statusHeight = Utils.getStatusBarHeight(getApplicationContext());
//                int titleHeight = layoutTitle.getHeight();
                int titleHeight = actionBar.getHeight();
                if(statusHeight + titleHeight >= s[1]){
                    layoutIndicatorTop.setVisibility(View.VISIBLE);
                }else{
                    layoutIndicatorTop.setVisibility(View.GONE);
                }
            }
        });
//        for (int i=0; i < 8; i++) {
//            Travel travel = new Travel();
//            travel.setTravelPrice(String.valueOf(121 + i));
//            travel.setTravelTitle("旅游" + i);
//            travel.setTravelIconPath("drawable://" + R.mipmap.travel_icon);
//            listsFollow.add(travel);
//        }
        //热门目的地
        hotDestAdapter = new HotDestinationAdapter(this, listHotDestination);
        gvHotDestination.setAdapter(hotDestAdapter);
        //热门推荐
        hotRecomAdapter = new HotRecommendGridViewAdapter(getApplicationContext(), listHotRecommend);
        gvHotRecommend.setAdapter(hotRecomAdapter);
    }

    //顶部图片展示
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private ViewFlipper flipper;
    private LinearLayout layoutPoint;
    private List<String> imageUrls = new ArrayList<>();
    private ImageView[] indicators; // 轮播图片数组
    private int currentPosition = 0; // 轮播当前位置

    private static final int FLING_MIN_DISTANCE = 20;
    private static final int FLING_MIN_VELOCITY = 0;

    private final int WHEEL = 100; // 转动
    private final int WHEEL_WAIT = 101; // 等待
    private boolean isScrolling = false; // 滚动框是否滚动着
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换

    private void getBannerData() {
        //广告位
        ForeEndActionBiz fbiz = new ForeEndActionBiz();
//        mark:index（首页）、line_index(国内游、出境游)、header（分类上方）、visa_index（签证）、customize_index(个性定制)
        ForeEndAdvertise ad = new ForeEndAdvertise("line_index");
        fbiz.foreEndGetAdvertisingPosition(ad, new BizGenericCallback<ArrayList<ForeEndAdvertisingPositionInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<ForeEndAdvertisingPositionInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<ForeEndAdvertisingPositionInfo> array = model.body;
                    LogUtil.e(TAG,"foreEndGetAdvertisingPosition =" + array.toString());
                    refreshViewBanner(array);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取广告位数据失败");
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取广告位数据出错");
                }
                LogUtil.e(TAG, "foreEndGetAdvertisingPosition: " + error.toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_inner_travel_main_follow: //跟团游
            case R.id.tv_travel_main_indicator_top_follow: //跟团游
                if (attr == 1)  return;
                lists.clear();
                gvAdapter.notifyDataSetChanged();
                attr = 1;
                page = 1;
                refresh = true;
                changeIndicator();
                if (listsFollow.size() != 0) {
                    if (listsFollow.size() > 10){
                        listsFollow.subList(0, 10);
                    }
                    lists = listsFollow;
                    gvAdapter.setData(lists);
                    return;
                }
                getTravelData();
                break;
            case R.id.tv_inner_travel_main_freedom: //自由游
            case R.id.tv_travel_main_indicator_top_freedom: //自由游
                if (attr == 142)    return;
                lists.clear();
                gvAdapter.notifyDataSetChanged();
                attr = 142;
                page = 1;
                refresh = true;
                changeIndicator();
                if (listsFreedom.size() != 0){
                    if (listsFreedom.size() > 10 ) {
                        listsFreedom.subList(0, 10);
                    }
                    lists = listsFreedom;
                    gvAdapter.setData(lists);
                    return;
                }
                getTravelData();
                break;
            case R.id.tv_hot_recommend_exchange: //换一换
                exchangeHotRecommend();
                break;
            case R.id.edit_search: //搜索
                Bundle bundleSearch = new Bundle();
                bundleSearch.putSerializable("selectCity", selectCity);
                SearchActivity.actionStart(getApplicationContext(), bundleSearch);
                break;
        }
    }

    private void changeIndicator(){
        switch (attr){
            case 1:
                tvFollow.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewFollow.setVisibility(View.VISIBLE);
                tvFreedom.setTextColor(getResources().getColor(android.R.color.black));
                viewFreedom.setVisibility(View.INVISIBLE);

                tvIndicatorTopFollow.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewIndicatorTopFollow.setVisibility(View.VISIBLE);
                tvIndicatorTopFreedom.setTextColor(getResources().getColor(android.R.color.black));
                viewIndicatorTopFreedom.setVisibility(View.INVISIBLE);
                break;
            case 142:
                tvFreedom.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewFreedom.setVisibility(View.VISIBLE);
                tvFollow.setTextColor(getResources().getColor(android.R.color.black));
                viewFollow.setVisibility(View.INVISIBLE);

                tvIndicatorTopFreedom.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewIndicatorTopFreedom.setVisibility(View.VISIBLE);
                tvIndicatorTopFollow  .setTextColor(getResources().getColor(android.R.color.black));
                viewIndicatorTopFollow.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private boolean exchange; //是否正在进行换一换
    private void exchangeHotRecommend() {
        if (exchange)   return;
        exchange = true;
        //热门推荐，换一换
        ExchangeBiz bizE = new ExchangeBiz(getApplicationContext(), handler);
        bizE.getHotRecommend(String.valueOf(type));
    }

    @Override
    protected void addListener() {
        super.addListener();
        gvHotDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //热门目的地详情
                HotDestination item = listHotDestination.get(position);
                String cityId = item.getCityId();
                Bundle bundle = new Bundle();
                bundle.putString("cityId", cityId);
                bundle.putString("cityName", item.getCityName());
                InnerTravelCityActivity.actionStart(getApplicationContext(), bundle);
            }
        });
        gvHotRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //热门推荐详情，没有UI设计，此处不正确
//                CityRecommend item = listHotRecommend.get(i);
//                Bundle bundle = new Bundle();
//                bundle.putString("id", item.getCityId());
//                InnerTravelDetailActivity.actionStart(getApplicationContext(), bundle);

                CityRecommend item = listHotRecommend.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("cityId", item.getCityId());
                bundle.putString("cityName", item.getCityName());
                InnerTravelCityActivity.actionStart(getApplicationContext(), bundle);
            }
        });

        tvFollow.setOnClickListener(this);
        tvFreedom.setOnClickListener(this);
        tvIndicatorTopFollow.setOnClickListener(this);
        tvIndicatorTopFreedom.setOnClickListener(this);
        tvHotRecommendExchange.setOnClickListener(this); //换一换
        gridViewFollow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //进入旅游详情页
//                LogUtil.e(TAG, "i = " + i + ", l = " + l);
                Travel travel = lists.get((int) l);
                Bundle bundle = new Bundle();
                bundle.putString("id", travel.getId());
                InnerTravelDetailActivity.actionStart(getApplicationContext(), bundle);
            }
        });
    }


    /**
     * 热门目的地推荐回调
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
//    private void clickHotDestinationItem(AdapterView<?> adapterView, View view, int i, long l) {
//        Bundle bundle = new Bundle();
//        bundle.putString(Consts.KEY_INNER_CITY_NAME, listHotDestination.get(i));
//        InnerTravelCityActivity.actionStart(getApplicationContext(), bundle);
//    }

    /**
     * 下拉刷新回调
     */
    @Override
    public void onRefresh() {
        if (refresh)    return;
        refresh = true;
        page = 1;
        getTravelData();
    }

    /**
     * 上拉加载回调
     */
    @Override
    public void onLoadMore() {
        if (loadMore)   return;
        loadMore = true;
        page ++;
        getTravelData();
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, InnerTravelMainActivity.class);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     */
    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     * @param motionEvent1
     * @param v
     * @param v1
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /**
     * GestureDetector.OnGestureListener 回调
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY) {
//            LogUtil.i(TAG, "==============开始向左滑动了================");
            showNextView();
            resetTime();
            return true;
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY) {
//            Log.i(TAG, "==============开始向右滑动了================");
            showPreviousView();
            resetTime();
            return true;
        }
        return false;
    }

    private void resetTime() {
        if (infos.size() <= 1) {
            return;
        }
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, Consts.TIME_PERIOD);
    }

    /**
     * @param view      layout布局
     * @param viewGroup
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
        //讨价还价
        if (MainActivity.logged) { //|| (number != null && !"null".equals(number) && pwd != null && !"null".equals(pwd))
            Intent intent = new Intent(getApplicationContext(), EasemobLoginActivity.class);
            String im = null;
            if (attr == 1){ //跟团游
                im = listsFollow.get(position).getIm();
            }else if (attr == 142){ //自由游
                im = listsFreedom.get(position).getIm();
            }
            if (im == null || im.length() == 0){
                ToastUtil.show(getApplicationContext(), "当前商户暂未提供客服功能");
                return;
            }
            intent.putExtra("im", im);
            startActivity(intent);
        }else{
//            ToastUtil.show(getApplicationContext(), "请登录后再试");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("type", 2);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_LOGIN);
        }
    }

    private int REQUEST_LOGIN = 2913; //请求登录

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_LOGIN) { //登录成功
                User user = (User) data.getExtras().getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    MainActivity.logged = true;
                    MainActivity.user = user;
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                }
            }
        }else{
            if (requestCode == REQUEST_LOGIN) { //登录
                ToastUtil.show(getApplicationContext(), "登录失败");
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!InnerTravelMainActivity.this.isFinishing()) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > Consts.TIME_PERIOD - 500) {
                    handler.sendEmptyMessage(WHEEL);
                } else {
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    private void refreshViewBanner(ArrayList<ForeEndAdvertisingPositionInfo> array) {
        ArrayList<ADInfo> infosNew = new ArrayList<>();
//        for (int i = 0; i < array.size(); i++){
        ForeEndAdvertisingPositionInfo item = array.get(0);
        ArrayList<String> picList = item.getT();
        ArrayList<String> linkList = item.getL();
        for (int j = 0; j < picList.size(); j++){
            ADInfo ad = new ADInfo();
            ad.setUrl(picList.get(j));
            ad.setContent(linkList.get(j));
            infosNew.add(ad);
        }
//        }
        updateBanner(infosNew);
    }
    private void updateBanner(ArrayList<ADInfo> listsBanner) {
        infos = listsBanner;
        flipper.removeAllViews();
        for (int i = 0; i < infos.size(); i++) {
            flipper.addView(ViewFactory.getImageView(getApplicationContext(), infos.get(i).getUrl()));
        }
        addIndicator(infos.size());
        setIndicator(0);
        handler.postDelayed(runnable, Consts.TIME_PERIOD);
    }

    private void addIndicator(int size){
//        if(indicators == null) {
        indicators = new ImageView[size];
//        }
        layoutPoint.removeAllViews();
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_cycle_viewpager_indicator, null);
            indicators[i] = (ImageView) view.findViewById(R.id.image_indicator);
            layoutPoint.addView(view);
        }

    }

    private void setIndicator(int current){
        for(int i = 0; i < indicators.length; i++) {
            if(i == current) {
                indicators[current].setImageResource(R.drawable.icon_point_pre);
            }else{
                indicators[i].setImageResource(R.drawable.icon_point);
            }
        }
    }

    private void addImageView(int length) {
        for(int i=0; i < length; i++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls.get(i));
            info.setContent("图片-->" + i);
            infos.add(info);
            flipper.addView(ViewFactory.getImageView(getApplicationContext(), infos.get(i).getUrl()));
        }
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

    private void showNextView() {
//        Log.i(TAG, "========showNextView=======向左滑动=======");
        flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_left_out));
        flipper.showNext();
        currentPosition++;
        if(currentPosition == flipper.getChildCount()){
            dianUnselect(currentPosition - 1);
            currentPosition = 0;
            dianSelect(currentPosition);
        }else{
            dianUnselect(currentPosition - 1);
            dianSelect(currentPosition);
        }
//		Log.i(TAG, "==============第"+currentPage+"页==========");
    }

    private void showPreviousView() {
//        Log.i(TAG, "========showPreviousView=======向右滑动=======");
        dianSelect(currentPosition);
        flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_right_out));
        flipper.showPrevious();
        currentPosition--;
        if(currentPosition == -1){
            dianUnselect(currentPosition + 1);
            currentPosition = flipper.getChildCount() - 1;
            dianSelect(currentPosition);
        }else{
            dianUnselect(currentPosition + 1);
            dianSelect(currentPosition);
        }
//		Log.i(TAG, "==============第"+currentPage+"页==========");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    private void resetValue() {
        if (loadMore){
            loadMore = false;
            page --;
            mScrollView.stopLoadMore();
        }
        if (refresh){
            refresh = false;
            mScrollView.stopRefresh();
        }
    }
    //这种方式不可用，高度总是固定的
    public void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;


           int totalHeight1 = 0;
            //每4次，进行一次总高度相加
            //求出4个元素的最大高度，才进行一次高度相加操作
            for (int j = 0; j < listAdapter.getCount(); j += col){
                LogUtil.e(TAG, "j = " + j);

                int temp = j + 4;
                if (j + 4 >= listAdapter.getCount()){
                    temp = listAdapter.getCount();
                }
                int tempHeight = 0; //保存4个元素中高度较大的值
                for (int i = j; i < temp; i++) {
                    //计算每个item的高度
                    View listItem = listAdapter.getView(i, null, listView);
                    listItem.measure(0, 0);
                    int itemHeight = listItem.getMeasuredHeight();
                    LogUtil.e(TAG, "i = " + i + ", itemHeight = " + itemHeight);
                    //单个高度较大的，计入高度值
                    if (itemHeight > tempHeight){
                        tempHeight = itemHeight;
                        LogUtil.e(TAG, "itemHeight = " + itemHeight +", tempHeight = " + tempHeight);
                    }
                }
                totalHeight1 += tempHeight;
            }
            LogUtil.e(TAG, "totalHeight1 = " + totalHeight1);

        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        LogUtil.e(TAG, "totalHeight = " + totalHeight);
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }
}
