package com.zounikeji.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtil {

	private SharedPreferences sharePreferences;
	private SharedPreferences.Editor editor;
	
	private final String APP_CONFIG_NAME = "helper_config";	//软件配置文件名称
	
	private final String KEY_SIGN_IN_FLAG = "key_sign_in_flag";
	private final String KEY_SIGN_IN_DATE = "key_sign_in_date";
	private final String KEY_SIGN_IN_DAY = "key_sign_in_day";
	private final String KEY_SHARE_FLAG = "key_share_flag";
	private final String KEY_SHARE_DATE = "key_share_date";
	private final String KEY_NOTIFICATION_FIND_SWITCH = "key_notification_find_switch";
	private final String KEY_NOTIFICATION_CONNECT_SWITCH = "key_notification_connect_switch";
	private final String KEY_HEIMI_CONNECT_FLAG = "key_heimi_connect_flag";
	
	public PreferencesUtil(Context context) {
		sharePreferences = context.getSharedPreferences(APP_CONFIG_NAME, Context.MODE_PRIVATE);
		editor = sharePreferences.edit();
	}
	
	public void setSignInFlag(boolean flag){
		editor.putBoolean(KEY_SIGN_IN_FLAG, flag);
		editor.commit();
	}
	
	public boolean getSignInFlag(){
		return sharePreferences.getBoolean(KEY_SIGN_IN_FLAG, false);
	}
	
	public void setSignInDate(String date){
		editor.putString(KEY_SIGN_IN_DATE, date);
		editor.commit();
	}
	
	public String getSignInDate(){
		return sharePreferences.getString(KEY_SIGN_IN_DATE, "");
	}
	
	public void setSignInDay(int day){
		editor.putInt(KEY_SIGN_IN_DAY, day);
		editor.commit();
	}
	
	public int getSignInDay(){
		return sharePreferences.getInt(KEY_SIGN_IN_DAY, 0);
	}
	
	public void setShareFlag(boolean flag){
		editor.putBoolean(KEY_SHARE_FLAG, flag);
		editor.commit();
	}
	
	public boolean getShareFlag(){
		return sharePreferences.getBoolean(KEY_SHARE_FLAG, false);
	}
	
	public void setShareDate(String date){
		editor.putString(KEY_SHARE_DATE, date);
		editor.commit();
	}
	
	public String getShareDate(){
		return sharePreferences.getString(KEY_SHARE_DATE, "");
	}
	
	//========连接相关==========
	public void setHeimiConnectFlag(boolean flag){
		editor.putBoolean(KEY_HEIMI_CONNECT_FLAG, flag);
		editor.commit();
	}
	
	public boolean getHeimiConnectFlag(){
		return sharePreferences.getBoolean(KEY_HEIMI_CONNECT_FLAG, false);
	}
	
	//=========每天免费�?��======
	private final String KEY_FREE_DATE = "key_free_date";
	private final String KEY_FREE_FLAG = "key_free_flag";
	public void setFreeDate(String date){
		editor.putString(KEY_FREE_DATE, date);
		editor.commit();
	}
	
	public String getFreeDate(){
		return sharePreferences.getString(KEY_FREE_DATE, "");
	}
	
	public void setFreeFlag(boolean flag){
		editor.putBoolean(KEY_FREE_FLAG, flag);
		editor.commit();
	}
	
	public boolean getFreeFlag(){
		return sharePreferences.getBoolean(KEY_FREE_FLAG, false);
	}
	
	//==========设置相关===========
	public void setNotificationFindSwitch(boolean flag){
		editor.putBoolean(KEY_NOTIFICATION_FIND_SWITCH, flag);
		editor.commit();
	}
	
	public boolean getNotificationFindSwitch(){
		return sharePreferences.getBoolean(KEY_NOTIFICATION_FIND_SWITCH, true);
	}
	
	public void setNotificationConnectSwitch(boolean flag){
		editor.putBoolean(KEY_NOTIFICATION_CONNECT_SWITCH, flag);
		editor.commit();
	}
	
	public boolean getNotificationConnectSwitch(){
		return sharePreferences.getBoolean(KEY_NOTIFICATION_CONNECT_SWITCH, true);
	}
	
	//===========用户信息===========
	private final String KEY_USER_ID = "key_uesr_id";
	private final String KEY_USER_NAME = "key_user_name";
	private final String KEY_USER_STATUS = "key_user_status";
	public void setUserId(String id){
		editor.putString(KEY_USER_ID, id);
		editor.commit();
	}
	
	public String getUserId(){
		return sharePreferences.getString(KEY_USER_ID, "联网重启");
	}
	
	public void setUserName(String name){
		editor.putString(KEY_USER_NAME, name);
		editor.commit();
	}
	
	public String getUserName(){
		return sharePreferences.getString(KEY_USER_NAME, "");
	}
	
	public void setUserStatus(int status){
		editor.putInt(KEY_USER_STATUS, status);
		editor.commit();
	}
	
	public int getUserStatus(){
		return sharePreferences.getInt(KEY_USER_STATUS, 1);
	}
	
	//========用户本地积分=============
	private final String KEY_USER_SCORE = "key_user_score";
	public void setUserScore(int score){
		editor.putInt(KEY_USER_SCORE, score);
		editor.commit();
	}
	
	public int getUserScore(){
		return sharePreferences.getInt(KEY_USER_SCORE, 0);
	}
	
	//=======是否每日已经扣费=========
	private final String KEY_FEE = "key_fee";
	public void setIsFee(boolean flag){
		editor.putBoolean(KEY_FEE, flag);
		editor.commit();
	}
	
	public boolean getIsFee(){
		return sharePreferences.getBoolean(KEY_FEE, false);
	}
	
	//=======是否为含推荐版本========
	private final String KEY_INVITE_FLAG = "key_invite_flag";
	public void setInviteFlag(boolean flag){
		editor.putBoolean(KEY_INVITE_FLAG, flag);
		editor.commit();
	}
	
	public boolean getInviteFlag(){
		return sharePreferences.getBoolean(KEY_INVITE_FLAG, true);
	}
}
