package com.jhhy.cuiweitourism.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.UserInformationBiz;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.picture.Bimp;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowImage;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.MyFileUtils;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.CircleImageView;
import com.just.sun.pricecalendar.ToastCommon;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Tab4UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = Tab4UserInfoActivity.class.getSimpleName();

    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private View layout;

    private RelativeLayout layoutUserIcon;
    private CircleImageView civUserIcon;
    private RelativeLayout layoutNickName;
    private TextView tvNickName;

    private RelativeLayout layoutGender;
    private TextView tvGender;
    private RelativeLayout layoutMobile;
    private TextView tvMobile;

    private TextView tvNotice;
    private LinearLayout layoutRealInfo;
    private TextView tvRealName;
    private TextView tvID;

    private String gender;
    private boolean changeIcon;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_MODIFY_USER_ICON: //修改用户头像
                    if (msg.arg1 == 1){
                        civUserIcon.setImageBitmap(Bimp.bmp.get(0));//把图片显示在ImageView控件上
                        ImageLoaderUtil.getInstance(getApplicationContext()).loadImage(String.valueOf(msg.obj));
                        MainActivity.user.setUserIconPath(String.valueOf(msg.obj));
                        changeIcon = true;
                    }else{
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    }
                    LoadingIndicator.cancel();
                    break;
                case MESSAGE_REFRESH_IMAGE:
                    modifyUserIcon();
                    break;
                case Consts.MESSAGE_MODIFY_USER_INFO: //修改用户性别
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        tvGender.setText(gender);
                        MainActivity.user.setUserGender(gender);
                    }
                    LoadingIndicator.cancel();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_user_info);
        setupView();
        addListener();
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.fragment_mine_personal_data));
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        layout = findViewById(R.id.activity_user_info);
        layoutUserIcon = (RelativeLayout) findViewById(R.id.fragment_userinfo_layout_change_icon);
        civUserIcon = (CircleImageView) findViewById(R.id.fragment_userinfo_civ_usericon);

        layoutNickName = (RelativeLayout) findViewById(R.id.fragment_userinfo_layout_change_name);
        tvNickName = (TextView) findViewById(R.id.fragment_userinfo_tv_show_name);

        layoutGender = (RelativeLayout) findViewById(R.id.fragment_userinfo_layout_change_gender);
        tvGender = (TextView) findViewById(R.id.fragment_userinfo_tv_show_gender);

        layoutMobile = (RelativeLayout) findViewById(R.id.fragment_userinfo_layout_phonenumber);
        tvMobile = (TextView) findViewById(R.id.fragment_userinfo_tv_show_phonenumber);

        tvNotice = (TextView) findViewById(R.id.tv_real_info_notice);

        layoutRealInfo = (LinearLayout) findViewById(R.id.layout_user_real_info);
        tvRealName = (TextView) findViewById(R.id.tv_real_name);
        tvID = (TextView) findViewById(R.id.tv_real_id);

        if (MainActivity.user != null && MainActivity.user.getUserIconPath() != null && !"null".equals(MainActivity.user.getUserIconPath())) {
//            ImageLoaderUtil.getInstance(getApplicationContext()).displayImage(MainActivity.user.getUserIconPath(), civUserIcon);
            ImageLoaderUtil.getInstance(getApplicationContext()).getImage(civUserIcon, MainActivity.user.getUserIconPath());
            ImageLoaderUtil.getInstance(getApplicationContext()).setCallBack(new ImageLoaderUtil.ImageLoaderCallBack() {
                @Override
                public void refreshAdapter(Bitmap loadedImage) {
                }
            });
        } else {
            civUserIcon.setImageResource(R.mipmap.ic_launcher);
        }

        tvNickName.setText(MainActivity.user.getUserNickName());
//        String gender = "0".equals(MainActivity.user.getUserGender()) ? "男" : "女";
        tvGender.setText(MainActivity.user.getUserGender());
        tvMobile.setText(MainActivity.user.getUserPhoneNumber());

//        if (MainActivity.user.getUserTrueName() == null || "null".equals(MainActivity.user.getUserTrueName())){
//        }
        tvRealName.setText(MainActivity.user.getUserTrueName());
        tvID.setText(MainActivity.user.getUserCardId());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (changeIcon){
                Intent data = new Intent();
                data.putExtra("tag", changeIcon);
                setResult(RESULT_OK, data);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        layoutUserIcon.setOnClickListener(this);
        layoutNickName.setOnClickListener(this);
        layoutGender.setOnClickListener(this);
        layoutMobile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                if (changeIcon){
                    Intent data = new Intent();
                    data.putExtra("tag", changeIcon);
                    setResult(RESULT_OK, data);
                }
                finish();
                break;
            case R.id.fragment_userinfo_layout_change_icon:
                picName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                //清空
                Bimp.bmp.clear();
                Bimp.drr.clear();
                Bimp.max = 0;
                new PopupWindowImage(Tab4UserInfoActivity.this, layout, picName);
                break;
            case R.id.fragment_userinfo_layout_change_name:
                Intent intentNick = new Intent(getApplicationContext(), EditUserInfoActivity.class);

                startActivityForResult(intentNick, REQUEST_CHANGE_NICK_NAME);
                break;
            case R.id.fragment_userinfo_layout_change_gender: //更改性别
                modifyGender();
                break;
            case R.id.fragment_userinfo_layout_phonenumber:
                Intent intentTel = new Intent(getApplicationContext(), ModifyTelephoneNumberActivity.class);
                startActivityForResult(intentTel, REQUEST_CHANGE_MOBILE);
                break;
        }
    }

    private void modifyGender() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Tab4UserInfoActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("选择性别");
        // 指定下拉列表的显示数据
        final String[] sexs = {"女", "男"};
        // 设置一个下拉的列表选择项
        builder.setItems(sexs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gender = sexs[which];
                save(gender);
            }
        });
        builder.show();
    }

    private void save(String gender) {
//        {"head":{"code":"User_edit"},"field":{"mid":"1","nickname":"小蜗","sex":"男"}}
        //请求前添加遮罩，在有回调的时候取消遮罩
        LoadingIndicator.show(Tab4UserInfoActivity.this, getString(R.string.http_notice));
        UserInformationBiz biz = new UserInformationBiz(getApplicationContext(), handler);
        biz.modifyUserInfo(MainActivity.user.getUserId(), MainActivity.user.getUserNickName(), gender);
    }

    private int REQUEST_CHANGE_NICK_NAME = 1122;
    private int REQUEST_CHANGE_MOBILE = 1124;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "----- onActivityResult-----， resultCode = " + resultCode + ", requestCode = " + requestCode);
        if (requestCode == REQUEST_CHANGE_NICK_NAME) {
            if (resultCode == RESULT_OK) {
                //TODO 更新用户名
                String nickName = data.getExtras().getString("nickname");
                tvNickName.setText(nickName);
                MainActivity.user.setUserNickName(nickName);
            }
        } else if (requestCode == REQUEST_CHANGE_MOBILE) {
            if (resultCode == RESULT_OK) {
                //TODO 更新手机号
                String mobile = data.getExtras().getString("mobile");
                tvNickName.setText(mobile);
                MainActivity.user.setUserPhoneNumber(mobile);
                SharedPreferencesUtils.getInstance(getApplicationContext()).saveTelephoneNumber(mobile);
            }
        } else if (PopupWindowImage.TAKE_PICTURE == requestCode) { //照相：
            if (resultCode == RESULT_OK) {
                // 设置文件保存路径
                File picture = new File(Consts.IMG_TEMP_PATH + picName);
//                LogUtil.e(TAG, "picPath = " + picture.getPath()); // /storage/emulated/0/cuiweiTemp/1474785184315.jpg
                Bimp.drr.add(picture.getPath());
                startPhotoZoom(Uri.fromFile(picture), Consts.IMG_TEMP_PATH + picName, Utils.getScreenWidth(getApplicationContext()), Utils.getScreenHeight(getApplicationContext()));//没保存么？
            }
        } else if (PopupWindowImage.OTHER_PICTURE == requestCode) { //图库
//                String imagePath = data.getStringExtra("imagePath"); // /storage/emulated/0/MIUI/wallpaper/宝马_&_457f9e19-e66c-4ec7-9c64-a26fc3f03612.jpg
            if (resultCode == RESULT_OK) {
                loading();
            }
//          upLoadBitmap.add(BitmapUtil.loadBitmap(Bimp.drr.get(Bimp.drr.size()-1), Utils.getScreenWidth(getApplicationContext()), Utils.getScreenHeight(getApplicationContext())));
//            if (userIcon != null) {
//                BitmapUtils.photo2Sd(userIcon, mResultPicPath, mPicName);
//            } else {
//                userIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//            }
//            ImageLoaderUtil.getInstance(getApplicationContext()).displayImage(Bimp.drr.get(Bimp.drr.size() - 1), civUserIcon);
        } else if (PHOTO_RESULT == requestCode) { //保存拍照的图片
            if (resultCode == RESULT_OK) {
                try {
//                    upLoadBitmap.add(BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile)));
//                if (userIcon != null) {
//                    userInfo.setUserIconPath(mPicName);
//                    BitmapUtils.photo2Sd(userIcon, mResultPicPath, mPicName);
//                }
                    loading();
                } catch (Exception e) { //FileNotFoundException
                    e.printStackTrace();
                }
            }
        }
    }

    private void modifyUserIcon() {
        LoadingIndicator.show(Tab4UserInfoActivity.this, "正在上传图片，请稍后...");
        UserInformationBiz biz = new UserInformationBiz(getApplicationContext(), handler);
        biz.modifyUserIcon(MainActivity.user.getUserId(), Bimp.bmp.get(0));
    }

    /**
     * 收缩图片
     * @param uri
     * @param path
     * @param width
     * @param height
     */
    public void startPhotoZoom(Uri uri, String path, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); //aspectX aspectY 是宽高的比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", height); //outputX outputY 是裁剪图片宽高
        intent.putExtra("outputY", height);
        uritempFile = Uri.parse("file://" + "/" + path);  //Uri.parse("file://"+path);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_RESULT);
    }

    private String picName = "";
//    private String path = "";
    private Uri uritempFile; //图片临时文件
    private static final String IMAGE_UNSPECIFIED = "image/*"; //保存图片的格式
    private static final int PHOTO_RESULT = 0x000010; //保存图片？

    private final int MESSAGE_REFRESH_IMAGE = 1999; //刷新图片显示

    public void loading() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    LogUtil.e(TAG, "Bimp.max = " + Bimp.max +", Bimp.drr.size() = " + Bimp.drr.size() +"，" + Arrays.toString(Bimp.drr.toArray()));
                    if (Bimp.max == Bimp.drr.size()) {
                        Message message = new Message();
                        message.what = MESSAGE_REFRESH_IMAGE;
                        message.arg1 = 1;
                        handler.sendMessage(message);
                        LogUtil.e(TAG, "-----loading----if----");
                        break;
                    } else {
                        try {
                            String path = Bimp.drr.get(Bimp.max);
                            LogUtil.e(TAG, "loading path = " + path);
                            Bitmap bm = Bimp.revitionImageSize(path);
                            Bimp.bmp.add(bm);
                            String newStr = path.substring(
                                    path.lastIndexOf("/") + 1,
                                    path.lastIndexOf("."));
                            MyFileUtils.saveBitmap(bm, "" + newStr);
                            Bimp.max += 1;
//                            Message message = new Message();
//                            message.arg1 = 1;
//                            message.what = MESSAGE_REFRESH_IMAGE;
//                            handler.sendMessage(message);
                            LogUtil.e(TAG, "-----loading----else----");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
