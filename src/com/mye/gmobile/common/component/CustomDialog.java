package com.mye.gmobile.common.component;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.mye.gmobile.R;


public class CustomDialog extends AlertDialog {
	private int dialog_layout = 0;//R.layout.dialog_house_name_layout;
	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}
	
	public CustomDialog(Context context, int theme,int layout) {
		super(context, theme);
		dialog_layout = layout;
	}
	
	public CustomDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(dialog_layout);
	}
}
