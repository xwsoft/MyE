package com.mye.gmobile.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.model.base.IJson;

public class Vacation implements IJson, Cloneable {

    /**
     * vacation name
     */
    private String name = null;

    /**
     * vacation type, 0/1.
     */
    private int type = Model.VACATION_TYPE_VACATION;

    /**
     * used by service, "12/31/2011".
     */
    private String oldEndDate = null;

    // ############## used for vacation begin ##############
    /**
     * cooling set point, 55-90, used for vacation.
     */
    private int cooling = Model.SETPOINT_MAX;

    /**
     * heating set point, 55-90, used for vacation.
     */
    private int heating = Model.SETPOINT_MIN;

    /**
     * used for vacation, "12/31/2011".
     */
    private String returnDate = null;

    /**
     * used for vacation, "09:00".
     */
    private String returnTime = null;

    /**
     * used for vacation, "12/30/2011".
     */
    private String leaveDate = null;

    /**
     * used for vacation, "10:00".
     */
    private String leaveTime = null;
    // ############## used for vacation end ##############

    // ############## used for staycation begin ##############

    /**
     * night cooling set point, 55-90, used for staycation.
     */
    private int nightCooling = Model.SETPOINT_MAX;

    /**
     * night heating set point, 55-90, used for staycation.
     */
    private int nightHeating = Model.SETPOINT_MIN;

    /**
     * day cooling set point, 55-90, used for staycation.
     */
    private int dayCooling = Model.SETPOINT_MAX;

    /**
     * day heating set point, 55-90, used for staycation.
     */
    private int dayHeating = Model.SETPOINT_MIN;

    /**
     * used for staycation, "12/30/2011".
     */
    private String startDate = null;

    /**
     * used for staycation, "12/31/2011".
     */
    private String endDate = null;

    /**
     * used for staycation, "09:00".
     */
    private String dayTime = null;

    /**
     * used for staycation, "22:00".
     */
    private String nightTime = null;

    /**
     * action.
     */
    private String action = Model.VACATION_ACTION_ADD;

    /**
     * get the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * set the name.
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the type.
     * 
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * set the type.
     * 
     * @param type
     *            the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * get the oldEndDate.
     * 
     * @return the oldEndDate
     */
    public String getOldEndDate() {
        return oldEndDate;
    }

    /**
     * set the oldEndDate.
     * 
     * @param oldEndDate
     *            the oldEndDate to set
     */
    public void setOldEndDate(String oldEndDate) {
        this.oldEndDate = oldEndDate;
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
     * get the returnDate.
     * 
     * @return the returnDate
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * set the returnDate.
     * 
     * @param returnDate
     *            the returnDate to set
     */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * get the returnTime.
     * 
     * @return the returnTime
     */
    public String getReturnTime() {
        return returnTime;
    }

    /**
     * set the returnTime.
     * 
     * @param returnTime
     *            the returnTime to set
     */
    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    /**
     * get the leaveDate.
     * 
     * @return the leaveDate
     */
    public String getLeaveDate() {
        return leaveDate;
    }

    /**
     * set the leaveDate.
     * 
     * @param leaveDate
     *            the leaveDate to set
     */
    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    /**
     * get the leaveTime.
     * 
     * @return the leaveTime
     */
    public String getLeaveTime() {
        return leaveTime;
    }

    /**
     * set the leaveTime.
     * 
     * @param leaveTime
     *            the leaveTime to set
     */
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    /**
     * get the nightCooling.
     * 
     * @return the nightCooling
     */
    public int getNightCooling() {
        return nightCooling;
    }

    /**
     * set the nightCooling.
     * 
     * @param nightCooling
     *            the nightCooling to set
     */
    public void setNightCooling(int nightCooling) {
        this.nightCooling = nightCooling;
    }

    /**
     * get the nightHeating.
     * 
     * @return the nightHeating
     */
    public int getNightHeating() {
        return nightHeating;
    }

    /**
     * set the nightHeating.
     * 
     * @param nightHeating
     *            the nightHeating to set
     */
    public void setNightHeating(int nightHeating) {
        this.nightHeating = nightHeating;
    }

    /**
     * get the dayCooling.
     * 
     * @return the dayCooling
     */
    public int getDayCooling() {
        return dayCooling;
    }

    /**
     * set the dayCooling.
     * 
     * @param dayCooling
     *            the dayCooling to set
     */
    public void setDayCooling(int dayCooling) {
        this.dayCooling = dayCooling;
    }

    /**
     * get the dayHeating.
     * 
     * @return the dayHeating
     */
    public int getDayHeating() {
        return dayHeating;
    }

    /**
     * set the dayHeating.
     * 
     * @param dayHeating
     *            the dayHeating to set
     */
    public void setDayHeating(int dayHeating) {
        this.dayHeating = dayHeating;
    }

    /**
     * get the startDate.
     * 
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * set the startDate.
     * 
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * get the endDate.
     * 
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * set the endDate.
     * 
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * get the dayTime.
     * 
     * @return the dayTime
     */
    public String getDayTime() {
        return dayTime;
    }

    /**
     * set the dayTime.
     * 
     * @param dayTime
     *            the dayTime to set
     */
    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    /**
     * get the nightTime.
     * 
     * @return the nightTime
     */
    public String getNightTime() {
        return nightTime;
    }

    /**
     * set the nightTime.
     * 
     * @param nightTime
     *            the nightTime to set
     */
    public void setNightTime(String nightTime) {
        this.nightTime = nightTime;
    }

    /**
     * get the action.
     * 
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * set the action.
     * 
     * @param action
     *            the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gmobile.model.base.IJson#transferFormJson(java.lang.String)
     */
    public void transferFormJson(String json) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gmobile.model.base.IJson#transferToJson()
     */
    public String transferToJson() {
        JSONObject vacation = new JSONObject();
        try {
            vacation.put("type", this.type);
            vacation.put("name", this.name);
            vacation.put("old_end_date",this.oldEndDate);
            if (this.type == Model.VACATION_TYPE_VACATION) {
                vacation.put("leaveDate", this.leaveDate);
                vacation.put("returnDate", this.returnDate);
                vacation.put("leaveTime", this.leaveTime);
                vacation.put("returnTime", this.returnTime);
                vacation.put("cooling", this.cooling);
                vacation.put("heating", this.heating);
            } else {
                vacation.put("nightCooling", this.nightCooling);
                vacation.put("nightHeating", this.nightHeating);
                vacation.put("dayCooling", this.dayCooling);
                vacation.put("dayHeating", this.dayHeating);
                vacation.put("startDate", this.startDate);
                vacation.put("endDate", this.endDate);
                vacation.put("dayTime", this.dayTime);
                vacation.put("nightTime", this.nightTime);
            }
        } catch (JSONException e) {
            LogUtil.error("Vacation", "transferFormJson() error", e);
        }
        return vacation.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        Vacation o = null;
        try {
            o = (Vacation) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("Vacation", "error in clone()", e);
        }
        return o;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        result = prime * result + cooling;
        result = prime * result + dayCooling;
        result = prime * result + dayHeating;
        result = prime * result + ((dayTime == null) ? 0 : dayTime.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + heating;
        result = prime * result
                        + ((leaveDate == null) ? 0 : leaveDate.hashCode());
        result = prime * result
                        + ((leaveTime == null) ? 0 : leaveTime.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + nightCooling;
        result = prime * result + nightHeating;
        result = prime * result
                        + ((nightTime == null) ? 0 : nightTime.hashCode());
        result = prime * result
                        + ((oldEndDate == null) ? 0 : oldEndDate.hashCode());
        result = prime * result
                        + ((returnDate == null) ? 0 : returnDate.hashCode());
        result = prime * result
                        + ((returnTime == null) ? 0 : returnTime.hashCode());
        result = prime * result
                        + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + type;
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Vacation other = (Vacation) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.type == Model.VACATION_TYPE_STAYCATION) {
            if (this.nightCooling != other.nightCooling) {
                return false;
            }
            if (this.nightHeating != other.nightHeating) {
                return false;
            }
            if (this.dayCooling != other.dayCooling) {
                return false;
            }
            if (this.dayHeating != other.dayHeating) {
                return false;
            }
            if (!this.startDate.equals(other.startDate)) {
                return false;
            }
            if (!this.endDate.equals(other.endDate)) {
                return false;
            }
            if (!this.dayTime.equals(other.dayTime)) {
                return false;
            }
            if (!this.nightTime.equals(other.nightTime)) {
                return false;
            }

        } else if (this.type == Model.VACATION_TYPE_VACATION) {
            if (this.cooling != other.cooling) {
                return false;
            }
            if (this.heating != other.heating) {
                return false;
            }
            if (!this.returnDate.equals(other.returnDate)) {
                return false;
            }
            if (!this.returnTime.equals(other.returnTime)) {
                return false;
            }
            if (!this.leaveDate.equals(other.leaveDate)) {
                return false;
            }
            if (!this.leaveTime.equals(other.leaveTime)) {
                return false;
            }
        }

        return true;
    }

}
