package com.zounikeji.meiriyiche;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.json.JSONObject;


import com.loopj.android.http.RequestParams;
import com.zounikeji.service.HelperService;
import com.zounikeji.util.PhoneInfo;
import com.zounikeji.util.PreferencesUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
//import cn.sharesdk.framework.ShareSDK;
//
//import com.dkf.wifi.ChinaNetWifi;
//import com.heimi.helper.MainApplication;
//import com.heimi.helper.R;
//import com.heimi.helper.constant.ApiConstant;
//import com.heimi.helper.service.HelperService;
//import com.heimi.helper.util.MyAppInfo;
//import com.heimi.helper.util.PhoneInfo;
//import com.heimi.helper.util.PreferencesUtil;
//import com.heimi.helper.util.ScoreManagerUtil;
//import com.loopj.android.http.JsonHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//import com.ruiduad.AdManager;
//import com.ruiduad.offers.OffersManager;
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.fb.FeedbackAgent;

public class SplashActivity extends Activity implements TagAliasCallback{
	
	private ImageView iv_splash;
	private AlphaAnimation aa;
	private Intent intent;
	private PreferencesUtil mPreferencesUtil;
	private WifiManager mWifiManager;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		initData();
		initDate();
		initAnimation();
		initView();
	}
	
	private void initAnimation() {
		aa = new AlphaAnimation(0.5f, 1.0f);
		aa.setDuration(3000);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				gotoMainActivity();
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				startService();
			}
		});
	}
	
	private void initView(){
		iv_splash = (ImageView) findViewById(R.id.iv_splash);
		iv_splash.startAnimation(aa);
	}
	
	private void initData(){
	//	MobclickAgent.updateOnlineConfig(this);
		
	//	AdManager.getInstance(this).init("d8adf87dde928332","b68cbe90153219bf", false);
	//	OffersManager.getInstance(this).onAppLaunch();
		
	//	ShareSDK.initSDK(this);
		
		mPreferencesUtil = new PreferencesUtil(this);
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if(!mWifiManager.isWifiEnabled()){
			mWifiManager.setWifiEnabled(true);
		}
		
		//FeedbackAgent agent = new FeedbackAgent(SplashActivity.this);
		//agent.sync();
		//ChinaNetWifi.isSmsValidationNeeded = false;
		//ScoreManagerUtil.addWallScore(this, mPreferencesUtil);
	}
	
	private void initDate(){
		Date date=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String s = formatter.format(date);
		
		int month = Integer.valueOf(s.substring(5, 7));
		int day = Integer.valueOf(s.substring(8, 10));
		
		final String old_s_sign = mPreferencesUtil.getSignInDate();
		if(!old_s_sign.equals("")){
			int old_month_sign = Integer.valueOf(old_s_sign.substring(5, 7));
			int old_day_sign = Integer.valueOf(old_s_sign.substring(8, 10));
			if(old_month_sign != month){
				mPreferencesUtil.setSignInFlag(false);
				mPreferencesUtil.setSignInDay(0);
			} else if(day > old_day_sign) {
				mPreferencesUtil.setSignInFlag(false);
			}
		}
		
		final String old_s_share = mPreferencesUtil.getShareDate();
		if(!old_s_share.equals("")){
			int old_month_share = Integer.valueOf(old_s_share.substring(5, 7));
			int old_day_share = Integer.valueOf(old_s_share.substring(8, 10));
			if(old_month_share != month){
				mPreferencesUtil.setShareFlag(false);
			} else if(day > old_day_share) {
				mPreferencesUtil.setShareFlag(false);
			}
		}
		
		final String old_s_free = mPreferencesUtil.getFreeDate();
		if(!old_s_free.equals("")){
			int old_month_free = Integer.valueOf(old_s_free.substring(5, 7));
			int old_day_free = Integer.valueOf(old_s_free.substring(8, 10));
			if(old_month_free != month){
				mPreferencesUtil.setFreeFlag(false);
				mPreferencesUtil.setIsFee(false);
				mPreferencesUtil.setFreeDate(s);
				initUser();
			} else if(day > old_day_free) {
				mPreferencesUtil.setFreeFlag(false);
				mPreferencesUtil.setIsFee(false);
				mPreferencesUtil.setFreeDate(s);
				initUser();
			} else if(mPreferencesUtil.getUserId().equals("联网重启")){
				initUser();
			}
		} else {
			mPreferencesUtil.setFreeDate(s);
			initUser();
		}
	}
	
	private void gotoMainActivity(){
//		if(!mPreferencesUtil.getInviteFlag()){
//			intent = new Intent(this, RecommendActivity.class);
//			startActivity(intent);
//			finish();
//		} else {
			intent = new Intent(this, TabHolderActivity.class);
			startActivity(intent);
			finish();
//		}
	}
	
	private void startService(){
		if(!PhoneInfo.isServiceRunning(this, "com.zounikeji.service.HelperService")){
			intent = new Intent(this, HelperService.class);
			startService(intent);
		}
	}
	
	private void initUser(){
//		RequestParams params = new RequestParams();
//		params.put("imei", PhoneInfo.getIMEI(this));
//		params.put("mac", PhoneInfo.getMacAddress(this));
//		params.put("version", MyAppInfo.getVerName(this));
//		MainApplication.getHttpClient().post(ApiConstant.POST_USER_LOGIN, params, new JsonHttpResponseHandler(){
//			@Override
//			public void onSuccess(int arg0, JSONObject arg1) {
//				if(arg1.optInt("code") == 200){
//					mPreferencesUtil.setUserId(arg1.optJSONObject("data").optString("id"));
//					mPreferencesUtil.setUserName(arg1.optJSONObject("data").optString("name"));
//					mPreferencesUtil.setUserStatus(arg1.optJSONObject("data").optInt("status"));
//					if(arg1.optJSONObject("data").optInt("isnew") == 1){
//						mPreferencesUtil.setUserScore(100);
//					}
//					JPushInterface.setAlias(SplashActivity.this, arg1.optJSONObject("data").optString("id"), SplashActivity.this);
//				}
//			}
//		});
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	//	MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	//	MobclickAgent.onResume(this);
	}

	@Override
	public void gotResult(int arg0, String arg1, Set<String> arg2) {
		
	}
}
