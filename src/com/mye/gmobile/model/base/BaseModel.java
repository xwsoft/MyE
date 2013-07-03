/*
 * BaseModel.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-1-16        DongYu      create
 */

package com.mye.gmobile.model.base;

import android.util.Log;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-1-16
 */

public abstract class BaseModel implements IJson, Cloneable {

    /**
     * user ID.
     */
    protected String userId = null;

    /**
     * house ID.
     */
    protected String houseId = null;

    /**
     * get the userId.
     * 
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * set the userId.
     * 
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * get the houseId.
     * 
     * @return the houseId
     */
    public String getHouseId() {
        return houseId;
    }

    /**
     * set the houseId.
     * 
     * @param houseId
     *            the houseId to set
     */
    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }


    public Object clone() {
        BaseModel o = null;
        try {
            o = (BaseModel) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("BaseModel", "error in clone()", e);
        }
        return o;
    }

}
