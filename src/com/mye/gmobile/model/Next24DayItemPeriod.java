package com.mye.gmobile.model;

import java.io.Serializable;

import com.mye.gmobile.common.constant.Model;

/**
 * @author xwsoft
 * @version date：2013-5-6 上午11:52:30
 * 
 */
public class Next24DayItemPeriod extends DayItemPeriod implements Serializable{
	/**
	 * color, for example: "0x006699"
	 */
	private String color = null;

	/**
	 * cooling set point.
	 */
	private int cooling = Model.SETPOINT_MAX;

	/**
	 * heating set point.
	 */
	private int heating = Model.SETPOINT_MIN;

	/**
	 * hold information.
	 */
	private String hold = null;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getCooling() {
		return cooling;
	}

	public void setCooling(int cooling) {
		this.cooling = cooling;
	}

	public int getHeating() {
		return heating;
	}

	public void setHeating(int heating) {
		this.heating = heating;
	}

	public String getHold() {
		return hold;
	}

	public void setHold(String hold) {
		this.hold = hold;
	}
	
	public Object clone() {
		Next24DayItemPeriod o = null;
		o = (Next24DayItemPeriod) super.clone();
		return o;
	}
}
