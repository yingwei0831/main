package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotelImageViewAdapter;

public class HotelPhotoActivity extends BaseActionBarActivity {

    RecyclerView cyImages;
    HotelImageViewAdapter adapter;
//    {"head":{"code":"Hotel_image"},"field":{"HotelID":"90203350","RoomID":"0001","Count":"0"}}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hotel_photo);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupView() {
        super.setupView();
        cyImages = (RecyclerView) findViewById(R.id.rv_recycleview);
        cyImages.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
    }

    @Override
    protected void addListener() {
        super.addListener();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }
}
