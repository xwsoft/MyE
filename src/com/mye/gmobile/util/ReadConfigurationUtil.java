/*
 * ReadConfigurationUtil.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-2-21        DongYu      create
 */

package com.mye.gmobile.util;

import android.content.Context;
import android.content.res.Resources;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-2-21
 */

public class ReadConfigurationUtil {

    private static Resources resource = null;

    public synchronized static void initConfiguration(Context context) {
        if (context != null) {
            resource = context.getResources();
        }
    }

    /**
     * get configuration data.
     * 
     * @param resourceId
     *            resource id, for example: R.string.timeoutConnection
     * @param defaultValue
     *            default value.
     * @return
     * @author:DongYu 2012-2-21
     */
    public static int getIntValue(int resourceId, int defaultValue) {
        int result = defaultValue;
        if (resource != null) {
            String temp = resource.getString(resourceId);
            if (temp != null && !"".equals(temp.trim())) {
                result = Integer.parseInt(temp.trim());
            }
        }
        return result;
    }

    /**
     * get configuration data.
     * 
     * @param resourceId
     *            resource id, for example: R.string.timeoutConnection
     * @return
     * @author:DongYu 2012-2-21
     */
    public static int getIntValue(int resourceId) {
        return getIntValue(resourceId, 0);
    }

    /**
     * get configuration data.
     * 
     * @param resourceId
     *            resource id, for example: R.string.timeoutConnection
     * @param defaultValue
     *            default value.
     * @return
     * @author:DongYu 2012-2-21
     */
    public static String getStringValue(int resourceId, String defaultValue) {
        String result = defaultValue;
        if (resource != null) {
            result = resource.getString(resourceId);
        }
        return result;
    }

    /**
     * get configuration data.
     * 
     * @param resourceId
     *            resource id, for example: R.string.timeoutConnection
     * @return
     * @author:DongYu 2012-2-21
     */
    public static String getStringValue(int resourceId) {
        return getStringValue(resourceId, "");
    }

    /**
     * get configuration data.
     * 
     * @param resourceId
     *            resource id, for example: R.string.timeoutConnection
     * @param defaultValue
     *            default value.
     * @return
     * @author:DongYu 2012-2-21
     */
    public static boolean getBooleanValue(int resourceId, boolean defaultValue) {
        boolean result = defaultValue;
        if (resource != null) {
            String temp = resource.getString(resourceId);
            if (temp != null && !"".equals(temp.trim())) {
                result = Boolean.parseBoolean(temp.trim());
            }
        }
        return result;
    }

    /**
     * get configuration data.
     * 
     * @param resourceId
     *            resource id, for example: R.string.timeoutConnection
     * @return
     * @author:DongYu 2012-2-21
     */
    public static boolean getBooleanValue(int resourceId) {
        return getBooleanValue(resourceId, false);
    }

}
