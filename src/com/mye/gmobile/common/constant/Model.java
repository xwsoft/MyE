package com.mye.gmobile.common.constant; 

import com.mye.gmobile.util.DataStack;

/** 
 * model���������õĳ�
 * @author xwsoft 
 * @version date��2013-4-23 ����9:47:06 
 * 
 */
public class Model {
	//�����û�����е��ֶ���
	public final static String Adv_Settings_Server_Addr = "server_address";
	public final static String Adv_Settings_Server_Port = "server_port";
	public final static String Adv_Settings_Server_UserName = "server_username";
	public final static String Adv_Settings_Server_PWD = "server_password";
	
	

	
    public final static int DASHBOARD_ORSTATUS_HOLD = 0;
    public final static int DASHBOARD_ORSTATUS_PERMANENT_HOLD = 1;
    public final static int DASHBOARD_ORSTATUS_TEMPORARY_HOLD = 2;

    public final static int DASHBOARD_FANCONTROL_AUTO = 0;
    public final static int DASHBOARD_FANCONTROL_ON = 1;

    public final static int DASHBOARD_SYSTEMCONTROL_HEAT = 1;
    public final static int DASHBOARD_SYSTEMCONTROL_COOL = 2;
    public final static int DASHBOARD_SYSTEMCONTROL_AUTO = 3;
    public final static int DASHBOARD_SYSTEMCONTROL_EMG_HEAT = 4;
    public final static int DASHBOARD_SYSTEMCONTROL_OFF = 5;

    public final static int DASHBOARD_SUBSTATUS_OFF = 0;
    public final static int DASHBOARD_SUBSTATUS_STAGE1 = 1;
    public final static int DASHBOARD_SUBSTATUS_STAGE2 = 2;
    public final static int DASHBOARD_SUBSTATUS_STAGE3 = 3;

    public final static int DASHBOARD_ISHEATCOLL_HEAT = 1;
    public final static int DASHBOARD_ISHEATCOLL_COOL = 2;

    public final static int SETPOINT_MIN = 55;
    public final static int SETPOINT_MAX = 90;
    /**
     * 制冷温度必须比制热大2度
     */
    public final static int SETPOINT_SEPARTATED = 2;

    public final static int VACATION_TYPE_VACATION = 0;
    public final static int VACATION_TYPE_STAYCATION = 1;

    public final static String VACATION_ACTION_DELETE_ALL = "deleteAll";
    public final static String VACATION_ACTION_ADD = "add";
    public final static String VACATION_ACTION_DELETE = "delete";
    public final static String VACATION_ACTION_EDIT = "edit";

    public final static String SETTING_KEYPAD_UNLOCK = "0";
    public final static String SETTING_KEYPAD_LOCK = "1";

    public final static int NEW_PERIOD_MIN_RANGE = 4;
    public final static int COMMON_PERIOD_MIN_RANGE = 1;
    public final static int PERIOD_ID_START = 0;
    public final static int PERIOD_ID_END = 48;

    public final static String MODE_ACTION_NEW = "newmode";
    public final static String MODE_ACTION_UPDATE = "editmode";
    public final static String MODE_ACTION_DELETE = "deletemode";

    
    /**
     * type: web 0; native app 1.
     */
    public final static int LOGIN_TYPE_NATIVE_APP = 1;

    public final static int HOUSE_THERMOSTAT_STATUS_TYPE_NORMAL = 0;
    public final static int HOUSE_THERMOSTAT_STATUS_TYPE_NO_CONNECT = 1;
    public final static int HOUSE_THERMOSTAT_STATUS_TYPE_NONE = 2;

    public final static int HOUSE_REMOTE_UNCONTROLLABLE = 0;
    public final static int HOUSE_REMOTE_CONTROLLABLE = 1;
    
    public final static String HARDWARE_STATUS_UNCONNECTION = "-999";
    public final static String HARDWARE_STATUS_REMOTE_UNCONTROLLABLE = "-998";
    
    
    //���³�Ϊ����ӵ� 2013-02-28
    public final static int M_CONNECTION_TRUE = 0;
    public final static int M_CONNECTION_FALSE = 1;
    
    public final static int T_CONNECTION_TRUE = 0;
    public final static int T_CONNECTION_FALSE = 1;
    
    public final static int T_REMOTE_TRUE = 1;
    public final static int T_REMOTE_FALSE = 0;
    
    public final static int T_KEYPAD_UNLOCK = 0;
    public final static int T_KEYPAD_LOCK = 1;
}
