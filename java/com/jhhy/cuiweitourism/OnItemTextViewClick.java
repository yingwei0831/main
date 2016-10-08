package com.jhhy.cuiweitourism;

import android.view.View;

/**
 * Created by jiahe008 on 2016/9/20.
 */
public interface OnItemTextViewClick {

    /**
     *
     * @param position 点击的联系人位置
     * @param textView 控件
     * @param id textView 的id
     */
    public void onItemTextViewClick(int position, View textView, int id);
}
