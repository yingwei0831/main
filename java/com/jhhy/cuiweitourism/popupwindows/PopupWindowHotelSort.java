package com.jhhy.cuiweitourism.popupwindows;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InnerTravelFirstListViewAdapter;

import java.util.ArrayList;


public class PopupWindowHotelSort extends PopupWindow implements AdapterView.OnItemClickListener {

    private Activity mActivity;
    private String TAG = PopupWindowHotelSort.class.getSimpleName();

    private int sortPosition = 0;
    private ListView listView;
    private ArrayList<String> listSort = new ArrayList<>();
    private InnerTravelFirstListViewAdapter adapter;

    public PopupWindowHotelSort(Activity activity, View parent) {
        super(activity);
        mActivity = activity;
        View view = View.inflate(activity, R.layout.popup_hotel_sort, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        initView(view);
        addListener();
    }

    /**
     * 第二次进入窗口，要显示之前选择的数ju
     */
    public void refreshView(int sortPosition){
        this.sortPosition = sortPosition;
        adapter.setSelectPosition(sortPosition);
        adapter.notifyDataSetChanged();
    }



    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.list_view_popup_sort);
        listSort.add("默认排序");
        listSort.add("价格 低高");
        listSort.add("价格 高低");
        listSort.add("星级 高低");
        listSort.add("星级 低高");

        adapter = new InnerTravelFirstListViewAdapter(mActivity, listSort);
        adapter.setSelectPosition(sortPosition);
        listView.setAdapter(adapter);
    }

    private void addListener() {
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        sortPosition = i;
        adapter.setSelectPosition(sortPosition);
        adapter.notifyDataSetChanged();
        dismiss();
    }

    public int getSortPosition(){
        return sortPosition;
    }

}
