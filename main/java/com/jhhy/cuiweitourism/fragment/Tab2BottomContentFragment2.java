package com.jhhy.cuiweitourism.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.Tab2ContentListViewAdapter;
import com.jhhy.cuiweitourism.adapter.Tab2LeftListViewAdapter;
import com.jhhy.cuiweitourism.biz.ClassifyBiz;
import com.jhhy.cuiweitourism.moudle.ClassifyArea;
import com.jhhy.cuiweitourism.ui.InnerTravelCityActivity;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.ns.fhvp.IPagerScroll;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2BottomContentFragment2 extends Fragment implements IPagerScroll, AdapterView.OnItemClickListener {

    private static final String TAG = Tab2BottomContentFragment2.class.getSimpleName();

    private ListView listView;
    private GridView gridView;
    private int selection = 0;

    private boolean listTop;
    private boolean gridTop;
    private boolean listOnTouch;
    private boolean gridOnTouch;

    private List<String> listLeft = new ArrayList<>();
    private List<ClassifyArea> listRight = new ArrayList<>();

    private Tab2LeftListViewAdapter adapterLeft;
    private Tab2ContentListViewAdapter adapterRight;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_CLASSIFY_OUT:
                    LoadingIndicator.cancel();
                    if(0 == msg.arg1){
                        ToastUtil.show(getContext(), "获取数据失败");
                    }else if(1 == msg.arg1) {
                        List[] array = (List[]) msg.obj;
                        if(array != null) {
                            listLeft = array[1];
                            listRight = array[0];
                            adapterLeft.setData(listLeft);
                            listView.setSelection(0);
                            adapterRight.setData(listRight.get(0).getListProvince());

                            handler.postDelayed(new Runnable() {
                                @Override public void run() {
                                    listView.performItemClick(listView.getAdapter().getView(0, null, null), 0, listView.getAdapter().getItemId(0));
                                }
                            }, 1000);

                        }else{
                            ToastUtil.show(getContext(), "没有分类数据");
                        }
                    }
                    break;
            }
        }
    };

    public Tab2BottomContentFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2_bottom_content, container, false);

        listView = (ListView) view.findViewById(R.id.lv_tab2_bottom_left);
        gridView = (GridView) view.findViewById(R.id.gv_tab2_bottom_right);

        adapterLeft = new Tab2LeftListViewAdapter(getContext(), listLeft);
        adapterLeft.setSelection(selection);
        listView.setAdapter(adapterLeft);

        ClassifyArea area = new ClassifyArea();
        List<ClassifyArea> pro = new ArrayList<>();
        area.setListProvince(pro);
        listRight.add(area);
        adapterRight = new Tab2ContentListViewAdapter(getContext(), listRight.get(0).getListProvince());
        gridView.setAdapter(adapterRight);

        getData("2");
        addListener();
        return view;
    }
    private void getData(String type) {
//        {"head":{"code":"Publics_classify"},"field":{"type":"1"}} //type:1国内、2出境、102周边
        LoadingIndicator.show(getActivity(), getString(R.string.http_notice));
        ClassifyBiz biz = new ClassifyBiz(getContext(), handler);
        biz.getAreaList(type,  Consts.MESSAGE_CLASSIFY_OUT);
    }

    private void addListener() {
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
//                LogUtil.i(TAG, "======== listView onScrollStateChanged ========"+i);
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                LogUtil.i(TAG, "======== listView onScroll ========"+firstVisibleItem +", " +visibleItemCount+", " + totalItemCount);
                if(firstVisibleItem == 0){
                    listTop = true;
                } else {
                    listTop = false;
                }
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0){
                    gridTop = true;
                }else{
                    gridTop = false;
                }
            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                listOnTouch = true;
                gridOnTouch = false;
                return false;
            }
        });
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gridOnTouch = true;
                listOnTouch = false;
                return false;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.i(TAG, "gridView onItemClick " + " position = " + i + ", id = " + l);
                String type = "2"; //出境游
                Intent intent = new Intent(getContext(), InnerTravelCityActivity.class); //当前城市列表
                Bundle bundle = new Bundle();
                bundle.putString("cityId", adapterRight.getItem(i).getAreaId());
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_VIEW_LIST);
            }
        });
    }
    private int REQUEST_CODE_VIEW_LIST = 1101;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VIEW_LIST){
            if (resultCode == Activity.RESULT_OK){

            }
        }
    }

    @Override
    public boolean isFirstChildOnTop() {
//        return PagerScrollUtils.isFirstChildOnTop(listView) && PagerScrollUtils.isFirstChildOnTop(gridView);
        if(listOnTouch){
            return listTop;
        }
        if(gridOnTouch){
            return gridTop;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        adapterLeft.setSelection(i);
        adapterLeft.notifyDataSetChanged();
        adapterRight.setData(listRight.get((int)l).getListProvince());
    }
}
