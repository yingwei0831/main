package com.jhhy.cuiweitourism.picture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowImage;

import java.io.Serializable;
import java.util.List;

public class TestPicActivity extends Activity {
	// ArrayList<Entity> dataList;//用来装载数据源的列表
	List<ImageBucket> dataList;
	GridView gridView;
	ImageBucketAdapter adapter;// 自定义的适配器
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;

	private int number = -1; //此处为允许上传图片的个数
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_bucket);
		Intent intent = getIntent();
		if (intent != null){
			Bundle bundle = intent.getExtras();
			if ( bundle != null){
				number = bundle.getInt("number");
			}
		}
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		initData();
		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// /**
		// * 这里，我们假设已经从网络或者本地解析好了数据，所以直接在这里模拟了10个实体类，直接装进列表中
		// */
		// dataList = new ArrayList<Entity>();
		// for(int i=-0;i<10;i++){
		// Entity entity = new Entity(R.drawable.picture, false);
		// dataList.add(entity);
		// }
		dataList = helper.getImagesBucketList(false);	
		bimap = BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
	}

	/**
	 * 初始化view视图
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new ImageBucketAdapter(TestPicActivity.this, dataList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				/**
				 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
				 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 通知适配器，绑定的数据发生了改变，应当刷新视图
				 */
				// adapter.notifyDataSetChanged();
				Intent intent = new Intent(TestPicActivity.this, ImageGridActivity.class);
				intent.putExtra(TestPicActivity.EXTRA_IMAGE_LIST, (Serializable) dataList.get(position).imageList);
				if (number == -1){
					startActivityForResult(intent, PopupWindowImage.OTHER_PICTURE);
				}else{
					intent.putExtra("number", number);
					startActivityForResult(intent, PopupWindowImage.OTHER_PICTURE_ONE);
				}
//				finish();
			}

		});
	}
//	private int SELECT_PICTURE = 101;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			if (requestCode == PopupWindowImage.OTHER_PICTURE){
				setResult(RESULT_OK, data);
				finish();
			}else if (requestCode == PopupWindowImage.OTHER_PICTURE_ONE){
				setResult(RESULT_OK, data);
				finish();
			}
//			if(requestCode == StartActivityEditActivity.OTHER_PICTURE && data != null){ // 上传头像；设置物品照片
//				String imagePath = data.getStringExtra("imagePath");// /storage/emulated/0/UCDownloads/雨.jpg;
//				Intent retIntent = new Intent();
//				retIntent.putExtra("imagePath", imagePath);
//				setResult(1, retIntent);
//				finish();
//			}
		}
	}
}
