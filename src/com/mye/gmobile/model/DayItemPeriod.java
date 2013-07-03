/*
 * DayItemPeriod.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-1-16        DongYu      create
 */

package com.mye.gmobile.model;

import java.io.Serializable;

import android.util.Log;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-1-16
 */

public class DayItemPeriod implements Cloneable,Serializable {

    /**
     * mode id.
     */
    private String modeId = null;

    /**
     * start time id, 0~48.
     */
    private int stid = 0;

    /**
     * end time id, 0~48.
     */
    private int etid = 0;

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
     * get the stid.
     * 
     * @return the stid
     */
    public int getStid() {
        return stid;
    }

    /**
     * set the stid.
     * 
     * @param stid
     *            the stid to set
     */
    public void setStid(int stid) {
        this.stid = stid;
    }

    /**
     * get the etid.
     * 
     * @return the etid
     */
    public int getEtid() {
        return etid;
    }

    /**
     * set the etid.
     * 
     * @param etid
     *            the etid to set
     */
    public void setEtid(int etid) {
        this.etid = etid;
    }

    /**
     * TODO: stid and etid should be 1-24
     * 
     */
    public boolean containsHour(int hour) {
        if (stid < etid) {
            return hour >= stid && hour < etid;
        }
        if (etid < stid) {
            return hour >= stid || hour < etid;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        DayItemPeriod o = null;
        try {
            o = (DayItemPeriod) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("DayItemPeriod", "error in clone()", e);
        }
        return o;
    }

}
