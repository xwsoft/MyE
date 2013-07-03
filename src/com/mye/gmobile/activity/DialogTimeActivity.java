package com.mye.gmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mye.gmobile.R;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.view.wheel.OnWheelScrollListener;
import com.mye.gmobile.view.wheel.WheelView;
import com.mye.gmobile.view.wheel.adapters.ArrayWheelAdapter;
import com.mye.gmobile.view.wheel.adapters.NumericWheelAdapter;

/**
 * @author xwsoft
 * @version date：2013-4-25 上午10:26:20
 * 
 */
public class DialogTimeActivity extends BaseActivity implements OnClickListener {

	private Button btn_cancel,btn_done;
	private WheelView whell_set_hour;
	private WheelView whell_set_minute;
	String minTime = "00:00";
	String maxTime = "23:30";
	String[] arrMinTime = minTime.split(":");
	String[] arrMaxTime = maxTime.split(":");
	String[] unitsValues = new String[] {"00","30"};
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_time);
		_context = this;
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_done = (Button) this.findViewById(R.id.btn_done);
		
		String currTime = getIntent().getStringExtra("currTime");
		id = getIntent().getIntExtra("id", 0);
		whell_set_hour = (WheelView) findViewById(R.id.whell_set_hour);
		whell_set_minute = (WheelView) findViewById(R.id.whell_set_minute);
		if(getIntent().getStringExtra("minTime")!=null){
			minTime = getIntent().getStringExtra("minTime");
			arrMinTime = minTime.split(":");
		}
		if(getIntent().getStringExtra("maxTime")!=null){
			maxTime = getIntent().getStringExtra("maxTime");
			arrMaxTime = maxTime.split(":");
		}
		
		whell_set_hour.setViewAdapter(new NumericWheelAdapter(this, Integer.parseInt(arrMinTime[0]), Integer.parseInt(arrMaxTime[0]), "%02d"));
		whell_set_hour.setCyclic(false);
		
		if(currTime!=null && !currTime.equals("")){
			String[] arrTime = currTime.split(":");
			if(arrTime.length==2){
				whell_set_hour.setCurrentItem(Integer.parseInt(arrTime[0])-Integer.parseInt(arrMinTime[0]));
		       	int h =  (Integer.parseInt(arrMinTime[0])+whell_set_hour.getCurrentItem());
		    	if(whell_set_hour.getCurrentItem()==0){
		    		if(Integer.parseInt(arrMinTime[1])==30){
		    			unitsValues = new String[] {"30"};
		    		}else{
		    			unitsValues = new String[] {"00","30"};
		    		}
		    	}else if(h==Integer.parseInt(arrMaxTime[0])){
		    		if(Integer.parseInt(arrMaxTime[1])==0){
		    			unitsValues = new String[] {"00"};
		    		}else{
		    			unitsValues = new String[] {"00","30"};
		    		}
		    	}
			}
		    ArrayWheelAdapter<String> unitsAdapter =  new ArrayWheelAdapter<String>(this, unitsValues);
			whell_set_minute.setViewAdapter(unitsAdapter);
			whell_set_minute.setCyclic(false);
			
			if(arrTime[1].equals("00")){
				whell_set_minute.setCurrentItem(0);
			}else{
				if(unitsValues.length==2){
					whell_set_minute.setCurrentItem(1);
				}else{
					whell_set_minute.setCurrentItem(0);
				}
			}
		}
		
		whell_set_hour.addScrollingListener( new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
            }
            public void onScrollingFinished(WheelView wheel) {
            	unitsValues = new String[] {"00","30"};
            	int h =  (Integer.parseInt(arrMinTime[0])+whell_set_hour.getCurrentItem());
            	if(whell_set_hour.getCurrentItem()==0){
            		if(Integer.parseInt(arrMinTime[1])==30){
            			unitsValues = new String[] {"30"};
            		}else{
            			unitsValues = new String[] {"00","30"};
            		}
            	}else if(h==Integer.parseInt(arrMaxTime[0])){
            		if(Integer.parseInt(arrMaxTime[1])==0){
            			unitsValues = new String[] {"00"};
            		}else{
            			unitsValues = new String[] {"00","30"};
            		}
            	}
            	ArrayWheelAdapter<String> unitsAdapter =  new ArrayWheelAdapter<String>(_context, unitsValues);
        		whell_set_minute.setViewAdapter(unitsAdapter);
        		if(unitsValues.length==1){
        			whell_set_minute.setCurrentItem(0);
        		}
            }
        });
		
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
			String _minute = "";
			int m = Integer.parseInt(unitsValues[whell_set_minute.getCurrentItem()]);
			if(m==0){
				_minute = "00";
			}else{
				_minute = "30";
			}
			String _hour = "";
			int h =  (Integer.parseInt(arrMinTime[0])+whell_set_hour.getCurrentItem());
			if(h<10){
				_hour = "0"+h;
			}else{
				_hour = ""+h;
			}
			String strTime = _hour +":"+ _minute;
			bundle.putInt("id", id);
			bundle.putString("currTime", strTime);
			intent.putExtras(bundle);
			setResult(CommMsgID.DAILOG_TIME, intent); // 设置返回结果
			finish(); // 关闭子窗口，否则数据无法返回
			break;
		default:
			break;
		}
		finish();
	}
	

}
