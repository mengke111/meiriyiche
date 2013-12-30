package com.zounikeji.util;

public class MkLog {
	private static String TAG = "Zounikeji";

	//add  by mengke18654111104 20131120  --start
    public static void MKLog(String str) {
        android.util.Log.d(TAG  , "------ " + str + "------");
        }
    //add  by mengke18654111104 20131120  --end
}
