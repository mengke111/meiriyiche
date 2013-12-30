package com.zounikeji.AIDL;
import com.zounikeji.AIDL.forActivity;
interface forService {
	void registerTestCall(forActivity cdb);
        void unregisterTestCall(forActivity cdb);
	void invokCallBack();
	void sendEmptyMessage();
}