package com.jhhy.cuiweitourism.view;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import android.webkit.WebView;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.net.URL;

/**
 * Created by jiahe008 on 2016/8/29.
 */
public class InnerTravelDetailDescribeView extends LinearLayout {

    private static final String TAG = InnerTravelDetailDescribeView.class.getSimpleName();

    private TextView tvDay;
    private TextView tvTitle;
    private View viewTop;
    private View viewBottom;
    private Handler handler;

//    private TextView tvContent;
    private WebView webViewContent;
    String start = "<html><style>body img{width:100%;}</style><body>";
    String end = "</body></html>";


    public InnerTravelDetailDescribeView(Context context, Handler handler) {
        this(context, null, handler);
    }

    public InnerTravelDetailDescribeView(Context context, AttributeSet attrs, Handler handler) {
        this(context, attrs, 0, handler);
    }

    public InnerTravelDetailDescribeView(Context context, AttributeSet attrs, int defStyleAttr, Handler handler) {
        super(context, attrs, defStyleAttr);
        this.handler = handler;
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_inner_travel_details_describe, this);
        tvDay = (TextView) rootView.findViewById(R.id.tv_inner_travel_details_describe_day);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_inner_travel_details_describe_title);
        viewTop = rootView.findViewById(R.id.view_inner_travel_details_describe_top);
        viewBottom = rootView.findViewById(R.id.view_inner_travel_details_describe_bottom);

//        tvContent = (TextView) rootView.findViewById(R.id.tv_inner_travel_details_describe_content);
        webViewContent = (WebView) rootView.findViewById(R.id.webview_travel_details_content);
    }

    /**
     * 设置第几天
     *
     * @param day
     */
    public void setTvDayText(String day) {
        tvDay.setText(day);
    }

    /**
     * 设置天的标题
     *
     * @param title
     */
    public void setTvTitleText(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置详细内容
     *
     * @param content
     */
    public void setTvContent(final String content) {
//        tvContent.setText(content);
        webViewContent.loadDataWithBaseURL(null, start + content + end, "text/html", "utf-8", null);
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

    public void isFirstStep(boolean firstStep) {
        if (firstStep) {
            viewTop.setVisibility(View.INVISIBLE);
        }
    }

    public void isLastStep(boolean lastStep) {
        if (lastStep) {
            viewBottom.setVisibility(View.GONE);
        }
    }

    public void isCenterStep(boolean centerStep) {
        if (centerStep) {

        }
    }

}
