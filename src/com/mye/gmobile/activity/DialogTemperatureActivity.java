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
public class DialogTemperatureActivity extends BaseActivity implements OnClickListener {

	private Button btn_cancel,btn_done;
	private WheelView whell_set_temperature;
	private int minTemperature=0;
	private int maxTemperature=0;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_temperature);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_done = (Button) this.findViewById(R.id.btn_done);
		
		int temperature = getIntent().getIntExtra("temperature", 70);
		minTemperature = getIntent().getIntExtra("minTemperature", 0);
		maxTemperature = getIntent().getIntExtra("maxTemperature", 0);
		if(minTemperature==0){
			minTemperature = Model.SETPOINT_MIN;
		}
		if(maxTemperature==0){
			maxTemperature = Model.SETPOINT_MAX;
		}
		id = getIntent().getIntExtra("id", 0);
		whell_set_temperature = (WheelView) findViewById(R.id.whell_set_temperature);
		whell_set_temperature.setViewAdapter(new NumericWheelAdapter(this, minTemperature, maxTemperature, "%02d"));
		whell_set_temperature.setCyclic(false);
		whell_set_temperature.setCurrentItem(temperature - minTemperature);
		
		// 添加按钮监听
		btn_cancel.setOnClickListener(this);
		btn_done.setOnClickListener(this);
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
		case R.id.btn_cancel:
			break;
		case R.id.btn_done:
			int temperature = whell_set_temperature.getCurrentItem() + minTemperature;
			bundle.putInt("id", id);
			bundle.putInt("temperature",temperature);
			intent.putExtras(bundle);
			setResult(CommMsgID.DAILOG_TEMPERTURE, intent); // 设置返回结果
			finish(); // 关闭子窗口，否则数据无法返回
			break;
		default:
			break;
		}
		finish();
	}
}
