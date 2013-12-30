package com.zounikeji.meiriyiche;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ruiduad.offers.PointsManager;
import com.umeng.analytics.MobclickAgent;
import com.zounikeji.util.PhoneInfo;
import com.zounikeji.util.PreferencesUtil;

public class UserActivity extends Activity implements OnClickListener{
	private TextView tv_navigation_name;
	private RelativeLayout rl_user_setting;
	private TextView tv_user_id;
	private TextView tv_user_score;
	private RelativeLayout rl_user_score;
	
	private PreferencesUtil mPreferencesUtil;
	private Intent intent;
	private ProgressDialog mDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		initData();
		initView();
	}
	
	private void initData(){
		mPreferencesUtil = new PreferencesUtil(this);
	}
	
	private void initView(){
		tv_navigation_name = (TextView) this.findViewById(R.id.tv_navigation_name);
		tv_navigation_name.setText("我的");
		rl_user_setting = (RelativeLayout) this.findViewById(R.id.rl_user_setting);
		rl_user_setting.setOnClickListener(this);
		tv_user_id = (TextView) this.findViewById(R.id.tv_user_id);
		tv_user_id.setText(mPreferencesUtil.getUserId());
		tv_user_score = (TextView) this.findViewById(R.id.tv_user_score);
		rl_user_score = (RelativeLayout) this.findViewById(R.id.rl_user_score);
		rl_user_score.setOnClickListener(this);
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
		super.onResume();
		MobclickAgent.onResume(this);
		tv_user_score.setText(mPreferencesUtil.getUserScore() + "");
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.rl_user_setting:
		//	intent = new Intent(UserActivity.this, SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.rl_user_score:
			addWallScore(UserActivity.this, mPreferencesUtil);
			break;
		}
	}
	
	private int result = 0;
	private int addWallScore(final Context context, final PreferencesUtil mPreferencesUtil){
		mDialog = ProgressDialog.show(context, null, "正在刷新...", true, true);
		mDialog.setCanceledOnTouchOutside(false);
		result = PointsManager.getInstance(context).queryPoints();
		if(result != 0) {
			RequestParams params = new RequestParams();
			params.put("score", result + "");
			params.put("uid", mPreferencesUtil.getUserId());
			params.put("mac", PhoneInfo.getMacAddress(context));
			params.put("type", "A1");
//			params.put("version", MyAppInfo.getVerName(context));
//			MainApplication.getHttpClient().post(ApiConstant.POST_ACCOUNT_CHG, params, new JsonHttpResponseHandler(){
//				@Override
//				public void onFinish() {
//					if(mDialog.isShowing())	{
//						mDialog.cancel();
//					}
//				}
//
//				@Override
//				public void onSuccess(int arg0, JSONObject arg1) {
//					if(arg1.optInt("code") == 200){
//						PointsManager.getInstance(context).spendPoints(result);
//						result = arg1.optJSONObject("data").optInt("score");
//						mPreferencesUtil.setUserScore(result);
//						tv_user_score.setText(result + "");
//					}
//				}
//			});
		} else {
			RequestParams params = new RequestParams();
			params.put("uid", mPreferencesUtil.getUserId());
			params.put("mac", PhoneInfo.getMacAddress(context));
//			params.put("version", MyAppInfo.getVerName(context));
//			MainApplication.getHttpClient().get(ApiConstant.GET_ACCOUNT_SCORE, params, new JsonHttpResponseHandler(){
//				@Override
//				public void onFinish() {
//					if(mDialog.isShowing())	{
//						mDialog.cancel();
//					}
//				}
//				
//				@Override
//				public void onSuccess(int arg0, JSONObject arg1) {
//					if(arg1.optInt("code") == 200){
//						result = arg1.optJSONObject("data").optInt("score");
//						mPreferencesUtil.setUserScore(result);
//						tv_user_score.setText(result + "");
//					}
//				}
//			});
		}
		return result;
	}
}
