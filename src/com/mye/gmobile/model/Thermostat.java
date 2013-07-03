/*
 * House.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-1-16        DongYu      create
 */

package com.mye.gmobile.model;

import android.util.Log;

import com.mye.gmobile.common.constant.Model;


public class Thermostat implements Cloneable {

    /**
     * t_id
     */
    private String t_id = null;

    /**
     * T别名
     */
    private String t_name = null;
    

    /**
     * thermostat status  0表示温控器正常连接，1表示有温控器但其没有连接，2表示用户没有购买温控器；
     */
    private int thermostatStatus = Model.HOUSE_THERMOSTAT_STATUS_TYPE_NONE;
    
    /**
     * remote controllable status.
     */
    private int remote = Model.HOUSE_REMOTE_UNCONTROLLABLE;
    
    /**
     * 硬件上按钮锁0:Unlock  1:Lock
     */
    private int keyPad = 0;
    
    /**
     * 硬件类型由服务器提供0表示美国温度控制器1  红外转发器2 智能插座3  通用控制器4 安防设备。
     */
    private int deviceType;

	public String getT_id() {
		return t_id;
	}

	public void setT_id(String t_id) {
		this.t_id = t_id;
	}

	public String getT_name() {
		return t_name;
	}

	public void setT_name(String t_name) {
		this.t_name = t_name;
	}


	public int getThermostatStatus() {
		return thermostatStatus;
	}

	public void setThermostatStatus(int thermostatStatus) {
		this.thermostatStatus = thermostatStatus;
	}

	public int getRemote() {
		return remote;
	}

	public void setRemote(int remote) {
		this.remote = remote;
	}

	public int getKeyPad() {
		return keyPad;
	}

	public void setKeyPad(int keyPad) {
		this.keyPad = keyPad;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public Object clone() {
        Thermostat o = null;
        try {
            o = (Thermostat) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("Thermostat", "error in clone()", e);
        }
        return o;

    }

}
