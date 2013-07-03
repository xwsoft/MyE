package com.mye.gmobile.activity; 

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
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
public class DialogEditTempActivity extends BaseActivity implements OnClickListener {

	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;

	private MyListView mPullRefreshListView;
	HouseListAdapter houseListAdapter;
	WheelView whell_set_temp_heat;
	WheelView whell_set_temp_cool;
	
	private Button btn_cancel, btn_done;
	private LinearLayout layout;
	Next24DayItemPeriod sel_ItemPeriod;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_next24_edit_temp);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_done = (Button) this.findViewById(R.id.btn_done);
		
		sel_ItemPeriod=(Next24DayItemPeriod) getIntent().getSerializableExtra("sel_ItemPeriod");

		whell_set_temp_heat = (WheelView) this.findViewById(R.id.whell_set_temp_heat);
		whell_set_temp_heat.setViewAdapter(new NumericWheelAdapter(this, 55, 95, "%02d"));
		whell_set_temp_heat.setCyclic(true);
		whell_set_temp_heat.setCurrentItem(sel_ItemPeriod.getHeating()- Model.SETPOINT_MIN);
		
		whell_set_temp_cool = (WheelView) this.findViewById(R.id.whell_set_temp_cool);
		whell_set_temp_cool.setViewAdapter(new NumericWheelAdapter(this, 55, 95, "%02d"));
		whell_set_temp_cool.setCyclic(true);
		whell_set_temp_cool.setCurrentItem(sel_ItemPeriod.getCooling()- Model.SETPOINT_MIN);
		
		layout=(LinearLayout)findViewById(R.id.pop_layout);
		
		//添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
		//添加按钮监听
		btn_cancel.setOnClickListener(this);
		btn_done.setOnClickListener(this);
		
		if(GlobalModels.next24Schedule.isPeriodContains(sel_ItemPeriod, GlobalModels.next24Schedule.getiHour48())){
			whell_set_temp_heat.setEnabled(false);
			whell_set_temp_cool.setEnabled(false);
			btn_done.setEnabled(false);
			btn_done.setTextColor(Color.GRAY);
		}
	}
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			break;
		case R.id.btn_done:
			sel_ItemPeriod.setHeating(whell_set_temp_heat.getCurrentItem()+55);
			sel_ItemPeriod.setCooling(whell_set_temp_cool.getCurrentItem()+55);
			Intent intent = new Intent();  
			Bundle bundle = new Bundle();  
			bundle.putSerializable("sel_ItemPeriod", sel_ItemPeriod);  
			intent.putExtras(bundle);
			setResult(CommMsgID.NEXT24_TEMP_EDIT_DIALOG, intent);  //设置返回结果
		    finish(); //关闭子窗口，否则数据无法返回
			break;
		default:
			break;
		}
		finish();
	}
}
