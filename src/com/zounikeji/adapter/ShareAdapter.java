package com.zounikeji.adapter;

import java.util.HashMap;

import com.zounikeji.constant.HelperConstant;
import com.zounikeji.meiriyiche.R;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.authorize.AuthorizeAdapter;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;



public class ShareAdapter extends AuthorizeAdapter implements OnClickListener,
		PlatformActionListener {
	private CheckedTextView ctvFollow;
	private PlatformActionListener backListener;

	public void onCreate() {
		// 闅愯棌鏍囬鏍忓彸閮ㄧ殑Share SDK Logo
		// hideShareSDKLogo();

		// TitleLayout llTitle = getTitleLayout();
		// llTitle.getTvTitle().setText("xxxx");

		String platName = getPlatformName();
		if (SinaWeibo.NAME.equals(platName)
				|| TencentWeibo.NAME.equals(platName)) {
			initUi(platName);
			interceptPlatformActionListener(platName);
			return;
		}

		// 浣垮脊鍑哄姩鐢诲け鏁堬紝鍙兘鍦╫nCreate涓皟鐢紝鍚﹀垯鏃犳硶璧蜂綔鐢�
		// disablePopUpAnimation();

		// 涓嬮潰鐨勪唬鐮佹紨绀哄浣曡缃嚜瀹氫箟鐨勬巿鏉冮〉闈㈡墦寮�姩鐢�
		// disablePopUpAnimation();
		// View rv = (View) getBodyView().getParent();
		// TranslateAnimation ta = new TranslateAnimation(
		// Animation.RELATIVE_TO_SELF, -1,
		// Animation.RELATIVE_TO_SELF, 0,
		// Animation.RELATIVE_TO_SELF, 0,
		// Animation.RELATIVE_TO_SELF, 0);
		// ta.setDuration(500);
		// rv.setAnimation(ta);
	}

	private void initUi(String platName) {
		ctvFollow = new CheckedTextView(getActivity());
		try {
			ctvFollow.setBackgroundResource(R.drawable.auth_follow_bg);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		ctvFollow.setChecked(true);
		int dp_10 = cn.sharesdk.framework.utils.R.dipToPx(getActivity(), 10);
		ctvFollow.setCompoundDrawablePadding(dp_10);
		ctvFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cb_auth_follow,
				0, 0, 0);
		ctvFollow.setGravity(Gravity.CENTER_VERTICAL);
		ctvFollow.setPadding(dp_10, dp_10, dp_10, dp_10);
		ctvFollow.setText(R.string.sm_item_fl_weibo);
		if (platName.equals(TencentWeibo.NAME)) {
			ctvFollow.setText(R.string.sm_item_fl_tc);
		}
		ctvFollow.setTextColor(0xff909090);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		ctvFollow.setLayoutParams(lp);
		LinearLayout llBody = (LinearLayout) getBodyView().getChildAt(0);
		llBody.addView(ctvFollow);
		ctvFollow.setOnClickListener(this);

		ctvFollow.measure(0, 0);
		int height = ctvFollow.getMeasuredHeight();
		TranslateAnimation animShow = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
				Animation.ABSOLUTE, height, Animation.ABSOLUTE, 0);
		animShow.setDuration(1000);
		getWebBody().startAnimation(animShow);
		ctvFollow.startAnimation(animShow);
	}

	private void interceptPlatformActionListener(String platName) {
		Platform plat = ShareSDK.getPlatform(getActivity(), platName);
		// 澶囦唤姝ゅ墠璁剧疆鐨勪簨浠剁洃鍚櫒
		backListener = plat.getPlatformActionListener();
		// 璁剧疆鏂扮殑鐩戝惉鍣紝瀹炵幇浜嬩欢鎷︽埅
		plat.setPlatformActionListener(this);
	}

	public void onError(Platform plat, int action, Throwable t) {
		if (action == Platform.ACTION_AUTHORIZING) { // null
			// 鎺堟潈鏃跺嵆鍙戠敓閿欒
			plat.setPlatformActionListener(backListener);
			if (backListener != null) {
				backListener.onError(plat, action, t);
			}
		} else {
			// 鍏虫敞鏃跺彂鐢熼敊璇�
			plat.setPlatformActionListener(backListener);
			if (backListener != null) {
				backListener
						.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
			}
		}
	}

	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		if (action == Platform.ACTION_FOLLOWING_USER) {
			// 褰撲綔鎺堟潈浠ュ悗涓嶅仛浠讳綍浜嬫儏
			plat.setPlatformActionListener(backListener);
			if (backListener != null) {
				backListener
						.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
			}
		} else if (ctvFollow.isChecked()) {
			// 鎺堟潈鎴愬姛锛屾墽琛屽叧娉�
			String account = HelperConstant.HELPER_SINAWEIBO_UID;
			if (TencentWeibo.NAME.equals(plat.getName())) {
				account = HelperConstant.HELPER_TENCENTWEIBO_UID;
			}
			plat.followFriend(account);
		} else {
			// 濡傛灉娌℃湁鏍囪涓衡�鎺堟潈骞跺叧娉ㄢ�鍒欑洿鎺ヨ繑鍥�
			plat.setPlatformActionListener(backListener);
			if (backListener != null) {
				// 鍏虫敞鎴愬姛涔熷彧鏄綋浣滄巿鏉冩垚鍔熻繑鍥�
				backListener
						.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
			}
		}
	}

	public void onCancel(Platform plat, int action) {
		plat.setPlatformActionListener(backListener);
		if (action == Platform.ACTION_AUTHORIZING) {
			// 鎺堟潈鍓嶅彇娑�
			if (backListener != null) {
				backListener.onCancel(plat, action);
			}
		} else {
			// 褰撲綔鎺堟潈浠ュ悗涓嶅仛浠讳綍浜嬫儏
			if (backListener != null) {
				backListener
						.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
			}

		}
	}

	public void onClick(View v) {
		CheckedTextView ctv = (CheckedTextView) v;
		ctv.setChecked(!ctv.isChecked());
	}

	public void onResize(int w, int h, int oldw, int oldh) {
		if (ctvFollow != null) {
			if (oldh - h > 100) {
				ctvFollow.setVisibility(View.GONE);
			} else {
				ctvFollow.setVisibility(View.VISIBLE);
			}
		}
	}

}
