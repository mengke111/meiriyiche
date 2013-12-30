package com.zounikeji.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zounikeji.meiriyiche.R;
import com.zounikeji.util.PhoneInfo;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class WifiAdapter extends BaseAdapter {
	private List<ScanResult> scanResults;
	private LayoutInflater inflater;
	private Context context;
	public WifiAdapter(Context context,List<ScanResult> scanResults) {
		if(scanResults!=null){
			this.scanResults =scanResults;
		}else{
			this.scanResults = new ArrayList<ScanResult>();
		}
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return scanResults.size();
	}

	@Override
	public Object getItem(int position) {
		return scanResults.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ScanResult scanResult = scanResults.get(position);
		ViewHolder holder = null;
		if(null==convertView){
			convertView = inflater.inflate(R.layout.wifi_list_item, null);
			holder = new ViewHolder();
			holder.signal = (ImageView) convertView.findViewById(R.id.signal_intensity);
			holder.SSID = (TextView) convertView.findViewById(R.id.wifi_name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.SSID.setText(scanResult.SSID);
		 //判断信号强度，显示对应的指示图标  
		int wifi_rssi_percent = 100 + (50 + PhoneInfo.roundRssi(scanResult.level)) * 2;
        if (wifi_rssi_percent <= 100 && wifi_rssi_percent > 75) {
        	holder.signal.setImageDrawable(context.getResources().getDrawable(R.drawable.stat_sys_wifi_signal_0));  
		} else if (wifi_rssi_percent <= 75 && wifi_rssi_percent > 50) {
			 holder.signal.setImageDrawable(context.getResources().getDrawable(R.drawable.stat_sys_wifi_signal_1));  
		} else if (wifi_rssi_percent <= 50 && wifi_rssi_percent > 25) {
			 holder.signal.setImageDrawable(context.getResources().getDrawable(R.drawable.stat_sys_wifi_signal_2));  
		} else if (wifi_rssi_percent <= 25 && wifi_rssi_percent > 0) {
			 holder.signal.setImageDrawable(context.getResources().getDrawable(R.drawable.stat_sys_wifi_signal_3));  
		} else {
			 holder.signal.setImageDrawable(context.getResources().getDrawable(R.drawable.stat_sys_wifi_signal_4));  
		}
		return convertView;
	}
	
	public void notify(List<ScanResult> scanResults){
		if(scanResults!=null){
			this.scanResults =scanResults;
		}else{
			this.scanResults = new ArrayList<ScanResult>();
		}
		notifyDataSetChanged();
	}
	
	static class ViewHolder{
		ImageView signal;
		TextView SSID;
		ImageView icon;
	}

}
