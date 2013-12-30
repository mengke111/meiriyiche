package com.zounikeji.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TaskModel {
	private int type;
	private String name;
	private String detail;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	
	public static List<TaskModel> parse(JSONObject object){
		List<TaskModel> result = new ArrayList<TaskModel>();
		if(object != null){
			JSONArray ja = object.optJSONArray("data");
			JSONObject temp;
			TaskModel task;
			for(int i = 0; i < ja.length(); i++){
				temp = ja.optJSONObject(i);
				task = new TaskModel();
				task.setType(temp.optInt("type"));
				task.setName(temp.optString("name"));
				task.setDetail(temp.optString("detail"));
				result.add(task);
			}
		}
		return result;
	}
	public static List<TaskModel> parse1(Object object) {
		// TODO Auto-generated method stub
		List<TaskModel> result = new ArrayList<TaskModel>();
	//	if(object != null){
	//		JSONArray ja = object.optJSONArray("data");
	//		JSONObject temp;
			TaskModel task;
		//	for(int i = 0; i < ja.length(); i++){
			//	temp = ja.optJSONObject(i);
				task = new TaskModel();
				task.setType(1);
				task.setName("name1");
				task.setDetail("detail1");
				result.add(task);
				task = new TaskModel();
				task.setType(2);
				task.setName("name2");
				task.setDetail("detail2");
				result.add(task);
				task = new TaskModel();
				task.setType(3);
				task.setName("name3");
				task.setDetail("detail3");
				result.add(task);
	//		}
	//	}
		return result;
	}
}
