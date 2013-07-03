package com.mye.gmobile.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.model.base.BaseModel;

/**
 * Program Today.
 * 
 * @author DongYu
 * @version 1.0 2012-1-23
 */
public class DaySchedule extends BaseModel {

    /**
     * current time, "12/29/2011 3:54".
     */
    private String currentTime = null;

    /**
     * current weekly id, get from service.
     */
    private int weeklyId = 0;
    
    
    private Date currentDate = new Date();

    /**
     * period information array.
     */
    private List<DaySchedulePeriod> periodList = new ArrayList<DaySchedulePeriod>();

    /**
     * get the currentTime.
     * 
     * @return the currentTime
     */
    public String getCurrentTime() {
        return currentTime;
    }

    /**
     * set the currentTime.
     * 
     * @param currentTime
     *            the currentTime to set
     */
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
        if (currentTime != null && !"".equals(currentTime)) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            try {
            	currentDate = format.parse(currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }   
    }

    /**
     * get the weeklyId.
     * 
     * @return the weeklyId
     */
    public int getWeeklyId() {
        return weeklyId;
    }

    /**
     * set the weeklyId.
     * 
     * @param weeklyId
     *            the weeklyId to set
     */
    public void setWeeklyId(int weeklyId) {
        this.weeklyId = weeklyId;
    }

    /**
     * get the periodList.
     * 
     * @return the periodList
     */
    public List<DaySchedulePeriod> getPeriodList() {
        return periodList;
    }

    /**
     * set the periodList.
     * 
     * @param periodList
     *            the periodList to set
     */
    public void setPeriodList(List<DaySchedulePeriod> periodList) {
        this.periodList = periodList;
    }

    /**
     * get DaySchedulePeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public DaySchedulePeriod getPeriodByIndex(int index) {
        DaySchedulePeriod daySchedulePeriod = null;
        if (this.periodList != null && index < this.periodList.size()) {
            daySchedulePeriod = this.periodList.get(index);
        }
        return daySchedulePeriod;
    }

    public int getPeriodIDByHour(int hour) {
        for (int i = 0; i < periodList.size(); i++) {
            if (periodList.get(i).containsHour(hour)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * remove DaySchedulePeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public DaySchedulePeriod removePeriodAt(int index) {
        DaySchedulePeriod daySchedulePeriod = null;
        if (this.periodList != null && index < this.periodList.size()) {
            daySchedulePeriod = this.periodList.remove(index);
        }
        return daySchedulePeriod;
    }

    /**
     * add DaySchedulePeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void addPeriodAt(int index, DaySchedulePeriod daySchedulePeriod) {
        if (this.periodList != null && index <= this.periodList.size()) {
            this.periodList.add(index, daySchedulePeriod);
        }
    }

    /**
     * add DaySchedulePeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void addPeriod(DaySchedulePeriod daySchedulePeriod) {
        if (this.periodList != null) {
            this.periodList.add(daySchedulePeriod);
        }
    }

    /**
     * update DaySchedulePeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void updatePeriodAt(int index, DaySchedulePeriod daySchedulePeriod) {
        if (this.periodList != null && index < this.periodList.size()) {
            this.periodList.set(index, daySchedulePeriod);
        }
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
        periodList.clear();
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(json);
            this.setCurrentTime(jsonObj.getString("currentTime"));
            this.houseId = jsonObj.getString("houseId");
            this.userId = jsonObj.getString("userId");
            this.weeklyId = (int) jsonObj.getLong("weeklyid");
            JSONArray periods = jsonObj.getJSONArray("periods");
            this.periodList.clear();
            if (periods != null) {
                for (int i = 0; i < periods.length(); i++) {
                    JSONObject tempJson = periods.getJSONObject(i);
                    if (tempJson != null) {
                        DaySchedulePeriod mode = new DaySchedulePeriod();
                        mode.setText(tempJson.getString("title"));
                        mode.setColor(tempJson.getString("color"));
                        mode.setStid((int) tempJson.getLong("stid"));
                        mode.setEtid((int) tempJson.getLong("etid"));
                        mode.setCooling((int) tempJson.getLong("cooling"));
                        mode.setHeating((int) tempJson.getLong("heating"));
                        mode.setHold(tempJson.getString("hold"));
                        this.periodList.add(mode);
                    }
                }
            }
        } catch (JSONException e) {
            LogUtil.error("DaySchedule", "transferFormJson() error", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gmobile.model.base.IJson#transferToJson()
     */
    public String transferToJson() {
        JSONObject schedule = new JSONObject();
        try {
            schedule.put("currentTime", this.currentTime);
            schedule.put("houseId", this.houseId);
            schedule.put("userId", this.userId);
            schedule.put("weeklyid", this.weeklyId);
            JSONArray periodArray = new JSONArray();
            for (DaySchedulePeriod mode : this.periodList) {
                if (mode != null) {
                    JSONObject period = new JSONObject();
                    period.put("title", mode.getText());
                    period.put("color", mode.getColor());
                    period.put("stid", mode.getStid());
                    period.put("etid", mode.getEtid());
                    period.put("cooling", mode.getCooling());
                    period.put("heating", mode.getHeating());
                    period.put("hold", mode.getHold());
                    periodArray.put(period);
                }
            }
            schedule.put("periods", periodArray);

        } catch (JSONException e) {
            LogUtil.error("DaySchedule", "transferToJson() error", e);
        }
        return schedule.toString();
    }

    /**
     * get DayItemPeriod by end time id.
     * 
     * @param etid
     * @return
     * @author:DongYu 2012-2-23
     */
    private DaySchedulePeriod getPeriodByEtid(int etid) {
        if (this.periodList != null) {
            for (DaySchedulePeriod temp : periodList) {
                if (temp.getEtid() == etid) {
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * update the DayItemPeriod and beside DayItemPeriods.
     * 
     * @param index
     * @param newStid
     * @return
     * @author:DongYu 2012-2-23
     */
    public boolean updateWithStidAt(int index, int newStid) {
        boolean result = false;
        DaySchedulePeriod dayItemPeriod = getPeriodByIndex(index);
        if (dayItemPeriod != null) {
            int stid = dayItemPeriod.getStid();
            int etid = dayItemPeriod.getEtid();
            if ((etid - newStid) < Model.COMMON_PERIOD_MIN_RANGE) {
                return false;
            }
            DaySchedulePeriod lowIdPeriod = null;
            if (stid > Model.PERIOD_ID_START) {
                lowIdPeriod = getPeriodByEtid(stid);
            }
            if (lowIdPeriod != null
                            && ((newStid - lowIdPeriod.getStid()) < Model.COMMON_PERIOD_MIN_RANGE)) {
                return false;
            }
            dayItemPeriod.setStid(newStid);
            if (lowIdPeriod != null) {
                lowIdPeriod.setEtid(newStid);
            }

            result = true;
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        DaySchedule o = null;
        o = (DaySchedule) super.clone();
        if (o != null && periodList != null) {
            o.periodList = new ArrayList<DaySchedulePeriod>();
            for (DaySchedulePeriod period : periodList) {
                o.periodList.add((DaySchedulePeriod) period.clone());
            }
        }
        return o;
    }

    public void updatePeriodTemp(int stid, int cooling, int heating) {
        for (int i = 0; i < periodList.size(); i++) {
            if (periodList.get(i).getStid() == stid) {
                periodList.get(i).setCooling(cooling);
                periodList.get(i).setHeating(heating);
                return;
            }
        }
    }

    public boolean isSameAsWeekSchedule(WeekSchedule weekSchedule) {
        boolean result = true;
        DayItem dayItem = getDayItemToday(weekSchedule);
        if (dayItem != null) {
            List<DayItemPeriod> periodList = dayItem.getPeriodList();
            if (periodList == null || this.periodList == null) {
                LogUtil.error("DaySchedule",
                                "in method isSameAsWeekSchedule(), there is a null periodList in DayItem or DaySchedule");
                if (periodList == null && this.periodList == null) {
                    return true;
                } else {
                    return false;
                }
            }
            if (periodList.size() == this.periodList.size()) {
                sort(periodList);
                sort(this.periodList);
                for (int i = 0; i < periodList.size(); i++) {
                    DayItemPeriod dayItemPeriod = periodList.get(i);
                    DaySchedulePeriod daySchedulePeriod = this.periodList
                                    .get(i);
                    if (dayItemPeriod == null || daySchedulePeriod == null) {
                        LogUtil.error("DaySchedule",
                                        "in method isSameAsWeekSchedule(), there is a null DayItemPeriod in periodList");
                        if (dayItemPeriod == null && daySchedulePeriod == null) {
                            continue;
                        } else {
                            result = false;
                            break;
                        }
                    }
                    Mode mode = weekSchedule.getModeByID(dayItemPeriod
                                    .getModeId());
                    if (mode == null) {
                        LogUtil.error("DaySchedule",
                                        "in method isSameAsWeekSchedule(), cannot find Mode by modeID = "
                                                        + dayItemPeriod.getModeId());
                        result = false;
                        break;
                    }
                    if (dayItemPeriod.getStid() != daySchedulePeriod.getStid()) {
                        result = false;
                        break;
                    } else if (dayItemPeriod.getEtid() != daySchedulePeriod
                                    .getEtid()) {
                        result = false;
                        break;
                    } else if (mode.getCooling() != daySchedulePeriod
                                    .getCooling()) {
                        result = false;
                        break;
                    } else if (mode.getHeating() != daySchedulePeriod
                                    .getHeating()) {
                        result = false;
                        break;
                    } else if (!mode.getColor().equalsIgnoreCase(
                                    daySchedulePeriod.getColor())) {
                        result = false;
                        break;
                    }
                }
            } else {
                result = false;
            }
        } else {
            LogUtil.error("DaySchedule",
                            "in method isSameAsWeekSchedule(), cannot find DayItem today");
            result = false;
        }
        return result;
    }

    public void resetByWeekSchedule(WeekSchedule weekSchedule) {
        DayItem dayItem = getDayItemToday(weekSchedule);
        if (dayItem != null) {
            List<DayItemPeriod> periodList = dayItem.getPeriodList();
            if (periodList == null) {
                LogUtil.error("DaySchedule",
                                "in method resetByWeekSchedule(), there is a null periodList in DayItem or DaySchedule");
            } else {
                this.periodList = new ArrayList<DaySchedulePeriod>();
                for (DayItemPeriod period : periodList) {
                    Mode mode = weekSchedule.getModeByID(period.getModeId());
                    if (mode == null) {
                        LogUtil.error("DaySchedule",
                                        "in method resetByWeekSchedule(), cannot find Mode by modeID = "
                                                        + period.getModeId());
                    } else {
                        DaySchedulePeriod newPeriod = new DaySchedulePeriod();
                        newPeriod.setText(mode.getText());
                        newPeriod.setColor(mode.getColor());
                        newPeriod.setStid(period.getStid());
                        newPeriod.setEtid(period.getEtid());
                        newPeriod.setCooling(mode.getCooling());
                        newPeriod.setHeating(mode.getHeating());
                        newPeriod.setHold("none");
                        this.periodList.add(newPeriod);
                    }
                }
            }
        } else {
            LogUtil.error("DaySchedule",
                            "in method resetByWeekSchedule(), cannot find DayItem today");
        }

    }

    private void sort(List periodList) {
        if (periodList != null && periodList.size() > 0) {
            DayItemPeriod tempObject;
            for (int i = 0; i < periodList.size(); i++) {
                for (int j = i + 1; j < periodList.size(); j++) {
                    int leftStid = ((DayItemPeriod) periodList.get(i))
                                    .getStid();
                    int rightStid = ((DayItemPeriod) periodList.get(j))
                                    .getStid();
                    if (leftStid > rightStid) {
                        tempObject = (DayItemPeriod) periodList.get(i);
                        periodList.set(i, periodList.get(j));
                        periodList.set(j, tempObject);
                    }
                }
            }
        }
    }

    private DayItem getDayItemToday(WeekSchedule weekSchedule) {
        DayItem dayItem = null;
        int dayIndex = 0;
        Date date = getCurrentDate();
        if (date != null) {
            dayIndex = date.getDay();
            dayIndex = (dayIndex - 1) >= 0 ? (dayIndex - 1) : dayIndex + 6;
        }
        if (weekSchedule != null) {
            DayItem[] dayItems = weekSchedule.getDayItems();
            if (dayItems != null) {
                for (int i = 0; i < dayItems.length; i++) {
                    if (dayItems[i] != null && dayItems[i].getDayId() == dayIndex) {
                        dayItem = dayItems[i];
                        break;
                    }
                }
            }
        }
        return dayItem;
    }

    public Date getCurrentDate() {
        return currentDate;
    }
    
    public boolean isCurrentPeriod(int periodIndex){
    	boolean result = false;
    	 DaySchedulePeriod period = getPeriodByIndex(periodIndex);
    	 Date date = getCurrentDate();
     	   if (date!=null && period != null) {
               int hour =date.getHours();
               int minute = date.getMinutes();
               int id = hour * 2 + (minute >= 30 ? 1 : 0);
               if (period.containsHour(id)) {
                   result = true;
               }
           }
    	 return result;
    }

    /**
     * if the period is past.
     * 
     * @param periodIndex
     * @return
     * @author:DongYu 2012-3-19
     */
    public boolean isPeriodEditable(int periodIndex) {
        boolean result = false;
        DaySchedulePeriod period = getPeriodByIndex(periodIndex);
        Date date = getCurrentDate();
        if (date != null && period != null) {
            int hour = date.getHours();
            int minute = date.getMinutes();
            int id = hour * 2 + (minute >= 30 ? 1 : 0);
            if (id <= period.getStid()) {
                result = true;
            }
        }
        return result;
    }

    public boolean isPeriodEditable(DaySchedulePeriod period,
                    boolean isUpdateStid, int updateID) {
        boolean result = false;
        Date date = getCurrentDate();

        if (date != null && period != null) {
            int hour = date.getHours();
            int minute = date.getMinutes();
            int id = hour * 2 + (minute >= 30 ? 2 : 1);
            if (id <= period.getStid() && !isUpdateStid) {
                result = true;
            }
            if (isUpdateStid) {
                DaySchedulePeriod lastPeriod = getPeriodByEtid(period.getStid());
                if (lastPeriod != null) {
                    if (id <= lastPeriod.getStid()) {
                        result = true;
                    } else if (lastPeriod.containsHour(id)
                                    && updateID > id) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

}
