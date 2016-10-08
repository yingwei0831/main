package com.jhhy.cuiweitourism.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ScrollView;

/**
 * Created by jiahe008 on 2016/9/28.
 */
public class MyScrollViewIS extends ScrollView implements AbsListView.OnScrollListener {
    public MyScrollViewIS(Context context) {
        super(context);
    }

    public MyScrollViewIS(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollViewIS(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyScrollViewIS(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(myOnXScrollChangedI != null) {
            myOnXScrollChangedI.onScrollChangedImpl(l, t, oldl, oldt);
        }
    }

    private onScrollChangedI myOnXScrollChangedI;

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    public interface onScrollChangedI{
        public void onScrollChangedImpl(int l, int t, int oldl, int oldt);
    }
    public void setOnScrollChangedI(onScrollChangedI l){
        myOnXScrollChangedI = l;
    }

}
