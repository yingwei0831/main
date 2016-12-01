package com.jhhy.cuiweitourism.popupwindows;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.TrainSeatTypeAdapter;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;

import java.util.ArrayList;

/**
 * Created by jiahe008 on 2016/11/30.
 */
public class PopTrainSeatType extends PopupWindow implements View.OnClickListener {

    private int position;
    private ListView listView;
    private TrainSeatTypeAdapter adapter;
    private TrainTicketDetailInfo.SeatInfo seatInfo;
    private TextView tvCancel; //取消
    private TextView tvConfirm; //确认
    private boolean commit;

    public PopTrainSeatType(Context context, final ArrayList<TrainTicketDetailInfo.SeatInfo> seatInfos) {

        View view = View.inflate(context, R.layout.layout_one_wheel, null);
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_ins));
//        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//        ll_popup.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_bottom_in_2));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
//        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        tvCancel = (TextView) view.findViewById(R.id.title_main_tv_left_location);
        tvConfirm = (TextView) view.findViewById(R.id.title_main_iv_right_telephone);

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        listView = (ListView) view.findViewById(R.id.list_seat_price);
        adapter = new TrainSeatTypeAdapter(context, seatInfos);
        listView.setAdapter(adapter);
        seatInfo = seatInfos.get(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                seatInfo = seatInfos.get(i);
                adapter.setSelection(i);
            }
        });
    }

    public void refreshView(int seatTypeSelection) {
        commit = false;
        listView.setSelection(seatTypeSelection);
    }

    public TrainTicketDetailInfo.SeatInfo getSeatInfo(){
        return seatInfo;
    }

    public int getSelection(){
        return position;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location: //取消
                commit = false;
                dismiss();
                break;
            case R.id.title_main_iv_right_telephone: //确定
                commit = true;
                dismiss();
                break;
        }
    }

    public boolean getCommit(){
        return commit;
    }
}
