package com.jhhy.cuiweitourism.picture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ImageGridActivity extends Activity implements OnClickListener {
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	private String TAG = ImageGridActivity.class.getSimpleName();

	// ArrayList<Entity> dataList;//鐢ㄦ潵瑁呰浇鏁版嵁婧愮殑鍒楄〃
	private List<ImageItem> dataList;
	private GridView gridView;
	private ImageGridAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣�
	private AlbumHelper helper;
	private Button bt;

	public static int number = -1; //此处为允许上传图片的个数

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(ImageGridActivity.this, "最多选择"+ Consts.IMAGE_COUNT+"张图片", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(ImageGridActivity.this, "最多选择"+ number+"张图片", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_grid);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(EXTRA_IMAGE_LIST);
		number = getIntent().getIntExtra("number", -1);
		LogUtil.e(TAG, "number = " + number);

		initView();
		bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					ArrayList<String> list = new ArrayList<String>();
					Collection<String> c = adapter.map.values();
					Iterator<String> it = c.iterator();
					for (; it.hasNext(); ) {
						list.add(it.next());
					}
				if (number == -1) {
					for (int i = 0; i < list.size(); i++) {
						if (Bimp.drr.size() < Consts.IMAGE_COUNT) {
							Bimp.drr.add(list.get(i));
						}
					}
					setResult(RESULT_OK);
				}else if (number == 1){
					for (int i = 0; i < list.size(); i++) {
						if (Bimp.drr.size() < number) {
							Bimp.drr.add(list.get(i));
						}
					}
					Intent data = new Intent();
					data.putExtra("imagePath", list.get(0));
					setResult(RESULT_OK, data);
				}
				finish();
			}

		});
	}

	/**
	 * 鍒濆鍖杤iew瑙嗗浘
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList, mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
			public void onListen(int count) {
				bt.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				/**
				 * 鏍规嵁position鍙傛暟锛屽彲浠ヨ幏寰楄窡GridView鐨勫瓙View鐩哥粦瀹氱殑瀹炰綋绫伙紝鐒跺悗鏍规嵁瀹冪殑isSelected鐘舵
				 * �锛� 鏉ュ垽鏂槸鍚︽樉绀洪�涓晥鏋溿� 鑷充簬閫変腑鏁堟灉鐨勮鍒欙紝涓嬮潰閫傞厤鍣ㄧ殑浠ｇ爜涓細鏈夎鏄�
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 閫氱煡閫傞厤鍣紝缁戝畾鐨勬暟鎹彂鐢熶簡鏀瑰彉锛屽簲褰撳埛鏂拌鍥�
				 */
				adapter.notifyDataSetChanged();
			}

		});

		TextView tvCancel = (TextView) findViewById(R.id.tv_image_grid_cancel);
		tvCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.tv_image_grid_cancel:
				setResult(RESULT_CANCELED);
				finish();
				break;
		}
	}
}
