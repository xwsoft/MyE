package com.mye.gmobile.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mye.gmobile.R;
import com.mye.gmobile.common.communication.ReqParamMap;
import com.mye.gmobile.common.communication.ResParamMap;
import com.mye.gmobile.common.component.CustomDialog;
import com.mye.gmobile.common.component.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.mye.gmobile.common.component.pulltorefresh.PullToRefreshListView;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.constant.HttpURL;
import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.model.DayItem;
import com.mye.gmobile.model.DayItemPeriod;
import com.mye.gmobile.model.GlobalModels;
import com.mye.gmobile.model.House;
import com.mye.gmobile.model.Mode;
import com.mye.gmobile.model.Next24DayItemPeriod;
import com.mye.gmobile.model.Next24Schedule;
import com.mye.gmobile.model.Thermostat;
import com.mye.gmobile.model.Vacation;
import com.mye.gmobile.model.WeekSchedule;
import com.mye.gmobile.util.CheckUtil;
import com.mye.gmobile.util.DataStack;
import com.mye.gmobile.view.BorderTextView;
import com.mye.gmobile.view.HourWatchScale;
import com.mye.gmobile.view.HourWatchScaleBg;
import com.mye.gmobile.view.HourWatchScaleLable;
import com.mye.gmobile.view.adapter.DialogSelectThermostatListAdapter;
import com.mye.gmobile.view.adapter.VacationListAdapter;
import com.mye.gmobile.view.wheel.WheelView;
import com.mye.gmobile.view.wheel.adapters.NumericWheelAdapter;

/**
 * @author xwsoft
 * @version date：2013-4-24 下午8:32:20
 * 
 */
public class ThermostatActivity extends BaseActivity {

	private ViewPager viewPager;// 页卡内容
	private ImageView imageView;// 动画图片
	private TextView textView1, textView2, textView3, textView4;
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View view1, view2, view3, view4;// 各个页卡
	private MyViewPagerAdapter myViewPagerAdapter;
	private boolean bView1InitFlag=false, bView2InitFlag=false, bView3InitFlag=false, bView4InitFlag=false;

	private ImageView img_switch_t;
	private ImageView img_refresh;
	private ImageView img_vacation_add;
	
	//T切换对话框
    public CustomDialog switch_T_dialog;
    public DialogSelectThermostatListAdapter select_thermostat_adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_context = this;
		setContentView(R.layout.activity_thermostat);
		InitImageView();
		InitTextView();
		InitViewPager();
		SendHttpRequest(0);
	}
	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		img_switch_t = (ImageView) findViewById(R.id.img_switch_t);
		img_refresh = (ImageView) findViewById(R.id.img_refresh);
		img_vacation_add = (ImageView)this.findViewById(R.id.img_vacation_add);
		img_vacation_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
				Bundle bundle = new Bundle();
				bundle.putInt("action", 0);
				bundle.putInt("position", -1);
				intent.setClass(_context,VacationEditActivity.class);  
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		img_switch_t.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CreateSwitchTDialog();

			}
		});
		img_refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SendHttpRequest(viewPager.getCurrentItem());
			}
		});
		views = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		view1 = inflater.inflate(R.layout.activity_dashboard, null);
		view2 = inflater.inflate(R.layout.activity_next24, null);
		view3 = inflater.inflate(R.layout.activity_weekly, null);
		view4 = inflater.inflate(R.layout.activity_vacation, null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		viewPager.setOffscreenPageLimit(4);
		myViewPagerAdapter = new MyViewPagerAdapter(views);
		viewPager.setAdapter(myViewPagerAdapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	
	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		textView1 = (TextView) findViewById(R.id.text1);
		textView2 = (TextView) findViewById(R.id.text2);
		textView3 = (TextView) findViewById(R.id.text3);
		textView4 = (TextView) findViewById(R.id.text4);

		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));
		textView4.setOnClickListener(new MyOnClickListener(3));
	}

	/**
	 * 2 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据 3
	 */
	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.viewpager_title_sel).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}
	
	
	//选择T
	public void CreateSwitchTDialog() {
        switch_T_dialog = new CustomDialog(_context,R.style.dialog,R.layout.dialog_select_thermostat);//创建Dialog并设置样式主题
        switch_T_dialog.show();
        switch_T_dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
        select_thermostat_adapter = new DialogSelectThermostatListAdapter(_context);
        select_thermostat_adapter.notifyDataSetChanged();
        select_thermostat_adapter.addItem(GlobalModels.houseList.getCurrentHouse().getThermostatList());
        ListView listView = (ListView)switch_T_dialog.findViewById(R.id.list_switch_thermostat);
		listView.setAdapter(select_thermostat_adapter);
		listView.setOnItemClickListener(switch_T_click);
    }
    public OnItemClickListener switch_T_click = new OnItemClickListener(){	  
    	  @Override
    	  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    		  TextView t_id = (TextView)v.findViewById(R.id.thermostat_id);
    		  House house = GlobalModels.houseList.getCurrentHouse();
    		  Thermostat sel_t = house.findThermostatById(t_id.getText().toString());
    		  //如果当前选中的T正常链接
    		  if(sel_t.getThermostatStatus()==0){
    			  GlobalModels.houseList.getCurrentHouse().setDefualtThermostat(t_id.getText().toString());
    			  select_thermostat_adapter.notifyDataSetChanged();
    			  SendHttpRequest(viewPager.getCurrentItem());
    			  DataStack.updateStringData(_context, "UserInfo",GlobalModels.getCurrHouseId()+"_DefualtThermostat",t_id.getText().toString());
    			  switch_T_dialog.dismiss();
    		  }else{
    			  Toast.makeText(_context, sel_t.getT_name()+" is off-line", Toast.LENGTH_SHORT).show();
    		  }
    	  }
     };
	
	//Next24用到的变量
	private RelativeLayout rl_next24_canvas;
	/**
	 * Next24需要显示的时间段，并且是已过滤好的只包含24小时的。
	 */
	private List<Next24DayItemPeriod> nextDayItemPeriod;
	/**
	 * 小时表盘
	 */
	private HourWatchScale[] hours = new HourWatchScale[48];
	/**
	 * 表盘背景
	 */
	private HourWatchScaleBg[] hours_bg = new HourWatchScaleBg[48];
	/**
	 * 存放每个时间段要显示的温度标签
	 */
	private HourWatchScaleLable[] hours_labs = null;
	
	private int selectHour = -1;
	
	private Button btn_next24_apply;
	private Button btn_next24_reset;
	private Button btn_next24_use_weekly;
	
	private void showNext24Btn(boolean isDisabled){
		if(isDisabled){
			btn_next24_apply.setBackgroundResource(R.drawable.apply2x);
			btn_next24_reset.setBackgroundResource(R.drawable.reset2x);
			btn_next24_apply.setEnabled(true);
			btn_next24_reset.setEnabled(true);
		}else{
			btn_next24_apply.setBackgroundResource(R.drawable.apply_disabled2x);
			btn_next24_reset.setBackgroundResource(R.drawable.reset_disabled2x);
			btn_next24_apply.setEnabled(false);
			btn_next24_reset.setEnabled(false);
		}

	}
	

	/**
	 * 操作Next24表盘时更新表盘显示
	 * @param sel_period
	 * @param mv_period
	 */
	public void updateNext24DayItemPeriod(int sel_period,int mv_period){
		Next24DayItemPeriod sel_ItemPeriod = GlobalModels.next24Schedule.getNext24DayItemPeriodByHour(nextDayItemPeriod, sel_period);
		Next24DayItemPeriod mv_ItemPeriod = GlobalModels.next24Schedule.getNext24DayItemPeriodByHour(nextDayItemPeriod, mv_period);
		int iHour48 = GlobalModels.next24Schedule.getiHour48();
		if(sel_ItemPeriod!=null && mv_ItemPeriod!=null && mv_ItemPeriod.getHold().equalsIgnoreCase("None") && sel_ItemPeriod.getHold().equalsIgnoreCase("None") && (!sel_ItemPeriod.getColor().equals(mv_ItemPeriod.getColor()))){
			showNext24Btn(true);
			Next24DayItemPeriod beforePeriod = null;
			Next24DayItemPeriod selectPeriod = null;
			Next24DayItemPeriod afterPeriod = null;
			int ibefore = 0;
			int iselect = 0;
			int iafter = 0;
			List<Next24DayItemPeriod> nextDayItemPeriod_tmp = new ArrayList<Next24DayItemPeriod>();
			for(int i=0;i<nextDayItemPeriod.size();i++){
				if(isPeriodContains(nextDayItemPeriod.get(i),sel_period)){//找出当前选中段
					if(i==0){
						ibefore = nextDayItemPeriod.size()-1;
						beforePeriod = nextDayItemPeriod.get(nextDayItemPeriod.size()-1);
					}else{
						ibefore = i-1;
						beforePeriod = nextDayItemPeriod.get(i-1);
					}
					selectPeriod = nextDayItemPeriod.get(i);
					iselect = i;
					if(i==nextDayItemPeriod.size()-1){
						iafter = 0;
						afterPeriod = nextDayItemPeriod.get(0);
					}else{
						iafter = i+1;
						afterPeriod = nextDayItemPeriod.get(i+1);
					}
					if(isPeriodContains(selectPeriod,mv_period)){
						break;
					}
					//逆时针滑动选择
					if(isPeriodContains(beforePeriod,mv_period)){
						boolean bflag = false;
						if(isPeriodContains(beforePeriod,iHour48)){
							int k=0;
							int apart = apartHour(beforePeriod);
							while(k<apart){
								if((iHour48-k)%47 == mv_period ){
									bflag = true;
									break;
								}
								k++;
							}
						}
						if(bflag){
							beforePeriod.setEtid((iHour48+1)%47);
							selectPeriod.setStid((iHour48+1)%47);
						}else{
							if(beforePeriod.getStid()==mv_period){
								beforePeriod.setEtid((mv_period+1)%47);
								selectPeriod.setStid((mv_period+1)%47);
							}else{
								beforePeriod.setEtid(mv_period);
								selectPeriod.setStid(mv_period);
							}
						}
					}
					//如果是顺时针滑动选择
					if(isPeriodContains(afterPeriod,mv_period)){
						boolean aflag = false;
						if(isPeriodContains(afterPeriod,iHour48)){
							int k=0;
							int apart = apartHour(afterPeriod);
							while(k<apart){
								if((iHour48+k)%47 == mv_period ){
									aflag = true;
									break;
								}
								k++;
							}
						}
						if(aflag){
							afterPeriod.setStid(iHour48);
							selectPeriod.setEtid(iHour48);
						}else{
							afterPeriod.setStid(mv_period);
							selectPeriod.setEtid(mv_period);
						}
					}	
				}
	    	}
	    	
			for(int i=0;i<nextDayItemPeriod.size();i++){
				if(i==ibefore){
					nextDayItemPeriod_tmp.add(beforePeriod);
				}else if(i==iselect){
					nextDayItemPeriod_tmp.add(selectPeriod); 
				}else if(i==iafter && (ibefore==iafter)){
					nextDayItemPeriod_tmp.add(afterPeriod);
				}else{
					nextDayItemPeriod_tmp.add(nextDayItemPeriod.get(i));
				}
			}
			nextDayItemPeriod = nextDayItemPeriod_tmp;
		}	
		refreshNext24Watch();
	}
	
	/**
	 * 计算起止时间相隔小时（半小时制）
	 * @param period
	 * @return
	 */
	private int apartHour(Next24DayItemPeriod period){
		int result = 0;
		if(period.getStid()>period.getEtid()){
			result = 47-(period.getStid()-period.getEtid());
		}else{
			result = period.getEtid()-period.getStid();
		}
		return result;
	}
	
	/**
	 * 判断传入的时间段(0-47)是否在period对象中
	 * @param period
	 * @param selHour
	 * @return
	 */
	public boolean isPeriodContains(Next24DayItemPeriod period,int selHour){
		boolean flag = false;
		if(period.getStid()>period.getEtid()){
			if(selHour>=period.getStid() || selHour<period.getEtid()){
				flag = true;
			}
		}else{
			if(selHour>=period.getStid() && selHour<period.getEtid()){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 显示表盘各时段的温度标签
	 */
	private void showWatchLabs(int isShow){
        if(hours_labs!=null){
	        for(int i=0;i<hours_labs.length;i++){
	        	hours_labs[i].setVisibility(isShow);
	        }
        }
	}
	
	/**
	 * 重新显示Next24表盘，及标签
	 */
	private void refreshNext24Watch(){
		//显示表盘中的每个时段
        for(int i=0;i<48;i++){      	
        	Next24DayItemPeriod period = GlobalModels.next24Schedule.getNext24DayItemPeriodByHour(nextDayItemPeriod, i);
        	//Log.i("Next24DayItemPeriod:"+i,period.getStid()+"***"+period.getEtid());
        	if(period!=null){
        		rl_next24_canvas.removeView(hours[i]);
	        	long color_value = Long.valueOf((period.getColor()).replace("0x", "FF"),16);
	        	if(!period.getHold().equalsIgnoreCase("None")){//如果当前时段为Hold状态则固定颜色
	        		color_value = Long.valueOf(("0xDADADA").replace("0x", "FF"),16); 
	        	}
	        	hours[i] = new HourWatchScale(this, i,(int) color_value,GlobalModels.next24Schedule.getiHour48());
	        	hours[i].resize(rl_next24_canvas.getLayoutParams().width,rl_next24_canvas.getLayoutParams().height);
	        	rl_next24_canvas.addView(hours[i]);
        	}
        }
        //显示每个时段温度标签
        if(hours_labs!=null){
	        for(int i=0;i<hours_labs.length;i++){
	        	rl_next24_canvas.removeView(hours_labs[i]);
	        }
        }
        hours_labs = new HourWatchScaleLable[nextDayItemPeriod.size()];
        for(int i=0;i<nextDayItemPeriod.size();i++){
        	Next24DayItemPeriod tmpPeriod = nextDayItemPeriod.get(i);
        	int iPoint = 0;
        	if(Math.abs(tmpPeriod.getEtid()-tmpPeriod.getStid())!=1){
	        	if(tmpPeriod.getStid()>tmpPeriod.getEtid()){
	        		iPoint = (tmpPeriod.getStid()+(47 - (tmpPeriod.getStid()-tmpPeriod.getEtid()))/2)%47;
	        	}else{
	        		iPoint = (tmpPeriod.getStid()+(tmpPeriod.getEtid()-tmpPeriod.getStid())/2);
	        	}
        	}else{
        		iPoint = tmpPeriod.getStid();
        	}
        	hours_labs[i] = new HourWatchScaleLable(this, iPoint,(int)Color.RED,GlobalModels.next24Schedule.getiHour48(),tmpPeriod.getCooling(),tmpPeriod.getHeating());
        	hours_labs[i].resize(rl_next24_canvas.getLayoutParams().width,rl_next24_canvas.getLayoutParams().height);
        	hours_labs[i].setVisibility(View.INVISIBLE);
        	hours_labs[i].bringToFront();
        	rl_next24_canvas.addView(hours_labs[i]);
        }
    }

	OnClickListener btn_next24_apply_ClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Next24Schedule _next24Schedule = GlobalModels.next24Schedule.getNext24TransferToJson(nextDayItemPeriod);
			//_next24Schedule.transferToJson();
			startProgressDialog();
			ReqParamMap reqParaMap = new ReqParamMap();
			reqParaMap.put("userId", GlobalModels.userid);
			reqParaMap.put("houseId", GlobalModels.getCurrHouseId());
			reqParaMap.put("tId", GlobalModels.houseList.getCurrentHouse().getDefualtThermostat());
			reqParaMap.put("schedule", _next24Schedule.transferToJson());
			GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.NEXT24_SAVE, myHandler, CommMsgID.NEXT24_SAVE);
		}
	};
	
	OnClickListener btn_next24_reset_ClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			refreshNext24View();
		}
	};
	
	OnClickListener btn_next24_use_weekly_ClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			startProgressDialog();
			ReqParamMap reqParaMap = new ReqParamMap();
			reqParaMap.put("userId", GlobalModels.userid);
			reqParaMap.put("houseId", GlobalModels.getCurrHouseId());
			reqParaMap.put("tId", GlobalModels.houseList.getCurrentHouse().getDefualtThermostat());
			GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.NEXT24_USE_WEEKLY, myHandler, CommMsgID.NEXT24_USE_WEEKLY);
		}
	};

	/**
	 * 刷新显示Next24界面
	 */
	public void refreshNext24View(){
		rl_next24_canvas = (RelativeLayout)this.findViewById(R.id.rl_next24_canvas);
    	for(int i=0;i<hours.length;i++){
    		rl_next24_canvas.removeView(hours_bg[i]);
    		rl_next24_canvas.removeView(hours[i]);
    	}
		nextDayItemPeriod = GlobalModels.next24Schedule.getCurrNext24();
		int iHour48 = GlobalModels.next24Schedule.getiHour48();
		Log.i("iHour48",""+iHour48);
        long bg_color = Long.valueOf(("0xDADADA").replace("0x", "FF"),16); 
        for(int i=0;i<48;i++){
        	hours_bg[i] = new HourWatchScaleBg(this, i,(int) bg_color,iHour48);
        	hours_bg[i].resize(rl_next24_canvas.getLayoutParams().width,rl_next24_canvas.getLayoutParams().height);
        	rl_next24_canvas.addView(hours_bg[i]);
        }
        refreshNext24Watch();
        btn_next24_apply = (Button)rl_next24_canvas.findViewById(R.id.btn_apply);
        btn_next24_reset = (Button)rl_next24_canvas.findViewById(R.id.btn_reset);
        btn_next24_use_weekly = (Button)view2.findViewById(R.id.btn_use_weekly);
        showNext24Btn(false);
        btn_next24_apply.bringToFront();
        btn_next24_reset.bringToFront();
        
        btn_next24_apply.setOnClickListener(btn_next24_apply_ClickListener);
        btn_next24_reset.setOnClickListener(btn_next24_reset_ClickListener);
        btn_next24_use_weekly.setOnClickListener(btn_next24_use_weekly_ClickListener);

        rl_next24_canvas.setOnTouchListener(new OnTouchListener() {
        	private int mLastMotionX, mLastMotionY;
        	//是否移动了
        	private boolean isMoved;
        	//移动的阈值
        	private static final int TOUCH_SLOP = 20;
			private long first = 0;
			private long second = 0;
			public boolean onTouch(View arg0, MotionEvent arg1) {
				int x = (int) arg1.getX();
				int y = (int) arg1.getY();
				
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					//长按事件代码
					mLastMotionX = x;
					mLastMotionY = y;
					isMoved = false;
					myCount = new CountDown(100, 2000);
					myCount.start();
					
					view2.getParent().requestDisallowInterceptTouchEvent(true);
					for (int i = 0; i < hours.length; i++) {
						if (hours[i].InArea(x, y)) {
							selectHour = i;
							Log.i("ACTION_DOWN",""+i);
							if(hours_labs[0].getVisibility()==View.VISIBLE){
								showWatchLabs(View.INVISIBLE);
							}
							if(first==0){
								first = System.currentTimeMillis();
							}else{
								second = System.currentTimeMillis();
								if (second - first <= 500) {
									//showWatchLabs(View.VISIBLE);
									Next24DayItemPeriod sel_ItemPeriod = GlobalModels.next24Schedule.getNext24DayItemPeriodByHour(nextDayItemPeriod, i);
									Intent intent = new Intent();  
									Bundle bundle = new Bundle();  
									bundle.putSerializable("sel_ItemPeriod", sel_ItemPeriod);
									if(sel_ItemPeriod.getHold().equalsIgnoreCase("None")){
										intent.setClass(ThermostatActivity.this,DialogEditTempActivity.class);  
										intent.putExtras(bundle);
										startActivityForResult(intent, CommMsgID.NEXT24_TEMP_EDIT_DIALOG);
									}else{
										intent.setClass(ThermostatActivity.this,DialogEditHoldActivity.class);  
										intent.putExtras(bundle);
										startActivityForResult(intent, CommMsgID.NEXT24_TEMP_HOLD_DIALOG);
									}
									first = 0;
									second = 0;
								}else{
									first = System.currentTimeMillis();
								}
							}
							break;
						}
					}
					return true;
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					//释放了  
					myCount.cancel();
					view2.getParent().requestDisallowInterceptTouchEvent(false);
					selectHour = -1;
					return false;
				} else if (arg1.getAction() == MotionEvent.ACTION_MOVE) {
					//长按事件
					if(!isMoved){
						if(Math.abs(mLastMotionX-x) > TOUCH_SLOP || Math.abs(mLastMotionY-y) > TOUCH_SLOP) {
							//移动超过阈值，则表示移动了
							isMoved = true;
							myCount.cancel();
						}
					}
					
					if(selectHour!=-1){
						for (int i = 0; i < hours.length; i++) {
							if (hours[i].InArea(x, y)) {
								Log.i("Action","MotionEvent.ACTION_MOVE"+i);
								if(selectHour!=i){
									updateNext24DayItemPeriod(selectHour,i);
								}
								break;
							}
						}
						return true;
					}
				}
				return true;
			}
        });
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data==null){
			return;
		}
		startProgressDialog();
		Bundle bundle = data.getExtras();
		ReqParamMap reqParaMap = new ReqParamMap();
		switch (resultCode) { 
		case CommMsgID.NEXT24_TEMP_EDIT_DIALOG:
			Next24DayItemPeriod sel_ItemPeriod = (Next24DayItemPeriod)bundle.getSerializable("sel_ItemPeriod");
			for(int i=0;i<nextDayItemPeriod.size();i++){
				if(nextDayItemPeriod.get(i).getStid()==sel_ItemPeriod.getStid() && nextDayItemPeriod.get(i).getEtid()==sel_ItemPeriod.getEtid()){
					nextDayItemPeriod.get(i).setHeating(sel_ItemPeriod.getHeating());
					nextDayItemPeriod.get(i).setCooling(sel_ItemPeriod.getCooling());
					//nextDayItemPeriod.set(i, sel_ItemPeriod);
					refreshNext24Watch();
					showNext24Btn(true);
					stopProgressDialog();
					break;
				}
			}
			break;
		case CommMsgID.NEXT24_TEMP_HOLD_DIALOG:
			String type = bundle.getString("type");
			int temp = bundle.getInt("temp");
			reqParaMap.put("userId", GlobalModels.userid);
			reqParaMap.put("houseId", GlobalModels.getCurrHouseId());
			reqParaMap.put("tId", GlobalModels.houseList.getCurrentHouse().getDefualtThermostat());
			if(type.equals("ok")){
				reqParaMap.put("setpoint", temp+"");
				reqParaMap.put("hold", GlobalModels.next24Schedule.getHold()+"");		
			}else if(type.equals("run")){
				reqParaMap.put("setpoint", temp+"");
				reqParaMap.put("hold", "0");
			}
			GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.NEXT24_HOLD_SAVE, myHandler, CommMsgID.NEXT24_HOLD_SAVE);
			break;
		case CommMsgID.VACATION_MENU:
			int iAction = bundle.getInt("action");
			int position = bundle.getInt("position");
			String vacation_name = bundle.getString("vacation_name");
			if(iAction==1){//txt_delete
				showToast(iAction+"		"+vacation_name);
			}else if(iAction==2){//edit
				showToast(iAction+"		"+vacation_name);
				Intent intent = new Intent();  
				bundle.putInt("position", position);
				intent.setClass(_context,VacationEditActivity.class);
				intent.putExtras(bundle);
				//startActivityForResult(intent, CommMsgID.VACATION_MENU);
				startActivity(intent);
			}
			reqParaMap.put("userId", GlobalModels.userid);
			reqParaMap.put("houseId", GlobalModels.getCurrHouseId());
			reqParaMap.put("tId", GlobalModels.houseList.getCurrentHouse().getDefualtThermostat());
			//GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.NEXT24_HOLD_SAVE, myHandler, CommMsgID.NEXT24_HOLD_SAVE);
			stopProgressDialog();
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	//dashboard用到的界面变量
	public WheelView whell_set_temp;
	public ImageView img_weather;
	public TextView txt_forcast_temp;
	public TextView txt_weatherTemp;
	public TextView txt_humidity;
	public TextView txt_temp;
	public TextView txt_curr_program;
	public ImageView img_controlMode;
	public ImageView img_fan;
	public ImageView img_hold;
	private AnimationDrawable anim_01;
	private AnimationDrawable anim_02;
	public void refreshDashboardView(){
		img_weather = (ImageView) view1.findViewById(R.id.img_weather);
		txt_forcast_temp = (TextView) view1.findViewById(R.id.txt_forcast_temp);
		txt_weatherTemp = (TextView) view1.findViewById(R.id.txt_weatherTemp);
		txt_humidity = (TextView) view1.findViewById(R.id.txt_humidity);
		txt_temp = (TextView) view1.findViewById(R.id.txt_temp);
		img_controlMode = (ImageView) view1.findViewById(R.id.img_controlMode);
		img_fan = (ImageView) view1.findViewById(R.id.img_fan);
		img_hold = (ImageView) view1.findViewById(R.id.img_hold);
		txt_curr_program = (TextView) view1.findViewById(R.id.txt_curr_program);
		//当前设置温度
		whell_set_temp = (WheelView) view1.findViewById(R.id.whell_set_temp);
		whell_set_temp.setViewAdapter(new NumericWheelAdapter(this, 55, 95, "%02d"));
		whell_set_temp.setCyclic(true);
		whell_set_temp.setCurrentItem(GlobalModels.dashboard.getSetPoint()- Model.SETPOINT_MIN);
		Resources res = getResources();
		int res_id = res.getIdentifier(GlobalModels.dashboard.getDayForecast().getWeather().toLowerCase(), "drawable", "com.mye.gmobile");
		char symbol=176;
		String temp_symbol = String.valueOf(symbol);//温度单位符号
		img_weather.setImageResource(res_id);//天气图片
		txt_forcast_temp.setText((int)(GlobalModels.dashboard.getDayForecast().getLowTemp())+"~"+(int)(GlobalModels.dashboard.getDayForecast().getHighTemp())+temp_symbol + "F");
		txt_weatherTemp.setText((int)(GlobalModels.dashboard.getDayForecast().getCurrentTemp())+temp_symbol + "F");
		txt_humidity.setText("Humidity "+GlobalModels.dashboard.getDayForecast().getHumidity()+"%");
		txt_temp.setText((int)(CheckUtil.round(GlobalModels.dashboard.getTemp(), 0))+temp_symbol + "F");
		refreshControlMode();
		refreshFan();
		txt_curr_program.setText(GlobalModels.dashboard.getCurrentProgram());
		myViewPagerAdapter.notifyDataSetChanged();
	}
	
	public void refreshControlMode(){
		if(GlobalModels.dashboard.getControlMode() == 1){
			if(GlobalModels.dashboard.getRealControlMode().equals("Heating")){
				img_controlMode.setBackgroundResource(R.anim.heating);
		    	anim_01 = (AnimationDrawable)img_controlMode.getBackground();
		        anim_01.start();
			}else if(GlobalModels.dashboard.getRealControlMode().equals("Off")){
				img_controlMode.setBackgroundResource(R.drawable.heating_01);
			} 
	    }else if(GlobalModels.dashboard.getControlMode() == 2){
	    	if(GlobalModels.dashboard.getRealControlMode().equals("Cooling")){
		    	img_controlMode.setBackgroundResource(R.anim.cooling);
		    	anim_01 = (AnimationDrawable)img_controlMode.getBackground();
		        anim_01.start();
	    	}else if(GlobalModels.dashboard.getRealControlMode().equals("Off")){
				img_controlMode.setBackgroundResource(R.drawable.cooling_01);
			} 
	    }else if(GlobalModels.dashboard.getControlMode() == 3){
	    	if(GlobalModels.dashboard.getIsHeatCool() == 1){
	    		if(GlobalModels.dashboard.getRealControlMode().equals("Heating")){
	    			img_controlMode.setBackgroundResource(R.anim.auto_heating);
	    			anim_01 = (AnimationDrawable)img_controlMode.getBackground();
	    	        anim_01.start();
	    		}else if(GlobalModels.dashboard.getRealControlMode().equals("Off")){
	    			img_controlMode.setBackgroundResource(R.drawable.auto_heating_01);
	    		} 
	    	}else if(GlobalModels.dashboard.getIsHeatCool() == 2){
	    		if(GlobalModels.dashboard.getRealControlMode().equals("Cooling")){
		    		img_controlMode.setBackgroundResource(R.anim.auto_cooling);
		    		anim_01 = (AnimationDrawable)img_controlMode.getBackground();
			        anim_01.start();
	    		}else if(GlobalModels.dashboard.getRealControlMode().equals("Off")){
	    			img_controlMode.setBackgroundResource(R.drawable.auto_cooling_01);
	    		} 
	    	}
	    }else if(GlobalModels.dashboard.getControlMode() == 4){
	    	img_controlMode.setBackgroundResource(R.anim.emgh);
	    	anim_01 = (AnimationDrawable)img_controlMode.getBackground();
	        anim_01.start();
	    }else if(GlobalModels.dashboard.getControlMode() == 5){
	    	img_controlMode.setBackgroundResource(R.drawable.off);
	    }
	}
	
	public void refreshFan(){
		if(GlobalModels.dashboard.getFanControl() == 0){
	    	img_fan.setBackgroundResource(R.anim.auto);
	    	anim_02 = (AnimationDrawable)img_fan.getBackground();
			if(GlobalModels.dashboard.getFanStatus().equals("On")){
	        	anim_02.start();
	        }else if(GlobalModels.dashboard.getFanStatus().equals("Off")){
	        	anim_02.stop();
	        }
	    }else if(GlobalModels.dashboard.getFanControl() == 1){
	    	img_fan.setBackgroundResource(R.anim.on);
	    	anim_02 = (AnimationDrawable)img_fan.getBackground();
	    	anim_02.start();
	    }
	}
	//End Dashboard
	
	//Weekly 相关代码
	private DayItem target;
	private Button btn_weekly_apply;
	private Button btn_weekly_reset;
	private RelativeLayout rl_weekly_canvas;
	private int currDay;//当前星期
	private int selectHourWeekly = -1;
	//选择某时段的边界（起始时间或结束时间）
	public int borderSelect = -1;
	private String sel_mode = "";//当前选中mode标志 modeid
	/**
	 * 小时表盘
	 */
	private HourWatchScale[] hours_weekly = new HourWatchScale[48];
	/**
	 * 表盘背景
	 */
	private HourWatchScaleBg[] hours_weekly_bg = new HourWatchScaleBg[48];
	/**
	 * 存放每个时间段要显示的温度标签
	 */
	private HourWatchScaleLable[] hours_weekly_labs = null;
	private LinearLayout l_btn_weekly;
	private LinearLayout l_mode;
	
	private Button btn_weekly_mode_add;
	private Button btn_weekly_mode_edit;
	/**
	 * 刷新显示Weekly界面
	 */
	public void refreshWeeklyView(){
		DayItem currDayItem = GlobalModels.weekly_cache.getDayItemByIndex(currDay);
		
		btn_weekly_mode_add = (Button)this.findViewById(R.id.btn_weekly_mode_add);
		btn_weekly_mode_edit = (Button)this.findViewById(R.id.btn_weekly_mode_edit);
		
		btn_weekly_mode_add.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
				Bundle bundle = new Bundle();  
				bundle.putSerializable("mode_id", "");
				intent.setClass(ThermostatActivity.this,DialogWeeklyModeEditActivity.class);  
				intent.putExtras(bundle);
				startActivityForResult(intent, CommMsgID.WEEKLY_MODE_ADD_DIALOG);
			}
		});
		btn_weekly_mode_edit.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if(sel_mode.equals("")){
					showToast("Please select mode.");
				}else{
					Intent intent = new Intent();  
					Bundle bundle = new Bundle();  
					bundle.putSerializable("mode_id", sel_mode);
					intent.setClass(ThermostatActivity.this,DialogWeeklyModeEditActivity.class);  
					intent.putExtras(bundle);
					startActivityForResult(intent, CommMsgID.WEEKLY_MODE_ADD_DIALOG);
				}
			}
		});
		
        l_btn_weekly = (LinearLayout)this.findViewById(R.id.l_btn_weekly);
        l_mode = (LinearLayout)this.findViewById(R.id.l_mode);
        setWeeklyBtn();
        showMode();
        
		rl_weekly_canvas = (RelativeLayout)this.findViewById(R.id.rl_weekly_canvas);
    	for(int i=0;i<hours_weekly.length;i++){
    		rl_weekly_canvas.removeView(hours_weekly_bg[i]);
    		rl_weekly_canvas.removeView(hours_weekly[i]);
    	}
        long bg_color = Long.valueOf(("0xDADADA").replace("0x", "FF"),16); 
        for(int i=0;i<48;i++){
        	hours_weekly_bg[i] = new HourWatchScaleBg(this, i,(int) bg_color,0);
        	hours_weekly_bg[i].resize(rl_weekly_canvas.getLayoutParams().width,rl_weekly_canvas.getLayoutParams().height);
        	rl_weekly_canvas.addView(hours_weekly_bg[i]);
        }
        refreshWeeklyWatch();
        
        btn_weekly_apply = (Button)rl_weekly_canvas.findViewById(R.id.btn_apply);
        btn_weekly_reset = (Button)rl_weekly_canvas.findViewById(R.id.btn_reset);
        btn_weekly_apply.bringToFront();
        btn_weekly_reset.bringToFront();
        showWeeklyBtn(false);
        
        btn_weekly_apply.setOnClickListener(btn_next24_apply_ClickListener);
        btn_weekly_reset.setOnClickListener(btn_next24_reset_ClickListener);

        rl_weekly_canvas.setOnTouchListener(new OnTouchListener() {
        	private int mLastMotionX, mLastMotionY;
        	//是否移动了
        	private boolean isMoved;
        	//移动的阈值
        	private static final int TOUCH_SLOP = 20;
			private long first = 0;
			private long second = 0;
			public boolean onTouch(View arg0, MotionEvent arg1) {
				int x = (int) arg1.getX();
				int y = (int) arg1.getY();

				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					//长按事件代码
					mLastMotionX = x;
					mLastMotionY = y;
					isMoved = false;
					myCountWeekly = new CountDownWeekly(100, 2000);
					myCountWeekly.start();
					
					view3.getParent().requestDisallowInterceptTouchEvent(true);
					for (int i = 0; i < hours_weekly.length; i++) {
						if (hours_weekly[i].InArea(x, y)) {
							selectHourWeekly = i;
							Log.i("ACTION_DOWN",""+i);
							if(!sel_mode.equals("")){//如果选中mode则认为是添加
								GlobalModels.weekly_cache.getDayItemByIndex(currDay).insertStart(sel_mode,selectHourWeekly);
								refreshWeeklyWatch();
							}
							if(GlobalModels.weekly_cache.getDayItemByIndex(currDay).isBorderHour(i)){
								borderSelect = i;
							}
							if(hours_weekly_labs[0].getVisibility()==View.VISIBLE){
								showWeeklyWatchLabs(View.INVISIBLE);
							}
							if(first==0){
								first = System.currentTimeMillis();
							}else{
								second = System.currentTimeMillis();
								if (second - first <= 500) {
									DayItemPeriod sel_ItemPeriod =  GlobalModels.weekly_cache.getDayItemByIndex(currDay).getDayItemPeriodByContainsTime(i);
									Intent intent = new Intent();  
									Bundle bundle = new Bundle();  
									bundle.putSerializable("sel_ItemPeriod", sel_ItemPeriod);
									intent.setClass(ThermostatActivity.this,DialogEditHoldActivity.class);  
									intent.putExtras(bundle);
									startActivityForResult(intent, CommMsgID.NEXT24_TEMP_HOLD_DIALOG);
									first = 0;
									second = 0;
								}else{
									first = System.currentTimeMillis();
								}
							}
							break;
						}
					}
					return true;
				} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
					//释放了  
					myCountWeekly.cancel();
					view3.getParent().requestDisallowInterceptTouchEvent(false);
					selectHourWeekly = -1;
					borderSelect = -1;
					sel_mode = "";
					showMode();
					return false;
				} else if (arg1.getAction() == MotionEvent.ACTION_MOVE) {
					//长按事件
					if(!isMoved){
						if(Math.abs(mLastMotionX-x) > TOUCH_SLOP || Math.abs(mLastMotionY-y) > TOUCH_SLOP) {
							//移动超过阈值，则表示移动了
							isMoved = true;
							myCountWeekly.cancel();
						}
					}
					
					if(selectHourWeekly!=-1){
						for (int i = 0; i < hours_weekly.length; i++) {
							if (hours_weekly[i].InArea(x, y)) {
								Log.i("Action","MotionEvent.ACTION_MOVE"+i);

								if(selectHourWeekly!=i){
									if(!sel_mode.equals("")){//如果选中mode则认为是添加
										GlobalModels.weekly_cache.getDayItemByIndex(currDay).insertUpdate(i);
										refreshWeeklyWatch();
									}else{
										if(borderSelect==-1){//当前选中段必须为某时段的边界
											if(GlobalModels.weekly_cache.getDayItemByIndex(currDay).isBorderHour(i)){
												borderSelect = i;
											}
											Log.i("borderSelect",""+borderSelect);
										}else{
											Log.i("borderSelect",""+borderSelect);
											updateWeeklyDayItemPeriod(borderSelect,i);
											refreshWeeklyWatch();
										}					
									}
									
								}
								break;
							}
						}
						return true;
					}
				}
				return true;
			}
        });
	}
	
	
	/**
	 * 显示weekly mode
	 */
	private void showMode(){
        l_mode.removeAllViews();
        List<Mode>  listMode = GlobalModels.weekly_cache.getModeList();
        for(int i=0;i<listMode.size();i++){
        	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);  
        	lp.gravity = Gravity.CENTER;
        	int btn_width = dip2px(_context,50);
        	TextView modeTxt = null;
        	if(listMode.get(i).getModeId().equalsIgnoreCase(sel_mode)){
        		modeTxt = new BorderTextView(this,null,Color.RED);
        	}else{
        		modeTxt = new TextView(this);
        	}
        	modeTxt.setTag(listMode.get(i).getModeId());
        	modeTxt.setText(listMode.get(i).getText());
        	modeTxt.setWidth(btn_width);
        	long mode_color = Long.valueOf(listMode.get(i).getColor().replace("0x", "FF"),16);
        	modeTxt.setBackgroundColor((int)mode_color);
        	modeTxt.setGravity(Gravity.CENTER);
        	modeTxt.setLayoutParams(lp);
        	modeTxt.setTextColor(Color.WHITE);
        	modeTxt.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					sel_mode = v.getTag().toString();
					showMode();
				}
			});
        	l_mode.addView(modeTxt);
        }
	}
	
	/**
	 * 将px转换为dip
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip转换为px
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	
	Button btn_mon;
	Button btn_tue;
	Button btn_wed;
	Button btn_thu;
	Button btn_fir;
	Button btn_sat;
	Button btn_sun;
	OnClickListener btn_weekly_ClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			startProgressDialog();
			switch (v.getId()) {
			case R.id.btn_mon:
				currDay = 1;
				break;
			case R.id.btn_tue:
				currDay = 2;
				break;
			case R.id.btn_wed:
				currDay = 3;
				break;
			case R.id.btn_thu:
				currDay = 4;
				break;
			case R.id.btn_fir:
				currDay = 5;
				break;
			case R.id.btn_sat:
				currDay = 6;
				break;	
			case R.id.btn_sun:
				currDay = 0;
				break;
			}
			refreshWeeklyView();
			stopProgressDialog();
		}
	};
	private void setWeeklyBtn(){
		btn_mon = (Button)l_btn_weekly.findViewById(R.id.btn_mon);
		btn_tue = (Button)l_btn_weekly.findViewById(R.id.btn_tue);
		btn_wed = (Button)l_btn_weekly.findViewById(R.id.btn_wed);
		btn_thu = (Button)l_btn_weekly.findViewById(R.id.btn_thu);
		btn_fir = (Button)l_btn_weekly.findViewById(R.id.btn_fir);
		btn_sat = (Button)l_btn_weekly.findViewById(R.id.btn_sat);
		btn_sun = (Button)l_btn_weekly.findViewById(R.id.btn_sun);
		btn_mon.setBackgroundResource(R.drawable.btn_weekly_bg);
		btn_tue.setBackgroundResource(R.drawable.btn_weekly_bg);
		btn_wed.setBackgroundResource(R.drawable.btn_weekly_bg);
		btn_thu.setBackgroundResource(R.drawable.btn_weekly_bg);
		btn_fir.setBackgroundResource(R.drawable.btn_weekly_bg);
		btn_sat.setBackgroundResource(R.drawable.btn_weekly_bg);
		btn_sun.setBackgroundResource(R.drawable.btn_weekly_bg);
		btn_mon.setOnClickListener(btn_weekly_ClickListener);
		btn_tue.setOnClickListener(btn_weekly_ClickListener);
		btn_wed.setOnClickListener(btn_weekly_ClickListener);
		btn_thu.setOnClickListener(btn_weekly_ClickListener);
		btn_fir.setOnClickListener(btn_weekly_ClickListener);
		btn_sat.setOnClickListener(btn_weekly_ClickListener);
		btn_sun.setOnClickListener(btn_weekly_ClickListener);
		switch (currDay){
		case 0:
			btn_sun.setBackgroundResource(R.drawable.btn1_style);
			break;
		case 1:
			btn_mon.setBackgroundResource(R.drawable.btn1_style);
			break;
		case 2:
			btn_tue.setBackgroundResource(R.drawable.btn1_style);
			break;
		case 3:
			btn_wed.setBackgroundResource(R.drawable.btn1_style);
			break;
		case 4:
			btn_thu.setBackgroundResource(R.drawable.btn1_style);
			break;
		case 5:
			btn_fir.setBackgroundResource(R.drawable.btn1_style);
			break;
		case 6:
			btn_sat.setBackgroundResource(R.drawable.btn1_style);
			break;
		}
		
	}
	
	/**
	 * 显示表盘各时段的温度标签
	 */
	private void showWeeklyWatchLabs(int isShow){
        if(hours_weekly_labs!=null){
	        for(int i=0;i<hours_weekly_labs.length;i++){
	        	hours_weekly_labs[i].setVisibility(isShow);
	        }
        }
	}
	
	/**
	 * 重新显示Weekly表盘，及标签
	 */
	private void refreshWeeklyWatch(){
		DayItem currDayItem = GlobalModels.weekly_cache.getDayItemByIndex(currDay);
		int currDayItemSize = currDayItem.getPeriodList().size();
		//显示表盘中的每个时段
        for(int i=0;i<48;i++){      	
        	DayItemPeriod period = currDayItem.getDayItemPeriodByContainsTime(i);
        	
        	if(period!=null){
        		Mode mode = GlobalModels.weekly_cache.getModeByID(period.getModeId());
        		rl_weekly_canvas.removeView(hours_weekly[i]);
	        	long color_value = Long.valueOf((mode.getColor()).replace("0x", "FF"),16);
	        	hours_weekly[i] = new HourWatchScale(this, i,(int) color_value,0);
	        	//hours_weekly[i].ChangeColor((int)color_value);
	        	hours_weekly[i].resize(rl_weekly_canvas.getLayoutParams().width,rl_weekly_canvas.getLayoutParams().height);
	        	rl_weekly_canvas.addView(hours_weekly[i]);
        	}
        }
        //显示每个时段温度标签
        if(hours_weekly_labs!=null){
	        for(int i=0;i<hours_weekly_labs.length;i++){
	        	rl_weekly_canvas.removeView(hours_weekly_labs[i]);
	        }
        }
        hours_weekly_labs = new HourWatchScaleLable[currDayItemSize];
        for(int i=0;i<currDayItemSize;i++){
        	DayItemPeriod tmpPeriod = currDayItem.getPeriodList().get(i);
        	int iPoint = 0;
        	if(Math.abs(tmpPeriod.getEtid()-tmpPeriod.getStid())!=1){
	        	if(tmpPeriod.getStid()>tmpPeriod.getEtid()){
	        		iPoint = (tmpPeriod.getStid()+(47 - (tmpPeriod.getStid()-tmpPeriod.getEtid()))/2)%47;
	        	}else{
	        		iPoint = (tmpPeriod.getStid()+(tmpPeriod.getEtid()-tmpPeriod.getStid())/2);
	        	}
        	}else{
        		iPoint = tmpPeriod.getStid();
        	}
        	Mode mode = GlobalModels.weekly_cache.getModeByID(tmpPeriod.getModeId());
        	hours_weekly_labs[i] = new HourWatchScaleLable(this, iPoint,(int)Color.RED,0,mode.getCooling(),mode.getHeating());
        	hours_weekly_labs[i].resize(rl_weekly_canvas.getLayoutParams().width,rl_weekly_canvas.getLayoutParams().height);
        	hours_weekly_labs[i].setVisibility(View.INVISIBLE);
        	hours_weekly_labs[i].bringToFront();
        	rl_weekly_canvas.addView(hours_weekly_labs[i]);
        }
    }
	/**
	 * 操作Weekly表盘时更新表盘显示
	 * @param sel_period 此处必须为边界时间
	 * @param mv_period
	 */
	public void updateWeeklyDayItemPeriod(int sel_period,int mv_period){
		DayItem dayItem = GlobalModels.weekly_cache.getDayItemByIndex(currDay);
		List<DayItemPeriod> periodList = dayItem.getPeriodList();
		dayItem.sort(periodList);
		DayItemPeriod sel_ItemPeriod = dayItem.getDayItemPeriodByHour(sel_period);
		DayItemPeriod mv_ItemPeriod = dayItem.getDayItemPeriodByHour(mv_period);
		int iSel = dayItem.getIndexByHour(sel_period);
		int iMV = dayItem.getIndexByHour(mv_period);
		if(sel_ItemPeriod!=null && mv_ItemPeriod!=null && periodList.size()>1 && sel_period!=mv_period && Math.abs(iSel-iMV)<=1){
			Log.i("","sel_period:"+sel_period+"		mv_period:"+mv_period);
			if(iSel==0){//起始段特殊处理
				if(iMV==periodList.size()-1){//如果选的是第一段且向最后一段滑动
					return;
				}
				if(sel_period>mv_period){//当前选中段向后滑动
					if(mv_period-periodList.get(iSel).getStid()>1){//则目标位置必须与前一时间的起始时间大于1
						periodList.get(iSel).setEtid(mv_period);
						periodList.get(iSel+1).setStid(mv_period);
						borderSelect = periodList.get(iSel).getEtid();
					}else{
						periodList.get(iSel).setEtid(periodList.get(iSel).getStid()+1);
						periodList.get(iSel+1).setStid(periodList.get(iSel).getStid()+1);
						borderSelect = periodList.get(iSel).getEtid();
					}
				}else{//向前滑动
					if (periodList.get(iSel+1).getEtid()-mv_period>1){
						periodList.get(iSel).setEtid(mv_period);
						periodList.get(iSel+1).setStid(mv_period);
						borderSelect = periodList.get(iSel).getEtid();
					}else{
						periodList.get(iSel).setEtid(periodList.get(iSel+1).getEtid()-1);
						periodList.get(iSel+1).setStid(periodList.get(iSel+1).getEtid()-1);
						borderSelect = periodList.get(iSel).getEtid();
					}
				}
			}else if(iSel==periodList.size()-1){//结束段特殊处理
				if(iMV==0){//如果选的是最后段且向第一段滑动
					return;
				}
				if(sel_period>mv_period){//如果是向后滑动
					if(mv_period-mv_ItemPeriod.getStid()>1){//则目标位置必须与前一时间的起始时间大于1
						periodList.get(iSel).setStid(mv_period);
						periodList.get(iSel-1).setEtid(mv_period);
						borderSelect = periodList.get(iSel).getStid();
					}else{
						periodList.get(iSel).setStid(periodList.get(iSel-1).getStid()+1);
						periodList.get(iSel-1).setEtid(periodList.get(iSel-1).getStid()+1);
						borderSelect = periodList.get(iSel-1).getStid();
					}
				}else{//向当前选中段前滑
					if (sel_ItemPeriod.getEtid()-mv_period>1){
						periodList.get(iSel).setStid(mv_period);
						periodList.get(iSel-1).setEtid(mv_period);
						borderSelect = periodList.get(iSel).getStid();
					}else{
						periodList.get(iSel).setStid(periodList.get(iSel).getEtid()-1);
						periodList.get(iSel-1).setEtid(periodList.get(iSel).getEtid()-1);
						borderSelect = periodList.get(iSel).getEtid();
					}
				}
			}else{//当前选中的非第一段和最后一段
				if(sel_ItemPeriod.getStid()==sel_period){//选中段的开始位置
					if(sel_period>mv_period){//如果是向后滑动
						if(mv_period-mv_ItemPeriod.getStid()>1){//则目标位置必须与前一时间的起始时间大于1
							periodList.get(iSel).setStid(mv_period);
							periodList.get(iSel-1).setEtid(mv_period);
							borderSelect = periodList.get(iSel).getStid();
						}else{
							periodList.get(iSel).setStid(periodList.get(iSel-1).getStid()+1);
							periodList.get(iSel-1).setEtid(periodList.get(iSel-1).getStid()+1);
							borderSelect = periodList.get(iSel-1).getStid();
						}
					}else{//向当前选中段后滑
						if (sel_ItemPeriod.getEtid()-mv_period>1){
							periodList.get(iSel).setStid(mv_period);
							periodList.get(iSel-1).setEtid(mv_period);
							borderSelect = periodList.get(iSel).getStid();
						}else{
							periodList.get(iSel).setStid(periodList.get(iSel).getEtid()-1);
							periodList.get(iSel-1).setEtid(periodList.get(iSel).getEtid()-1);
							borderSelect = periodList.get(iSel).getEtid();
						}
					}	
				}else{//当前选 中的是结束位置
					if(sel_period>mv_period){//当前选中段向后滑动
						if(mv_period-periodList.get(iSel).getStid()>1){//则目标位置必须与前一时间的起始时间大于1
							periodList.get(iSel).setEtid(mv_period);
							periodList.get(iSel+1).setStid(mv_period);
							borderSelect = periodList.get(iSel).getEtid();
						}else{
							periodList.get(iSel).setEtid(periodList.get(iSel).getStid()+1);
							periodList.get(iSel+1).setStid(periodList.get(iSel).getStid()+1);
							borderSelect = periodList.get(iSel).getStid();
						}
					}else{//向前滑动
						if (periodList.get(iSel+1).getEtid()-mv_period>1){
							periodList.get(iSel).setEtid(mv_period);
							periodList.get(iSel+1).setStid(mv_period);
							borderSelect = periodList.get(iSel).getEtid();
						}else{
							periodList.get(iSel).setEtid(periodList.get(iSel+1).getEtid()-1);
							periodList.get(iSel+1).setStid(periodList.get(iSel+1).getEtid()-1);
							borderSelect = periodList.get(iSel).getEtid();
						}
					}
				}
			}
			GlobalModels.weekly_cache.getDayItemByIndex(currDay).setPeriodList(periodList);
			refreshWeeklyWatch();
		}
		selectHourWeekly = borderSelect;
	}
	
	private void showWeeklyBtn(boolean isDisabled){
		if(isDisabled){
			btn_weekly_apply.setBackgroundResource(R.drawable.apply2x);
			btn_weekly_reset.setBackgroundResource(R.drawable.reset2x);
			btn_weekly_apply.setEnabled(true);
			btn_weekly_reset.setEnabled(true);
		}else{
			btn_weekly_apply.setBackgroundResource(R.drawable.apply_disabled2x);
			btn_weekly_reset.setBackgroundResource(R.drawable.reset_disabled2x);
			btn_weekly_apply.setEnabled(false);
			btn_weekly_reset.setEnabled(false);
		}

	}
	//End Weekly 相关代码
	
	//Start Vaction相关代码  PullToRefreshExpandableListView
	private PullToRefreshListView vaction_pull_refresh_list;
	//private PullToRefreshExpandableListView vaction_pull_refresh_list;
	private VacationListAdapter vacationListAdapter;
	private ListView actualListView;
	private int currSelVacation=-1;
	/**
	 * Vaction列表显示
	 */
	public void refreshVactionView(){
		img_vacation_add.setVisibility(View.VISIBLE);
		vaction_pull_refresh_list = (PullToRefreshListView)this.findViewById(R.id.vaction_pull_refresh_list);
		vacationListAdapter = new VacationListAdapter(this);
		vacationListAdapter.addItem(GlobalModels.vacationList.getVacationList());
		actualListView = vaction_pull_refresh_list.getRefreshableView();
		actualListView.setAdapter(vacationListAdapter);
		
		vaction_pull_refresh_list.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				SendHttpRequest(3);
			}
		});
		actualListView.setOnItemClickListener(new OnItemClickListener() {
			@Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				Vacation tmp_vacation = GlobalModels.vacationList.getVacationsByIndex(position);
				Intent intent = new Intent();  
				Bundle bundle = new Bundle();
				bundle.putInt("action", 1);
				bundle.putInt("position", position);
				intent.setClass(_context,VacationEditActivity.class);  
				intent.putExtras(bundle);
				startActivity(intent);
				//startActivityForResult(intent, CommMsgID.VACATION_MENU);
            }  
		});
		
		actualListView.setOnItemLongClickListener(new OnItemLongClickListener() {		
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				currSelVacation = position;
				AlertDialog.Builder builder = new AlertDialog.Builder(ThermostatActivity.this);
			    //builder.setIcon(R.drawable.icon);
	            builder.setTitle("are you sure to delete this?");
	            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {	
	                	Vacation tmpVacation = new Vacation();
	                	if(currSelVacation==-1){
	                		showToast("delete fial.");
	                		return;
	                	}
	                	startProgressDialog();
	                	tmpVacation = GlobalModels.vacationList.getVacationsByIndex(currSelVacation);
	    				ReqParamMap reqParaMap = new ReqParamMap();
	    				reqParaMap.put("userId", GlobalModels.userid);
	    				reqParaMap.put("houseId", GlobalModels.getCurrHouseId());
	    				reqParaMap.put("tId", GlobalModels.houseList.getCurrentHouse().getDefualtThermostat());
	    				reqParaMap.put("action", "delete");
	    				reqParaMap.put("vacation",tmpVacation.transferToJson());
	    				GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.VACATION_SAVE, myHandler, CommMsgID.VACATION_SAVE);
	                }
	            });
	            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                }
	            });
	            builder.create().show();
				return true;
			}
		});
		vacationListAdapter.notifyDataSetChanged();
		vaction_pull_refresh_list.onRefreshComplete();
		stopProgressDialog();
	}
	
	

	
	//End Vaction相关代码
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("onResume",viewPager.getCurrentItem()+"");
		if(viewPager.getCurrentItem()==3){
			refreshVactionView();
		}
	}
	public void SendHttpRequest(int type){
		startProgressDialog();
		ReqParamMap reqParaMap = new ReqParamMap();
		reqParaMap.put("userId", GlobalModels.userid);
		reqParaMap.put("houseId", GlobalModels.getCurrHouseId());
		reqParaMap.put("tId", GlobalModels.houseList.getCurrentHouse().getDefualtThermostat());
		switch (viewPager.getCurrentItem()) {
		case 0:
			bView1InitFlag=true;
			GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.DASHBOARD_VIEW, myHandler, CommMsgID.DASHBOARD_VIEW);
			break;
		case 1:
			bView2InitFlag=true;
			GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.NEXT24_VIEW, myHandler, CommMsgID.NEXT24_VIEW);
			break;
		case 2:
			bView3InitFlag=true;
			GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.MASTER_PROGRAM_VIEW, myHandler, CommMsgID.MASTER_PROGRAM_VIEW);
			break;
		case 3:
			bView4InitFlag=true;
			GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.VACATION_VIEW, myHandler, CommMsgID.VACATION_VIEW);
			break;
		}
		
	}
	
	/**
	 * 异步回调处理
	 */
	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {	
			if(!ResultCheck(msg)){
				stopProgressDialog();
				return;
			}
			String data = msg.getData().getString(ResParamMap.KEY_RECEIVE_MESSAGE);
			switch (msg.what) {
			case CommMsgID.DASHBOARD_VIEW:
				GlobalModels.dashboard.transferFormJson(data);
				RefreshView(0);
				break;
			case CommMsgID.NEXT24_VIEW:
				GlobalModels.next24Schedule.transferFormJson(data);
				RefreshView(1);
				break;
			case CommMsgID.NEXT24_SAVE:
				if(msg.getData().equals("OK")){
					SendHttpRequest(1);
				}
				break;
			case CommMsgID.NEXT24_USE_WEEKLY:
				GlobalModels.next24Schedule.transferFormJson(data);
				RefreshView(1);
				break;
			case CommMsgID.NEXT24_HOLD_SAVE:
				GlobalModels.next24Schedule.transferFormJson(data);
				RefreshView(1);
				break;
			case CommMsgID.MASTER_PROGRAM_VIEW:
				GlobalModels.weekly.transferFormJson(data);
				GlobalModels.weekly_cache = (WeekSchedule) GlobalModels.weekly.clone();
				RefreshView(2);
				break;
			case CommMsgID.VACATION_VIEW:
				GlobalModels.vacationList.transferFormJson(data);
				RefreshView(3);
				break;
			case CommMsgID.VACATION_SAVE:
				GlobalModels.vacationList.removeVacationAt(currSelVacation);
				RefreshView(3);
				break;
			}
			stopProgressDialog();
			super.handleMessage(msg);
		}
	};

	public void RefreshView(int type){
		img_vacation_add.setVisibility(View.GONE);
		switch (type) {
		case 0:
			refreshDashboardView();
			break;
		case 1:
			refreshNext24View();
			break;
		case 2:
			refreshWeeklyView();
			break;
		case 3:
			refreshVactionView();
			break;
		}
	}
	
	//内部类
	/**
	 * 
	 * 头标点击监听 3
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}

	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		// 销毁position位置的界面
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one * currIndex, one* arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
			switch (viewPager.getCurrentItem()) {
			case 0:
				if(!bView1InitFlag){
					bView1InitFlag = true;
					SendHttpRequest(0);
				}
				break;
			case 1:
				if(!bView2InitFlag){
					bView2InitFlag = true;
					SendHttpRequest(1);
				}
				break;
			case 2:
				if(!bView3InitFlag){
					bView3InitFlag = true;
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					currDay = cal.get(Calendar.DAY_OF_WEEK)-1;
					SendHttpRequest(2);
				}
				break;
			case 3:
				if(!bView4InitFlag){
					bView4InitFlag = true;
					SendHttpRequest(3);
				}
				break;
			}
			myViewPagerAdapter.notifyDataSetChanged();
		}
	}
	
	private CountDown myCount;
	class CountDown extends CountDownTimer {
		public CountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);	
		}

		@Override
		public void onFinish() {
			showWatchLabs(View.VISIBLE);
			myCount.cancel();
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
	
	private CountDownWeekly myCountWeekly;
	class CountDownWeekly extends CountDownTimer {
		public CountDownWeekly(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);	
		}

		@Override
		public void onFinish() {
			showWeeklyWatchLabs(View.VISIBLE);
			myCountWeekly.cancel();
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
}
