package com.jhhy.cuiweitourism.popupwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.VisaTypeListAdapter;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.List;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class PopupWindowVisaType extends PopupWindow implements AdapterView.OnItemClickListener {

    private Activity activity;

    private VisaTypeListAdapter adapter;
    private List<String> lists;
    private int selection;

    private String TAG = PopupWindowVisaType.class.getSimpleName();

    public PopupWindowVisaType(final Activity activity, View parent, List<String> visaTypeList) {
        this.activity = activity;
        this.lists = visaTypeList;

        View view = View.inflate(activity, R.layout.popup_visa_type, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
//        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));
        setAnimationStyle(R.style.anim_menu_bottombar);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
//        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        showAsDropDown(parent, 0, -Utils.getScreenHeight(activity));
        update();

        ListView listView = (ListView) view.findViewById(R.id.list_visa_type);
        adapter = new VisaTypeListAdapter(activity, visaTypeList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        adapter.setSelection(i);
        adapter.notifyDataSetChanged();
        selection = i;
        dismiss();
    }

    public int getSelection(){
        return selection;
    }

    public void refreshView(int selection){
        this.selection = selection;
        adapter.setSelection(selection);
        adapter.notifyDataSetChanged();
    }

}
