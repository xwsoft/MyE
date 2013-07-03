package com.mye.gmobile.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.common.log.LogUtil;

import android.util.Log;

/**
 * Day item information.
 * 
 * @author DongYu
 * @version 1.0 2012-1-23
 */

public class DayItem implements Cloneable {

    /**
     * day ID.
     */
    private int dayId = 0;

    /**
     * period information array.
     */
    private List<DayItemPeriod> periodList = new ArrayList<DayItemPeriod>();

    private List<DayItemPeriod> oldPeriodList = null;

    private String insertModeID = null;

    private int insertStid = -1;

    private int insertUpdateTid = -1;

    private boolean lastIsClockwise = true;

    private int lastConatinTid = 0;

    /**
     * get the dayId.
     * 
     * @return the dayId
     */
    public int getDayId() {
        return dayId;
    }

    /**
     * set the dayId.
     * 
     * @param dayId
     *            the dayId to set
     */
    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    /**
     * get the periodList.
     * 
     * @return the periodList
     */
    public List<DayItemPeriod> getPeriodList() {
        return periodList;
    }

    /**
     * set the periodList.
     * 
     * @param periodList
     *            the periodList to set
     */
    public void setPeriodList(List<DayItemPeriod> periodList) {
        this.periodList = periodList;
    }

    
    
    /**
     * get DayItemPeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public DayItemPeriod getPeriodByIndex(int index) {
        DayItemPeriod dayItemPeriod = null;
        if (this.periodList != null && index < this.periodList.size()) {
            dayItemPeriod = this.periodList.get(index);
        }
        return dayItemPeriod;
    }
    
    public int getIndexByHour(int iHour){
    	for(int i=0;i< this.periodList.size();i++){
    		DayItemPeriod period = this.periodList.get(i);
    		if(period.getEtid()<period.getStid()){
    			if(period.getStid()<=iHour || period.getEtid()>iHour){
    				return i;
    			}
    		}else if(iHour>=period.getStid() && iHour<period.getEtid()){
    			return i;
    		}
    	}
    	return -1;
    }
    
    public DayItemPeriod getDayItemPeriodByHour(int iHour){
    	if(this.periodList!=null){
	    	for(DayItemPeriod period:this.periodList){
	    		if(period.getEtid()<period.getStid()){
	    			if(period.getStid()<=iHour || period.getEtid()>iHour){
	    				return period;
	    			}
	    		}else if(iHour>=period.getStid() && iHour<period.getEtid()){
	    			return period;
	    		}
	    	}
    	}
    	return null;
    }

    /**
     * remove DayItemPeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public DayItemPeriod removePeriodAt(int index) {
        DayItemPeriod dayItemPeriod = null;
        if (this.periodList != null && index < this.periodList.size()) {
            dayItemPeriod = this.periodList.remove(index);
        }
        return dayItemPeriod;
    }

    /**
     * add DayItemPeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void addPeriodAt(int index, DayItemPeriod dayItemPeriod) {
        if (this.periodList != null && index <= this.periodList.size()) {
            this.periodList.add(index, dayItemPeriod);
        }
    }

    /**
     * add DayItemPeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void addPeriod(DayItemPeriod dayItemPeriod) {
        if (this.periodList != null) {
            this.periodList.add(dayItemPeriod);
        }
    }

    /**
     * update DayItemPeriod by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void updatePeriodAt(int index, DayItemPeriod dayItemPeriod) {
        if (this.periodList != null && index < this.periodList.size()) {
            this.periodList.set(index, dayItemPeriod);
        }
    }

    /**
     * get DayItemPeriod by start time id.
     * 
     * @param stid
     * @return
     * @author:DongYu 2012-2-23
     */
    private DayItemPeriod getDayItemPeriodByStid(int stid) {
        if (this.periodList != null) {
            for (DayItemPeriod temp : periodList) {
                if (temp.getStid() == stid) {
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * get DayItemPeriod by end time id.
     * 
     * @param etid
     * @return
     * @author:DongYu 2012-2-23
     */
    private DayItemPeriod getDayItemPeriodByEtid(int etid) {
        if (this.periodList != null) {
            for (DayItemPeriod temp : periodList) {
                if (temp.getEtid() == etid) {
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * get DayItemPeriod contain the time id.
     * 
     * @param tid
     * @return
     * @author:DongYu 2012-2-23
     */
    public DayItemPeriod getDayItemPeriodByContainsTid(int tid) {
        if (this.periodList != null) {
            for (DayItemPeriod temp : periodList) {
                if (temp.getEtid() > tid && temp.getStid() < tid) {
                    return temp;
                }
            }
        }
        return null;

    }

    public DayItemPeriod getDayItemPeriodByContainsTime(int tid) {
        if (this.periodList != null) {
            for (DayItemPeriod temp : periodList) {
                if (temp.getEtid() > tid && temp.getStid() <= tid) {
                    return temp;
                }
            }
        }
        return null;

    }

    /**
     * remove the DayItemPeriod and update the beside DayItemPeriods.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-23
     */
    public boolean removeAt(int index) {
        boolean result = false;
        DayItemPeriod dayItemPeriod = getPeriodByIndex(index);
        if (dayItemPeriod != null) {
            int stid = dayItemPeriod.getStid();
            int etid = dayItemPeriod.getEtid();
            DayItemPeriod lowIdPeriod = null;
            DayItemPeriod highIdPeriod = null;
            if (stid > Model.PERIOD_ID_START) {
                lowIdPeriod = getDayItemPeriodByEtid(stid);
            }
            if (etid < Model.PERIOD_ID_END) {
                highIdPeriod = getDayItemPeriodByStid(etid);
            }
            if (lowIdPeriod != null && highIdPeriod != null) {
                int tidCount = (stid + etid) / 2;
                lowIdPeriod.setEtid(tidCount);
                highIdPeriod.setStid(tidCount);
                result = true;
            } else if (lowIdPeriod != null) {
                lowIdPeriod.setEtid(etid);
                result = true;
            } else if (highIdPeriod != null) {
                highIdPeriod.setStid(stid);
                result = true;
            }
            if (result) {
                removePeriodAt(index);
            }
        }
        return result;
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
        DayItemPeriod dayItemPeriod = getPeriodByIndex(index);
        if (dayItemPeriod != null) {
            int stid = dayItemPeriod.getStid();
            int etid = dayItemPeriod.getEtid();
            if ((etid - newStid) < Model.COMMON_PERIOD_MIN_RANGE) {
                return false;
            }
            DayItemPeriod lowIdPeriod = null;
            if (stid > Model.PERIOD_ID_START) {
                lowIdPeriod = getDayItemPeriodByEtid(stid);
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

    /**
     * update the DayItemPeriod and beside DayItemPeriods.
     * 
     * @param index
     * @param newEtid
     * @return
     * @author:DongYu 2012-2-23
     */
    public boolean updateWithEtidAt(int index, int newEtid) {
        boolean result = false;
        DayItemPeriod dayItemPeriod = getPeriodByIndex(index);
        if (dayItemPeriod != null) {
            int stid = dayItemPeriod.getStid();
            int etid = dayItemPeriod.getEtid();
            if ((newEtid - stid) < Model.COMMON_PERIOD_MIN_RANGE) {
                return false;
            }
            DayItemPeriod highIdPeriod = null;
            if (etid < Model.PERIOD_ID_END) {
                highIdPeriod = getDayItemPeriodByStid(etid);
            }
            if (highIdPeriod != null
                            && ((highIdPeriod.getEtid() - newEtid) < Model.COMMON_PERIOD_MIN_RANGE)) {
                return false;
            }
            dayItemPeriod.setEtid(newEtid);
            if (highIdPeriod != null) {
                highIdPeriod.setStid(newEtid);
            }

            result = true;
        }
        return result;
    }

    /**
     * check add new DayItemPeriod.
     * 
     * @param index
     * @param newStid
     * @return
     * @author:DongYu 2012-2-23
     */
    public boolean AddAt(String modeID, int newStid) {
        DayItemPeriod containTidPeriod = getDayItemPeriodByContainsTime(newStid);
        int index = 0;
        if (containTidPeriod != null) {
            index = periodList.indexOf(containTidPeriod) + 1;
            int stid = containTidPeriod.getStid();
            int etid = containTidPeriod.getEtid();
            if ((newStid - stid) >= Model.COMMON_PERIOD_MIN_RANGE
                            && (etid - newStid) >= (Model.COMMON_PERIOD_MIN_RANGE + Model.NEW_PERIOD_MIN_RANGE)) {
                DayItemPeriod newPeriod = new DayItemPeriod();
                DayItemPeriod leftPeriod = (DayItemPeriod) containTidPeriod
                                .clone();
                DayItemPeriod rightPeriod = (DayItemPeriod) containTidPeriod
                                .clone();
                leftPeriod.setEtid(newStid);
                newPeriod.setStid(newStid);
                newPeriod.setEtid(newStid + Model.NEW_PERIOD_MIN_RANGE);
                newPeriod.setModeId(modeID);
                rightPeriod.setStid(newStid + Model.NEW_PERIOD_MIN_RANGE);
                addPeriodAt(index, newPeriod);
                addPeriodAt(index, leftPeriod);
                addPeriodAt(index + 2, rightPeriod);
                periodList.remove(containTidPeriod);
                return true;
            } else if ((newStid - stid) < (Model.COMMON_PERIOD_MIN_RANGE)
                            && (etid - stid) >= (Model.COMMON_PERIOD_MIN_RANGE + Model.NEW_PERIOD_MIN_RANGE)) {
                // left
                DayItemPeriod newPeriod = new DayItemPeriod();
                newPeriod.setStid(stid);
                newPeriod.setEtid(stid + Model.NEW_PERIOD_MIN_RANGE);
                newPeriod.setModeId(modeID);
                addPeriodAt(index, newPeriod);
                containTidPeriod.setStid(stid + Model.NEW_PERIOD_MIN_RANGE);
                return true;

            } else if ((etid - newStid) < (Model.COMMON_PERIOD_MIN_RANGE + Model.NEW_PERIOD_MIN_RANGE)
                            && (etid - stid) >= (Model.COMMON_PERIOD_MIN_RANGE + Model.NEW_PERIOD_MIN_RANGE)) {
                // right
                DayItemPeriod newPeriod = new DayItemPeriod();
                newPeriod.setStid(etid - Model.NEW_PERIOD_MIN_RANGE);
                newPeriod.setEtid(etid);
                newPeriod.setModeId(modeID);
                addPeriodAt(index, newPeriod);
                containTidPeriod.setEtid(etid - Model.NEW_PERIOD_MIN_RANGE);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public Object clone() {
        DayItem o = null;
        try {
            o = (DayItem) super.clone();
            if (o != null && periodList != null) {
                o.periodList = new ArrayList<DayItemPeriod>();
                for (DayItemPeriod period : periodList) {
                    o.periodList.add((DayItemPeriod) period.clone());
                }
            }
        } catch (CloneNotSupportedException e) {
            Log.e("DayItem", "error in clone()", e);
        }
        return o;
    }

    public void insertStart(String modeID, int stid) {
        LogUtil.info("DayItem", "insertStart() start");
        saveOldPeriodList();
        insertModeID = modeID;
        insertStid = stid;
        insertUpdateTid = stid;
        lastConatinTid = 0;
        insertPeriodClockwise(modeID, stid % Model.PERIOD_ID_END, (stid + 1) % Model.PERIOD_ID_END);
        lastIsClockwise = true;
        LogUtil.info("DayItem", "new periodList is "
                        + toStringOfPeriodList(periodList));
        LogUtil.info("DayItem", "saved periodList is "
                        + toStringOfPeriodList(oldPeriodList));
        LogUtil.info("DayItem", "insertStart() end");
    }

    public void insertUpdate(int updateTid) {
        LogUtil.info("DayItem", "insertUpdate() start");
        boolean success = restoreCurrentPeriodList();
        if (!success) {
            return;
        }
        boolean isClockwise = isClockwise(insertUpdateTid, updateTid);
        boolean isCircle = false;
        int currentConatinTid = containTidCount(insertUpdateTid, updateTid,
                        isClockwise);
        if (lastIsClockwise != isClockwise) {
            if (lastConatinTid >= currentConatinTid) {
                lastConatinTid -= currentConatinTid;
                isClockwise = lastIsClockwise;
            } else {
                lastConatinTid = currentConatinTid - lastConatinTid;
            }
        } else if ((lastIsClockwise == isClockwise)) {
            lastConatinTid += currentConatinTid;
        }
        if (lastConatinTid >= Model.PERIOD_ID_END) {
            isCircle = true;
        }
        lastIsClockwise = isClockwise;
        insertUpdateTid = updateTid;
        LogUtil.info("DayItem", "start id = " + insertStid);
        LogUtil.info("DayItem", "update id = " + updateTid);
        LogUtil.info("DayItem", "isClockwise = " + isClockwise);

        if (isCircle) {
            this.periodList = new ArrayList<DayItemPeriod>();
            DayItemPeriod newPeriod = new DayItemPeriod();
            newPeriod.setStid(Model.PERIOD_ID_START);
            newPeriod.setEtid(Model.PERIOD_ID_END);
            newPeriod.setModeId(insertModeID);
            this.periodList.add(newPeriod);

        } else if (isClockwise) {
            insertPeriodClockwise(insertModeID, insertStid, updateTid);
        } else {
            insertPeriodClockwise(insertModeID, updateTid, insertStid);
        }
        LogUtil.info("DayItem", "new periodList is "
                        + toStringOfPeriodList(periodList));
        LogUtil.info("DayItem", "saved periodList is "
                        + toStringOfPeriodList(oldPeriodList));
        LogUtil.info("DayItem", "insertUpdate() end");
    }

    public void insertDone(int etid) {
        LogUtil.info("DayItem", "insertDone() start");
        insertUpdate(etid);
        oldPeriodList = null;
        insertModeID = null;
        insertStid = -1;
        insertUpdateTid = -1;
        LogUtil.info("DayItem", "new periodList is "
                        + toStringOfPeriodList(periodList));
        LogUtil.info("DayItem", "saved periodList is "
                        + toStringOfPeriodList(oldPeriodList));
        LogUtil.info("DayItem", "insertDone() end");
    }

    private void insertPeriodClockwise(String modeID, int stid, int etid) {
        if (stid == Model.PERIOD_ID_END) {
            stid = Model.PERIOD_ID_START;
        }
        if (etid == Model.PERIOD_ID_START) {
            etid = Model.PERIOD_ID_END;
        }
        boolean isContainZero = false;
        List<DayItemPeriod> newPeriodList = new ArrayList<DayItemPeriod>();
        if (etid - stid > 0) {
            DayItemPeriod newPeriod = new DayItemPeriod();
            newPeriod.setStid(stid);
            newPeriod.setEtid(etid);
            newPeriod.setModeId(modeID);
            newPeriodList.add(newPeriod);
        } else if (etid - stid < 0) {
            isContainZero = true;
            DayItemPeriod newPeriod1 = new DayItemPeriod();
            newPeriod1.setStid(Model.PERIOD_ID_START);
            newPeriod1.setEtid(etid);
            newPeriod1.setModeId(modeID);
            DayItemPeriod newPeriod2 = new DayItemPeriod();
            newPeriod2.setStid(stid);
            newPeriod2.setEtid(Model.PERIOD_ID_END);
            newPeriod2.setModeId(modeID);
            newPeriodList.add(newPeriod1);
            newPeriodList.add(newPeriod2);
        } else {
            return;
        }

        if (this.periodList != null) {
            for (DayItemPeriod temp : periodList) {
                int tempStid = temp.getStid();
                int tempEtid = temp.getEtid();
                if (!isContainZero) {
                    if (tempStid >= etid || tempEtid <= stid) {
                        newPeriodList.add(temp);
                    } else if (tempStid < stid && tempEtid > etid) {
                        DayItemPeriod leftPeriod = (DayItemPeriod) temp.clone();
                        DayItemPeriod rightPeriod = (DayItemPeriod) temp
                                        .clone();
                        leftPeriod.setEtid(stid);
                        rightPeriod.setStid(etid);
                        newPeriodList.add(leftPeriod);
                        newPeriodList.add(rightPeriod);
                    } else if (tempStid < stid && tempEtid > stid
                                    && tempEtid <= etid) {
                        temp.setEtid(stid);
                        newPeriodList.add(temp);
                    } else if (tempEtid > etid && tempStid < etid
                                    && tempStid >= stid) {
                        temp.setStid(etid);
                        newPeriodList.add(temp);
                    }
                } else {
                    if (tempStid >= etid && tempEtid <= stid) {
                        newPeriodList.add(temp);
                    } else if (tempStid < stid && tempEtid > stid
                                    && tempEtid <= Model.PERIOD_ID_END) {
                        temp.setEtid(stid);
                        if (tempEtid > etid && tempStid < etid
                                        && tempStid >= Model.PERIOD_ID_START) {
                            temp.setStid(etid);
                        }
                        newPeriodList.add(temp);
                    } else if (tempEtid > etid && tempStid < etid
                                    && tempStid >= Model.PERIOD_ID_START) {
                        temp.setStid(etid);
                        newPeriodList.add(temp);
                    }
                }

            }
        }
        sort(newPeriodList);
        newPeriodList = removeAdjacentSameModeIDPeriod(newPeriodList);
        if (newPeriodList != null && newPeriodList.size() > 0) {
            this.periodList = newPeriodList;
        }

    }

    private List<DayItemPeriod> removeAdjacentSameModeIDPeriod(
                    List<DayItemPeriod> periodList) {
        if (periodList != null && periodList.size() > 0) {
            for (int i = 0; i < periodList.size(); i++) {
                String leftModeID = periodList.get(i).getModeId();
                String rightModeID = null;
                if ((i + 1) < periodList.size()) {
                    rightModeID = periodList.get(i + 1).getModeId();
                }
                if (rightModeID != null && rightModeID.equals(leftModeID)) {
                    periodList.get(i).setEtid(periodList.get(i + 1).getEtid());
                    periodList.remove(i + 1);
                    return removeAdjacentSameModeIDPeriod(periodList);
                }
            }
        }
        return periodList;
    }

    public void sort(List<DayItemPeriod> periodList) {
        if (periodList != null && periodList.size() > 0) {
            DayItemPeriod tempObject;
            for (int i = 0; i < periodList.size(); i++) {
                for (int j = i + 1; j < periodList.size(); j++) {
                    int leftStid = periodList.get(i).getStid();
                    int rightStid = periodList.get(j).getStid();
                    if (leftStid > rightStid) {
                        tempObject = periodList.get(i);
                        periodList.set(i, periodList.get(j));
                        periodList.set(j, tempObject);
                    }
                }
            }
        }
    }

	/**
	 * 当前时间（48半小时制）是否在该period段中
	 * @param period
	 * @param selHour
	 * @return
	 */
	public boolean isPeriodContains(DayItemPeriod period,int selHour){
		boolean flag = false;
		if(period.getStid()>period.getEtid()){
			if(selHour>=period.getStid() || selHour<period.getEtid()){
				flag = true;
			}
		}else{
			if(selHour>=period.getStid() && selHour<period.getEtid()){
				flag = true;
			}
		}
		return flag;
	}
	/**
	 * 计算起止时间相隔小时（半小时制）
	 * @param period
	 * @return
	 */
	public int apartHour(DayItemPeriod period){
		int result = 0;
		if(period.getStid()>period.getEtid()){
			result = 47-(period.getStid()-period.getEtid());
		}else{
			result = period.getEtid()-period.getStid();
		}
		return result;
	}
    private void saveOldPeriodList() {
        oldPeriodList = new ArrayList<DayItemPeriod>();
        for (DayItemPeriod period : periodList) {
            oldPeriodList.add((DayItemPeriod) period.clone());
        }
    }

    private boolean restoreCurrentPeriodList() {
        if (oldPeriodList != null && oldPeriodList.size() > 0) {
            periodList = new ArrayList<DayItemPeriod>();
            for (DayItemPeriod period : oldPeriodList) {
                periodList.add((DayItemPeriod) period.clone());
            }
            return true;
        }
        return false;
    }

    private boolean isClockwise(int stid, int etid) {
        int temp = etid - stid;
        if (temp < 0) {
            temp += Model.PERIOD_ID_END;
        }
        if (temp <= (Model.PERIOD_ID_END) / 2) {
            return true;
        } else {
            return false;
        }
    }

    private int containTidCount(int stid, int etid, boolean isClockwise) {
        if (isClockwise) {
            if (etid >= stid) {
                return etid - stid;
            } else {
                return etid - stid + Model.PERIOD_ID_END;
            }
        } else {
            if (etid <= stid) {
                return stid - etid;
            } else {
                return stid - etid + Model.PERIOD_ID_END;
            }
        }
    }

    /**
     * 确认当前选中是否在时间段的起始位置或结束位置
     * @param ihour
     * @return
     */
    public boolean isBorderHour(int ihour){
    	boolean flag = false;
        if (periodList != null && periodList.size() > 0) {
            for (int i = 0; i < periodList.size(); i++) {
            	if(periodList.get(i).getStid()==ihour || periodList.get(i).getEtid()-1==ihour){
            		flag = true;
            		break;
            	}
            }
        }
        return flag;
    }
    
    private String toStringOfPeriodList(List<DayItemPeriod> periodList) {
        JSONObject dayItemJson = new JSONObject();
        try {
            if (periodList != null) {
                JSONArray periodArray = new JSONArray();
                for (DayItemPeriod dayPeriod : periodList) {
                    if (dayPeriod != null) {
                        JSONObject period = new JSONObject();
                        period.put("modeid", dayPeriod.getModeId());
                        period.put("stid", dayPeriod.getStid());
                        period.put("etid", dayPeriod.getEtid());
                        periodArray.put(period);
                    }
                }
                dayItemJson.put("periods", periodArray);
            }
        } catch (JSONException e) {
            LogUtil.error("DayItem", "toStringOfPeriodList() error", e);
        }
        return dayItemJson.toString();
    }

}
