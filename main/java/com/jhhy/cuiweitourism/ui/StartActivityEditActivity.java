package com.jhhy.cuiweitourism.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.StartActivityBiz;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.moudle.CustomActivity;
import com.jhhy.cuiweitourism.picture.Bimp;
import com.jhhy.cuiweitourism.picture.PhotoActivity;
import com.jhhy.cuiweitourism.picture.PublishedActivity;
import com.jhhy.cuiweitourism.picture.TestPicActivity;

import com.jhhy.cuiweitourism.utils.BitmapUtil;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.MyFileUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StartActivityEditActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    private MyGridView  gvImages;
    private GridAdapter adapter;
    private ImageView   ivAddImage;
    private ArrayList<String> listImagePath = new ArrayList<>(); //存放图片列表中 图片的地址

    private EditText etTitle;
    private EditText etPrice;
    private TextView tvSelectFromCity;
    private TextView tvSelectFromTime;
    private EditText etInputDays; //行程天数
    private EditText etInputCount; //随团人数
    private EditText etLineDetails; //线路概况
    private EditText etDetailDescribe; //详情描述
    private EditText etPriceDescribe; //费用描述
    private EditText etTripDescribe; //行程描述
    private EditText etReserveNotice; //预订须知

    private Button btnCommit;

    private Handler handlerResult = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingIndicator.cancel();

            switch (msg.what){
                case Consts.MESSAGE_START_ACTIVITY:
                    if (msg.arg1 == 0){
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                    } else {
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity_edit);

        setupView();
        addListener();
    }

    private void setupView() {
        gvImages    = (MyGridView) findViewById(R.id.gv_start_activity_edit_imgs);
        ivAddImage  = (ImageView) findViewById(R.id.iv_start_activity_edit_add_images);

        etTitle = (EditText) findViewById(R.id.et_start_activity_title);
        etPrice = (EditText) findViewById(R.id.et_start_activity_price);
        tvSelectFromCity = (TextView) findViewById(R.id.tv_start_activity_select_city);
        tvSelectFromTime = (TextView) findViewById(R.id.tv_start_activity_select_date);
        etInputDays = (EditText) findViewById(R.id.tv_start_activity_set_period);
        etInputCount = (EditText) findViewById(R.id.tv_start_activity_set_number);
        etLineDetails = (EditText) findViewById(R.id.et_start_activity_line_details);
        etDetailDescribe = (EditText) findViewById(R.id.et_start_activity_travel_details);
        etPriceDescribe = (EditText) findViewById(R.id.et_start_activity_price_details);
        etTripDescribe = (EditText) findViewById(R.id.et_start_activity_trip_details);
        etReserveNotice = (EditText) findViewById(R.id.et_start_activity_reserve_notice);

        btnCommit = (Button) findViewById(R.id.btn_start_activity_commit);

        gvImages.setSelector(new ColorDrawable(Color.TRANSPARENT));
        Bimp.drr.clear();
        Bimp.bmp.clear();
        Bimp.max = 0;
        adapter = new GridAdapter(getApplicationContext());
//        adapter.update1();
        gvImages.setAdapter(adapter);
    }

    private void addListener() {
        gvImages.setOnItemClickListener(this);
        ivAddImage.setOnClickListener(this);
        tvSelectFromCity.setOnClickListener(this);
        tvSelectFromTime.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 浏览图片，删除图片
        Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
        intent.putExtra("ID", position);
        startActivity(intent);
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

private String TAG = getClass().getSimpleName();
private int SELECT_CITY = 111;
private int SELECT_TIME = 112;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_start_activity_edit_add_images:
                Utils.setKeyboardInvisible(StartActivityEditActivity.this);
                selectPicture();
                break;
            case R.id.tv_start_activity_select_city: //选择出发城市
                //TODO
                Intent intent = new Intent(getApplicationContext(), CitySelectionActivity.class);
                startActivityForResult(intent, SELECT_CITY);
                break;
            case R.id.tv_start_activity_select_date: //选择出发时间
                //TODO
                Intent intentTime = new Intent(getApplicationContext(), DatePickerActivity.class);
                startActivityForResult(intentTime, SELECT_TIME);
                break;
            case R.id.btn_start_activity_commit:
                commitActivity();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i(TAG, "----- onActivityResult-----， resultCode = " + resultCode + ", requestCode = " + requestCode);
        LogUtil.i(TAG, "other picture = " + OTHER_PICTURE);
        if (resultCode == RESULT_OK) {
            if (TAKE_PICTURE == requestCode) { //照相：
                if (resultCode == RESULT_CANCELED) {
                    return;
                }
                if (Bimp.drr.size() < Consts.IMAGE_COUNT) {
                    Bimp.drr.add(path);
                }
                LogUtil.e(TAG, "path = " + path);
                // 设置文件保存路径
                File picture = new File(Consts.IMG_TEMP_PATH + mPicName); LogUtil.e(TAG, picture.getPath());
                startPhotoZoom(Uri.fromFile(picture), Consts.IMG_TEMP_PATH + mPicName, Utils.getScreenWidth(getApplicationContext()), Utils.getScreenHeight(getApplicationContext()));//没保存么？
            }
            else if (OTHER_PICTURE == requestCode) { //图库：上传头像
//                String imagePath = data.getStringExtra("imagePath"); // /storage/emulated/0/MIUI/wallpaper/宝马_&_457f9e19-e66c-4ec7-9c64-a26fc3f03612.jpg
//            if (imagePath == null) {
//               ToastUtil.show(getApplicationContext(), "加载图片失败");
//                return;
//            }
                LogUtil.e(TAG, "bimp.size = " + Bimp.drr.size());
                LogUtil.e(TAG, "imagePath = "+ Bimp.drr.get(Bimp.drr.size()-1) );
                upLoadBitmap.add(BitmapUtil.loadBitmap(Bimp.drr.get(Bimp.drr.size()-1), Utils.getScreenWidth(getApplicationContext()), Utils.getScreenHeight(getApplicationContext())));
//            if (userIcon != null) {
//                BitmapUtils.photo2Sd(userIcon, mResultPicPath, mPicName);
//            } else {
//                userIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//            }
//            if (userIcon != null) {
//                userInfo.setUserIconPath(mPicName);
//            }
//            ivIconRelease.setImageBitmap(userIcon); //把图片显示在ImageView控件上
            } else if (PHOTO_RESOULT == requestCode) { //保存拍照的图片
                try {
                upLoadBitmap.add(BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile)));
//                if (userIcon != null) {
//                    userInfo.setUserIconPath(mPicName);
//                    BitmapUtils.photo2Sd(userIcon, mResultPicPath, mPicName);
//                }
//                ivIconRelease.setImageBitmap(userIcon); //把图片显示在ImageView控件上
                } catch (Exception e) { //FileNotFoundException
                    e.printStackTrace();
                }
            } else if (SELECT_CITY == requestCode) { //选择出发城市
                Bundle bundle = data.getExtras();
                String selectCity = bundle.getString("selectCity");
                tvSelectFromCity.setText(selectCity);
            } else if (SELECT_TIME == requestCode){ //选择出发时间
                Bundle bundle = data.getExtras();
                String selectDate = bundle.getString("selectDate");
                tvSelectFromTime.setText(selectDate);
            }
        }else{

        }
    }

    private String path = "";
    private String mPicName; //拍照照片名字
    private List<Bitmap> upLoadBitmap = new ArrayList<>(); //待上传的图像集合

    private void selectPicture() {
        mPicName = System.currentTimeMillis() + ".jpg";
        new PopupWindows(getApplicationContext(), gvImages);
    }

    private static final String IMAGE_UNSPECIFIED = "image/*"; //保存图片的格式
    private Uri uritempFile; //图片临时文件
    private static final int PHOTO_RESOULT = 0x000010; //保存图片？
    private static final int TAKE_PICTURE = 0x000000; //拍照
    public static final int OTHER_PICTURE = 0x0001020; //相册选择图片

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
        startActivityForResult(intent, PHOTO_RESOULT);
    }

    /**
     * 发布活动
     */
    private void commitActivity() {
        CustomActivity activity = new CustomActivity();
        //图片
        if (Bimp.drr.size() < 0){
            ToastUtil.show(getApplicationContext(), "请至少上传一张活动封面图片");
            return;
        }
        String[] array = new String[upLoadBitmap.size()];
        LogUtil.e(TAG, "iamge size = " + upLoadBitmap.size());
        LogUtil.e(TAG, "Bimp.bim size = " + Bimp.bmp.size());

//        for (int i = 0; i < upLoadBitmap.size(); i++){
//            LogUtil.e(TAG, "upLoadBitmap: " + upLoadBitmap.get(i) + ", ") ;
//            array[i] = BitmapUtil.bitmaptoString(upLoadBitmap.get(i));
//        }
//        for (int i = 0; i < Bimp.drr.size(); i++){
//            LogUtil.e(TAG, "Bimp.drr: " + Bimp.drr.get(i));
//        }
        activity.setLipPicAry(array);

        String title = etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)){
            ToastUtil.show(getApplicationContext(), "标题不能为空");
            return;
        }
        activity.setActivityTitle(title);

        String price = etPrice.getText().toString().trim();
        if (TextUtils.isEmpty(price)){
            ToastUtil.show(getApplicationContext(), "价格不能为空");
            return;
        }
        activity.setActivityPrice(price);

        String fromCity = tvSelectFromCity.getText().toString().trim();
        if (TextUtils.isEmpty(fromCity)){
            ToastUtil.show(getApplicationContext(), "请选择出发城市");
            return;
        }
        activity.setActivityFromCity(fromCity);

        String fromTime = tvSelectFromTime.getText().toString().trim();
        if (TextUtils.isEmpty(fromTime)){
            ToastUtil.show(getApplicationContext(), "请选择出发时间");
            return;
        }
        activity.setActivityFromTime(fromTime);

        String tripDays = etInputDays.getText().toString().trim();
        if (TextUtils.isEmpty(tripDays)){
            ToastUtil.show(getApplicationContext(), "行程天数不能为空");
            return;
        }
        activity.setActivityTripDays(tripDays);

        String groupCount = etInputCount.getText().toString().trim();
        if (TextUtils.isEmpty(groupCount)){
            ToastUtil.show(getApplicationContext(), "活动最多人数不能为空");
            return;
        }
        activity.setActivityGroupAccount(groupCount);

        String lineDetails = etLineDetails.getText().toString().trim();
        if (TextUtils.isEmpty(lineDetails)){
            ToastUtil.show(getApplicationContext(), "线路概况不能为空");
            return;
        }
        activity.setActivityLineDetails(lineDetails);

        String detailDescribe = etDetailDescribe.getText().toString().trim();
        if (TextUtils.isEmpty(detailDescribe)){
            ToastUtil.show(getApplicationContext(), "详情描述不能为空");
            return;
        }
        activity.setActivityDetailDescribe(detailDescribe);

        String priceDescribe = etPriceDescribe.getText().toString().trim();
        if (TextUtils.isEmpty(priceDescribe)){
            ToastUtil.show(getApplicationContext(), "费用描述不能为空");
            return;
        }
        activity.setActivityPriceDescribe(priceDescribe);

        String tripDescribe = etTripDescribe.getText().toString().trim();
        if (TextUtils.isEmpty(tripDescribe)){
            ToastUtil.show(getApplicationContext(), "行程描述不能为空");
            return;
        }
        activity.setActivityTripDescribe(tripDescribe);

        String reserveNotice = etReserveNotice.getText().toString().trim();
        if (TextUtils.isEmpty(reserveNotice)){
            ToastUtil.show(getApplicationContext(), "预订须知不能为空");
            return;
        }
        activity.setActivityReserveNotice(reserveNotice);
        LoadingIndicator.show(StartActivityEditActivity.this, getString(R.string.http_notice));
        StartActivityBiz biz = new StartActivityBiz(getApplicationContext(), handlerResult, upLoadBitmap);
        biz.publishActivity(activity);
    }

    public static void actionStart(Context context, Bundle data) {
        Intent intent = new Intent(context, StartActivityEditActivity.class);
        if(data != null){
            intent.putExtra("data", data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

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
                    Intent intent = new Intent(StartActivityEditActivity.this, TestPicActivity.class);
                    startActivityForResult(intent, OTHER_PICTURE);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    public void photo() {
        String status = Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(Consts.IMG_TEMP_PATH);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(dir, mPicName);
            path = file.getPath();
            Uri imageUri = Uri.fromFile(file);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        } else {
            ToastUtil.show(getApplicationContext(), "没有储存卡");
        }
    }

    private class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
//        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            return (Bimp.bmp.size());
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

//        public void setSelectedPosition(int position) {
//            selectedPosition = position;
//        }

//        public int getSelectedPosition() {
//            return selectedPosition;
//        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

//            if (position == Bimp.bmp.size()) {
//                holder.image.setImageBitmap(BitmapFactory.decodeResource(
//                        getResources(), R.drawable.icon_addpic_unfocused));
//                if (position == 9) {
//                    holder.image.setVisibility(View.GONE);
//                }
//            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
//            }

            return convertView;
        }

        public void update1() {
            loading1();
        }
        public void loading1() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                String path = Bimp.drr.get(Bimp.max);
                                Bitmap bm = Bimp.revisionImageSize(path);
                                Bimp.bmp.add(bm);
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                MyFileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        if (adapter.getCount() == Consts.IMAGE_COUNT){
                            ivAddImage.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                String path = Bimp.drr.get(Bimp.max);
                                System.out.println(path);
                                Bitmap bm = Bimp.revitionImageSize(path);
                                Bimp.bmp.add(bm);
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                MyFileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }
}
