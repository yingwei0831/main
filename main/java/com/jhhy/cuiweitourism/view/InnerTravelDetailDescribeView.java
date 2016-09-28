package com.jhhy.cuiweitourism.view;

import android.content.Context;
import android.graphics.Canvas;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.LinearLayout;

import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.utils.LogUtil;

/**
 * Created by jiahe008 on 2016/8/29.
 */
public class InnerTravelDetailDescribeView extends LinearLayout{

    private static final String TAG = InnerTravelDetailDescribeView.class.getSimpleName();

    private TextView tvDay;
    private TextView tvTitle;
    private View viewTop;
    private View viewBottom;

    private TextView tvContent;

    public InnerTravelDetailDescribeView(Context context) {
        this(context, null);
    }

    public InnerTravelDetailDescribeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerTravelDetailDescribeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_inner_travel_details_describe, this);
        tvDay = (TextView) rootView.findViewById(R.id.tv_inner_travel_details_describe_day);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_inner_travel_details_describe_title);
        viewTop = rootView.findViewById(R.id.view_inner_travel_details_describe_top);
        viewBottom = rootView.findViewById(R.id.view_inner_travel_details_describe_bottom);

        tvContent = (TextView) rootView.findViewById(R.id.tv_inner_travel_details_describe_content);

    }

    /**
     * 设置第几天
     * @param day
     */
    public void setTvDayText(String day){
        tvDay.setText(day);
    }

    /**
     * 设置天的标题
     * @param title
     */
    public void setTvTitleText(String title){
        tvTitle.setText(title);
    }

    /**
     * 设置详细内容
     * @param content
     */
    public void setTvContent(String content){
        tvContent.setText(content);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtil.i(TAG, "------onDraw------");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void isFirstStep(boolean firstStep){
        if(firstStep) {
            viewTop.setVisibility(View.INVISIBLE);
        }
    }

    public void isLastStep(boolean lastStep){
        if(lastStep) {
            viewBottom.setVisibility(View.GONE);
        }
    }

    public void isCenterStep(boolean centerStep){
        if(centerStep) {

        }
    }

}
