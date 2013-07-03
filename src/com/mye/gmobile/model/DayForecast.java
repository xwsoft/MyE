package com.mye.gmobile.model;

import android.util.Log;

/**
 * Forecast.
 * 
 * @author DongYu
 * @version 1.0 2012-1-16
 */
public class DayForecast implements Cloneable {

    /**
     * current temperature.
     */
    private double currentTemp = 0;

    /**
     * low temperature.
     */
    private double lowTemp = 0;

    /**
     * high temperature.
     */
    private double highTemp = 0;

    /**
     * weather.
     */
    private String weather = null;

    /**
     * humidity
     */
    private int humidity = 0;

    /**
     * get the currentTemp.
     * 
     * @return the currentTemp
     */
    public double getCurrentTemp() {
        return currentTemp;
    }

    /**
     * set the currentTemp.
     * 
     * @param currentTemp
     *            the currentTemp to set
     */
    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    /**
     * get the lowTemp.
     * 
     * @return the lowTemp
     */
    public double getLowTemp() {
        return lowTemp;
    }

    /**
     * set the lowTemp.
     * 
     * @param lowTemp
     *            the lowTemp to set
     */
    public void setLowTemp(double lowTemp) {
        this.lowTemp = lowTemp;
    }

    /**
     * get the highTemp.
     * 
     * @return the highTemp
     */
    public double getHighTemp() {
        return highTemp;
    }

    /**
     * set the highTemp.
     * 
     * @param highTemp
     *            the highTemp to set
     */
    public void setHighTemp(double highTemp) {
        this.highTemp = highTemp;
    }

    /**
     * get the weather.
     * 
     * @return the weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     * set the weather.
     * 
     * @param weather
     *            the weather to set
     */
    public void setWeather(String weather) {
        this.weather = weather;
    }

    /**
     * get the humidity.
     * 
     * @return the humidity
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * set the humidity.
     * 
     * @param humidity
     *            the humidity to set
     */
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        DayForecast o = null;
        try {
            o = (DayForecast) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("DayForecast", "error in clone()", e);
        }
        return o;
    }

}
