package com.jhhy.cuiweitourism.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jiahe008 on 2016/8/14.
 */
public class My3522ImageView extends ImageView {
    public My3522ImageView(Context context) {
        super(context);
    }

    public My3522ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public My3522ImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public My3522ImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, 22 * widthMeasureSpec / 35);
    }
}
