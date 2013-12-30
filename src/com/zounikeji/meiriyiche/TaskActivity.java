package com.zounikeji.meiriyiche;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ruiduad.offers.OffersManager;
import com.umeng.analytics.MobclickAgent;
import com.zounikeji.adapter.TaskAdapter;
import com.zounikeji.constant.HelperConstant;
import com.zounikeji.model.TaskModel;
import com.zounikeji.util.PreferencesUtil;
import com.zounikeji.widget.DropDownListView;
import com.zounikeji.widget.DropDownListView.OnRefreshListener;

public class TaskActivity extends Activity implements OnItemClickListener, OnClickListener{

	private DropDownListView lv_task;
	private TaskAdapter mAdapter;
	private List<TaskModel> task_list;
	private TaskModel task;
	private Intent intent;
	private TextView tv_navigation_name;
	private ImageView iv_task_ad;
	
	private FinalBitmap fb;
	private PreferencesUtil mPreferencesUtil;
	
	private final int KEY_REFRESH_END = 0;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case KEY_REFRESH_END:
				lv_task.onRefreshComplete();
				String value = MobclickAgent.getConfigParams(TaskActivity.this, "task_list_1.3");
				if(value != null && !value.equals("")){
					HelperConstant.TASK_LIST = value;
				}
				JSONObject jo = null;
				try {
					jo = new JSONObject(HelperConstant.TASK_LIST);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				task_list = TaskModel.parse(jo);
				mAdapter.updateTaskAdapter(task_list);
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		initData();
		initView();
	}
	
	private void initData(){
		String value = MobclickAgent.getConfigParams(this, "task_list_1.3");
		if(value != null && !value.equals("")){
			HelperConstant.TASK_LIST = value;
		}
		JSONObject jo = null;
		try {
			jo = new JSONObject(HelperConstant.TASK_LIST);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		task_list = TaskModel.parse(jo);
		mAdapter = new TaskAdapter(this, task_list);
		
		fb = FinalBitmap.create(this);
		mPreferencesUtil = new PreferencesUtil(this);
	}
	
	private void initView(){
		lv_task = (DropDownListView) this.findViewById(R.id.lv_task);
		lv_task.setAdapter(mAdapter);
		lv_task.setOnItemClickListener(this);
		lv_task.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				MobclickAgent.updateOnlineConfig(TaskActivity.this);
				mHandler.sendEmptyMessageDelayed(KEY_REFRESH_END, 1500);
			}
		});
		tv_navigation_name = (TextView) this.findViewById(R.id.tv_navigation_name);
		tv_navigation_name.setText("娲诲姩");
		iv_task_ad = (ImageView) this.findViewById(R.id.iv_task_ad);
		iv_task_ad.setOnClickListener(this);
		fb.display(iv_task_ad, MobclickAgent.getConfigParams(this, "ad_img"));
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		task = task_list.get(arg2 - 1);
		switch(task.getType()){
		case 0:
			MobclickAgent.onEvent(TaskActivity.this, "task_score");
			OffersManager.getInstance(this).showOffersWall();
			break;
		case 1:
			MobclickAgent.onEvent(TaskActivity.this, "task_signin");
	//		intent = new Intent(TaskActivity.this, SignInActivity.class);
			startActivity(intent);
			break;
		case 2:
			MobclickAgent.onEvent(TaskActivity.this, "task_share");
		//	intent = new Intent(TaskActivity.this, ShareActivity.class);
			startActivity(intent);
			break;
		case 3:
			MobclickAgent.onEvent(TaskActivity.this, "task_active");
			String url = MobclickAgent.getConfigParams(TaskActivity.this, "ad_task_url");
			if(url.equals("") || url.equals("no")){
				Toast.makeText(TaskActivity.this, "娲诲姩鍗冲皢寮�惎锛屾暚璇锋湡寰厏", Toast.LENGTH_LONG).show();
			} else {
				openWeb(url);
			}
			break;
		case 4:
			//intent = new Intent(TaskActivity.this, InviteActivity.class);
			startActivity(intent);
			break;
		case 5:
		//	intent = new Intent(TaskActivity.this, SuperGiftActivity.class);
			startActivity(intent);
			break;
		case 6:
		//	intent = new Intent(TaskActivity.this, CDKeyActivity.class);
			startActivity(intent);
			break;
		case 7:
		//	intent = new Intent(TaskActivity.this, WifiListActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.iv_task_ad:
			String url = MobclickAgent.getConfigParams(TaskActivity.this, "ad_banner_url");
			if(url.equals("") || url.equals("no")){
				
			} else {
				openWeb(url);
			}
			break;
		}
	}
	
	private void openWeb(String url){
		Uri uri = Uri.parse(url);  
		Intent it = new Intent(Intent.ACTION_VIEW, uri);  
		startActivity(it);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
	//	ScoreManagerUtil.addWallScore(TaskActivity.this, mPreferencesUtil);
		super.onResume();
		MobclickAgent.onResume(this);
	}
}
