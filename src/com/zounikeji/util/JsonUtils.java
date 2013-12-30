package com.zounikeji.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;



//import com.google.gson.stream.JsonReader;

public class JsonUtils {

	public static String getConnection(String path)  
            throws MalformedURLException, IOException, ProtocolException {  
        URL url = new URL(path);  
        try {  
//        	建立一个http连接
            HttpURLConnection cn = (HttpURLConnection) url.openConnection();  
//            5秒超时
            cn.setConnectTimeout(5 * 1000);  
//            使用Get方法传递数据
            cn.setRequestMethod("GET");   
            InputStreamReader in = new InputStreamReader(cn.getInputStream());  
            
//        	URL Url = new URL(path);  
//			URLConnection conn = Url.openConnection();  
//			conn.connect();  
//			InputStream in = conn.getInputStream();       
            
            
            
//             流数据读取
            BufferedReader buff = new BufferedReader(in);  
            String data = buff.readLine().toString();  
            System.out.println("流数据data:    " + data);  
            buff.close();  
            in.close(); 
//            返回数据流
            return data;  
        } 
        catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
           // Toast.makeText(, "��ݸ��³ɹ�", Toast.LENGTH_LONG).show();
            System.out.println("查询失败,请检查网络...");  
            return null;  
        }  
    }  
	public  List<Map<String, String>> getWeather(String path) {  
		  
	    List<Map<String, String>> list = new ArrayList<Map<String, String>>();  
	    String json = null;  
	    Map<String, String> map;  	  
	    try { 
//	    	调用getConnection()获得数据流
	        String line = getConnection(path);  	  
	        if (line != null) {  
	            json = new String(line);  
	            // 对象的形式  
	            JSONObject Item = new JSONObject(json);  
	            // 得到对象中的对象  
	            JSONObject item = Item.getJSONObject("weatherinfo");  
	            String city = item.getString("city");  
	            String temp = item.getString("temp");  
	            String time = item.getString("time");    
	            // 添加到map中  	  
	            map = new HashMap<String, String>();  
	            map.put("city", city);  
	            map.put("temp", temp);    
	            map.put("time", time); 
	            list.add(map); 
	        } 
	        else { 
	            System.out.println("获取流数据失败!");  
	        }  
	  
	    } catch (MalformedURLException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    } catch (ProtocolException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    } catch (JSONException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	    return list;  
	}
}