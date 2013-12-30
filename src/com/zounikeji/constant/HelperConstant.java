package com.zounikeji.constant;

import com.zounikeji.meiriyiche.MainActivity;
import com.zounikeji.meiriyiche.R;
import com.zounikeji.meiriyiche.TaskActivity;
import com.zounikeji.meiriyiche.UserActivity;



public class HelperConstant {
	public static int   mImageViewArray[] = {R.drawable.ic_tab_item1,
		R.drawable.ic_tab_item2,
		R.drawable.ic_tab_item3};

	public static String mTextviewArray[] = {"连接", "活动", "我的"};


	public static Class mTabClassArray[]= {MainActivity.class,
			TaskActivity.class,
			UserActivity.class};
	
	public static String[] shareMsg = {
		"我用@747助手 上ChinaNet呢，超简单，还不花钱，你试试：",
		"我发现一款可以免费上ChinaNet的手机应用@747助手，简单而且真的不花钱哦，行动起来：",
		"@747助手 可以免费连ChinaNet WiFi哦，每天不限时，我惊呆了！这么好用，也推荐给你：",
		"@747助手 真的做到了！不限时的免费上网，ChinaNet一键连接，不花钱的好软件，也分享给你：",
		"这款软件@747助手 可以免费上ChinaNet的WiFi，ChinaNet还挺多的，你试试吧，反正也不花钱："
	};

	public static String TASK_LIST = "{\"data\": [{\"type\": 0,\"name\": \"免费积分大厅\",\"detail\": \"简简单单赢取高额积分，最高每天可赚300+积分\"}," +
			"{\"type\": 2,\"name\": \"分享\",\"detail\": \"分享给小伙伴们，还能赚20积分\"}," +
			"{\"type\": 1,\"name\": \"签到\",\"detail\": \"每天来签到，可赚20积分\"}]}\"";
	
	public static int[] TASK_TYPE_IMG = new int[]{
		R.drawable.ic_task_0,
		R.drawable.ic_task_1,
		R.drawable.ic_task_2,
		R.drawable.ic_task_3,
		R.drawable.ic_task_4,
		R.drawable.ic_task_5,
		R.drawable.ic_task_5,
		R.drawable.ic_task_6
	};
	
	/** 官方微信 */
	public static final String WECHAT_ADDR = "http://weixin.qq.com/r/HHURHl7EjmDxh099nyA4";
	/** 官方网站 */
	public static final String WEBSITE_ADDR = "http://www.747.cn";
	/** 官方新浪微博 */
	public static final String HELPER_SINAWEIBO_UID = "3772777902";
	/** 官方腾讯微博 */
	public static final String HELPER_TENCENTWEIBO_UID = "zhushou747";
	
	public static final int DEFAULT_FEE_COUNT = 100;
	
	public static final String BROADCAST_NOTIFICATION_SWITCH = "com.heimi.helper.notification.switch";
	public static final String EXTRA_NOTIFICATION_SWITCH = "notification_switch";
	
	public static final String BROADCAST_NOTIFICATION_START_TIME = "com.heimi.helper.notification.start.time";
	public static final String BROADCAST_NOTIFICATION_REFRESH_TIME = "com.heimi.helper.notification.refresh.time";
	public static final String BROADCAST_NOTIFICATION_END_TIME = "com.heimi.helper.notification.end.time";
}
