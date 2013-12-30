package com.zounikeji.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 获取自己应用的一些信息 如 应用版本号 包名等
 * 
 * @author Neil
 */
public class MyAppInfo {

	/**
	 * @param context
	 * @return 程序当前versionCode
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;

		try {
			verCode = context.getPackageManager().getPackageInfo(
					"com.heimi.helper", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return verCode;
	}

	/**
	 * 
	 * @param context
	 * @return 程序当前versionName
	 */
	public static String getVerName(Context context) {
		String verName = "";

		try {
			verName = context.getPackageManager().getPackageInfo(
					"com.heimi.helper", 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return verName;
	}

	/**
	 * 
	 * @param newVersion
	 *            新版本号
	 * @param oldVersion
	 *            旧版本号
	 * @return true 有新版本 false 无新版本
	 */
	public static boolean isNew(int newVersion, int oldVersion) {
		boolean result = false;

		if (newVersion > oldVersion) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}
}
