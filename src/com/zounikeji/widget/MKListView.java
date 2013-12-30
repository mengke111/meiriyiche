package com.zounikeji.widget;

import java.util.Date;

import com.zounikeji.meiriyiche.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MKListView extends ListView implements OnScrollListener {
	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private LayoutInflater inflater;
	private LinearLayout headView;
	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	// 鐢ㄤ簬淇濊瘉startY鐨勫�鍦ㄤ竴涓畬鏁寸殑touch浜嬩欢涓彧琚褰曚竴娆�
	private boolean isRecored;
	private int headContentWidth;
	private int headContentHeight;
	private int startY;
	private int firstItemIndex;
	private int state;
	private boolean isBack;
	public OnRefreshListener refreshListener;
	private final static String TAG = "abc";

	public MKListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		inflater = LayoutInflater.from(context);
		headView = (LinearLayout) inflater.inflate(R.layout.head, null);
		arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(50);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();
		Log.v("size", "width:" + headContentWidth + " height:"
				+ headContentHeight);
		addHeaderView(headView);
		setOnScrollListener(this);
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);
		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(250);
		reverseAnimation.setFillAfter(true);
	}

	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
			int arg3) {
		firstItemIndex = firstVisiableItem;
	}

	public void onScrollStateChanged(AbsListView arg0, int arg1) {
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (firstItemIndex == 0 && !isRecored) {
				startY = (int) event.getY();
				isRecored = true;
				Log.v(TAG, "鍦╠own鏃跺�璁板綍褰撳墠浣嶇疆鈥�");
			}
			break;
		case MotionEvent.ACTION_UP:
			if (state != REFRESHING) {
				if (state == DONE) {
					// 浠�箞閮戒笉鍋�
				}
				if (state == PULL_To_REFRESH) {
					state = DONE;
					changeHeaderViewByState();
					Log.v(TAG, "鐢变笅鎷夊埛鏂扮姸鎬侊紝鍒癲one鐘舵�");
				}
				if (state == RELEASE_To_REFRESH) {
					state = REFRESHING;
					changeHeaderViewByState();
					onRefresh();
					Log.v(TAG, "鐢辨澗寮�埛鏂扮姸鎬侊紝鍒癲one鐘舵�");
				}
			}
			isRecored = false;
			isBack = false;
			break;
		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();
			if (!isRecored && firstItemIndex == 0) {
				Log.v(TAG, "鍦╩ove鏃跺�璁板綍涓嬩綅缃�");
				isRecored = true;
				startY = tempY;
			}
			if (state != REFRESHING && isRecored) {
				// 鍙互鏉炬墜鍘诲埛鏂颁簡
				if (state == RELEASE_To_REFRESH) {
					// 寰�笂鎺ㄤ簡锛屾帹鍒颁簡灞忓箷瓒冲鎺╃洊head鐨勭▼搴︼紝浣嗘槸杩樻病鏈夋帹鍒板叏閮ㄦ帺鐩栫殑鍦版
					if ((tempY - startY < headContentHeight)
							&& (tempY - startY) > 0) {
						state = PULL_To_REFRESH;
						changeHeaderViewByState();
						Log.v(TAG, "鐢辨澗寮�埛鏂扮姸鎬佽浆鍙樺埌涓嬫媺鍒锋柊鐘舵�");
					}
					// 涓�笅瀛愭帹鍒伴《浜�
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();
						Log.v(TAG, "鐢辨澗寮�埛鏂扮姸鎬佽浆鍙樺埌done鐘舵�");
					}
					// 寰�笅鎷変簡锛屾垨鑰呰繕娌℃湁涓婃帹鍒板睆骞曢《閮ㄦ帺鐩杊ead鐨勫湴姝�
					else {
						// 涓嶇敤杩涜鐗瑰埆鐨勬搷浣滐紝鍙敤鏇存柊paddingTop鐨勫�灏辫浜�
					}
				}
				// 杩樻病鏈夊埌杈炬樉绀烘澗寮�埛鏂扮殑鏃跺�,DONE鎴栬�鏄疨ULL_To_REFRESH鐘舵�
				if (state == PULL_To_REFRESH) {
					// 涓嬫媺鍒板彲浠ヨ繘鍏ELEASE_TO_REFRESH鐨勭姸鎬�
					if (tempY - startY >= headContentHeight) {
						state = RELEASE_To_REFRESH;
						isBack = true;
						changeHeaderViewByState();
						Log.v(TAG, "鐢眃one鎴栬�涓嬫媺鍒锋柊鐘舵�杞彉鍒版澗寮�埛鏂�");
					}
					// 涓婃帹鍒伴《浜�
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();
						Log.v(TAG, "鐢盌One鎴栬�涓嬫媺鍒锋柊鐘舵�杞彉鍒癲one鐘舵�");
					}
				}
				// done鐘舵�涓�
				if (state == DONE) {
					if (tempY - startY > 0) {
						state = PULL_To_REFRESH;
						changeHeaderViewByState();
					}
				}
				// 鏇存柊headView鐨剆ize
				if (state == PULL_To_REFRESH) {
					headView.setPadding(0, -1 * headContentHeight
							+ (tempY - startY), 0, 0);
					headView.invalidate();
				}
				// 鏇存柊headView鐨刾addingTop
				if (state == RELEASE_To_REFRESH) {
					headView.setPadding(0, tempY - startY - headContentHeight,
							0, 0);
					headView.invalidate();
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	// 褰撶姸鎬佹敼鍙樻椂鍊欙紝璋冪敤璇ユ柟娉曪紝浠ユ洿鏂扮晫闈�
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);
			tipsTextview.setText("鏉惧紑鍒锋柊");
			Log.v(TAG, "褰撳墠鐘舵�锛屾澗寮�埛鏂�");
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// 鏄敱RELEASE_To_REFRESH鐘舵�杞彉鏉ョ殑
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);
				tipsTextview.setText("涓嬫媺鍒锋柊");
			} else {
				tipsTextview.setText("涓嬫媺鍒锋柊");
			}
			Log.v(TAG, "褰撳墠鐘舵�锛屼笅鎷夊埛鏂�");
			break;
		case REFRESHING:
			headView.setPadding(0, 0, 0, 0);
			headView.invalidate();
			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText("姝ｅ湪鍒锋柊...");
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			Log.v(TAG, "褰撳墠鐘舵�,姝ｅ湪鍒锋柊...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			headView.invalidate();
			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.ic_refresh);
			tipsTextview.setText("涓嬫媺鍒锋柊");
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			Log.v(TAG, "褰撳墠鐘舵�锛宒one");
			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void onRefreshComplete() {
		state = DONE;
		lastUpdatedTextView.setText("鏈�繎鏇存柊:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	// 姝ゆ柟娉曠洿鎺ョ収鎼嚜缃戠粶涓婄殑涓�釜涓嬫媺鍒锋柊鐨刣emo锛屾澶勬槸鈥滀及璁♀�headView鐨剋idth浠ュ強height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
}