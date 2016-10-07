package com.jhhy.cuiweitourism.fragment;


import android.content.Context;
import android.os.Bundle;
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
import com.jhhy.cuiweitourism.moudle.ADInfo;
import com.jhhy.cuiweitourism.moudle.ClassifyArea;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.jhhy.cuiweitourism.view.MyListView;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2Fragment extends Fragment implements XScrollView.IXScrollViewListener, GestureDetector.OnGestureListener, AbsListView.OnScrollListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = Tab2Fragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private Context mContext;
    private View content;
    private XScrollView mScrollView;

    private ListView listViewIndicator;
    private ArrayAdapter indicatorAdapter;

    private MyGridView listViewContent;
    private Tab2ContentListViewAdapter contentAdapter;


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
        setupView(view);
        addListener();
        return view;
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

            mGestureDetector = new GestureDetector(getActivity(), this);

            listViewContent = (MyGridView) content.findViewById(R.id.tab2_gridview_content);
            listViewContent.setFocusable(false);
            listViewContent.setFocusableInTouchMode(false);
            contentAdapter = new Tab2ContentListViewAdapter(getContext(), mData.get(0).getListProvince());
            listViewContent.setAdapter(contentAdapter);


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
            Log.i(TAG, "==============开始向左滑动了================");
            showNextView();
        }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Log.i(TAG, "==============开始向右滑动了================");
            showPreviousView();
        }
        return false;
    }

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
}
