package com.mye.gmobile.model; 

import android.content.Context;

import com.mye.gmobile.common.communication.http.Handler;

/** 
 * 程序运行时用到的全局变量
 * @author xwsoft 
 * @version date：2013-4-23 上午9:55:43 
 * 
 */
public class GlobalModels {
	//访问接口相关变量
	public static Handler handler;
	public static String server_address = "www.myenergydomain.com";
    public static int server_port = 8080;
	public static String server_username = "test";
	public static String server_password = "test2012";
	
	//用户登录信息
	public static String username = "";
	public static String userid = "";
	public static String pwd = "";
	public static String timeFormat = "";
	/**
	 * 其它方法使用这个参数必须使用该方法的get
	 */
	public static String currHouseId = "";
	
	//themostat相关变量
	public static HouseList houseList = new HouseList();
	public static Dashboard dashboard = new Dashboard();
	public static Next24Schedule next24Schedule = new Next24Schedule();
	public static WeekSchedule weekly = new WeekSchedule();
	public static WeekSchedule weekly_cache = new WeekSchedule();
	public static VacationList vacationList = new VacationList();
	
	
	//访问接口相关
	public static Handler getHandler() {
        if (GlobalModels.handler == null) {
        	GlobalModels.handler = new Handler(server_address,server_port,server_username,server_password); 
        }
        return GlobalModels.handler;
	}


	public static String getCurrHouseId() {
		if(currHouseId.equals("")){
			GlobalModels.currHouseId = GlobalModels.houseList.getHouses()[0].getHouseId();
		}
		return GlobalModels.currHouseId;
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


}
