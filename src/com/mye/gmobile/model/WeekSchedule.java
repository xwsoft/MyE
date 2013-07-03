package com.mye.gmobile.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.model.base.BaseModel;

/**
 * Master Program.
 * 
 * @author DongYu
 * @version 1.0 2012-1-23
 */
public class WeekSchedule extends BaseModel {

    /**
     * day item array.
     */
    private DayItem[] dayItems = null;

    /**
     * mode array.
     */
    private List<Mode> modeList = new ArrayList<Mode>();

    /**
     * get the dayItems.
     * 
     * @return the dayItems
     */
    public DayItem[] getDayItems() {
        return dayItems;
    }

    /**
     * set the dayItems.
     * 
     * @param dayItems
     *            the dayItems to set
     */
    public void setDayItems(DayItem[] dayItems) {
        this.dayItems = dayItems;
    }

    /**
     * get the modeList.
     * 
     * @return the modeList
     */
    public List<Mode> getModeList() {
        return modeList;
    }

    /**
     * set the modeList.
     * 
     * @param modeList
     *            the modeList to set
     */
    public void setModeList(List<Mode> modeList) {
        this.modeList = modeList;
    }

    /**
     * get Mode by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public Mode getModeByIndex(int index) {
        Mode mode = null;
        if (this.modeList != null && index < this.modeList.size()) {
            mode = this.modeList.get(index);
        }
        return mode;
    }

    public Mode getModeByID(String modeID) {
        for (Mode mode : modeList) {
            if (mode.getModeId().equals(modeID)) {
                return mode;
            }
        }
        return null;
    }

    /**
     * remove Mode by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public Mode removeModeAt(int index) {
        Mode mode = null;
        if (this.modeList != null && index < this.modeList.size()) {
            mode = this.modeList.remove(index);
        }
        return mode;
    }

    /**
     * add Mode by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void addModeAt(int index, Mode mode) {
        if (this.modeList != null && index <= this.modeList.size()) {
            this.modeList.add(index, mode);
        }
    }

    /**
     * add Mode by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void addMode(Mode mode) {
        if (this.modeList != null) {
            this.modeList.add(mode);
        }
    }

    /**
     * update Mode by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void updateModeAt(int index, Mode mode) {
        if (this.modeList != null && index < this.modeList.size()) {
            this.modeList.set(index, mode);
        }
    }

    /**
     * get DayItem by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public DayItem getDayItemByIndex(int index) {
        DayItem dayItem = null;
        if (this.dayItems != null && index < this.dayItems.length) {
            dayItem = this.dayItems[index];
        }
        return dayItem;
    }

    /**
     * update DayItem by index.
     * 
     * @param index
     * @param dayItem
     * @author:DongYu 2012-2-6
     */
    public void updateDayItemAt(int index, DayItem dayItem) {
        if (this.dayItems != null && index < this.dayItems.length) {
            this.dayItems[index] = dayItem;
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
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(json);
            this.houseId = jsonObj.getString("houseId");
            this.userId = jsonObj.getString("userId");
            JSONArray dayItemArray = jsonObj.getJSONArray("dayItems");
            if (dayItemArray != null) {
                this.dayItems = new DayItem[dayItemArray.length()];
                for (int i = 0; i < dayItemArray.length(); i++) {
                    JSONObject tempDayItem = dayItemArray.getJSONObject(i);
                    if (tempDayItem != null) {
                        DayItem dayItem = new DayItem();
                        dayItem.setDayId((int) tempDayItem.getLong("dayId"));
                        JSONArray periods = tempDayItem.getJSONArray("periods");
                        if (periods != null) {
                            for (int j = 0; j < periods.length(); j++) {
                                JSONObject tempJson = periods.getJSONObject(j);
                                if (tempJson != null) {
                                    DayItemPeriod period = new DayItemPeriod();
                                    period.setModeId(tempJson
                                                    .getString("modeid"));
                                    period.setStid((int) tempJson
                                                    .getLong("stid"));
                                    period.setEtid((int) tempJson
                                                    .getLong("etid"));
                                    dayItem.getPeriodList().add(period);
                                }
                            }
                        }
                        this.dayItems[i] = dayItem;
                    }
                }
            }
            this.modeList.clear();
            JSONArray modeArray = jsonObj.getJSONArray("modes");
            this.modeList.clear();
            if (modeArray != null) {
                for (int i = 0; i < modeArray.length(); i++) {
                    JSONObject tempMode = modeArray.getJSONObject(i);
                    if (tempMode != null) {
                        Mode mode = new Mode();
                        mode.setModeId(tempMode.getString("modeid"));
                        mode.setText(tempMode.getString("modeName"));
                        mode.setColor(tempMode.getString("color"));
                        mode.setCooling((int) tempMode.getLong("cooling"));
                        mode.setHeating((int) tempMode.getLong("heating"));
                        this.modeList.add(mode);
                    }
                }
            }

        } catch (JSONException e) {
            LogUtil.error("WeekSchedule", "transferFormJson() error", e);
        }

    }

    /**
     * transfer Mode to Json String.
     * 
     * @param mode
     * @return
     * @author:DongYu 2012-3-13
     */
    public static String transferToJson(Mode mode) {
        String result = "";
        if (mode != null) {
            JSONObject schedule = new JSONObject();
            try {
                JSONObject modeJson = new JSONObject();
                modeJson.put("modeid", mode.getModeId());
                modeJson.put("modeName", mode.getText());
                modeJson.put("color", mode.getColor());
                modeJson.put("cooling", mode.getCooling());
                modeJson.put("heating", mode.getHeating());

                schedule.put("modes", modeJson);
                result = schedule.toString();

            } catch (JSONException e) {
                LogUtil.error("WeekSchedule",
                                "transferToJson(Mode mode) error", e);
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gmobile.model.base.IJson#transferToJson()
     */
    public String transferToJson() {
        JSONObject schedule = new JSONObject();
        try {
            schedule.put("houseId", this.houseId);
            schedule.put("userId", this.userId);
            JSONArray dayItemArray = new JSONArray();
            if (this.dayItems != null) {
                for (int i = 0; i < dayItems.length; i++) {
                    DayItem dayItem = dayItems[i];
                    if (dayItem != null) {
                        JSONObject dayItemJson = new JSONObject();
                        dayItemJson.put("dayId", dayItem.getDayId());
                        JSONArray periodArray = new JSONArray();
                        for (DayItemPeriod dayPeriod : dayItem.getPeriodList()) {
                            if (dayPeriod != null) {
                                JSONObject period = new JSONObject();
                                period.put("modeid", dayPeriod.getModeId());
                                period.put("stid", dayPeriod.getStid());
                                period.put("etid", dayPeriod.getEtid());
                                periodArray.put(period);
                            }
                        }
                        dayItemJson.put("periods", periodArray);
                        dayItemArray.put(dayItemJson);
                    }
                }
            }
            schedule.put("dayItems", dayItemArray);

            JSONArray modeArray = new JSONArray();
            for (Mode mode : modeList) {
                if (mode != null) {
                    JSONObject modeJson = new JSONObject();
                    modeJson.put("modeid", mode.getModeId());
                    modeJson.put("modeName", mode.getText());
                    modeJson.put("color", mode.getColor());
                    modeJson.put("cooling", mode.getCooling());
                    modeJson.put("heating", mode.getHeating());
                    modeArray.put(modeJson);
                }
            }
            schedule.put("modes", modeArray);

        } catch (JSONException e) {
            LogUtil.error("WeekSchedule", "transferToJson() error", e);
        }
        return schedule.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        WeekSchedule o = null;
        o = (WeekSchedule) super.clone();
        if (o != null && modeList != null) {
            o.modeList = new ArrayList<Mode>();
            for (Mode mode : modeList) {
                o.modeList.add((Mode) mode.clone());
            }
        }
        if (o != null && dayItems != null) {
            o.dayItems = new DayItem[dayItems.length];
            for (int i = 0; i < dayItems.length; i++) {
                o.dayItems[i] = (DayItem) dayItems[i].clone();
            }
        }
        return o;
    }

}
