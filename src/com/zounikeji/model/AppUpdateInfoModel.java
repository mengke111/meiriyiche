package com.zounikeji.model;

import org.json.JSONObject;



public class AppUpdateInfoModel {
	private String description;
	private boolean force_update;
	private String url;
	private String version;
	private int versionCode;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isForce_update() {
		return force_update;
	}
	public void setForce_update(boolean force_update) {
		this.force_update = force_update;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	
	public static AppUpdateInfoModel parse(JSONObject jo){
		AppUpdateInfoModel result = new AppUpdateInfoModel();
		if(jo.optInt("code") == 200){
			JSONObject data = jo.optJSONArray("data").optJSONObject(0);
			result.setDescription(data.optString("desc"));
			result.setForce_update(data.optBoolean("force"));
			result.setUrl(data.optString("url"));
			result.setVersion(data.optString("version"));
			result.setVersionCode(data.optInt("version_code"));
//			ApiConstant.DOWN_UPDATE_URL = result.getUrl();
//			String[] temp = ApiConstant.DOWN_UPDATE_URL.split("/");
//			ApiConstant.APP_NAME = temp[temp.length-1];
		}
		return result;
	}
}
