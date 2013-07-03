package com.mye.gmobile.common.component;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.TextView;

public class WarningInfo {
	private static WarningInfo instance;
	private Context _context;
	private static TextView tv;
	private static AlertDialog.Builder confirm;
	
	public WarningInfo(Context context){
		_context = context;
		
		tv = new TextView(_context);
		tv.setGravity(Gravity.CENTER);
		confirm = new AlertDialog.Builder(_context);
		confirm.setIcon(android.R.drawable.ic_dialog_alert);
		confirm.setTitle(" ");
		confirm.setView(tv);

		
		confirm.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	
	public void setOKListener(DialogInterface.OnClickListener lis){
		if(lis != null){
			confirm.setPositiveButton("OK", lis);
		}
	}
	
	public static WarningInfo getInstance(Context context){
		instance = new WarningInfo(context);
		return instance;
	}
	
	public void setTitle(String title){
		confirm.setTitle(title);
	}
	
	public void setIcon(int id){
		confirm.setIcon(id);
	}
	
	public void showWarningInfo(String info, int textSize, int color){
		tv.setTextColor(color);
		tv.setTextSize(textSize);
		tv.setText(info);
		
		confirm.show();
	}
}
