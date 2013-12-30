package com.zounikeji.adapter;

import java.util.List;

import com.zounikeji.constant.HelperConstant;
import com.zounikeji.meiriyiche.R;
import com.zounikeji.model.TaskModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class TaskAdapter extends BaseAdapter{
	
	private List<TaskModel> taskList;
	private LayoutInflater mLayoutInflater;
	private ViewHolder viewHolder;
	private TaskModel task;
	
	private static class ViewHolder{
		ImageView iv_type;
		TextView tv_name;
		TextView tv_detail;
	}
	
	public TaskAdapter(Context context, List<TaskModel> taskList){
		this.mLayoutInflater = LayoutInflater.from(context);
		this.taskList = taskList;
	}
	
	public void updateTaskAdapter(List<TaskModel> taskList){
		this.taskList = taskList;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return taskList == null ? 0 : taskList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.task_list_item, null);
			viewHolder.iv_type = (ImageView) convertView.findViewById(R.id.iv_task_type);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_task_name);
			viewHolder.tv_detail = (TextView) convertView.findViewById(R.id.tv_task_detail);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		task = taskList.get(position);
		
		viewHolder.iv_type.setBackgroundResource(HelperConstant.TASK_TYPE_IMG[task.getType()]);
		viewHolder.tv_name.setText(task.getName());
		viewHolder.tv_detail.setText(task.getDetail());
		
		return convertView;
	}

}
