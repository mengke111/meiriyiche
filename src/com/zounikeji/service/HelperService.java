package com.zounikeji.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import com.zounikeji.AIDL.forActivity;
import com.zounikeji.AIDL.forService;
import com.zounikeji.constant.HelperConstant;
import com.zounikeji.meiriyiche.R;
import com.zounikeji.meiriyiche.SplashActivity;
import com.zounikeji.util.PreferencesUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.format.Time;
import android.util.Log;




public class HelperService extends Service{

	private PreferencesUtil mPreferencesUtil;
	private IntentFilter filter;
	private NotificationManager mNM;
	private WifiManager mWifiManager;
	private ConnectivityManager mConnectivityManage;
	private List<ScanResult> result;
    private static final String TAG = "747HelperService";  
    private forActivity callback;
    private static Time start_time=new Time();
    private static Time now_time=new Time();
    private static boolean b_timer = false;
    private static long TIME_NOT_START = -1;
    private static long start_time_millis = TIME_NOT_START;
    private void Log(String str) {
        Log.d(TAG, "------ " + str + "------");
    }
	

    @Override
    public void onStart(Intent intent, int startId) {
        Log("service start id=" + startId);//.setClass(MainActivity.this,.setClass(MainActivity.this,
     
    }
    
    @Override
    public IBinder onBind(Intent t) {
    	Log("service on bind");
//		 IBinder result = null;  
//		    if ( null == result ) result = new MyBinder() ;
//		Toast.makeText(this, "onBind",Toast.LENGTH_LONG);
//		return result;  	
    	
        
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log("Helperservice on unbind");
        return super.onUnbind(intent);
    }
    public void onRebind(Intent intent) {
        Log("Helperservice on rebind");
        super.onRebind(intent);
    }

	@Override
	public void onCreate() {
		Log("Helperservice create");
		super.onCreate();
		initData();
		registBroadcastReceiver();
	}
	
	private void initData(){
		mPreferencesUtil = new PreferencesUtil(this);
		mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		mConnectivityManage = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	private void registBroadcastReceiver(){
		filter = new IntentFilter();
		filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction(HelperConstant.BROADCAST_NOTIFICATION_SWITCH);
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		filter.addAction(HelperConstant.BROADCAST_NOTIFICATION_START_TIME);
		filter.addAction(HelperConstant.BROADCAST_NOTIFICATION_END_TIME);
		this.registerReceiver(mReceiver, filter);
	}

	private void unregistBroadcastReceiver() {
		this.unregisterReceiver(mReceiver);
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String action = arg1.getAction();
			if (HelperConstant.BROADCAST_NOTIFICATION_START_TIME.equals(action)){
				Log("BROADCAST_NOTIFICATION_START_TIME");
				startTimer_mengke();
			//	startTimer();
			} else if (HelperConstant.BROADCAST_NOTIFICATION_END_TIME.equals(action)){
				Log("BROADCAST_NOTIFICATION_END_TIME");
				endTimer1();
			} else if (HelperConstant.BROADCAST_NOTIFICATION_SWITCH.equals(action)) {
				Log("BROADCAST_NOTIFICATION_SWITCH");
				if (arg1.getBooleanExtra(HelperConstant.EXTRA_NOTIFICATION_SWITCH, true)) {
					setNotifiSwitch(true);
				} else {
					setNotifiSwitch(false);
				}
			} else if (mPreferencesUtil.getNotificationFindSwitch() || mPreferencesUtil.getNotificationConnectSwitch()) {
				setNotifiState();
				if(WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action) && !mWifiManager.isWifiEnabled()){
					endTimer();
				}
			}
		}


	};
	
	
	private void setNotifiSwitch(boolean flag) {
		mPreferencesUtil.setNotificationFindSwitch(flag);
		if (flag) {
			setNotifiState();
		} else {
			deteleNotifi();
		}
	}
	
	private void setNotifiState() {
//		result = mWifiManager.getScanResults();
//		if (mWifiManager.isWifiEnabled()) {
//			NetworkInfo wifiNetInfo = mConnectivityManage.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//			NetworkInfo.DetailedState connectionState = wifiNetInfo.getDetailedState();
//			if (connectionState == NetworkInfo.DetailedState.CONNECTED) {
//				if(mWifiManager.getConnectionInfo() != null && 
//						(mWifiManager.getConnectionInfo().getSSID().equals("ChinaNet") || mWifiManager.getConnectionInfo().getSSID().equals("\"ChinaNet\"")) 
//					&& mPreferencesUtil.getNotificationConnectSwitch()){
//					addNotifi("WiFi宸茶繛鎺�, ","WiFi宸茶繛鎺ワ細ChinaNet", "鐐瑰嚮璧氱Н鍒嗭紝姣忓ぉ閮芥潵鍏嶈垂涓妦");
//				}
//			} else if (result != null && mPreferencesUtil.getNotificationFindSwitch()) {
//				for(ScanResult sr:result){
//					if(sr.SSID.equals("ChinaNet")){
//						addNotifi("鎵弿鍒扮數淇″厤璐筗iFi", "鍙戠幇ChinaNet淇″彿", "鐐瑰嚮鍙韩鍏嶈垂涓婄綉~");
//						break;
//					}
//				}
//			} else if(connectionState == NetworkInfo.DetailedState.DISCONNECTED){
//				deteleNotifi();
//				endTimer();
//				this.sendBroadcast(new Intent(HeartReceiver.BROADCAST_HEART_END));
//			}
//		} else {
//			mPreferencesUtil.setHeimiConnectFlag(false);
//			this.sendBroadcast(new Intent(HeartReceiver.BROADCAST_HEART_END));
//			deteleNotifi();
//		}
	}

	@SuppressWarnings("deprecation")
	public void addNotifi(String tickTitle, String title, String content) {
		Notification notification = new Notification();
		notification.when = System.currentTimeMillis() + 300;
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = tickTitle;
		Intent intent = new Intent(HelperService.this, SplashActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(HelperService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.contentIntent = contentIntent;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
//		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		notification.setLatestEventInfo(HelperService.this, title, content, contentIntent);
		mNM.notify(R.string.app_name, notification);
	}

	public void deteleNotifi() {
		if (mNM != null) {
			mNM.cancel(R.string.app_name);
		}
	}
	
	private void startTimer_mengke() {
		// TODO Auto-generated method stub
		Log("startTimer_mengke");
		if(b_timer  == false ||TIME_NOT_START ==  start_time_millis){
		start_time.setToNow();
		start_time_millis = start_time.toMillis(false);
		b_timer = true;
		}
		startTimer1();
	}
	//public static long time1 = 0;
	Handler handler=new Handler();
	Runnable runnable=new Runnable(){
		private long time2;

		@Override
		public void run() {
		// TODO Auto-generated method stub
		//瑕佸仛鐨勪簨鎯�
			//time1 ++;
			
			try {
				now_time.setToNow();
				
				time2 = (now_time.toMillis(false)- start_time_millis)/1000;
				//Log("time : " + time2);
				if(callback != null)
				callback.UIrefresh(time2);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		handler.postDelayed(this, 1000);
		}
		};
	private void startTimer1(){
		
		handler.postDelayed(runnable, 1000);
	}
	
	private void endTimer1(){
		handler.removeCallbacks(runnable);
		//time1 = -1;
		b_timer = false;
		mPreferencesUtil.setHeimiConnectFlag(false);
	}
	
	private void endTimer(){
	//	handler.removeCallbacks(runnable);
	//	time1 = -1;
	//	mPreferencesUtil.setHeimiConnectFlag(false);
	}
	
	@Override
	public void onDestroy() {
		Log("onDestroy");
		super.onDestroy();
		unregistBroadcastReceiver();
		deteleNotifi();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log("onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	 private final forService.Stub mBinder = new forService.Stub() {
			@Override
			public void invokCallBack() throws RemoteException
			{
				Log("invokCallBack");
			//	callback.performAction();
				
			//	callback.UIrefresh(Weathermessage) ;
				
			}
			@Override
			public void registerTestCall(forActivity cb) throws RemoteException
			{
				Log("registerTestCall");
				callback = cb;
				
			}
			@Override
			public void sendEmptyMessage() throws RemoteException {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void unregisterTestCall(forActivity cdb)
					throws RemoteException {
				// TODO Auto-generated method stub
				
			}
	        
	    };
	    

}
