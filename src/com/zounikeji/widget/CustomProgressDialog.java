package com.zounikeji.widget;

import com.zounikeji.meiriyiche.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;



public class CustomProgressDialog extends Dialog {
	private static CustomProgressDialog customProgressDialog = null;
	
	public CustomProgressDialog(Context context){
		super(context);
	}
	
	public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public static CustomProgressDialog createDialog(Context context){
		customProgressDialog = new CustomProgressDialog(context,R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.dialog_custom_progress);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		
		return customProgressDialog;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
    	if (customProgressDialog == null){
    		return;
    	}
    }
 
    /**
     * 
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public CustomProgressDialog setTitile(String strTitle){
    	return customProgressDialog;
    }
    
    /**
     * 
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public CustomProgressDialog setMessageImg(int img_id){
    	ImageView tvMsg = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
    	
    	if (tvMsg != null){
    		tvMsg.setBackgroundResource(img_id);
    	}
    	
    	return customProgressDialog;
    }
    
    public CustomProgressDialog setMessage(String msg){
    	TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.loadingmsg);
    	
    	if (tvMsg != null){
    		tvMsg.setText(msg);
    	}
    	
    	return customProgressDialog;
    }
}