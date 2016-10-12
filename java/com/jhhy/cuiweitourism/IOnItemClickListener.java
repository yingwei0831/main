package com.jhhy.cuiweitourism;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by jiahe008 on 2016/10/12.
 */
public interface IOnItemClickListener {
    /**
     *列表的 item 点击事件
     * @param adapterView
     * @param view
     * @param position 当前列表的 position
     * @param id
     * @param parentPosition 父列表的 position
     */
    public void onItemClickI(AdapterView<?> adapterView, View view, int position, long id, int parentPosition);
}
