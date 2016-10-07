package com.jhhy.cuiweitourism.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.jhhy.cuiweitourism.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        setContentView(R.layout.activity_base);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_main);

        Log.i("BaseActivity", "onCreate");
    }

}
