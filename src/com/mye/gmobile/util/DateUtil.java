package com.mye.gmobile.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类,有关日期的处理都写在些类中
 * 
 */
public class DateUtil {

	public static SimpleDateFormat dateHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 将日期类型转换为字符串日期格式。
	 */
	public static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat();
	
	/**
	 * 通过当前传入的年月取得当前月最大的天数
	 * @param year
	 * @param month
	 * @return 当前月最大的天数
	 */
	public static int getCurrLastDay(int year,int month) {
		Date d1 = new Date(year,month,0);//获取的为1991年2月份的天数
		return d1.getDate();
	}
	
	public static Date str2Date(String strDate) {
		dateFormat.applyPattern("MM/dd/yyyy");
		Date dateTime = null;
		try {
			dateTime = dateFormat.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
	}
	/**
	 * 根据zone返回yyyy-MM-dd HH:mm:ss格式的日期字符串
	 * 
	 * @param timeZone
	 * @return
	 */
	public static String dateToStrHMS(int timeZone) {
		Calendar calendar = getCurrentDateByZone(timeZone);
		Date date = calendar.getTime();
		String strHMS = dateHMS.format(date);
		return strHMS;
	}

	/**
	 * 把日期类型转换成yyyy-MM-dd的日期格式返回
	 * 
	 * @param now
	 * @return
	 * @throws Exception
	 */
	public static Date dateFormatYMD(Date now) throws Exception {
		return format.parse(dateformat.format(now));

	}

	/**
	 * 根据zone返回当前时区的日期以字符串MM/dd/yyyy HH:mm格式
	 * 
	 * @param timeZone
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentTimeToStrYMDHM(int timeZone) {
		Calendar cal = getCurrentDateByZone(timeZone);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		return month + "/" + day + "/" + year + " " + hour + ":" + min;
	}

	/**
	 * 根据美国的时区获取具体的时间.
	 * 
	 * @param timeZone
	 * @return
	 * @throws Exception
	 */
	public static Date getTodayDateByTimeZone(int timeZone) {
		Calendar calendar = getCurrentDateByZone(timeZone);
		return calendar.getTime();
	}

	/**
	 * 根据zone时区取到当前calendar对象
	 * 
	 * @param timeZone
	 * @return
	 * @throws Exception
	 */
	public static Calendar getCurrentDateByZone(int timeZone) {
		int hr = 0;
		if (timeZone == 1) {
			hr = 1;
		} else if (timeZone == 2) {
			hr = 0;
		} else if (timeZone == 3) {
			hr = -1;
		} else if (timeZone == 4) {
			hr = -2;
		}else if (timeZone == 5) {
			hr = -3;
		} else if (timeZone == 6) {
			hr = -4;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, hr);
		return calendar;
	}

	/**
	 * 根据zone时区取到当前小时
	 * 
	 * @param timeZone
	 * @return
	 * @throws Exception
	 */
	public static int getCurrentTimeHour(int timeZone) {
		Calendar calendar = getCurrentDateByZone(timeZone);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	/**
	 * 根据星期几返回星期字符串
	 * 
	 * @param day
	 * @return
	 */
	public static String getDayOfWeek(int day) {
		String week = "";
		switch (day) {
		case 1:
			week = "Sunday";
			break;
		case 2:
			week = "Monday";
			break;
		case 3:
			week = "Tuesday";
			break;
		case 4:
			week = "Wednesday";
			break;
		case 5:
			week = "Thursday";
			break;
		case 6:
			week = "Friday";
			break;
		case 7:
			week = "Saturday";
			break;
		}
		return week;
	}

	/***
	 * 
	 * @param date
	 *            获取星期几
	 */
	public static int showDay(int year, int month, int day) {
		dateFormat.applyPattern("yyyy:MM:dd");
		String dateStr = year + ":" + month + ":" + day;
		Date dateTime = null;
		try {
			dateTime = dateFormat.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cd = Calendar.getInstance();
		cd.setTime(dateTime);
		int mydate = cd.get(Calendar.DAY_OF_WEEK);
		int[] dayNames = { 7, 1, 2, 3, 4, 5, 6 };
		if (mydate < 0) {
			mydate = 0;
		}
		return dayNames[mydate - 1];
	}

	/***
	 * 
	 * @param date
	 *            获取一周的日期
	 */
	public static List<Date> getThisWeekDate(Date date) throws Exception {

		List<Date> dateList = new ArrayList<Date>();
		// 改为从当天起取未来7天的日期
		dateList.add(addDateByDay(date, 0));
		dateList.add(addDateByDay(date, 1));
		dateList.add(addDateByDay(date, 2));
		dateList.add(addDateByDay(date, 3));
		dateList.add(addDateByDay(date, 4));
		dateList.add(addDateByDay(date, 5));
		dateList.add(addDateByDay(date, 6));
		/*
		 * dateList.add(getMonday(date)); dateList.add(getFeb(date));
		 * dateList.add(getWednesday(date)); dateList.add(getFriday(date));
		 * dateList.add(getSaturday(date)); dateList.add(getSunDay(date));
		 * dateList.add(getThursday(date));
		 */
		return dateList;

	}

	private static Date addDateByDay(Date date, int iDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, iDay);
		return c.getTime();
	}

	// 获得周一的日期
	private Date getMonday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	// 获得周五的日期
	private Date getFriday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

		return c.getTime();
	}

	// 获得周二的日期
	private Date getFeb(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		return c.getTime();
	}

	// 获得周三的日期
	private Date getWednesday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		return c.getTime();
	}

	// 获得周四的日期
	private Date getThursday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		return c.getTime();
	}

	// 获得周六的日期
	private Date getSaturday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return c.getTime();
	}

	// 获得周日的日期
	private Date getSunDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return c.getTime();
	}
	
	public int CompareTime(String time1, String time2) {
		int iResult = 1;
		try {
			String[] arrTime1 = time1.split(":");
			String[] arrTime2 = time2.split(":");
			int _h1 = Integer.parseInt(arrTime1[0]);
			int _h2 = Integer.parseInt(arrTime2[0]);
			if (_h1 < _h2) {
				iResult = -1;
			} else if (_h1 == _h2) {
				int _m1 = Integer.parseInt(arrTime1[1]);
				int _m2 = Integer.parseInt(arrTime2[1]);
				if(_m1<_m2){
					iResult = -1;
				}else if(_m1==_m2){
					iResult = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iResult;
	}
	
	public int CompareDate(String date1,String date2){
		int iResult = 1;
		try {
			Date _SDate = dateformat.parse(date1);
			Date _EDate = dateformat.parse(date2);
			return _SDate.compareTo(_EDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return iResult;
	}
	
	public String addDateDay(String date,int iDay){
		Date _Date;
		try {
			_Date = dateformat.parse(date);
			Calendar _date = Calendar.getInstance();
			_date.setTime(_Date);
			_date.add(Calendar.DAY_OF_MONTH, 1);
			return dateformat.format(_date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) throws ParseException {
		int day = DateUtil.getCurrLastDay(2013,4);
		System.out.println(day);
		Date date = DateUtil.str2Date("06/06/2013");
		System.out.println(date);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
		System.out.println(dateformat.format(new Date()));
		System.out.println(dateformat.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		System.out.println(dateformat.format(calendar.getTime()));
		Date d = dateformat.parse("05/09/2013");
		System.out.println(d.compareTo(date));
	}
}
