package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.ClassifyArea;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/8.
 */
public class Tab2ContentListViewAdapter extends BaseAdapter {
    private String TAG = getClass().getSimpleName();

    private LayoutInflater inflater;
    private List<ClassifyArea> mList;
    private Context context;

    public Tab2ContentListViewAdapter(Context context, List<ClassifyArea> list){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ClassifyArea getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Tab2ContentViewHolder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.tab2_listview_item, null);
            holder = new Tab2ContentViewHolder();
            holder.ivDestinationImg = (ImageView) view.findViewById(R.id.iv_tab2_listview_content_destination_img);
            holder.tvDestinationName = (TextView) view.findViewById(R.id.tv_tab2_listview_content_destination_name);
            view.setTag(holder);
        }else{
            holder = (Tab2ContentViewHolder) view.getTag();
        }
        ClassifyArea travel = getItem(i);
        holder.tvDestinationName.setText(travel.getAreaName());
        ImageLoaderUtil.getInstance(context).displayImage(travel.getAreaPic().substring(travel.getAreaPic().lastIndexOf("http://")), holder.ivDestinationImg);
//        ImageLoaderUtil.getInstance(context).getImage( holder.ivDestinationImg, travel.getAreaPic().substring(travel.getAreaPic().lastIndexOf("http://")));
//        ImageLoaderUtil.getInstance(context).setCallBack(new ImageLoaderUtil.ImageLoaderCallBack() {
//            @Override
//            public void refreshAdapter(Bitmap loadedImage) {
//
//            }
//        });
        return view;
    }

    public void setData(List<ClassifyArea> listRight) {
        this.mList = listRight;
        notifyDataSetChanged();
    }

    class Tab2ContentViewHolder{
        private ImageView ivDestinationImg;
        private TextView  tvDestinationName;
    }
}
