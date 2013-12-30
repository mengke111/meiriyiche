package com.zounikeji.constant;

public class ApiConstant {
	public static final String API_HOST = "http://mengkezouni.cn";
//	public static final String API_HOST = "http://assapi.747.cn:10010/1";
//	public static final String API_HOST = "http://172.16.1.238:10001/1";

	public static final String POST_USER_LOGIN = API_HOST + "/sppget.php";
	
	public static final String POST_ACCOUNT_CHG = API_HOST + "/account/chg";
	
	public static final String POST_INVITE_FRIEND = API_HOST + "/app/invite";
	
	public static final String POST_SUPER_CODE = API_HOST + "/app/supercode";
	
	public static final String POST_CDKEY_CODE = API_HOST + "/app/giftcode";
	
	public static final String GET_ACCOUNT_SCORE = API_HOST + "/account/score";
	
	public static final String POST_WIFI_MESSAGE = API_HOST + "/wifi/collect";
	
	public static final String POST_SIGNIN_SCORE = API_HOST + "/app/signin";

	// =======================APP更新升级相关=========================
	public static final String UPGRADE_HOST = "~!~http://baseapp.747.cn/4/";
	public static final String APP_VERSION = "ahelper010100.json";
	public static final String GET_APP_UPDATE_URL = UPGRADE_HOST + "upgrade/app/index/" + APP_VERSION;
	public static String DOWN_UPDATE_URL;
	public static String APP_NAME;
}
