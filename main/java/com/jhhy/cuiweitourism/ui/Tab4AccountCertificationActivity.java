package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.UserInformationBiz;
import com.jhhy.cuiweitourism.picture.Bimp;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowImage;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.LogUtil;
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

    private EditText etRemark;

    private ImageView ivOther;

    private Button btnCommit;

    private Bitmap bimpAvatar;
    private Bitmap bimpPositive;
    private Bitmap bimpOpposite;
    private Bitmap bimpOther;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_MODIFY_USER_AUTHENTICATION:
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        setResult(RESULT_OK);
                        finish();
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
        tvTitle.setText("账户安全");
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
                goCertivication();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PopupWindowImage.TAKE_PICTURE == requestCode) { //照相：
            if (resultCode == RESULT_OK) {
                // 设置文件保存路径
                File picture = new File(Consts.IMG_TEMP_PATH + picName);
//                LogUtil.e(TAG, "picPath = " + picture.getPath()); // /storage/emulated/0/cuiweiTemp/1474785184315.jpg
                Bimp.drr.add(picture.getPath());
                startPhotoZoom(Uri.fromFile(picture), Consts.IMG_TEMP_PATH + picName,
                        Utils.getScreenWidth(getApplicationContext()), Utils.getScreenHeight(getApplicationContext())); //裁剪
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
                } catch (Exception e) { //FileNotFoundException
                    e.printStackTrace();
                }
            }
        }
    }

    private String picName;
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

    private void goCertivication() {



        UserInformationBiz biz = new UserInformationBiz(getApplicationContext(), handler);
//        biz.userAuthentication();
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
