package com.mye.gmobile.activity; 

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mye.gmobile.R;
import com.mye.gmobile.common.communication.ReqParamMap;
import com.mye.gmobile.common.communication.ResParamMap;
import com.mye.gmobile.common.component.MyListView;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.constant.ErrorCode;
import com.mye.gmobile.common.constant.HttpURL;
import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.model.GlobalModels;
import com.mye.gmobile.model.House;
import com.mye.gmobile.model.Next24DayItemPeriod;
import com.mye.gmobile.util.CheckUtil;
import com.mye.gmobile.view.adapter.HouseListAdapter;
import com.mye.gmobile.view.wheel.WheelView;
import com.mye.gmobile.view.wheel.adapters.NumericWheelAdapter;

/** 
 * @author xwsoft 
 * @version date：2013-4-25 上午10:26:20 
 * 
 */
public class DialogEditHoldActivity extends BaseActivity implements OnClickListener {
	WheelView whell_set_temp;
	private Button btn_cancel, btn_ok, btn_run;
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_next24_edit_hold);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_ok = (Button) this.findViewById(R.id.btn_ok);
		btn_run = (Button) this.findViewById(R.id.btn_run);
		
		whell_set_temp = (WheelView) this.findViewById(R.id.whell_set_temp);
		whell_set_temp.setViewAdapter(new NumericWheelAdapter(this, 55, 95, "%02d"));
		whell_set_temp.setCyclic(true);
		whell_set_temp.setCurrentItem(GlobalModels.next24Schedule.getSetpoint() - Model.SETPOINT_MIN);
		
		layout=(LinearLayout)findViewById(R.id.pop_layout);
		
		//添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
		//添加按钮监听
		btn_cancel.setOnClickListener(this);
		btn_run.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	public void onClick(View v) {
		Intent intent = new Intent();  
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.btn_cancel:
			break;
		case R.id.btn_run:
			bundle.putString("type", "run"); 
			bundle.putInt("temp", whell_set_temp.getCurrentItem()+55);
			intent.putExtras(bundle);
			setResult(CommMsgID.NEXT24_TEMP_HOLD_DIALOG, intent);  //设置返回结果
		    finish(); //关闭子窗口，否则数据无法返回
			break;
		case R.id.btn_ok:
			bundle.putString("type", "ok"); 
			bundle.putInt("temp", whell_set_temp.getCurrentItem()+55);
			intent.putExtras(bundle);
			setResult(CommMsgID.NEXT24_TEMP_HOLD_DIALOG, intent);  //设置返回结果
		    finish(); //关闭子窗口，否则数据无法返回
			break;
		default:
			break;
		}
		finish();
	}
}
