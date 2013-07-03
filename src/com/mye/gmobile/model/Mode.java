/*
 * Mode.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-1-16        DongYu      create
 */

package com.mye.gmobile.model;

import android.util.Log;

import com.mye.gmobile.common.constant.Model;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-1-16
 */

public class Mode implements Cloneable {

    /**
     * mode id.
     */
    private String modeId = null;

    /**
     * mode name.
     */
    private String title = null;

    /**
     * color.
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
     * get the modeId.
     * 
     * @return the modeId
     */
    public String getModeId() {
        return modeId;
    }

    /**
     * set the modeId.
     * 
     * @param modeId
     *            the modeId to set
     */
    public void setModeId(String modeId) {
        this.modeId = modeId;
    }

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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        Mode o = null;
        try {
            o = (Mode) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("Mode", "error in clone()", e);
        }
        return o;

    }

}
