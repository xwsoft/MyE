package com.mye.gmobile.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-1-16
 */
public class CheckUtil {

    private static Context context = null;

    public synchronized static void initContext(Context context) {
        if (CheckUtil.context == null) {
            CheckUtil.context = context;
        }
    }

    /**
     * The cooling temperature must be at least higher 4 than the heating
     * temperature.
     * 
     * @param heating
     * @param cooling
     * @return
     * @author:DongYu 2012-1-16
     */
    public static boolean isLegalMode(int heating, int cooling) {
        if (cooling - heating > 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check IP.
     * 
     * @param Ip
     * @return
     * @author:DongYu 2012-3-1
     */
    public static boolean checkIpAddressFormat(String ip) {
        if (ip != null && !"".equals(ip)) {
            Pattern p = Pattern
                            .compile("^[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}$");
            Matcher m = p.matcher(ip);
            if (!m.matches()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean checkPortFormat(String port) {
        if (port == null || "".equals(port)) {
            return false;
        } else {
            Pattern p = Pattern.compile("[1-9][0-9]{0,4}");
            Matcher m = p.matcher(port);
            if (!m.matches()) {
                return false;
            }
        }
        return true;
    }

    public static float getTextHeight(int textSize) {
        Paint mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.LEFT);
        FontMetrics fm = mPaint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * alter error dialog.
     * 
     * @param context
     * @param errorMessage
     * @author:DongYu 2012-5-3
     */
    public static void alertErrorDialog(Context context, String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static float round(double d, int times) {// times��ʾ����С���timesλ��scale��ʾ������?
        int value = 1;
        for (int i = 0; i <= times; i++) {
            value *= 10;
        }
        int a = ((int) (d * value)) % 10;
        if (a >= 5) {
            a = (int) (d * value / 10) + 1;
        } else {
            a = (int) (d * value / 10);
        }
        float result = ((float) (a * 1.0)) / (value / 10);
        return result;
    }
    
    public static String cut(String data){
    	data = data.trim();
    	data = data.replace("\r", "");
		data = data.replace("\n", "");
		return data;
    }

    /**
     * Get round string value.
     * 
     * @param value
     *            double
     * @param format
     *            for example: 0.00
     * @return
     * @author:DongYu 2012-5-17
     */
    public static String double2String(double value, String format) {
        DecimalFormat temp = new DecimalFormat(format);
        return temp.format(value);
    }

    public static long round(double value) {
        return Math.round(value);
    }

    /**
     * check whether the current net works.
     * 
     * @return
     * @author:DongYu 2012-5-17
     */
    public static boolean isNetworkAvailable() {
        boolean result = false;
        Context context1 = context.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context1
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

}
