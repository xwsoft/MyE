package com.mye.gmobile.activity; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mye.gmobile.R;
import com.mye.gmobile.common.communication.ReqParamMap;
import com.mye.gmobile.common.communication.ResParamMap;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.constant.HttpURL;
import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.model.GlobalModels;
import com.mye.gmobile.model.Vacation;

/** 
 * @author xwsoft 
 * @version date：2013-4-25 上午10:26:20 
 * 
 */
public class VacationEditActivity extends BaseActivity  {
	Button btn_cancel,btn_save;
	LinearLayout l_vacation,l_staycation;
	//vacation
	LinearLayout l_vacation_type,l_vacation_start_date,l_vacation_leave_time,l_vacation_end_date,l_vacation_return_time,l_vacation_cooling,l_vacation_heating;
	//staycation
	LinearLayout l_staycation_startDate,l_staycation_endDate,l_staycation_rise_time,l_staycation_rise_cooling,l_staycation_rise_heating,l_staycation_sleep,l_staycation_sleep_cooling,l_staycation_sleep_heating;
	
	TextView txt_vacation_name,txt_vacation_type;
	//vacation
	TextView txt_start_date,txt_leave_time,txt_end_date,txt_vacation_return_time,txt_vacation_cooling,txt_vacation_heating;
	//staycation
	TextView txt_staycation_startDate,txt_staycation_endDate,txt_staycation_rise_time,txt_staycation_rise_cooling,txt_staycation_rise_heating,txt_staycation_sleep_time,txt_staycation_sleep_cooling,txt_staycation_sleep_heating;
	int action;//1为修改，0为添加
	int position;
	Vacation vacation;
	Calendar calendar = Calendar.getInstance();
	public static SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vacation_edit);
		_context = this;
		position = getIntent().getIntExtra("position", 0);
		action = getIntent().getIntExtra("action", 0);	
		if(action==0){
			vacation = new Vacation();
			vacation.setType(0);
			vacation.setName("Vacation_"+(GlobalModels.vacationList.getVacationList().size()+1));
		}else{
			vacation = GlobalModels.vacationList.getVacationsByIndex(position);
		}
		
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!ValidVaction()){
					return;
				}
				startProgressDialog();
				vacation.setName(txt_vacation_name.getText().toString());
				ReqParamMap reqParaMap = new ReqParamMap();
				reqParaMap.put("userId", GlobalModels.userid);
				reqParaMap.put("houseId", GlobalModels.getCurrHouseId());
				reqParaMap.put("tId", GlobalModels.houseList.getCurrentHouse().getDefualtThermostat());
				if(action==0){
					reqParaMap.put("action", "add");
					GlobalModels.vacationList.addVacation(vacation);
				}else{
					reqParaMap.put("action", "edit");
					GlobalModels.vacationList.updateVacationAt(position, vacation);
				}
				reqParaMap.put("vacation",vacation.transferToJson());
				GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.VACATION_SAVE, myHandler, CommMsgID.VACATION_SAVE);
			}
		});
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		l_vacation = (LinearLayout) findViewById(R.id.l_vacation);
		l_staycation = (LinearLayout) findViewById(R.id.l_staycation);
		
		l_vacation_type = (LinearLayout) findViewById(R.id.l_vacation_type);
		l_vacation_start_date = (LinearLayout) findViewById(R.id.l_vacation_start_date);
		l_vacation_leave_time = (LinearLayout) findViewById(R.id.l_vacation_leave_time);
		l_vacation_end_date = (LinearLayout) findViewById(R.id.l_vacation_end_date);
		l_vacation_return_time = (LinearLayout) findViewById(R.id.l_vacation_return_time);
		l_vacation_cooling = (LinearLayout) findViewById(R.id.l_vacation_cooling);
		l_vacation_heating = (LinearLayout) findViewById(R.id.l_vacation_heating);
		
		
		l_staycation_startDate = (LinearLayout) findViewById(R.id.l_staycation_startDate);
		l_staycation_endDate = (LinearLayout) findViewById(R.id.l_staycation_endDate);
		l_staycation_rise_time = (LinearLayout) findViewById(R.id.l_staycation_rise_time);
		l_staycation_rise_cooling = (LinearLayout) findViewById(R.id.l_staycation_rise_cooling);
		l_staycation_rise_heating = (LinearLayout) findViewById(R.id.l_staycation_rise_heating);
		l_staycation_sleep = (LinearLayout) findViewById(R.id.l_staycation_sleep);
		l_staycation_sleep_cooling = (LinearLayout) findViewById(R.id.l_staycation_sleep_cooling);
		l_staycation_sleep_heating = (LinearLayout) findViewById(R.id.l_staycation_sleep_heating);
		
		//vaction name,type
		txt_vacation_name = (TextView) findViewById(R.id.txt_vacation_name);
		txt_vacation_type = (TextView) findViewById(R.id.txt_vacation_type);
					
		txt_start_date = (TextView) findViewById(R.id.txt_start_date);
		txt_leave_time = (TextView) findViewById(R.id.txt_leave_time);
		txt_end_date = (TextView) findViewById(R.id.txt_end_date);
		txt_vacation_return_time = (TextView) findViewById(R.id.txt_vacation_return_time);
		txt_vacation_cooling = (TextView) findViewById(R.id.txt_vacation_cooling);
		txt_vacation_heating = (TextView) findViewById(R.id.txt_vacation_heating);


		txt_staycation_startDate = (TextView) findViewById(R.id.txt_staycation_startDate);
		txt_staycation_endDate = (TextView) findViewById(R.id.txt_staycation_endDate);
		txt_staycation_rise_time = (TextView) findViewById(R.id.txt_staycation_rise_time);
		txt_staycation_rise_cooling = (TextView) findViewById(R.id.txt_staycation_rise_cooling);
		txt_staycation_rise_heating = (TextView) findViewById(R.id.txt_staycation_rise_heating);
		txt_staycation_sleep_time = (TextView) findViewById(R.id.txt_staycation_sleep_time);
		txt_staycation_sleep_cooling = (TextView) findViewById(R.id.txt_staycation_sleep_cooling);
		txt_staycation_sleep_heating = (TextView) findViewById(R.id.txt_staycation_sleep_heating);
		txt_vacation_name.setText(vacation.getName());
		initView();
		
		//temperature
		View.OnClickListener typeOnClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();  
				bundle.putInt("id", v.getId());
				bundle.putInt("type", vacation.getType());
				intent.setClass(_context,DialogVacationTypeActivity.class);  
				intent.putExtras(bundle);
				startActivityForResult(intent, CommMsgID.DAILOG_VACATION_TYPE);
			}
		};
		l_vacation_type.setOnClickListener(typeOnClickListener);
		
		//date
		View.OnClickListener dateOnClickListener = new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
				Bundle bundle = new Bundle();  
				bundle.putInt("id", v.getId());
				bundle.putString("currDate", getDateById(v.getId()));
				intent.setClass(_context,DialogDateActivity.class);  
				intent.putExtras(bundle);
				startActivityForResult(intent, CommMsgID.DAILOG_DATE);
			}
		};
		l_staycation_startDate.setOnClickListener(dateOnClickListener);
		l_staycation_endDate.setOnClickListener(dateOnClickListener);
		l_vacation_start_date.setOnClickListener(dateOnClickListener);
		l_vacation_end_date.setOnClickListener(dateOnClickListener);

		//time
		View.OnClickListener timeOnClickListener = new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
				Bundle bundle = new Bundle();  
				bundle.putInt("id", v.getId());
				String tmpTime = "";
				switch(v.getId()){
				case R.id.l_vacation_leave_time:
					tmpTime = txt_leave_time.getText().toString();
					break;
				case R.id.l_vacation_return_time:
					try {
						int icompareDate = CompareDate(vacation.getLeaveDate(),vacation.getReturnDate());
						if(icompareDate==0){
							if(vacation.getReturnTime().equals("23:30")){
								vacation.setReturnDate(dateformat.format(addDateDay(vacation.getLeaveDate(), 1)));
								vacation.setReturnTime("00:00");
								initView();
								return;
							}
							String[] arrLeaveTime = vacation.getLeaveTime().split(":");
							if(Integer.parseInt(arrLeaveTime[1])==0){
								bundle.putString("minTime", arrLeaveTime[0]+":30");
							}else{
								bundle.putString("minTime", (Integer.parseInt(arrLeaveTime[0])+1)+":00");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case R.id.l_staycation_rise_time:
					String[] arrNightTime = vacation.getNightTime().split(":");
					if(Integer.parseInt(arrNightTime[1])==0){
						bundle.putString("maxTime", (Integer.parseInt(arrNightTime[0])-1)+":30");
					}else{
						bundle.putString("maxTime", arrNightTime[0]+":00");
					}
					break;
				case R.id.l_staycation_sleep:
					String[] arrDayTime = vacation.getDayTime().split(":");
					if(Integer.parseInt(arrDayTime[1])==0){
						bundle.putString("minTime", arrDayTime[0]+":30");
					}else{
						bundle.putString("minTime", (Integer.parseInt(arrDayTime[0])+1)+":00");
					}
					break;
				default:
						break;
				}
				bundle.putString("currTime", getTimeById(v.getId()));
				intent.setClass(_context,DialogTimeActivity.class);  
				intent.putExtras(bundle);
				startActivityForResult(intent, CommMsgID.DAILOG_TIME);
			}
		};
		l_vacation_leave_time.setOnClickListener(timeOnClickListener);
		l_vacation_return_time.setOnClickListener(timeOnClickListener);
		l_staycation_rise_time.setOnClickListener(timeOnClickListener);
		l_staycation_sleep.setOnClickListener(timeOnClickListener);

		//temperature
		View.OnClickListener temperatureOnClickListener = new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
				Bundle bundle = new Bundle();  
				bundle.putInt("id", v.getId());
				String temperature = "55";
				switch(v.getId()){
				case R.id.l_vacation_cooling:
					bundle.putInt("minTemperature", getTemperatureById(R.id.l_vacation_heating)+Model.SETPOINT_SEPARTATED);
					break;
				case R.id.l_vacation_heating:
					bundle.putInt("maxTemperature", getTemperatureById(R.id.l_vacation_cooling)-Model.SETPOINT_SEPARTATED);
					break;
				case R.id.l_staycation_rise_cooling:
					bundle.putInt("minTemperature", getTemperatureById(R.id.l_staycation_rise_heating)+Model.SETPOINT_SEPARTATED);
					break;
				case R.id.l_staycation_rise_heating:
					bundle.putInt("maxTemperature", getTemperatureById(R.id.l_staycation_rise_cooling)-Model.SETPOINT_SEPARTATED);
					break;
				case R.id.l_staycation_sleep_cooling:
					bundle.putInt("minTemperature", getTemperatureById(R.id.l_staycation_sleep_heating)+Model.SETPOINT_SEPARTATED);
					break;
				case R.id.l_staycation_sleep_heating:
					bundle.putInt("maxTemperature", getTemperatureById(R.id.l_staycation_sleep_cooling)-Model.SETPOINT_SEPARTATED);
					break;
				default:
						break;
				}
				bundle.putInt("temperature", getTemperatureById(v.getId()));
				intent.setClass(_context,DialogTemperatureActivity.class);  
				intent.putExtras(bundle);
				startActivityForResult(intent, CommMsgID.DAILOG_TEMPERTURE);
			}
		};
		l_vacation_cooling.setOnClickListener(temperatureOnClickListener);
		l_vacation_heating.setOnClickListener(temperatureOnClickListener);
		l_staycation_rise_cooling.setOnClickListener(temperatureOnClickListener);
		l_staycation_rise_heating.setOnClickListener(temperatureOnClickListener);
		l_staycation_sleep_cooling.setOnClickListener(temperatureOnClickListener);
		l_staycation_sleep_heating.setOnClickListener(temperatureOnClickListener);	
	}

	public String getDateById(int id){
		String tmpDate = "";
		switch(id){
		case R.id.l_staycation_startDate:
			tmpDate = txt_staycation_startDate.getText().toString();
			break;
		case R.id.l_staycation_endDate:
			tmpDate = txt_staycation_endDate.getText().toString();
			break;
		case R.id.l_vacation_start_date:
			tmpDate = txt_start_date.getText().toString();
			break;
		case R.id.l_vacation_end_date:
			tmpDate = txt_end_date.getText().toString();
			break;
		default:
				break;
		}
		return tmpDate;
	}
	public void setDateById(int id,String val){
		switch(id){
		case R.id.l_staycation_startDate:
			txt_staycation_startDate.setText(val);
			vacation.setStartDate(val);
			if(ValidVaction()){
			}else{
				initView();
			}
			break;
		case R.id.l_staycation_endDate:
			txt_staycation_endDate.setText(val);
			vacation.setEndDate(val);
			if(ValidVaction()){
			}else{
				initView();
			}
			break;
		case R.id.l_vacation_start_date:
			txt_start_date.setText(val);
			if(ValidVaction()){
				vacation.setLeaveDate(val);
			}else{
				initView();
			}
			break;
		case R.id.l_vacation_end_date:
			txt_end_date.setText(val);
			if(ValidVaction()){
				vacation.setReturnDate(val);
			}else{
				initView();
			}
			break;
		default:
				break;
		}
	}
	
	public String getTimeById(int id){
		String tmpTime = "";
		switch(id){
		case R.id.l_vacation_leave_time:
			tmpTime = txt_leave_time.getText().toString();
			break;
		case R.id.l_vacation_return_time:
			tmpTime = txt_vacation_return_time.getText().toString();
			break;
		case R.id.l_staycation_rise_time:
			tmpTime = txt_staycation_rise_time.getText().toString();
			break;
		case R.id.l_staycation_sleep:
			tmpTime = txt_staycation_sleep_time.getText().toString();
			break;
		default:
				break;
		}
		return tmpTime;
	}
	public void setTimeById(int id,String val){
		switch(id){
		case R.id.l_vacation_leave_time:
			txt_leave_time.setText(val);
			vacation.setLeaveTime(val);
			try {
				Date _SDate = dateformat.parse(vacation.getLeaveDate());
				Date _EDate = dateformat.parse(vacation.getReturnDate());
				if(_SDate.compareTo(_EDate)==0){
					if(vacation.getLeaveTime().equals("23:30")){
						Calendar date = Calendar.getInstance();
						date.setTime(_EDate);
						date.add(Calendar.DAY_OF_MONTH, 1);
						vacation.setReturnDate(dateformat.format(date.getTime()));
						vacation.setReturnTime("00:00");
						return;
					}
					String[] arrLeaveTime = vacation.getLeaveTime().split(":");
					String[] arrReturnTime = vacation.getReturnTime().split(":");
					if(Integer.parseInt(arrLeaveTime[0])>Integer.parseInt(arrReturnTime[0])){
						if(Integer.parseInt(arrLeaveTime[1])==0){
							vacation.setReturnTime(arrLeaveTime[0]+":30");
						}else{
							vacation.setReturnTime((arrLeaveTime[0]+1)+":00");
						}
						return;
					}else if(Integer.parseInt(arrLeaveTime[0])==Integer.parseInt(arrReturnTime[0])){
						if(Integer.parseInt(arrLeaveTime[1])>=Integer.parseInt(arrReturnTime[1])){
							if(Integer.parseInt(arrLeaveTime[1])==0){
								vacation.setReturnTime(arrLeaveTime[0]+":30");
							}else{
								vacation.setReturnTime((arrLeaveTime[0]+1)+":00");
							}
						}
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case R.id.l_vacation_return_time:
			txt_vacation_return_time.setText(val);
			vacation.setReturnTime(val);
			try {
				Date _SDate = dateformat.parse(vacation.getLeaveDate());
				Date _EDate = dateformat.parse(vacation.getReturnDate());
				if(_SDate.compareTo(_EDate)==0){
					
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case R.id.l_staycation_rise_time:
			txt_staycation_rise_time.setText(val);
			vacation.setDayTime(val);
			break;
		case R.id.l_staycation_sleep:
			txt_staycation_sleep_time.setText(val);
			vacation.setNightTime(val);
			break;
		default:
				break;
		}
	}


	public int getTemperatureById(int id){
		String temperature = "55";
		switch(id){
		case R.id.l_vacation_cooling:
			temperature = txt_vacation_cooling.getText().toString();
			break;
		case R.id.l_vacation_heating:
			temperature = txt_vacation_heating.getText().toString();
			break;
		case R.id.l_staycation_rise_cooling:
			temperature = txt_staycation_rise_cooling.getText().toString();
			break;
		case R.id.l_staycation_rise_heating:
			temperature = txt_staycation_rise_heating.getText().toString();
			break;
		case R.id.l_staycation_sleep_cooling:
			temperature = txt_staycation_sleep_cooling.getText().toString();
			break;
		case R.id.l_staycation_sleep_heating:
			temperature = txt_staycation_sleep_heating.getText().toString();
			break;
		default:
				break;
		}
		return Integer.parseInt(temperature);
	}

	public void setTemperatureById(int id,int val){
		switch(id){
		case R.id.l_vacation_cooling:
			txt_vacation_cooling.setText(val+"");
			vacation.setCooling(val);
			break;
		case R.id.l_vacation_heating:
			txt_vacation_heating.setText(val+"");
			vacation.setHeating(val);
			break;
		case R.id.l_staycation_rise_cooling:
			txt_staycation_rise_cooling.setText(val+"");
			vacation.setDayCooling(val);
			break;
		case R.id.l_staycation_rise_heating:
			txt_staycation_rise_heating.setText(val+"");
			vacation.setDayHeating(val);
			break;
		case R.id.l_staycation_sleep_cooling:
			txt_staycation_sleep_cooling.setText(val+"");
			vacation.setNightCooling(val);
			break;
		case R.id.l_staycation_sleep_heating:
			txt_staycation_sleep_heating.setText(val+"");
			vacation.setNightHeating(val);
			break;
		default:
				break;
		}
	}
	
	public void initView(){
		calendar = Calendar.getInstance();	
		if(vacation.getType()==0){//vacation
			l_vacation.setVisibility(View.VISIBLE);
			l_staycation.setVisibility(View.GONE);
			txt_vacation_type.setText("Vacation");
			
			if(vacation.getLeaveDate()==null || vacation.getLeaveDate().equals("")){
				if(vacation.getStartDate() ==null || vacation.getStartDate().equals("")){
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					vacation.setLeaveDate(dateformat.format(calendar.getTime()));
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					vacation.setReturnDate(dateformat.format(calendar.getTime()));
				}else{
					vacation.setLeaveDate(vacation.getStartDate());
					vacation.setReturnDate(vacation.getEndDate());
				}
				vacation.setLeaveTime("8:00");
				vacation.setReturnTime("22:00");
				vacation.setCooling(85);
				vacation.setHeating(55);
			}
			txt_start_date.setText(vacation.getLeaveDate());
			txt_leave_time.setText(vacation.getLeaveTime());
			txt_end_date.setText(vacation.getReturnDate());
			txt_vacation_return_time.setText(vacation.getReturnTime());
			txt_vacation_cooling.setText(vacation.getCooling()+"");
			txt_vacation_heating.setText(vacation.getHeating()+"");
		}else{//staycation
			l_vacation.setVisibility(View.GONE);
			l_staycation.setVisibility(View.VISIBLE);
			
			if(vacation.getStartDate() ==null || vacation.getStartDate().equals("")){
				if(vacation.getLeaveDate()==null || vacation.getLeaveDate().equals("")){
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					vacation.setStartDate(dateformat.format(calendar.getTime()));
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					vacation.setEndDate(dateformat.format(calendar.getTime()));
				}else{
					vacation.setStartDate(vacation.getLeaveDate());
					vacation.setEndDate(vacation.getReturnDate());
				}
				vacation.setDayTime("8:00");
				vacation.setDayCooling(74);
				vacation.setDayHeating(70);
				vacation.setNightTime("22:00");
				vacation.setNightCooling(78);
				vacation.setNightHeating(65);
			}
			
			txt_vacation_type.setText("Staycation");
			txt_staycation_startDate.setText(vacation.getStartDate());
			txt_staycation_endDate.setText(vacation.getEndDate());
			txt_staycation_rise_time .setText(vacation.getDayTime());
			txt_staycation_rise_cooling.setText(vacation.getDayCooling()+"");
			txt_staycation_rise_heating.setText(vacation.getDayHeating()+"");
			txt_staycation_sleep_time.setText(vacation.getNightTime());
			txt_staycation_sleep_cooling.setText(vacation.getNightCooling()+"");
			txt_staycation_sleep_heating.setText(vacation.getNightHeating()+"");
		}
	}
	
	public boolean ValidVaction(){
		boolean flag = false;
		Date _date = new Date();
		//vacation 名称验证
		String _name = txt_vacation_name.getText().toString();
		if(_name==null||_name.equals("")){
			showToast("Please enter the name.");
			return flag;
		}
		List<Vacation> listVacation = GlobalModels.vacationList.getVacationList();
		for(int i=0;i<listVacation.size();i++){
			if(position!=i && listVacation.get(i).getName().equalsIgnoreCase(_name)){
				showToast("The description already exists.");
				return flag;
			}
		}
		if(vacation.getType()==0){//vacation
			String _start_date = txt_start_date.getText().toString();
			String _end_date = txt_end_date.getText().toString();
			try {
				Date _SDate = dateformat.parse(_start_date);
				Date _EDate = dateformat.parse(_end_date);
				if(_date.compareTo(_SDate)>=0){
					String _new_date = addDateDay(dateformat.format(_date),1);
					txt_start_date.setText(_new_date);
					vacation.setLeaveDate(_new_date);
					if(CompareDate(vacation.getReturnDate(),vacation.getLeaveDate())<0){
						String _new_date2 = addDateDay(dateformat.format(_date),2);
						txt_end_date.setText(_new_date2);
						vacation.setReturnDate(_new_date2);
					}
					showToast("Start date must be greater than the day.");
					return flag;
				}
				int iCompareDate = CompareDate(_start_date, _end_date);
				if(iCompareDate>0){
					String _new_date = addDateDay(dateformat.format(_date),1);
					txt_start_date.setText(_new_date);
					vacation.setLeaveDate(_new_date);
					if(CompareDate(vacation.getReturnDate(),vacation.getLeaveDate())<0){
						String _new_date2 = addDateDay(dateformat.format(_date),2);
						txt_end_date.setText(_new_date2);
						vacation.setReturnDate(_new_date2);
					}
					showToast("Start date must be greater than the day.");
					return flag;
				}else if(iCompareDate==0){
					String _leaveTime = txt_leave_time.getText().toString();
					String _returnTime = txt_vacation_return_time.getText().toString();
					if(CompareTime(_leaveTime,_returnTime)>=0){
						showToast("日期相同时时间结束时间必须要大于起始时间");
						return flag;
					}
				}
				int _cooling = Integer.parseInt(txt_vacation_cooling.getText().toString());
				int _heating =Integer.parseInt(txt_vacation_heating.getText().toString());
				if(_cooling<=_heating || _cooling-_heating<2){
					showToast("The heating setpoint must be at least 2F lower than the cooling setpoint.");
					return flag;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{//staycation
			String _start_date = txt_staycation_startDate.getText().toString();
			String _end_date = txt_staycation_endDate.getText().toString();
			try {
				Date _SDate = dateformat.parse(_start_date);
				Date _EDate = dateformat.parse(_end_date);
				if(_date.compareTo(_SDate)>=0){//起始日期如果不小于当前日期
					String _new_date = addDateDay(dateformat.format(_date),1);
					txt_staycation_startDate.setText(_new_date);
					vacation.setStartDate(_new_date);
					if(CompareDate(vacation.getEndDate(),vacation.getStartDate())<0){
						String _new_date2 = addDateDay(dateformat.format(_date),2);
						txt_staycation_endDate.setText(_new_date2);
						vacation.setEndDate(_new_date2);
					}
					showToast("Start date must be greater than the day.");
					return flag;
				}
				if(_date.compareTo(_EDate)>=0){//起始日期如果不小于当前日期
					String _new_date = addDateDay(dateformat.format(_date),1);
					txt_staycation_endDate.setText(_new_date);
					vacation.setEndDate(_new_date);
					showToast("End date must be greater than the day.");
					return flag;
				}
				//Rise time和Sleep time必须保持先后关系，前者必须比后者早至少半个小时。这个限制跟Start Date和End Date没有关系
				String _leaveTime = txt_staycation_rise_time.getText().toString();
				String _returnTime = txt_staycation_sleep_time.getText().toString();
				String[] arrLeaveTime = _leaveTime.split(":");
				String[] arrReturnTime = _leaveTime.split(":");
				if(Integer.parseInt(arrLeaveTime[0])>Integer.parseInt(arrReturnTime[0])){
					showToast("日期相同时时间结束时间必须要大于起始时间");
					return flag;
				}else if(Integer.parseInt(arrLeaveTime[0])==Integer.parseInt(arrReturnTime[0])){
					if(Integer.parseInt(arrLeaveTime[1])>=Integer.parseInt(arrReturnTime[1])){
						showToast("日期相同时时间结束时间必须要大于起始时间");
						return flag;
					}
				}
				int _cooling = Integer.parseInt(txt_staycation_rise_cooling.getText().toString());
				int _heating =Integer.parseInt(txt_staycation_rise_heating.getText().toString());
				if(_cooling<=_heating || _cooling-_heating<2){
					showToast("The heating setpoint must be at least 2F lower than the cooling setpoint.");
					return flag;
				}
				
				int _sleep_cooling = Integer.parseInt(txt_staycation_sleep_cooling.getText().toString());
				int _sleep_heating =Integer.parseInt(txt_staycation_sleep_heating.getText().toString());
				if(_sleep_cooling<=_sleep_heating || _sleep_cooling-_sleep_heating<2){
					showToast("The heating setpoint must be at least 2F lower than the cooling setpoint.");
					return flag;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		flag = true;
		return flag;
	}

	/**
	 * 对比8:00格式的时间
	 * @param time1
	 * @param time2
	 * @return 1: tim1大于tim2, 0为相等  -1为小于
	 */
	public int CompareTime(String time1, String time2) {
		int iResult = 1;
		try {
			String[] arrTime1 = time1.split(":");
			String[] arrTime2 = time2.split(":");
			int _h1 = Integer.parseInt(arrTime1[0]);
			int _h2 = Integer.parseInt(arrTime2[0]);
			if (_h1 < _h2) {
				iResult = -1;
			} else if (_h1 == _h2) {
				int _m1 = Integer.parseInt(arrTime1[1]);
				int _m2 = Integer.parseInt(arrTime2[1]);
				if(_m1<_m2){
					iResult = -1;
				}else if(_m1==_m2){
					iResult = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iResult;
	}
	
	public int CompareDate(String date1,String date2){
		int iResult = 1;
		try {
			Date _SDate = dateformat.parse(date1);
			Date _EDate = dateformat.parse(date2);
			return _SDate.compareTo(_EDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return iResult;
	}
	
	public String addDateDay(String date,int iDay){
		Date _Date;
		try {
			_Date = dateformat.parse(date);
			Calendar _date = Calendar.getInstance();
			_date.setTime(_Date);
			_date.add(Calendar.DAY_OF_MONTH, iDay);
			return dateformat.format(_date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
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
			case CommMsgID.VACATION_SAVE:
				ReqParamMap reqParaMap = new ReqParamMap();
				reqParaMap.put("userId", GlobalModels.userid);
				reqParaMap.put("houseId", GlobalModels.getCurrHouseId());
				reqParaMap.put("tId", GlobalModels.houseList.getCurrentHouse().getDefualtThermostat());
				GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.VACATION_VIEW, myHandler, CommMsgID.VACATION_VIEW);
				break;
			case CommMsgID.VACATION_VIEW:
				GlobalModels.vacationList.transferFormJson(data);
				stopProgressDialog();
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data==null){
			return;
		}
		startProgressDialog();
		Bundle bundle = data.getExtras();
		ReqParamMap reqParaMap = new ReqParamMap();
		int id = bundle.getInt("id");
		switch (resultCode) { 
		case CommMsgID.DAILOG_VACATION_TYPE:
			int type = bundle.getInt("type");
			vacation.setType(type);
			//GlobalModels.vacationList.updateVacationAt(position, vacation);
			break;
		case CommMsgID.DAILOG_DATE:
			String currDate = bundle.getString("currDate");
			setDateById(id,currDate);
			//GlobalModels.vacationList.updateVacationAt(position, vacation);
			break;
		case CommMsgID.DAILOG_TIME:
			String currTime = bundle.getString("currTime");
			setTimeById(id,currTime);
			//GlobalModels.vacationList.updateVacationAt(position, vacation);
			break;
		case CommMsgID.DAILOG_TEMPERTURE:
			int temperature = bundle.getInt("temperature");
			setTemperatureById(id,temperature);
			//GlobalModels.vacationList.updateVacationAt(position, vacation);
			break;
		default:
			break;
		}
		initView();
		stopProgressDialog();
		super.onActivityResult(requestCode, resultCode, data);
	}
}
