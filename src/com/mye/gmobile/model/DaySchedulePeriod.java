/*
 * DaySchedulePeriod.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-1-16        DongYu      create
 */

package com.mye.gmobile.model;

import com.mye.gmobile.common.constant.Model;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-1-16
 */

public class DaySchedulePeriod extends DayItemPeriod {

    /**
     * mode name.
     */
    private String title = null;

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

    /**
     * get the text.
     * 
     * @return the text
     */
    public String getText() {
        return title;
    }

    /**
     * set the text.
     * 
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.title = text;
    }

    /**
     * get the color.
     * 
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * set the color.
     * 
     * @param color
     *            the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * get the cooling.
     * 
     * @return the cooling
     */
    public int getCooling() {
        return cooling;
    }

    /**
     * set the cooling.
     * 
     * @param cooling
     *            the cooling to set
     */
    public void setCooling(int cooling) {
        this.cooling = cooling;
    }

    /**
     * get the heating.
     * 
     * @return the heating
     */
    public int getHeating() {
        return heating;
    }

    /**
     * set the heating.
     * 
     * @param heating
     *            the heating to set
     */
    public void setHeating(int heating) {
        this.heating = heating;
    }

    /**
     * get the hold.
     * 
     * @return the hold
     */
    public String getHold() {
        return hold;
    }

    /**
     * set the hold.
     * 
     * @param hold
     *            the hold to set
     */
    public void setHold(String hold) {
        this.hold = hold;
    }
    
    public boolean equalsMode(DaySchedulePeriod target){
    	boolean result = true;
    	if(target.getCooling() != cooling){
    		result = false;
    	}
    	if(target.getHeating() != heating){
    		result = false;
    	}
    	if(!target.getColor().equalsIgnoreCase(color)){
    		result = false;
    	}
    	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return (DaySchedulePeriod) super.clone();
    }

}
