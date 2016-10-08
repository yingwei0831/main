package com.jhhy.cuiweitourism;

import android.view.View;

/**
 * Created by jiahe008 on 2016/8/4.
 */
public interface ArgumentOnClick {
    /**
     *
     * @param view layout布局
     * @param viewGroup
     * @param position 列表中的位置
     * @param which 控件的id
     */
    public void goToArgument(View view, View viewGroup, int position, int which);
}
