package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jhhy.cuiweitourism.ILauncherView;
import com.jhhy.cuiweitourism.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager适配器: ImageView, ViewIndicator
 * Created by jiahe008 on 2016/7/20.
 */
public class LauncherPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private List<View> views;
    //每页显示的图片
    private int[] images = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_4};
    private ILauncherView launcherView;

    public LauncherPagerAdapter(Context context, ILauncherView launcherView){
        views = new ArrayList<>();
        this.launcherView = launcherView;
        //初始化每页显示的View
        for(int i = 0; i < images.length; i ++) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_luancher_pager_item, null, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            imageView.setImageResource(images[i]);
            view.findViewById(R.id.tv_start_headlines).setOnClickListener(this);
            views.add(view);
        }

    }

    public List<View> getViews(){
        return views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onClick(View view) {
        launcherView.gotoMain();
    }
}
