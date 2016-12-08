package com.jhhy.cuiweitourism.fragment;


import android.content.Context;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.Tab1GridViewAdapter;
import com.jhhy.cuiweitourism.model.ADInfo;
import com.jhhy.cuiweitourism.model.Travel;
import com.jhhy.cuiweitourism.ui.CitySelectionActivity;
import com.jhhy.cuiweitourism.ui.InnerTravelMainActivity3;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1Fragment_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1Fragment_2 extends Fragment implements GestureDetector.OnGestureListener, View.OnClickListener, ArgumentOnClick {

    private static final String TAG = Tab1Fragment_2.class.getSimpleName();


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

    public Tab1Fragment_2() {
        // Required empty public constructor
    }

    public static Tab1Fragment_2 newInstance(String param1, String param2) {
        Tab1Fragment_2 fragment = new Tab1Fragment_2();
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
    private TextView tvSearchBarAddress;
    private MyGridView gridViewRecommend;

    private List<Travel> lists = new ArrayList<>();
    private Tab1GridViewAdapter adapter;

    private TextView tvRow1;
    private MyGridView gvTab1Travel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LogUtil.i(TAG, "======onCreateView======");

        View view = inflater.inflate(R.layout.my_x_scroll_view_2, container, false);
        setupView(view);
        addListener();

        return view;
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

    private void setupView(View view) {

        for(int i = 0; i < 20; i++){
            Travel travel = new Travel();
            travel.setTravelTitle(getString(R.string.tab1_recommend_for_you_title));
            travel.setTravelPrice("12100");
            lists.add(travel);
        }

        tvSearchBarAddress = (TextView) view.findViewById(R.id.tv_search_bar_main_left_location);

        tvRow1 = (TextView) view.findViewById(R.id.tv_tab1_2_inner_travel);


        gvTab1Travel = (MyGridView) view.findViewById(R.id.gv_tab1_2);
        adapter = new Tab1GridViewAdapter(mContext, lists, this);
        gvTab1Travel.setAdapter(adapter);

    }

    private void addListener() {
        tvSearchBarAddress.setOnClickListener(this);
        tvRow1.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab1_2_inner_travel:
//                InnerTravelCityActivity.actionStart(getContext(), null);
                InnerTravelMainActivity3.actionStart(getContext(), null);
                break;
            case R.id.tv_search_bar_main_left_location: //顶部搜索栏地址
                CitySelectionActivity.actionStart(getContext(), null);
                break;
        }
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
    }
}
