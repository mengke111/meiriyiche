package com.zounikeji;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

import com.loopj.android.http.AsyncHttpClient;

public class MainApplication extends Application {
	// HttpClient鍒濆鍖�閫氱敤鍗曚緥妯″紡
	private static final AsyncHttpClient httpClient = new AsyncHttpClient();

	public synchronized static AsyncHttpClient getHttpClient() {
		return httpClient;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.init(this);
	}
}
