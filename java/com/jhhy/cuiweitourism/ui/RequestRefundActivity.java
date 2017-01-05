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
import android.support.annotation.Nullable;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.OrderActionBiz;
import com.jhhy.cuiweitourism.model.Order;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderCancelRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderCancelResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderDetailResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.picture.Bimp;
import com.jhhy.cuiweitourism.picture.PhotoActivity;
import com.jhhy.cuiweitourism.picture.TestPicActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.MyFileUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.io.File;
import java.io.IOException;

public class RequestRefundActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String TAG = "RequestRefundActivity";
    private int type; //-1:申请退款 1：评论 2：酒店取消订单
    private Order order;
    private HotelOrderDetailResponse hotelOrderDetail; //酒店订单详情

    private EditText etReason;
    private TextView tvRefundNotice; //商家退款说明
    private Button btnCommit;

    private GridView gvImages;
    private GridAdapter adapter;

    private Handler handlerMain = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e(TAG, "----------------------handleMessage-----------------");
            if (msg.arg1 == 0){
                ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
            }else {
                switch (msg.what) {
                    case Consts.MESSAGE_ORDER_REFUND: //取消订单
                        ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case Consts.MESSAGE_ORDER_COMMENT: //评论订单
                        ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case MESSAGE_REFRESH_IMAGE:
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_refund);
        getData();
        setupView();
        addListener();
    }

    protected void onRestart() {
        LogUtil.e(TAG, "========== onRestart =========");
//        adapter.update();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e(TAG, "========== onResume =========");
//        adapter.update();
    }


    private void setupView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        if (type == 2){
            tvTitle.setText("取消订单");
        }
        btnCommit = (Button) findViewById(R.id.btn_request_refund_commit);
        tvRefundNotice = (TextView) findViewById(R.id.tv_refund_notice);
        etReason = (EditText) findViewById(R.id.et_reason);
        gvImages = (GridView) findViewById(R.id.gridview_comment_imgs);
        if (type == -1){ //申请退款
            gvImages.setVisibility(View.GONE);
        }else if (type == 2){ //酒店取消订单
            gvImages.setVisibility(View.GONE);
            tvRefundNotice.setVisibility(View.GONE);
            etReason.setHint("输入取消订单原因");
            btnCommit.setText("提交");
        }
        else{ //评论
            etReason.setHint("分享你的购买心得");
            tvRefundNotice.setVisibility(View.GONE);
            btnCommit.setText("提交评论");
            gvImages.setSelector(new ColorDrawable(Color.TRANSPARENT));
            Bimp.drr.clear();
            Bimp.bmp.clear();
            Bimp.max = 0;
            adapter = new GridAdapter(getApplicationContext());
            gvImages.setAdapter(adapter);
        }
    }

    private void addListener() {
        btnCommit.setOnClickListener(this);
        gvImages.setOnItemClickListener(this);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                type = bundle.getInt("type");
                order = (Order) bundle.getSerializable("order");
                if (2 == type){
                    hotelOrderDetail = Tab4OrderDetailsActivity.hotelOrderDetail;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        String remark = etReason.getText().toString();
        if (TextUtils.isEmpty(remark)){
            ToastCommon.toastShortShow(getApplicationContext(), null ,getString(R.string.empty_input));
            return;
        }
        if (type == -1){ //申请退款
            OrderActionBiz biz = new OrderActionBiz(getApplicationContext(), handlerMain);
            biz.requestRefund(order.getOrderSN(), remark);
        } else if (2 == type){ //酒店取消订单
           cancelHotelOrder(remark);
        }
        else{ //提交评论
            commitCommont(remark);
        }
    }

    /**
     * 酒店取消（什么标识的订单才能取消）
     */
    private void cancelHotelOrder(String remark) {
        HotelActionBiz hotelActionBiz = new HotelActionBiz();
        HotelOrderCancelRequest request = new HotelOrderCancelRequest(hotelOrderDetail.getOrderSN(), hotelOrderDetail.getPlatOrderNo(), remark, remark);
        hotelActionBiz.setHotelOrderCancel(request, new BizGenericCallback<HotelOrderCancelResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelOrderCancelResponse> model) {
                LogUtil.e(TAG, "setHotelOrderCancel: " + model);
                finish();
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "setHotelOrderCancel: " + error);
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "取消订单失败，请重试");
                }
            }
        });
    }

    //提交评论
    private void commitCommont(String remark) {
        LogUtil.e(TAG, "Bimp.bmp.size = " + Bimp.bmp.size());
        OrderActionBiz biz = new OrderActionBiz(getApplicationContext(), handlerMain);
        biz.requestComment(MainActivity.user.getUserId(), order, remark, Bimp.bmp);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == Bimp.drr.size()){ //添加图片
            Utils.setKeyboardInvisible(RequestRefundActivity.this);
            selectPicture();
        } else {
            // 浏览图片，删除图片
            Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
            intent.putExtra("ID", position);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "----- onActivityResult-----， resultCode = " + resultCode + ", requestCode = " + requestCode);
        LogUtil.e(TAG, "other picture = " + OTHER_PICTURE);
        if (resultCode == RESULT_OK) {
            if (TAKE_PICTURE == requestCode) { //照相：

                if (Bimp.drr.size() < Consts.IMAGE_COUNT) {
                    Bimp.drr.add(path);
                }
                LogUtil.e(TAG, "path = " + path);
                // 设置文件保存路径
                File picture = new File(Consts.IMG_TEMP_PATH + mPicName); LogUtil.e(TAG, picture.getPath());
                startPhotoZoom(Uri.fromFile(picture), Consts.IMG_TEMP_PATH + mPicName, Utils.getScreenWidth(getApplicationContext()), Utils.getScreenHeight(getApplicationContext()));//没保存么？
            }
            else if (OTHER_PICTURE == requestCode) { //图库
//                String imagePath = data.getStringExtra("imagePath"); // /storage/emulated/0/MIUI/wallpaper/宝马_&_457f9e19-e66c-4ec7-9c64-a26fc3f03612.jpg
//            if (imagePath == null) {
//               ToastUtil.show(getApplicationContext(), "加载图片失败");
//                return;
//            }
                LogUtil.e(TAG, "bimp.size = " + Bimp.drr.size());
                LogUtil.e(TAG, "imagePath = "+ Bimp.drr.get(Bimp.drr.size()-1) );
                adapter.update();
//                upLoadBitmap.add(BitmapUtil.loadBitmap(Bimp.drr.get(Bimp.drr.size()-1), Utils.getScreenWidth(getApplicationContext()), Utils.getScreenHeight(getApplicationContext())));
//            if (userIcon != null) {
//                BitmapUtils.photo2Sd(userIcon, mResultPicPath, mPicName);
//            } else {
//                userIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//            }
//            if (userIcon != null) {
//                userInfo.setUserIconPath(mPicName);
//            }
//            ivIconRelease.setImageBitmap(userIcon); //把图片显示在ImageView控件上
            } else if (PHOTO_RESULT == requestCode) { //保存拍照的图片
                try {
                    LogUtil.e(TAG, "bimp.size = " + Bimp.drr.size());
                    LogUtil.e(TAG, "imagePath = "+ Bimp.drr.get(Bimp.drr.size()-1) );
                    adapter.update();
//                    upLoadBitmap.add(BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile)));
//                if (userIcon != null) {
//                    userInfo.setUserIconPath(mPicName);
//                    BitmapUtils.photo2Sd(userIcon, mResultPicPath, mPicName);
//                }
//                ivIconRelease.setImageBitmap(userIcon); //把图片显示在ImageView控件上
                } catch (Exception e) { //FileNotFoundException
                    e.printStackTrace();
                }
            }
        }else {
            LogUtil.e(TAG, "other picture = " + OTHER_PICTURE);
        }
    }

    private String mPicName = "";
    private String path = "";
//    private List<Bitmap> upLoadBitmap = new ArrayList<>(); //待上传的图像集合

    private Uri uritempFile; //图片临时文件

    private static final String IMAGE_UNSPECIFIED = "image/*"; //保存图片的格式
    private static final int PHOTO_RESULT = 0x000010; //保存图片？
    private static final int TAKE_PICTURE = 0x000000; //拍照
    private static final int OTHER_PICTURE = 0x0001020; //相册选择图片

    private final int MESSAGE_REFRESH_IMAGE = 1099; //刷新图片

    private void selectPicture() {
        mPicName = System.currentTimeMillis() + ".jpg";
        new PopupWindows(getApplicationContext(), gvImages);
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
                    Intent intent = new Intent(RequestRefundActivity.this, TestPicActivity.class);
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
            return (Bimp.bmp.size() + 1);
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
            LogUtil.e(TAG, "bmp.size = " + Bimp.bmp.size() +", position = " + position);
            if (position == Bimp.bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
                if (position == Consts.IMAGE_COUNT) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            }

            return convertView;
        }

//        public void update1() {
//            loading1();
//        }


        public class ViewHolder {
            public ImageView image;
        }

//        Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                LogUtil.e(TAG, "----------------------handleMessage----------inner-------");
//                switch (msg.what) {
//                    case 1:
//                        adapter.notifyDataSetChanged();
////                        if (adapter.getCount() == Consts.IMAGE_COUNT){
////                            ivAddImage.setVisibility(View.INVISIBLE);
////                        }
//                        break;
//                }
//                super.handleMessage(msg);
//            }
//        };


    }
//    public void loading1() {
//        new Thread(new Runnable() {
//            public void run() {
//                while (true) {
//                    if (Bimp.max == Bimp.drr.size()) {
//                        Message message = new Message();
//                        message.arg1 = 1;
//                        message.what = MESSAGE_REFRESH_IMAGE;
//                        handlerMain.sendMessage(message);
//                        LogUtil.e(TAG, "-----loading1----if----");
//                        break;
//                    } else {
//                        try {
//                            String path = Bimp.drr.get(Bimp.max);
//                            Bitmap bm = Bimp.revisionImageSize(path);
//                            Bimp.bmp.add(bm);
//                            String newStr = path.substring(
//                                    path.lastIndexOf("/") + 1,
//                                    path.lastIndexOf("."));
//                            MyFileUtils.saveBitmap(bm, "" + newStr);
//                            Bimp.max += 1;
//                            Message message = new Message();
//                            message.arg1 = 1;
//                            message.what = MESSAGE_REFRESH_IMAGE;
//                            handlerMain.sendMessage(message);
//                            LogUtil.e(TAG, "-----loading1----else----");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }).start();
//    }

    public void loading() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (Bimp.max == Bimp.drr.size()) {
//                        Message message = new Message();
//                        message.what = MESSAGE_REFRESH_IMAGE;
//                        message.arg1 = 1;
//                        handlerMain.sendMessage(message);
//                        LogUtil.e(TAG, "-----loading----if----");
                        break;
                    } else {
                        try {
                            String path = Bimp.drr.get(Bimp.max);
                            LogUtil.e(TAG, "path = " + path);
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
                            handlerMain.sendMessage(message);
                            LogUtil.e(TAG, "-----loading----else----");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "========== onDestroy =========");
        hotelOrderDetail = null;
        etReason= null;
        tvRefundNotice = null;
        btnCommit = null;
        gvImages = null;
        adapter = null;
        handlerMain = null;
        Bimp.drr.clear();
        Bimp.bmp.clear();
    }
}
