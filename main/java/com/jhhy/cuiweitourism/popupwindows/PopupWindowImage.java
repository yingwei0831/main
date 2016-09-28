package com.jhhy.cuiweitourism.popupwindows;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.picture.PublishedActivity;
import com.jhhy.cuiweitourism.picture.TestPicActivity;
import com.jhhy.cuiweitourism.utils.Consts;

import java.io.File;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class PopupWindowImage extends PopupWindow {

    private Activity activity;
    private String mPicName;

    public static final int TAKE_PICTURE = 0x000009;
    public static final int OTHER_PICTURE=0x0001020;


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
                photo();
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, TestPicActivity.class);
                activity.startActivityForResult(intent, OTHER_PICTURE);
                dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

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
