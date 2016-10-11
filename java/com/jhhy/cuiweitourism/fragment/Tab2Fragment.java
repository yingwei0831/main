package com.jhhy.cuiweitourism.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.Tab1GridViewAdapter;
import com.jhhy.cuiweitourism.adapter.Tab2ContentListViewAdapter;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.moudle.ADInfo;
import com.jhhy.cuiweitourism.moudle.ClassifyArea;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.biz.ForeEndActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndAdvertise;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndAdvertisingPositionInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.jhhy.cuiweitourism.view.MyListView;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.just.sun.pricecalendar.ToastCommon;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2Fragment extends Fragment implements XScrollView.IXScrollViewListener, GestureDetector.OnGestureListener, AbsListView.OnScrollListener, View.OnTouchListener {

    private static final String TAG = Tab2Fragment.class.getSimpleName();


    private Context mContext;
    private View content;
    private XScrollView mScrollView;

    private ListView listViewIndicator;
    private ArrayAdapter indicatorAdapter;

    private MyGridView listViewContent;
    private Tab2ContentListViewAdapter contentAdapter;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case WHEEL:
                    if(flipper.getChildCount() != 0){
                        if(!isScrolling){
                            //向前滑向后滑
                            showNextView();
                        }
                    }
                    releaseTime = System.currentTimeMillis();
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, time);
                    break;
                case WHEEL_WAIT:
                    if(flipper.getChildCount() != 0){
                        handler.removeCallbacks(runnable);
                        handler.postDelayed(runnable, time);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (getActivity() != null && !getActivity().isFinishing()) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > time - 500) {
                    handler.sendEmptyMessage(WHEEL);
                } else {
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    public Tab2Fragment() {
        // Required empty public constructor
    }

    public static Tab2Fragment newInstance(String param1, String param2) {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab2_my_x_scroll_view, container, false);
        getInternetData();
        setupView(view);
        addListener();
        handler.postDelayed(runnable, time);
        return view;
    }

    private void getInternetData() {
        imageUrls.add("drawable://" + R.drawable.ic_empty);
        //广告位
        ForeEndActionBiz fbiz = new ForeEndActionBiz();
//        mark:index（首页）、line_index(国内游、出境游)、header（分类上方）、visa_index（签证）、customize_index(个性定制)
        ForeEndAdvertise ad = new ForeEndAdvertise("header");
        fbiz.foreEndGetAdvertisingPosition(ad, new BizGenericCallback<ArrayList<ForeEndAdvertisingPositionInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<ForeEndAdvertisingPositionInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<ForeEndAdvertisingPositionInfo> array = model.body;
                    LogUtil.e(TAG,"foreEndGetAdvertisingPosition =" + array.toString());
                    refreshViewBanner(array);
                }else{
                    ToastCommon.toastShortShow(getContext(), null, "获取广告位数据失败");
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getContext(), null, "获取广告位数据出错");
                }
                LogUtil.e(TAG, "foreEndGetAdvertisingPosition: " + error.toString());
            }
        });
    }

    private void setupView(View view) {
        List<ClassifyArea> mData = new ArrayList<>();
        for(int i = 0; i < 40; i++){
            ClassifyArea travel = new ClassifyArea();
            travel.setAreaName("巴拉拉");
            mData.add(travel);
        }

        mScrollView = (XScrollView)view.findViewById(R.id.scroll_view_classification);
        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(true);
        mScrollView.setAutoLoadEnable(true);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(Utils.getCurrentTime());

        mScrollView.setOnScrollListener(this); //头部停靠

        content = LayoutInflater.from(getActivity()).inflate(R.layout.tab2_my_x_scroll_view_content, null);
        if (null != content) {
            listViewIndicator = (ListView) content.findViewById(R.id.tab2_listview_indicator);
            listViewIndicator.setFocusable(false);
            listViewIndicator.setFocusableInTouchMode(false);

            indicatorAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, new String[]{"热门","上海","北京","海南","东北","华北","西北","云南","香港","澳门","热门","上海","北京","海南","东北","华北","西北","云南","香港","澳门"});
            listViewIndicator.setAdapter(indicatorAdapter);

            listViewContent = (MyGridView) content.findViewById(R.id.tab2_gridview_content);
            listViewContent.setFocusable(false);
            listViewContent.setFocusableInTouchMode(false);
            contentAdapter = new Tab2ContentListViewAdapter(getContext(), mData.get(0).getListProvince());
            listViewContent.setAdapter(contentAdapter);

            mGestureDetector = new GestureDetector(getActivity(), this);

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
    }

    private void addListener() {

    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "=====onRefresh=====");
    }

    @Override
    public void onLoadMore() {
        Log.i(TAG, "=====onLoadMore=====");

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
//        Log.i(TAG, "=========================================单击事件！");
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
//            Log.i(TAG, "==============开始向左滑动了================");
            showNextView();
            releaseTime = System.currentTimeMillis();
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, time);
            return true;
        }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
//            Log.i(TAG, "==============开始向右滑动了================");
            showPreviousView();
            releaseTime = System.currentTimeMillis();
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, time);
            return true;
        }else{
            return false;
        }
    }

    //  轮播图片
    private List<ADInfo> infos = new ArrayList<ADInfo>();

    private ViewFlipper flipper;
    private LinearLayout layoutPoint;
    private List<String> imageUrls = new ArrayList<>();

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

    private void showNextView() {
        Log.i(TAG, "========showNextView=======向左滑动=======");
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
        Log.i(TAG, "==============第"+currentPosition+"页==========");
    }

    private void showPreviousView() {
        // TODO Auto-generated method stub
//        Log.i(TAG, "========showPreviousView=======向右滑动=======");
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
//		Log.i(TAG, "==============第"+currentPage+"页==========");
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

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        Log.i(TAG, "=====onScrollStateChanged=====");
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        Log.i(TAG, "=====onScroll=====");

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return  mGestureDetector.onTouchEvent(motionEvent);
    }
    private void addImageView(int length) {
        for(int i=0; i < length; i++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls.get(i));
            info.setContent("图片-->" + i);
            infos.add(info);
            flipper.addView(ViewFactory.getImageView(getContext(), infos.get(i).getUrl()));
        }
    }

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
            flipper.addView(ViewFactory.getImageView(getContext(), infos.get(i).getUrl()));
        }
        addIndicator(infos.size());
        setIndicator(0);
    }

    private void addIndicator(int size){
//        if(indicators == null) {
        indicators = new ImageView[size];
//        }
        layoutPoint.removeAllViews();
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_cycle_viewpager_indicator, null);
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
}
