/*
 * DayForecastList.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-1-16        DongYu      create
 */

package com.mye.gmobile.model;

/**
 * Forecast List.
 * 
 * @author DongYu
 * @version 1.0 2012-1-16
 */

public class DayForecastList {

    /**
     * house list.
     */
    private DayForecast[] forecasts = null;

    /**
     * get the forecasts.
     * 
     * @return the forecasts
     */
    public DayForecast[] getForecasts() {
        return forecasts;
    }

    /**
     * set the forecasts.
     * 
     * @param forecasts
     *            the forecasts to set
     */
    public void setForecasts(DayForecast[] forecasts) {
        this.forecasts = forecasts;
    }

}
