package com.mye.gmobile.model; 

import java.util.ArrayList;
import java.util.List;

/** 
 * @author xwsoft 
 * @version date：2013-5-6 上午11:51:15 
 * 
 */
public class Next24DayItem implements Cloneable{

	private int day;
	private int month;
	private int year;
	private List<Next24DayItemPeriod> next24DayItemPeriodList = new ArrayList<Next24DayItemPeriod>();
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<Next24DayItemPeriod> getNext24DayItemPeriodList() {
		return next24DayItemPeriodList;
	}
	public void setNextDayItemPeriodList(
			List<Next24DayItemPeriod> next24DayItemPeriodList) {
		this.next24DayItemPeriodList = next24DayItemPeriodList;
	}
	
    public Object clone() {
    	Next24DayItem o = null;
        try {
			o = (Next24DayItem) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
        if (next24DayItemPeriodList != null) {
            o.next24DayItemPeriodList = new ArrayList<Next24DayItemPeriod>();
            for (Next24DayItemPeriod period : next24DayItemPeriodList) {
                o.next24DayItemPeriodList.add((Next24DayItemPeriod) period.clone());
            }
        }
        return o;
    }
}
