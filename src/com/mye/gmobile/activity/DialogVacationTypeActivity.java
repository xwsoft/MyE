package com.mye.gmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mye.gmobile.R;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.view.wheel.WheelView;
import com.mye.gmobile.view.wheel.adapters.NumericWheelAdapter;

/**
 * @author xwsoft
 * @version date：2013-6-07 上午10:26:20
 * 
 */
public class DialogVacationTypeActivity extends BaseActivity implements OnClickListener {

	private Button btn_vacation,btn_staycation;
	private WheelView whell_set_temperature;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_vacation_type);
		btn_vacation = (Button) this.findViewById(R.id.btn_vacation);
		btn_staycation = (Button) this.findViewById(R.id.btn_staycation);
		
		int type = getIntent().getIntExtra("type", 0);
		id = getIntent().getIntExtra("id", 0);
		if(type==0){
			btn_vacation.setBackgroundResource(R.drawable.button_bg);
			btn_staycation.setBackgroundResource(R.drawable.button_bg_pressed);
		}else{
			btn_vacation.setBackgroundResource(R.drawable.button_bg_pressed);
			btn_staycation.setBackgroundResource(R.drawable.button_bg);
		}
		
		// 添加按钮监听
		btn_vacation.setOnClickListener(this);
		btn_staycation.setOnClickListener(this);
	}

	// 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.btn_vacation:
			bundle.putInt("id", id);
			bundle.putInt("type",0);
			intent.putExtras(bundle);
			setResult(CommMsgID.DAILOG_VACATION_TYPE, intent); // 设置返回结果
			finish(); // 关闭子窗口，否则数据无法返回
			break;
		case R.id.btn_staycation:
			bundle.putInt("id", id);
			bundle.putInt("type",1);
			intent.putExtras(bundle);
			setResult(CommMsgID.DAILOG_VACATION_TYPE, intent); // 设置返回结果
			finish(); // 关闭子窗口，否则数据无法返回
			break;
		default:
			break;
		}
		finish();
	}
}
