package com.jhhy.cuiweitourism.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.UserInformationBiz;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.picture.Bimp;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowImage;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.BitmapUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.MyFileUtils;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.io.File;
import java.io.IOException;


public class Tab4AccountCertificationActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = Tab4AccountCertificationActivity.class.getSimpleName();

    private TextView tvTitle;
    private ImageView ivTitleLeft;
    private View layout;

    private EditText etRealName;
    private EditText etMobile;
    private TextView tvGender;
    private EditText etIdNumber;

    private ImageView ivAvatar;
    private ImageView ivPositive;
    private ImageView ivOpposite;
    private ImageView ivOther;

    private EditText etRemark;

    private String gender; //真实性别

    private Bitmap bimpAvatar;
    private Bitmap bimpPositive;
    private Bitmap bimpOpposite;
    private Bitmap bimpOther;

    private Button btnCommit;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_MODIFY_USER_AUTHENTICATION:
                    if (msg.arg1 == 1){
                        ToastCommon.toastShortShow(getApplicationContext(), null, "上传成功，请等待审核");
                        setResult(RESULT_OK);
                        finish();
                    }else {
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_account_certification);

        setupView();
        addListener();
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.fragment_mine_account_certification));
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        layout = findViewById(R.id.layout_certification);
        etRealName = (EditText) findViewById(R.id.et_real_name);
        etMobile = (EditText) findViewById(R.id.et_real_mobile);
        tvGender = (TextView) findViewById(R.id.et_real_gender);
        etIdNumber = (EditText) findViewById(R.id.et_real_idinfo);

        ivAvatar = (ImageView) findViewById(R.id.iv_real_icon) ;
        ivPositive = (ImageView) findViewById(R.id.iv_real_idinfo_positive) ;
        ivOpposite = (ImageView) findViewById(R.id.iv_real_idinfo_opposite) ;

        etRemark = (EditText) findViewById(R.id.certivication_remark);
        ivOther = (ImageView) findViewById(R.id.iv_real_other) ;
        btnCommit = (Button) findViewById(R.id.btn_commit);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        tvGender.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        ivPositive.setOnClickListener(this);
        ivOpposite.setOnClickListener(this);
        ivOther.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.et_real_gender:
                modifyGender();
                break;
            case R.id.iv_real_icon: //真实头像
                avatarName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                picName = avatarName;
                avatar = true;
                positive = false;
                opposite = false;
                other = false;
                //清空
                Bimp.bmp.clear();
                Bimp.drr.clear();
                Bimp.max = 0;
                new PopupWindowImage(Tab4AccountCertificationActivity.this, layout, picName);
                break;
            case R.id.iv_real_idinfo_positive: //身份证正面照
                idPositionName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                picName = idPositionName;
                avatar = false;
                positive = true;
                opposite = false;
                other = false;
                //清空
                Bimp.bmp.clear();
                Bimp.drr.clear();
                Bimp.max = 0;
                new PopupWindowImage(Tab4AccountCertificationActivity.this, layout, picName);
                break;
            case R.id.iv_real_idinfo_opposite: //身份证反面照
                idOppositeName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                picName = idOppositeName;
                avatar = false;
                positive = false;
                opposite = true;
                other = false;
                //清空
                Bimp.bmp.clear();
                Bimp.drr.clear();
                Bimp.max = 0;
                new PopupWindowImage(Tab4AccountCertificationActivity.this, layout, picName);
                break;
            case R.id.iv_real_other: //其他照片
                otherName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                picName = otherName;
                avatar = false;
                positive = false;
                opposite = false;
                other = true;
                //清空
                Bimp.bmp.clear();
                Bimp.drr.clear();
                Bimp.max = 0;
                new PopupWindowImage(Tab4AccountCertificationActivity.this, layout, picName);
                break;
            case R.id.btn_commit:
                goCertification();
                break;
        }
    }

    private void modifyGender() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Tab4AccountCertificationActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("选择性别");
        // 指定下拉列表的显示数据
        final String[] sexs = {"女", "男"};
        // 设置一个下拉的列表选择项
        builder.setItems(sexs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gender = sexs[which];
                tvGender.setText(gender);
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PopupWindowImage.TAKE_PICTURE == requestCode) { //照相：
            if (resultCode == RESULT_OK) {
                // 设置文件保存路径
                File picture = new File(Consts.IMG_TEMP_PATH + picName);
                picPath = picture.getPath();
                LogUtil.e(TAG, "picPath = " + picPath); // /storage/emulated/0/cuiweiTemp/1474785184315.jpg
//                Bimp.drr.add(picPath);
//                position = Bimp.drr.indexOf(picPath);
                startPhotoZoom(Uri.fromFile(picture), Consts.IMG_TEMP_PATH + picName,
                        Utils.getScreenWidth(getApplicationContext()), Utils.getScreenHeight(getApplicationContext())); //裁剪
            }
        } else if (PopupWindowImage.OTHER_PICTURE_ONE == requestCode) { //图库
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String imagePath = data.getStringExtra("imagePath"); // /storage/emulated/0/MIUI/wallpaper/宝马_&_457f9e19-e66c-4ec7-9c64-a26fc3f03612.jpg
                    LogUtil.e(TAG, "imagePath = " + imagePath);
                    LogUtil.e(TAG, "Bimp.drr.get(0) = " + Bimp.drr.get(0));
                    if (avatar) {
                        bimpAvatar = BitmapUtil.loadBitmap(imagePath, ivAvatar.getWidth(), ivAvatar.getHeight());
                        ivAvatar.setImageBitmap(bimpAvatar);
                    } else if (positive){
                        bimpPositive = BitmapUtil.loadBitmap(imagePath, ivPositive.getWidth(), ivPositive.getHeight());
                        ivPositive.setImageBitmap(bimpPositive);
                    } else if (opposite){
                        bimpOpposite = BitmapUtil.loadBitmap(imagePath, ivOpposite.getWidth(), ivOpposite.getHeight());
                        ivOpposite.setImageBitmap(bimpOpposite);
                    }else if(other) {
                        bimpOther = BitmapUtil.loadBitmap(Bimp.drr.get(0), ivOther.getWidth(), ivOther.getHeight());
                        ivOther.setImageBitmap(bimpOther);
                    }
                }
//                loading();
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
                    if (avatar) {
                        bimpAvatar = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                        ivAvatar.setImageBitmap(bimpAvatar);
                    } else if (positive) {
                        bimpPositive = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                        ivPositive.setImageBitmap(bimpPositive);
                    } else if (opposite) {
                        bimpOpposite = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                        ivOpposite.setImageBitmap(bimpOpposite);
                    }else if(other){
                        bimpOther = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                        ivOther.setImageBitmap(bimpOther);
                    }
//                    upLoadBitmap.add(BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile)));
//                if (userIcon != null) {
//                    userInfo.setUserIconPath(mPicName);
//                    BitmapUtils.photo2Sd(userIcon, mResultPicPath, mPicName);
//                }
                } catch (Exception e) { //FileNotFoundException
                    e.printStackTrace();
                }
            }
        }
    }

    private String picName;
    private String picPath;

    private String avatarName;
    private String idPositionName;
    private String idOppositeName;
    private String otherName;
    private boolean avatar;
    private boolean positive;
    private boolean opposite;
    private boolean other;

    private static final int PHOTO_RESULT = 0x000010; //裁剪图片返回
    private Uri uritempFile; //图片临时文件
    private static final String IMAGE_UNSPECIFIED = "image/*"; //保存图片的格式

    private final int MESSAGE_REFRESH_IMAGE = 1999; //刷新图片显示

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

    //身份认证
    private void goCertification() {
        String name = etRealName.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入真实姓名");
            return;
        }
        String mobile = etMobile.getText().toString();
        if (TextUtils.isEmpty(mobile)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入手机号码");
            return;
        }else{
            if (mobile.trim().length() != 11){
                ToastCommon.toastShortShow(getApplicationContext(), null, "请核对手机号码");
                return;
            }
        }
        if (TextUtils.isEmpty(gender)) {
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择性别");
            return;
        }
        String idStr = etIdNumber.getText().toString();
        if (TextUtils.isEmpty(idStr)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入身份证号码");
            return;
        }else{
            if (idStr.trim().length() != 18){
                ToastCommon.toastShortShow(getApplicationContext(), null, "请核对身份证号码");
                return;
            }else{
                boolean resul = Utils.is18ByteIdCard(idStr);
                if (!resul){
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请核对身份证号码");
                    return;
                }
            }
        }

        if (bimpAvatar == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请上传真实头像");
            return;
        }
        if (bimpPositive == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请上传身份证正面照");
            return;
        }
        if (bimpOpposite == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请上传身份证反面照");
            return;
        }

        String remark = etRemark.getText().toString();

        UserInformationBiz biz = new UserInformationBiz(getApplicationContext(), handler);
        biz.userAuthentication(MainActivity.user.getUserId(), name, idStr, bimpAvatar, bimpPositive, bimpOpposite, remark, bimpOther);
    }

    public void loading() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
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
                            Message message = new Message();
                            message.arg1 = 1;
                            message.what = MESSAGE_REFRESH_IMAGE;
                            handler.sendMessage(message);
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
