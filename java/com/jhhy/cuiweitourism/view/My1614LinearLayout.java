package com.jhhy.cuiweitourism.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by jiahe008 on 2016/8/4.
 */
public class My1614LinearLayout extends LinearLayout {

    public My1614LinearLayout(Context context) {
        super(context);
    }

    public My1614LinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public My1614LinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec * 14 / 16);
    }
}
