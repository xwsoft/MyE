package com.mye.gmobile.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.model.base.BaseModel;

/**
 * @author xwsoft
 *
 */
public class Next24Schedule extends BaseModel {

	private int iHour48 = 0;
    private String currentTime = null;
    private Date currentDate = new Date();
    private int weeklyId = 0;
    private int setpoint = 0;
    private int hold = 0;
    private String locWeb = "enabled";
    private List<Next24DayItem> next24DayItemList = new ArrayList<Next24DayItem>();

	public boolean isPeriodContains(Next24DayItemPeriod period,int selHour){
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
            this.setCurrentTime(jsonObj.getString("currentTime"));
            this.houseId = jsonObj.getString("houseId");
            this.userId = jsonObj.getString("userId");
            //this.weeklyId = (int) jsonObj.getLong("weeklyid");
            this.setpoint = (int) jsonObj.getLong("setpoint");
            this.hold = (int) jsonObj.getLong("hold");
            this.locWeb = jsonObj.getString("locWeb");
            this.next24DayItemList = new ArrayList<Next24DayItem>();
            JSONArray dayItemsJSON = jsonObj.getJSONArray("dayItems");
            if(dayItemsJSON!=null){
            	for (int j = 0; j < dayItemsJSON.length(); j++) {
            		Next24DayItem next24DayItem = new Next24DayItem();
            		next24DayItem.setDay(dayItemsJSON.getJSONObject(j).getInt("date"));
            		next24DayItem.setMonth(dayItemsJSON.getJSONObject(j).getInt("month"));	
            		next24DayItem.setYear(dayItemsJSON.getJSONObject(j).getInt("year"));	
            		List<Next24DayItemPeriod> next24DayItemPeriodList = new ArrayList<Next24DayItemPeriod>();          		
            		JSONArray periods = dayItemsJSON.getJSONObject(j).getJSONArray("periods");
                    if (periods != null) {
                        for (int i = 0; i < periods.length(); i++) {
                            JSONObject tempJson = periods.getJSONObject(i);
                            if (tempJson != null) {
                            	Next24DayItemPeriod period = new Next24DayItemPeriod();
                                period.setColor(tempJson.getString("color"));
                                period.setStid((int) tempJson.getLong("stid"));
                                period.setEtid((int) tempJson.getLong("etid"));
                                period.setCooling((int) tempJson.getLong("cooling"));
                                period.setHeating((int) tempJson.getLong("heating"));
                                period.setHold(tempJson.getString("hold"));
                                next24DayItemPeriodList.add(period);
                            }
                        }  
                        next24DayItem.setNextDayItemPeriodList(next24DayItemPeriodList);
                    }              
                    this.next24DayItemList.add(next24DayItem);
            	}
            }
            

        } catch (JSONException e) {
            LogUtil.error("DaySchedule", "transferFormJson() error", e);
        }
    }
    
    public String transferToJson() {
        JSONObject schedule = new JSONObject();
        try {
            schedule.put("currentTime", this.currentTime);
            schedule.put("houseId", this.houseId);
            schedule.put("userId", this.userId);
            schedule.put("setpoint", this.setpoint);
            schedule.put("hold", this.hold);
            schedule.put("locWeb", this.locWeb);
            JSONArray dayItems = new JSONArray();
            for(Next24DayItem next24DayItem : next24DayItemList){
            	JSONObject dayItem = new JSONObject();
            	dayItem.put("date", next24DayItem.getDay());
            	dayItem.put("month", next24DayItem.getMonth());
            	dayItem.put("year", next24DayItem.getYear());
            	JSONArray periods = new JSONArray();
            	for(int i=0;i<next24DayItem.getNext24DayItemPeriodList().size();i++){
            		Next24DayItemPeriod next24DayItemPeriod = next24DayItem.getNext24DayItemPeriodList().get(i);
            		JSONObject period = new JSONObject();
            		period.put("color", next24DayItemPeriod.getColor());
            		period.put("stid", next24DayItemPeriod.getStid());
            		period.put("etid", next24DayItemPeriod.getEtid());
            		period.put("cooling", next24DayItemPeriod.getCooling());
            		period.put("heating", next24DayItemPeriod.getHeating());
            		period.put("hold", next24DayItemPeriod.getHold());
            		periods.put(period);
            	}
            	dayItem.put("periods",periods);
            	dayItems.put(dayItem);
            }
            schedule.put("dayItems", dayItems);

        } catch (JSONException e) {
            LogUtil.error("DaySchedule", "transferToJson() error", e);
        }
        Log.i("transferToJson",schedule.toString());
        return schedule.toString();
    }
    


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        Next24Schedule o = null;
        o = (Next24Schedule) super.clone();
        if (o != null && next24DayItemList != null) {
            o.next24DayItemList = new ArrayList<Next24DayItem>();
            for (Next24DayItem dayItem : next24DayItemList) {
                o.next24DayItemList.add((Next24DayItem) dayItem.clone());
            }
        }
        return o;
    }
    
    public Next24Schedule getNext24TransferToJson(List<Next24DayItemPeriod> nextDayItemPeriod){
    	Next24Schedule _next24Schedule = ((Next24Schedule)GlobalModels.next24Schedule.clone());
    	List<Next24DayItemPeriod> next24DayItemPeriodList0 = _next24Schedule.next24DayItemList.get(0).getNext24DayItemPeriodList();
    	int ich = getiHour48();
    	Next24DayItemPeriod tmpPeriod = null;
    	List<Next24DayItemPeriod> day0 = new ArrayList<Next24DayItemPeriod>();
    	for(int i=0;i<48;i++){
    		Next24DayItemPeriod cur_ItemPeriod;
    		if(i<=ich){
    			cur_ItemPeriod = _next24Schedule.getNext24DayItemPeriodByHour(next24DayItemPeriodList0, i);
    		}else{
    			cur_ItemPeriod = _next24Schedule.getNext24DayItemPeriodByHour(nextDayItemPeriod, i);
    		}
    		if(i==0){
    			tmpPeriod = (Next24DayItemPeriod)cur_ItemPeriod.clone();
    		}
    		if(tmpPeriod.getHold().equalsIgnoreCase(cur_ItemPeriod.getHold()) && tmpPeriod.getColor().equalsIgnoreCase(cur_ItemPeriod.getColor()) && tmpPeriod.getHeating()==cur_ItemPeriod.getHeating() && tmpPeriod.getCooling()==cur_ItemPeriod.getCooling()){
    			tmpPeriod.setEtid(i+1);
    		}else{
    			day0.add(tmpPeriod);
    			tmpPeriod = (Next24DayItemPeriod)cur_ItemPeriod.clone();
    			tmpPeriod.setStid(i);
    		}
    		if(i==47){
    			tmpPeriod.setEtid(48);
    			day0.add(tmpPeriod);
    		}
    	}
    	_next24Schedule.next24DayItemList.get(0).setNextDayItemPeriodList(day0);
    	
    	if(_next24Schedule.next24DayItemList.size()>1 ){
    		List<Next24DayItemPeriod> next24DayItemPeriodList1 = _next24Schedule.next24DayItemList.get(1).getNext24DayItemPeriodList();
    		tmpPeriod = null;
        	List<Next24DayItemPeriod> day1 = new ArrayList<Next24DayItemPeriod>();
    		for(int i=0;i<48;i++){
        		Next24DayItemPeriod cur_ItemPeriod;
        		if(i>ich){
        			cur_ItemPeriod = _next24Schedule.getNext24DayItemPeriodByHour(next24DayItemPeriodList1, i);
        		}else{
        			cur_ItemPeriod = _next24Schedule.getNext24DayItemPeriodByHour(nextDayItemPeriod, i);
        		}
        		if(i==0){
        			tmpPeriod = (Next24DayItemPeriod)cur_ItemPeriod.clone();
        			tmpPeriod.setStid(0);
        		}
        		if(tmpPeriod.getHold().equalsIgnoreCase(cur_ItemPeriod.getHold()) && tmpPeriod.getColor().equalsIgnoreCase(cur_ItemPeriod.getColor()) && tmpPeriod.getHeating()==cur_ItemPeriod.getHeating() && tmpPeriod.getCooling()==cur_ItemPeriod.getCooling()){
        			tmpPeriod.setEtid(i+1);
        		}else{
        			day1.add(tmpPeriod);
        			tmpPeriod = (Next24DayItemPeriod)cur_ItemPeriod.clone();
        			tmpPeriod.setStid(i);
        		}
        		if(i==47){
        			tmpPeriod.setEtid(48);
        			day1.add(tmpPeriod);
        		}
        	}
    		_next24Schedule.next24DayItemList.get(1).setNextDayItemPeriodList(day1);
    	}
    	return _next24Schedule;
    }

    public List<Next24DayItemPeriod> getCurrNext24(){
    	int ich = getiHour48();
    	Next24Schedule _next24Schedule = ((Next24Schedule)GlobalModels.next24Schedule.clone());
    	List<Next24DayItemPeriod> currNext24 = new ArrayList<Next24DayItemPeriod>();
    	
    	List<Next24DayItemPeriod> currNext24_1 = new ArrayList<Next24DayItemPeriod>();
    	List<Next24DayItemPeriod> next24DayItemPeriodList = _next24Schedule.next24DayItemList.get(0).getNext24DayItemPeriodList();
    	for(Next24DayItemPeriod period: next24DayItemPeriodList){
    		if(ich >= period.getStid() && ich<period.getEtid()){
    			period.setStid(ich);
    			currNext24_1.add(period);
    		}
    		if(ich < period.getStid()){
    			currNext24_1.add(period);
    		}
    	}
    	List<Next24DayItemPeriod> currNext24_2 = new ArrayList<Next24DayItemPeriod>();
    	if(ich!=0){
    		boolean flag = true;
    		List<Next24DayItemPeriod> next24DayItemPeriodList2 = _next24Schedule.next24DayItemList.get(1).getNext24DayItemPeriodList();
        	for(Next24DayItemPeriod period: next24DayItemPeriodList2){
        		if(ich > period.getStid()){
        			if(ich>=period.getEtid()){
        				currNext24_2.add(period);
        			}else{
            			period.setEtid(ich);
            			currNext24_2.add(period);
        			}
        		}
        	}	
    	}
    	sort(currNext24_1);
    	sort(currNext24_2);
    	if(currNext24_2.size()>0){
    		boolean flag = false;
    		boolean flag2 = false;
    		Next24DayItemPeriod tmp1 = currNext24_1.get(currNext24_1.size()-1);
    		Next24DayItemPeriod tmp2 = currNext24_2.get(0);
    		Next24DayItemPeriod tmp4 = null;
    		if(tmp1.getHeating()==tmp2.getHeating() && tmp1.getCooling()==tmp2.getCooling()){
    			currNext24_1.remove(currNext24_1.size()-1);
    			currNext24_2.remove(0);
    			tmp1.setEtid(tmp2.getEtid());
    			flag = true;
    		}
    		if(currNext24_1.size()>0){
	    		Next24DayItemPeriod tmp3 = currNext24_1.get(0);
	    		tmp4 = currNext24_2.get(currNext24_2.size()-1);
	    		if(tmp3.getHeating()==tmp4.getHeating() && tmp3.getCooling()==tmp4.getCooling()){
	    			currNext24_1.remove(0);
	    			currNext24_2.remove(currNext24_2.size()-1);
	    			tmp4.setEtid(tmp3.getEtid());
	    			flag2 = true;
	    		}

    		}else if(currNext24_1.size()==0){//第一天只有一段的情况
    			tmp4 = currNext24_2.get(currNext24_2.size()-1);
    			if(tmp1.getHeating()==tmp4.getHeating() && tmp1.getCooling()==tmp4.getCooling()){
    				currNext24_2.remove(currNext24_2.size()-1);
    				tmp1.setStid(tmp4.getStid());
    			}
    		}
        	for(Next24DayItemPeriod period: currNext24_1){
        		currNext24.add(period);
        	}
        	if(flag){
        		currNext24.add(tmp1);
        	}
        	for(Next24DayItemPeriod period: currNext24_2){
        		currNext24.add(period);
        	}
        	if(flag2){
        		currNext24.add(tmp4);
        	}
    	}else{
        	return currNext24_1;
    	}
    	Log.i("iHour48",""+currNext24.size());
    	return currNext24;
    }
    
    private void sort(List<Next24DayItemPeriod> currNext24List) {
        if (currNext24List != null && currNext24List.size() > 0) {
        	Next24DayItemPeriod tempObject;
            for (int i = 0; i < currNext24List.size(); i++) {
                for (int j = i + 1; j < currNext24List.size(); j++) {
                    int leftStid = ((Next24DayItemPeriod) currNext24List.get(i)).getStid();
                    int rightStid = ((Next24DayItemPeriod) currNext24List.get(j)).getStid();
                    if (leftStid > rightStid) {
                        tempObject = (Next24DayItemPeriod) currNext24List.get(i);
                        currNext24List.set(i, currNext24List.get(j));
                        currNext24List.set(j, tempObject);
                    }
                }
            }
        }
    }
    
    public Next24DayItemPeriod getNext24DayItemPeriodByHour(List<Next24DayItemPeriod> currNext24List,int iHour){
    	if(currNext24List!=null){
	    	for(Next24DayItemPeriod period:currNext24List){
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

	public int getWeeklyId() {
		return weeklyId;
	}

	public void setWeeklyId(int weeklyId) {
		this.weeklyId = weeklyId;
	}

	public int getSetpoint() {
		return setpoint;
	}

	public void setSetpoint(int setpoint) {
		this.setpoint = setpoint;
	}

	public int getHold() {
		return hold;
	}

	public void setHold(int hold) {
		this.hold = hold;
	}

	public String getLocWeb() {
		return locWeb;
	}

	public void setLocWeb(String locWeb) {
		this.locWeb = locWeb;
	}

	public List<Next24DayItem> getNext24DayItemList() {
		return next24DayItemList;
	}

	public void setNext24DayItemList(List<Next24DayItem> next24DayItemList) {
		this.next24DayItemList = next24DayItemList;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public int getiHour48() {
    	int iHour = currentDate.getHours();
    	int iMinutes = currentDate.getMinutes();
    	int ich = iHour * 2;
    	if(iMinutes>=30){
    		ich = ich + 1;
    	}
    	this.iHour48 = ich;
		return this.iHour48;
	}
    
}
