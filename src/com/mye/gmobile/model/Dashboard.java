package com.mye.gmobile.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.model.base.BaseModel;

/**
 * dash board.
 * 
 * @author DongYu
 * @version 1.0 2012-1-14
 */
public class Dashboard extends BaseModel {

    /**
     * ovrried status.
     */
    private int isOvrried = Model.DASHBOARD_ORSTATUS_HOLD;

    /**
     * current room temperature, unit F, display integer.
     */
    private double temp = 0;

    /**
     * fan control mode.
     */
    private int fanControl = Model.DASHBOARD_FANCONTROL_AUTO;

    /**
     * fan status, On/Off, get from service.
     */
    private String fanStatus = null;

    /**
     * set point.
     */
    private int setPoint = Model.SETPOINT_MIN;

    /**
     * remote control status, ENABLED/DISABLED, get from service, read only.
     */
    private String locWeb = null;

    /**
     * current control mode, 1-5.
     */
    private int controlMode = Model.DASHBOARD_SYSTEMCONTROL_OFF;

    /**
     * real control mode: strHeat = "Heating"; strCool = "Cooling"; strHeatEMG =
     * "Emg Heat"; strOFF = "Off";
     */
    private String realControlMode = null;

    /**
     * current stage level, 0-3.
     */
    private int stageLevel = Model.DASHBOARD_SUBSTATUS_OFF;

    /**
     * whether heating or cooling, 1/2.
     */
    private int isHeatCool = Model.DASHBOARD_ISHEATCOLL_HEAT;

    /**
     * current program, get from service, read only.
     */
    private String currentProgram = null;

    /**
     * day forecast.
     */
    private DayForecast dayForecast = null;
    
    /**
     * 0或1,0不显示aux图标，1加亮显示aux图标
     */
    private int aux = 0;

    /**
     * get the isOvrried.
     * 
     * @return the isOvrried
     */
    public int getIsOvrried() {
        return isOvrried;
    }

    /**
     * set the isOvrried.
     * 
     * @param isOvrried
     *            the isOvrried to set
     */
    public void setIsOvrried(int isOvrried) {
        this.isOvrried = isOvrried;
    }

    /**
     * get the temp.
     * 
     * @return the temp
     */
    public double getTemp() {
        return temp;
    }

    /**
     * set the temp.
     * 
     * @param temp
     *            the temp to set
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     * get the fanControl.
     * 
     * @return the fanControl
     */
    public int getFanControl() {
        return fanControl;
    }

    /**
     * set the fanControl.
     * 
     * @param fanControl
     *            the fanControl to set
     */
    public void setFanControl(int fanControl) {
        this.fanControl = fanControl;
    }

    /**
     * get the fanStatus.
     * 
     * @return the fanStatus
     */
    public String getFanStatus() {
        return fanStatus;
    }

    /**
     * set the fanStatus.
     * 
     * @param fanStatus
     *            the fanStatus to set
     */
    public void setFanStatus(String fanStatus) {
        this.fanStatus = fanStatus;
    }

    /**
     * get the setPoint.
     * 
     * @return the setPoint
     */
    public int getSetPoint() {
        return setPoint;
    }

    /**
     * set the setPoint.
     * 
     * @param setPoint
     *            the setPoint to set
     */
    public void setSetPoint(int setPoint) {
        this.setPoint = setPoint;
    }

    /**
     * get the locWeb.
     * 
     * @return the locWeb
     */
    public String getLocWeb() {
        return locWeb;
    }

    /**
     * set the locWeb.
     * 
     * @param locWeb
     *            the locWeb to set
     */
    public void setLocWeb(String locWeb) {
        this.locWeb = locWeb;
    }

    /**
     * get the controlMode.
     * 
     * @return the controlMode
     */
    public int getControlMode() {
        return controlMode;
    }

    /**
     * set the controlMode.
     * 
     * @param controlMode
     *            the controlMode to set
     */
    public void setControlMode(int controlMode) {
        this.controlMode = controlMode;
    }

    /**
     * get the realControlMode.
     * 
     * @return the realControlMode
     */
    public String getRealControlMode() {
        return realControlMode;
    }

    /**
     * set the realControlMode.
     * 
     * @param realControlMode
     *            the realControlMode to set
     */
    public void setRealControlMode(String realControlMode) {
        this.realControlMode = realControlMode;
    }

    /**
     * get the stageLevel.
     * 
     * @return the stageLevel
     */
    public int getStageLevel() {
        return stageLevel;
    }

    /**
     * set the stageLevel.
     * 
     * @param stageLevel
     *            the stageLevel to set
     */
    public void setStageLevel(int stageLevel) {
        this.stageLevel = stageLevel;
    }

    /**
     * get the isHeatCool.
     * 
     * @return the isHeatCool
     */
    public int getIsHeatCool() {
        return isHeatCool;
    }

    /**
     * set the isHeatCool.
     * 
     * @param isHeatCool
     *            the isHeatCool to set
     */
    public void setIsHeatCool(int isHeatCool) {
        this.isHeatCool = isHeatCool;
    }

    /**
     * get the currentProgram.
     * 
     * @return the currentProgram
     */
    public String getCurrentProgram() {
        return currentProgram;
    }

    /**
     * set the currentProgram.
     * 
     * @param currentProgram
     *            the currentProgram to set
     */
    public void setCurrentProgram(String currentProgram) {
        this.currentProgram = currentProgram;
    }

    /**
     * get the dayForecast.
     * 
     * @return the dayForecast
     */
    public DayForecast getDayForecast() {
        return dayForecast;
    }

    /**
     * set the dayForecast.
     * 
     * @param dayForecast
     *            the dayForecast to set
     */
    public void setDayForecast(DayForecast dayForecast) {
        this.dayForecast = dayForecast;
    }

    public int getAux() {
		return aux;
	}

	public void setAux(int aux) {
		this.aux = aux;
	}

	/*
     * (non-Javadoc)
     * 
     * @see com.gmobile.model.base.IJson#transferFormJson(java.lang.String)
     */
    public void transferFormJson(String json) {
        if (json == null) {
            return;
        }
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(json);
            this.locWeb = jsonObj.getString("locWeb");
            this.isHeatCool = (int) jsonObj.getLong("isheatcool");
            this.setPoint = (int) jsonObj.getLong("setpoint");
            this.stageLevel = (int) jsonObj.getLong("stageLevel");
            this.controlMode = (int) jsonObj.getLong("controlMode");
            this.realControlMode = jsonObj.getString("realControlMode");
            this.isOvrried = (int) jsonObj.getLong("isOvrried");
            this.fanControl = (int) jsonObj.getLong("fan_control");
            this.fanStatus = jsonObj.getString("fan_status");
            this.currentProgram = jsonObj.getString("currentProgram");
            this.temp = jsonObj.getDouble("temperature");
            this.dayForecast = new DayForecast();
            this.dayForecast.setCurrentTemp(jsonObj.getDouble("weatherTemp"));
            this.dayForecast.setHighTemp(jsonObj.getDouble("highTemp"));
            this.dayForecast.setLowTemp(jsonObj.getDouble("lowTemp"));
            this.dayForecast.setWeather(jsonObj.getString("weather"));
            this.dayForecast.setHumidity((int) jsonObj.getLong("humidity"));
            this.aux = (int) jsonObj.getLong("aux");
        } catch (JSONException e) {
            LogUtil.error("Dashboard", "transferFormJson() error", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gmobile.model.base.IJson#transferToJson()
     */
    public String transferToJson() {
        JSONObject datamodel = new JSONObject();
        try {
            datamodel.put("locWeb", this.locWeb);
            datamodel.put("isheatcool", this.isHeatCool);
			if(this.setPoint>Model.SETPOINT_MAX){
				this.setPoint = Model.SETPOINT_MIN;
			}
            datamodel.put("setpoint", this.setPoint);
            datamodel.put("stageLevel", this.stageLevel);
            datamodel.put("controlMode", this.controlMode);
            datamodel.put("realControlMode", this.realControlMode);
            datamodel.put("isOvrried", this.isOvrried);
            datamodel.put("fan_control", this.fanControl);
            datamodel.put("fan_status", this.fanStatus);
            datamodel.put("currentProgram", this.currentProgram);
            datamodel.put("temperature", this.temp);
            datamodel.put("weather", this.dayForecast.getWeather());
            datamodel.put("weatherTemp", this.dayForecast.getCurrentTemp());
            datamodel.put("highTemp", this.dayForecast.getHighTemp());
            datamodel.put("lowTemp", this.dayForecast.getLowTemp());
            datamodel.put("humidity", this.dayForecast.getHumidity());
            datamodel.put("aux", this.aux);
        } catch (JSONException e) {
            LogUtil.error("Dashboard", "transferToJson() error", e);
        }
        return datamodel.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        Dashboard o = null;
        o = (Dashboard) super.clone();
        if (o != null && dayForecast != null) {
            o.dayForecast = (DayForecast) dayForecast.clone();
        }
        return o;
    }

}
