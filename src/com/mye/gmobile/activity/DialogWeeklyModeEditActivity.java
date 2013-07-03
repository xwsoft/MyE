package com.mye.gmobile.activity; 

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mye.gmobile.R;
import com.mye.gmobile.common.component.MyListView;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.model.GlobalModels;
import com.mye.gmobile.model.Mode;
import com.mye.gmobile.model.Next24DayItemPeriod;
import com.mye.gmobile.view.BorderTextView;
import com.mye.gmobile.view.adapter.HouseListAdapter;
import com.mye.gmobile.view.wheel.WheelView;
import com.mye.gmobile.view.wheel.adapters.NumericWheelAdapter;

/** 
 * @author xwsoft 
 * @version date：2013-4-25 上午10:26:20 
 * 
 */
public class DialogWeeklyModeEditActivity extends BaseActivity implements OnClickListener {

	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;

	private MyListView mPullRefreshListView;
	HouseListAdapter houseListAdapter;
	WheelView whell_set_temp_heat;
	WheelView whell_set_temp_cool;
	
	private int sel_mode = -1;//当前选中mode标志 modeid
	private LinearLayout l_mode;
	
	private Button btn_cancel, btn_done;
	private LinearLayout layout;
	Next24DayItemPeriod sel_ItemPeriod;
	List<Mode>  listMode;
	String mode_id = "";
	Mode _mode = new Mode();
	
	String[] arr_mode_color = {"0xC12E08", "0xF93907", "0xFA6748", "0xFF8D7A",
	                     "0xB38710", "0xD6B32A", "0xF2CF45", "0xF9E655",
	                     "0x0065A2", "0x0082C0", "0x5598CB", "0x8DB9E5",
	                     "0x7E1676", "0xB028A5", "0xC468BD", "0xDD99D8"};
	                     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_context = this;
		setContentView(R.layout.dialog_weekly_mode_edit);
		
		listMode = GlobalModels.weekly_cache.getModeList();
		mode_id = getIntent().getStringExtra("mode_id");
		if(mode_id.equals("")){
			for(int j=0;j<arr_mode_color.length;j++){
				if(!isUseColor(arr_mode_color[j])){
					_mode.setColor(arr_mode_color[j]);
					break;
				}
			}
			_mode.setHeating(65);
			_mode.setCooling(80);
		}else{
			_mode = GlobalModels.weekly_cache.getModeByID(mode_id);
		}
	
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_done = (Button) this.findViewById(R.id.btn_done);
		l_mode = (LinearLayout)this.findViewById(R.id.l_mode);
		showMode();
		
		whell_set_temp_heat = (WheelView) this.findViewById(R.id.whell_set_temp_heat);
		whell_set_temp_heat.setViewAdapter(new NumericWheelAdapter(this,55, 95, "%02d"));
		whell_set_temp_heat.setCyclic(true);
		whell_set_temp_heat.setCurrentItem(_mode.getHeating()- Model.SETPOINT_MIN);
		
		whell_set_temp_cool = (WheelView) this.findViewById(R.id.whell_set_temp_cool);
		whell_set_temp_cool.setViewAdapter(new NumericWheelAdapter(this, 55, 95, "%02d"));
		whell_set_temp_cool.setCyclic(true);
		whell_set_temp_cool.setCurrentItem(_mode.getCooling()- Model.SETPOINT_MIN);
		
		layout=(LinearLayout)findViewById(R.id.pop_layout);
		
		//添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
		//添加按钮监听
		btn_cancel.setOnClickListener(this);
		btn_done.setOnClickListener(this);
		
//		if(GlobalModels.next24Schedule.isPeriodContains(sel_ItemPeriod, GlobalModels.next24Schedule.getiHour48())){
//			whell_set_temp_heat.setEnabled(false);
//			whell_set_temp_cool.setEnabled(false);
//			btn_done.setEnabled(false);
//			btn_done.setTextColor(Color.GRAY);
//		}
	}
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	private boolean isUseColor(String color){
		boolean iFlag = false;
		for(int i=0;i<listMode.size();i++){
			if(listMode.get(i).getColor().equalsIgnoreCase(color)){
				iFlag = true;
				break;
			}
		}
		return iFlag;
	}
	private void showMode(){
        l_mode.removeAllViews();
        for(int i=0;i<arr_mode_color.length;i++){
        	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);  
        	lp.gravity = Gravity.CENTER;
        	int btn_width = GlobalModels.dip2px(_context,50);
        	TextView modeTxt = null;
        	if(sel_mode==i){
        		modeTxt = new BorderTextView(this,null,Color.RED);
        	}else{
        		modeTxt = new TextView(this);
        	}
        	modeTxt.setTag(i);
        	modeTxt.setWidth(btn_width);
        	long mode_color = Long.valueOf(arr_mode_color[i].replace("0x", "FF"),16);
        	modeTxt.setBackgroundColor((int)mode_color);
        	modeTxt.setGravity(Gravity.CENTER);
        	modeTxt.setLayoutParams(lp);
        	modeTxt.setTextColor(Color.WHITE);
        	modeTxt.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					sel_mode = Integer.parseInt(v.getTag().toString());
					showMode();
				}
			});
        	l_mode.addView(modeTxt);
        }
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
