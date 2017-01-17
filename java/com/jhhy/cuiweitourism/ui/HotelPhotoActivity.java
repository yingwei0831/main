package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.NormalRecyclerViewAdapter;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelImagesResponse;

import java.util.ArrayList;

public class HotelPhotoActivity extends BaseActionBarActivity implements NormalRecyclerViewAdapter.onItemClickListener {

    RecyclerView cyImages;
    ArrayList<String> listImages;
    NormalRecyclerViewAdapter adapter;
//    {"head":{"code":"Hotel_image"},"field":{"HotelID":"90203350","RoomID":"0001","Count":"0"}}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hotel_photo);
        getData();
        super.onCreate(savedInstanceState);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            listImages = intent.getStringArrayListExtra("images");
        }
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText("酒店图片");
        cyImages = (RecyclerView) findViewById(R.id.rv_recycleview);
        adapter = new NormalRecyclerViewAdapter(getApplicationContext(), listImages);
        adapter.setOnItemClickListener(this);
        cyImages.setAdapter(adapter);
        cyImages.setLayoutManager(new LinearLayoutManager(getApplicationContext())); //这里用线性显示 类似于listview
//        cyImages.setLayoutManager(new GridLayoutManager(this, 2));
//        cyImages.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)); //这里用线性宫格显示 类似于瀑布流
    }

    @Override
    protected void addListener() {
        super.addListener();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }

    @Override
    public void onItemClick(View view, int position) {
        //点击某个图片，是否展示大图？

    }
}
