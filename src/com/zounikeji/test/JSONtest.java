package com.zounikeji.test;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.zounikeji.util.MkLog;

import android.app.Activity;

public class JSONtest 

{
	public static List<Map<String, String>> getJsonpath(String path)
			throws Exception

	{

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		String json = null;

		Map<String, String> map;

		URL url = new URL(path);
		HttpURLConnection cn = (HttpURLConnection) url.openConnection();
		cn.setConnectTimeout(5 * 1000);
		cn.setRequestMethod("GET");
//		System.out.println("stream======="+cn.getInputStream());
		InputStreamReader in = new InputStreamReader(cn.getInputStream());
//		System.out.println("in======" + in);

		// 流的应用与读取

		BufferedReader bu = new BufferedReader(in);

		String line = bu.readLine().toString();

		bu.close();

		in.close();

		// 把字符数组转换成字符串

		json = new String(line);
		// 这里是以对象的形式

		JSONObject item1 = new JSONObject(json);

		// 得到对象中的对象

		JSONObject item = item1.getJSONObject("weatherinfo");

		// System.out.println(path);

		// 获取对象中的每一个数值

		System.out.println(json);

		String name = item.getString("city");

		String id = item.getString("cityid");

		String temp = item.getString("temp");

		String fengxiang = item.getString("WD");

		String daxiao = item.getString("WS");

		String wet = item.getString("SD");

		String time = item.getString("time");

		String isreader = item.getString("isRadar");

		String Radar = item.getString("Radar");

		// 添加到MAP中

		map = new HashMap<String, String>();

		map.put("name", name);

		map.put("id", id);

		map.put("temp", temp);

		map.put("fengxiang", fengxiang);

		map.put("daxiao", daxiao);

		map.put("wet", wet);

		map.put("time", time);

		map.put("isReader", isreader);

		map.put("Rader", Radar);

		list.add(map);

		// 测试数据

		for (Map<String, String> list1 : list)

		{
			MkLog.MKLog("测试数据...");
			MkLog.MKLog(list1.get("name"));

			MkLog.MKLog(list1.get("id"));

			MkLog.MKLog(list1.get("temp"));

			MkLog.MKLog(list1.get("fengxiang"));

			MkLog.MKLog(list1.get("daxiao"));

		}

		return list;

	}
}
