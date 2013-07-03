package com.mye.gmobile.common.constant;

import android.content.Context;

import com.mye.gmobile.util.DataStack;

public class TipMessage {
	private final static String DASHBOARD_TIP = "Click on the icons to bring up the system and fan mode menu.";
	private final static String DASHBOARD_TIP_2 = "You can check which thermostat you are currently viewing by double-clicking the top menu bar.";
	private final static String TODAY_TIP_1 = "Swipe along the circle to adjust the time setting. Double click on the colored block to view/edit the temperature setpoint. However, you cannot change any setpoint that has been or is being executed.";
	private final static String TODAY_TIP_2 = "When the setpoint is held from Dashboard, the corresponding time block will turn gray. Double click to view the setpoint.";
	private final static String WEEKLY_TIP = "Swipe along the circle to adjust the time setting. Double click on the colored block to view/edit the temperature setpoint. Select a mode first, and swipe on the circle to apply the selected mode.";
	private final static String VACATION_TIP = "Click the icon with \"+\" to add new vacation/staycation. Swipe to delete.";
	private final static String DETECTION_HELP = "Homing detection is a feature to use your home Wi-Fi to detect your presence and let the thermostat automatically adjusts the temperature setting when you leave or return home. Your app needs to stay open to support this feature.";
	private final static String DETECTION_ON = "Find and mark a Wi-Fi signal as your Home Wi-Fi. This does NOT automatically switch your phone to Wi-Fi connection.";
	private final static String SETTING_TIP_1 = "Reset \"Tip Popups\" to re-activate all closed tips.";
	private final static String SETTING_TIP_2 = "Switch on Homing Detection to let the thermostat automatically adjust the setpoint when you leave or return home.";
	public enum TipMessages{
		DASHBOARD_TIP ,
		DASHBOARD_TIP_2,
		TODAY_TIP_1,
		TODAY_TIP_2, 
		WEEKLY_TIP,
		VACATION_TIP,
		DETECTION_ON,
		DETECTION_HELP,
		SETTING_TIP_1,
		SETTING_TIP_2
	}
	
	public static String getTipMessage(TipMessages id){
		switch(id){
		case DASHBOARD_TIP:
			return DASHBOARD_TIP;
		case DASHBOARD_TIP_2:
			return DASHBOARD_TIP_2;
		case TODAY_TIP_1:
			return TODAY_TIP_1;
		case TODAY_TIP_2:
			return TODAY_TIP_2;
		case WEEKLY_TIP:
			return WEEKLY_TIP;
		case VACATION_TIP:
			return VACATION_TIP;
		case DETECTION_HELP:
			return DETECTION_HELP;
		case DETECTION_ON:
			return DETECTION_ON;
		case SETTING_TIP_1:
			return SETTING_TIP_1;
		case SETTING_TIP_2:
			return SETTING_TIP_2;
		default :
			return "";
		}
	}
	
	public static boolean isTipEnabled(Context context,TipMessages id){
		return DataStack.getBoolean(context, "UserInfo", id.name(), true);
	}
	
	public static void disableTip(Context context,TipMessages id){
		DataStack.updateBooleanData(context, "UserInfo", id.name(), false);
	}
	
	public static void enbaleAllTip(Context context){
		DataStack.updateBooleanData(context, "UserInfo", TipMessages.DASHBOARD_TIP.name(), true);
		DataStack.updateBooleanData(context, "UserInfo", TipMessages.DASHBOARD_TIP_2.name(), true);
		DataStack.updateBooleanData(context, "UserInfo", TipMessages.TODAY_TIP_1.name(), true);
		DataStack.updateBooleanData(context, "UserInfo", TipMessages.TODAY_TIP_2.name(), true);
		DataStack.updateBooleanData(context, "UserInfo", TipMessages.WEEKLY_TIP.name(), true);
		DataStack.updateBooleanData(context, "UserInfo", TipMessages.VACATION_TIP.name(), true);
		DataStack.updateBooleanData(context, "UserInfo", TipMessages.SETTING_TIP_1.name(), true);
		DataStack.updateBooleanData(context, "UserInfo", TipMessages.SETTING_TIP_2.name(), true);
		DataStack.updateBooleanData(context, "UserInfo", TipMessages.DETECTION_ON.name(), true);
	}
}
