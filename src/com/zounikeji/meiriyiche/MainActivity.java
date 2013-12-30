package com.zounikeji.meiriyiche;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONException;
import org.json.JSONObject;



import com.heimi.helper.util.MyAppInfo;
import com.heimi.helper.util.PhoneInfo;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;




import com.zounikeji.MainApplication;
import com.zounikeji.AIDL.forActivity;
import com.zounikeji.AIDL.forService;
import com.zounikeji.adapter.TaskAdapter;
import com.zounikeji.constant.ApiConstant;
import com.zounikeji.constant.HelperConstant;
import com.zounikeji.model.TaskModel;
import com.zounikeji.test.JSONtest;
import com.zounikeji.util.PreferencesUtil;
import com.zounikeji.widget.DropDownListView;
import com.zounikeji.widget.DropDownListView.OnRefreshListener;
import com.zounikeji.util.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener{

	private TextView tv_navigation_name;
	private ImageView iv_task_ad;
	private FinalBitmap fb;
	private PreferencesUtil mPreferencesUtil;
	private List<TaskModel> task_list;
	private TaskAdapter mAdapter;
	private DropDownListView lv_task;
	
	private final int KEY_REFRESH_END = 0;
	
	
	   private forActivity mCallback = new forActivity.Stub() {
			public void performAction() throws RemoteException
			{
				//findViews() ;
				Toast.makeText(MainActivity.this, "this toast is called from service", 1).show();
			}

			




			@Override
			public void UIrefresh(long i) throws RemoteException {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void refreshView() throws RemoteException {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void CustomDialogShow1(int msg) throws RemoteException {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void CustomDialogShow2(int msg) throws RemoteException {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void CustomDialogShow3(int msg) throws RemoteException {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void CustomDialogShow4(int msg) throws RemoteException {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void CustomDialogShow5(int msg) throws RemoteException {
				// TODO Auto-generated method stub
				
			}
			};
			
		forService mService;
		private ServiceConnection mConnection = new ServiceConnection() {
			public void onServiceConnected(ComponentName className,
					IBinder service) {
				MkLog.MKLog("onServiceConnected");
				mService = forService.Stub.asInterface(service);
				try {
					mService.registerTestCall(mCallback);}
				catch (RemoteException e) {
					
				}
				}
			public void onServiceDisconnected(ComponentName className) {
				MkLog.MKLog("disconnect service");
				mService = null;
				}
			};
	
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case KEY_REFRESH_END:

				task_list = TaskModel.parse1(null);
				mAdapter.updateTaskAdapter(task_list);
				break;
			}
			super.handleMessage(msg);
		}
	};
	private String TAG = "ZouniMainActivity";
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainmk);
		
		
		initData();
		initView();
		initserviceconnection();
		inithttp();
		objHandler.postDelayed(mTasks, 1000); 
	}

	 private void inithttp() {
		// TODO Auto-generated method stub
		 RequestParams params = new RequestParams();
			params.put("imei", PhoneInfo.getIMEI(this));
			params.put("mac", PhoneInfo.getMacAddress(this));
			params.put("version", MyAppInfo.getVerName(this));
		 MainApplication.getHttpClient().post(ApiConstant.POST_USER_LOGIN, params, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int arg0, JSONObject arg1) {
					if(arg1.optInt("code") == 200){
						mPreferencesUtil.setUserId(arg1.optJSONObject("data").optString("id"));
						mPreferencesUtil.setUserName(arg1.optJSONObject("data").optString("name"));
						mPreferencesUtil.setUserStatus(arg1.optJSONObject("data").optInt("status"));
						if(arg1.optJSONObject("data").optInt("isnew") == 1){
							mPreferencesUtil.setUserScore(100);
						}
					}
				}
			});
	}

	private void initserviceconnection() {
		// TODO Auto-generated method stub
	        Bundle args = new Bundle();
			Intent intent = new Intent();
			intent.setAction("com.zounikeji.service.HelprService");
			intent.putExtras(args);
			bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
			startService(intent);
	}

	private Handler objHandler = new Handler();
		private Runnable mTasks = new Runnable() {
			public void run() {
			//findViews() ;;
				 //testjson() ;
				findViews();
				 Toast.makeText(MainActivity.this, "��ݸ��³ɹ�", Toast.LENGTH_LONG).show();
			}
		};
		private String Weathermessage;
	
	
		private void findViews() {
			
			//textView = (TextView)findViewById(R.id.WeathertextView1);
//			json数据解析
			JsonUtils jsonUtils = new JsonUtils();
//			北京天气网络数据
			 String path ="http://www.weather.com.cn/data/sk/101010100.html";
			 List list= jsonUtils.getWeather(path);
			 if(!list.isEmpty())
			 {
				 Map map =(Map) list.get(0);
	             if(!map.isEmpty())
	             {
	    			 String time="发布时间：" +(String) map.get("time")+"\n";
	    			 String city="当前城市："+(String) map.get("city")+"\n";
	    			 String temp="实时温度："+(String) map.get("temp")+"℃";
	    			 String message =time+city+temp;
//	    			 将解析数据放置到textView里显示
	    		//	 textView1.setText((CharSequence) message); 
	             }

			 }
			 else
			 {
				 AlertDialog.Builder build=new AlertDialog.Builder(this);
				 build.setTitle("提示")  
	             .setMessage("网络出现故障，请稍后重试！")  
	             .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	                   
	                 @Override  
	                 public void onClick(DialogInterface dialog, int which) {  
	                     // TODO Auto-generated method stub  
	                 }  
	             }).show(); }}
		
		private void findViews1() {
			MkLog.MKLog("findViews1");
				//textView = (TextView)findViewById(R.id.WeathertextView1);
//				json数据解析
				JsonUtils jsonUtils = new JsonUtils();
//				北京天气网络数据
				// String path ="http://www.weather.com.cn/data/sk/10101010.html";
				 String path ="http://www.weather.com.cn/data/sk/101220101.html";
				 List list= jsonUtils.getWeather(path);
				 if(!list.isEmpty())
				 {
					 Map map =(Map) list.get(0);
		             if(!map.isEmpty())
		             {
		    			 String time="发布时间：" +(String) map.get("time")+"\n";
		    			 String city="当前城市："+(String) map.get("city")+"\n";
		    			 String temp="实时温度："+(String) map.get("temp")+"℃";
		    			 String message =time+city+temp;
		    			 Weathermessage = message;
		    			 MkLog.MKLog(message);
		             }

				 }
				 else
				 {
					 AlertDialog.Builder build=new AlertDialog.Builder(this);
					 build.setTitle("提示")  
		             .setMessage("网络出现故障，请稍后重试！")  
		             .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
		                   
		                 @Override  
		                 public void onClick(DialogInterface dialog, int which) {  
		                     // TODO Auto-generated method stub  
		                 }  
		             }).show(); }}
	
	private void testjson() {
		// TODO Auto-generated method stub
		try {

			/*********************************/

			/*********************************/

			String path = "http://www.weather.com.cn/data/sk/101010100.html";
			/*
			 * {"weatherinfo":{"city":"北京","cityid":"101010100","temp":"-10"
			 * ,
			 * "WD":"东北风","WS":"1级","SD":"52%","WSE":"1","time":"22:40",
			 * "isRadar":"1","Radar":"JC_RADAR_AZ9010_JB"}}
			 */
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			list = JSONtest.getJsonpath(path);
			for (Map<String, String> list1 : list) {
				MkLog.MKLog("打印数据...");

				MkLog.MKLog(list1.get("name"));

				MkLog.MKLog(list1.get("id"));

				MkLog.MKLog(list1.get("temp"));

				MkLog.MKLog(list1.get("fengxiang"));

				MkLog.MKLog(list1.get("daxiao"));

				MkLog.MKLog(list1.get("fengxiang"));

//				textView1.setText("城市   :" + list1.get("name"));
//
//				textView2.setText("风向   :" + list1.get("fengxiang"));
//
//				textView3.setText(list1.get("temp"));
//
//				textView4.setText(list1.get("daxiao"));

				// layout.addView(textView1);
				// layout.addView(textView2);
				// layout.addView(textView3);
				// layout.addView(textView4);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		//	System.out.println("获取失败!");
			Log.i("info", "获取失败!");
		}
	}



	private void initData(){
//		String value = MobclickAgent.getConfigParams(this, "task_list_1.3");
//		if(value != null && !value.equals("")){
//			HelperConstant.TASK_LIST = value;
//		}
//		JSONObject jo = null;
//		try {
//			jo = new JSONObject(HelperConstant.TASK_LIST);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		task_list = TaskModel.parse1(null);
		mAdapter = new TaskAdapter(this, task_list);
		
		fb = FinalBitmap.create(this);
//		mPreferencesUtil = new PreferencesUtil(this);
	}
	
	private void initView(){
		lv_task = (DropDownListView) this.findViewById(R.id.lv_mk);
		lv_task.setAdapter(mAdapter);
		lv_task.setOnItemClickListener(this);
		lv_task.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
			//	MobclickAgent.updateOnlineConfig(MainActivity.this);
				mHandler.sendEmptyMessageDelayed(KEY_REFRESH_END, 1500);
			}
		});
//		tv_navigation_name = (TextView) this.findViewById(R.id.tv_navigation_name);
//		tv_navigation_name.setText("活动");
//		iv_task_ad = (ImageView) this.findViewById(R.id.iv_task_ad);
//		iv_task_ad.setOnClickListener(this);
		//fb.display(iv_task_ad, MobclickAgent.getConfigParams(this, "ad_img"));
	//	fb.display(iv_task_ad, "kakaka");
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
