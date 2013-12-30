package com.zounikeji.meiriyiche;



import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.dkf.wifi.ChinaNetWifi;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zounikeji.MainApplication;
import com.zounikeji.constant.ApiConstant;
import com.zounikeji.constant.HelperConstant;
import com.zounikeji.model.AppUpdateInfoModel;
import com.zounikeji.service.AppVersionUpdateService;
import com.zounikeji.util.MyAppInfo;

@SuppressWarnings("deprecation")
public class TabHolderActivity extends TabActivity {
	private TabHost m_tabHost;
	private LayoutInflater mLayoutInflater;
	
	//=============更新============
	private AppUpdateInfoModel mAppUpdateInfoModel;
	private RequestParams params;
	private final int HAS_NEW = 0;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case HAS_NEW:
				doNewVersionUpdateDialog();
				break;
			}
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_tab_holder);
		
		initView();
	//	checkUpdate();
	}

	private void initView() {
		m_tabHost = getTabHost();
		mLayoutInflater = LayoutInflater.from(this);
		
		int count = HelperConstant.mTabClassArray.length;		
		for(int i = 0; i < count; i++) {	
			TabSpec tabSpec = m_tabHost.newTabSpec(HelperConstant.mTextviewArray[i]).
													setIndicator(getTabItemView(i)).
													setContent(getTabItemIntent(i));
			m_tabHost.addTab(tabSpec);
			m_tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.btn_tab_item_selector);
		}
	}

	private View getTabItemView(int index) {
		View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
	
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

		if (imageView != null) {
			imageView.setImageResource(HelperConstant.mImageViewArray[index]);
		}
		
		TextView textView = (TextView) view.findViewById(R.id.textview);
		
		textView.setText(HelperConstant.mTextviewArray[index]);
	
		return view;
	}
	
	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent(this, HelperConstant.mTabClassArray[index]);
		return intent;
	}
	
	private void checkUpdate(){
		params = new RequestParams();
		params.put("os", "android");
		params.put("app_name", "helper");
		MainApplication.getHttpClient().get(TabHolderActivity.this, ApiConstant.GET_APP_UPDATE_URL, params, new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				
			}
			
			@Override
			public void onSuccess(JSONObject arg0) {
				if(arg0 != null){
					mAppUpdateInfoModel = AppUpdateInfoModel.parse(arg0);
					if(mAppUpdateInfoModel.getUrl() != null && !mAppUpdateInfoModel.getUrl().equals("")){
						if(MyAppInfo.isNew(mAppUpdateInfoModel.getVersionCode(), MyAppInfo.getVerCode(TabHolderActivity.this))){
							mHandler.sendEmptyMessage(HAS_NEW);
						}
					} 
				}
			}
		});
	}
	
	private void doNewVersionUpdateDialog() {
		StringBuffer sb = new StringBuffer();
		sb.append("发现新版本");
		sb.append(mAppUpdateInfoModel.getVersion());		// 新版本版号
		sb.append(", 是否更新?\n");
		sb.append("更新内容:\n");
		sb.append(mAppUpdateInfoModel.getDescription());	// 新版本特征描述
		Dialog dialog = new AlertDialog.Builder(TabHolderActivity.this).setTitle("升级提示").setMessage(sb.toString())
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
							startDownloadService();
						} else {
							Toast.makeText(TabHolderActivity.this, "没有内存卡,无法完成下载", Toast.LENGTH_SHORT).show();
						}
						if(mAppUpdateInfoModel.isForce_update()){
							finish();
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
						if(mAppUpdateInfoModel.isForce_update()){
							finish();
						}
					}
				}).create();// 创建
		// 显示对话框
		dialog.show();
	}
	
	private void startDownloadService(){
		Intent updateServiceIntent = new Intent(TabHolderActivity.this, AppVersionUpdateService.class);
		this.startService(updateServiceIntent);
	}	

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
//			AlertDialog.Builder builder = new Builder(this);
//			builder.setMessage("确定要退出吗?");
//			builder.setTitle("提示");
//			builder.setPositiveButton("退出",
//					new android.content.DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							OffersManager.getInstance(TabHolderActivity.this).onAppExit(); 
//							dialog.dismiss();
//							finish();
//						}
//					});
//			builder.setNegativeButton("取消",
//					new android.content.DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							dialog.dismiss();
//						}
//					});
//			builder.create().show();
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		//=====销毁chinanet=====
	//	ChinaNetWifi.getInstance().destroy();
		//=====销毁sharesdk=====
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}
}

