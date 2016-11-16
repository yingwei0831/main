package com.jhhy.cuiweitourism.popupwindows;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.picture.TestPicActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.ui.Tab4AccountCertificationActivity;

import java.io.File;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class PopupWindowImage extends PopupWindow {

    private Activity activity;
    private String mPicName;

    public static final int TAKE_PICTURE = 0x000009; //
    public static final int OTHER_PICTURE = 0x0001020; //可以选择多张图片
    public static final int OTHER_PICTURE_ONE = 0x0001021; //可以选择一张图片


    public PopupWindowImage(final Activity activity, View parent, String picName) {
        this.activity = activity;
        this.mPicName = picName;

        View view = View
                .inflate(activity, R.layout.item_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view
                .findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_bottom_in_2));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkPermission();
                photo();
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, TestPicActivity.class);
                if (activity instanceof Tab4AccountCertificationActivity){ //此处只允许上传一张图片
                    Bundle bundle = new Bundle();
                    bundle.putInt("number", 1);
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent, OTHER_PICTURE_ONE);
                }else{ //这里可以上传多张图片
                    activity.startActivityForResult(intent, OTHER_PICTURE);
                }
                dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void checkPermission(){
        // Check whether we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,//需要请求的所有权限，这是个数组String[]
                    REQUEST_EXTERNAL_STORAGE//请求码
            );
        }
    }
    private void photo() {
        String status = Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED))  {
            File dir = new File(Consts.IMG_TEMP_PATH);
            if(!dir.exists())   dir.mkdirs();

            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(dir, mPicName);
            // path = file.getPath();
            Uri imageUri = Uri.fromFile(file);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            activity.startActivityForResult(openCameraIntent, TAKE_PICTURE);
        } else {
            Toast.makeText(activity, "没有储存卡",Toast.LENGTH_SHORT).show();
        }
    }

}
