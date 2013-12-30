package com.zounikeji.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class PhoneInfo {
	private static final String FILE_MEMORY = "/proc/meminfo";
	private static final String FILE_CPU = "/proc/cpuinfo";
	public String mIMEI;
	public int mPhoneType;
	public int mSysVersion;
	public String mNetWorkCountryIso;
	public String mNetWorkOperator;
	public String mNetWorkOperatorName;
	public int mNetWorkType;
	public boolean mIsOnLine;
	public String mConnectTypeName;
	public long mFreeMem;
	public long mTotalMem;
	public String mCupInfo;
	public String mProductName;
	public String mModelName;
	public String mManufacturerName;

	/**
	 * private constructor
	 */
	private PhoneInfo() {

	}

	public static int[] getDisplaySize(Context context) {
		int[] size = new int[2];
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		size[0] = Math.min(metric.widthPixels, metric.heightPixels);
		size[1] = Math.max(metric.widthPixels, metric.heightPixels);
		return size;
	}
	
	/**
	 * 鑾峰彇mac
	 */
	public static String getMacAddress(Context context){
		WifiManager wifiMgr = (WifiManager)context.getSystemService(Activity.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		return info.getMacAddress();
	}

	/**
	 * 鑾峰彇灞忓箷灏哄 濡傦細480x800
	 * 
	 * @param context
	 * @return
	 */
	public static String getScreenSize(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metric);
		return metric.widthPixels + "x" + metric.heightPixels;
	}

	/**
	 * get imei
	 * 
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		// check if has the permission
		if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
				.checkPermission(Manifest.permission.READ_PHONE_STATE,
						context.getPackageName())) {
			return manager.getDeviceId();
		} else {
			return null;
		}
	}

	/**
	 * get phone type,like :GSM CDMA SIP NONE
	 * 
	 * @param context
	 * @return
	 */
	public static int getPhoneType(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getPhoneType();
	}

	/**
	 * get phone sys version
	 * 
	 * @return
	 */
	public static int getSysVersion() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * Returns the ISO country code equivalent of the current registered
	 * operator's MCC (Mobile Country Code).
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetWorkCountryIso(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getNetworkCountryIso();
	}

	/**
	 * Returns the numeric name (MCC+MNC) of current registered operator.may not
	 * work on CDMA phone
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetWorkOperator(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getNetworkOperator();
	}

	/**
	 * Returns the alphabetic name of current registered operator.may not work
	 * on CDMA phone
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetWorkOperatorName(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getNetworkOperatorName();
	}

	/**
	 * get type of current network
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetworkType(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		return manager.getNetworkType();
	}

	/**
	 * is webservice aviliable
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * get current data connection type name ,like ,Mobile WIFI OFFLINE
	 * 
	 * @param context
	 * @return
	 */
	public static String getConnectTypeName(Context context) {
		if (!isOnline(context)) {
			return "OFFLINE";
		}
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			return info.getTypeName();
		} else {
			return "OFFLINE";
		}
	}

	/**
	 * get free memory of phone, in M
	 * 
	 * @param context
	 * @return
	 */
	public static long getFreeMem(Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		MemoryInfo info = new MemoryInfo();
		manager.getMemoryInfo(info);
		long free = info.availMem / 1024 / 1024;
		return free;
	}

	/**
	 * get total memory of phone , in M
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("resource")
	public static long getTotalMem(Context context) {
		try {
			FileReader fr = new FileReader(FILE_MEMORY);
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split("\\s+");
			return Long.valueOf(array[1]) / 1024;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@SuppressWarnings("resource")
	public static String getCpuInfo() {
		try {
			FileReader fr = new FileReader(FILE_CPU);
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get product name of phone
	 * 
	 * @return
	 */
	public static String getProductName() {
		return Build.PRODUCT;
	}

	/**
	 * 鑾峰彇鎵嬫満鍨嬪彿
	 * 
	 * @return
	 */
	public static String getModelName() {
		return Build.MODEL;
	}

	/**
	 * 鑾峰彇鎵嬫満鍒堕�鍟�
	 * 
	 * @return
	 */
	public static String getManufacturerName() {
		return Build.MANUFACTURER;
	}

	// 鑾峰彇wifi鐨勪俊鍙峰己搴�
	public static int roundRssi(int Rssi) {
		int newRssi = 0;
		if (Rssi > -50) {
			newRssi = -50;
		} else if (Rssi < -100) {
			newRssi = -100;
		} else {
			newRssi = Rssi;
		}
		return newRssi;
	}
	
	/**
	 * 妫�煡鏄惁瀛樺湪璇ユ湇鍔�
	 * @param context
	 * @param className
	 * @return
	 */
	public static boolean isServiceRunning(Context context, String className){
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager)context.getSystemService(Activity.ACTIVITY_SERVICE); 
		List<ActivityManager.RunningServiceInfo> mServiceList = activityManager.getRunningServices(Integer.MAX_VALUE);
		if (!(mServiceList.size()>0)) {
		      return false;
	    }
		for(ActivityManager.RunningServiceInfo info:mServiceList){
			if(info.service.getClassName().equals(className)){
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	public static PhoneInfo getPhoneInfo(Context context) {
		PhoneInfo result = new PhoneInfo();
		result.mIMEI = getIMEI(context);
		result.mPhoneType = getPhoneType(context);
		result.mSysVersion = getSysVersion();
		result.mNetWorkCountryIso = getNetWorkCountryIso(context);
		result.mNetWorkOperator = getNetWorkOperator(context);
		result.mNetWorkOperatorName = getNetWorkOperatorName(context);
		result.mNetWorkType = getNetworkType(context);
		result.mIsOnLine = isOnline(context);
		result.mConnectTypeName = getConnectTypeName(context);
		result.mFreeMem = getFreeMem(context);
		result.mTotalMem = getTotalMem(context);
		result.mCupInfo = getCpuInfo();
		result.mProductName = getProductName();
		result.mModelName = getModelName();
		result.mManufacturerName = getManufacturerName();
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IMEI : " + mIMEI + "\n");
		builder.append("mPhoneType : " + mPhoneType + "\n");
		builder.append("mSysVersion : " + mSysVersion + "\n");
		builder.append("mNetWorkCountryIso : " + mNetWorkCountryIso + "\n");
		builder.append("mNetWorkOperator : " + mNetWorkOperator + "\n");
		builder.append("mNetWorkOperatorName : " + mNetWorkOperatorName + "\n");
		builder.append("mNetWorkType : " + mNetWorkType + "\n");
		builder.append("mIsOnLine : " + mIsOnLine + "\n");
		builder.append("mConnectTypeName : " + mConnectTypeName + "\n");
		builder.append("mFreeMem : " + mFreeMem + "M\n");
		builder.append("mTotalMem : " + mTotalMem + "M\n");
		builder.append("mCupInfo : " + mCupInfo + "\n");
		builder.append("mProductName : " + mProductName + "\n");
		builder.append("mModelName : " + mModelName + "\n");
		builder.append("mManufacturerName : " + mManufacturerName + "\n");
		return builder.toString();
	}
}
