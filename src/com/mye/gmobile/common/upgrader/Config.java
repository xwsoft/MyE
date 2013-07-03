package com.mye.gmobile.common.upgrader; 

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.mye.gmobile.R;

/** 
 * @author xwsoft 
 * @version date£º2013-4-19 ÉÏÎç9:59:45 
 * 
 */
public class Config {
    private static final String TAG = "Config";
    
    public static final String UPDATE_APKNAME = "MyE.apk";
    public static final String UPDATE_VERJSON = "ver.json";
    public static final String UPDATE_SAVENAME = "MyE.apk";
    
    
    public static int getVerCode(Context context) {
            int verCode = -1;
            try {
                    verCode = context.getPackageManager().getPackageInfo("com.mye.gmobile", 0).versionCode;
            } catch (NameNotFoundException e) {
                    Log.e(TAG, e.getMessage());
            }
            return verCode;
    }
    
    public static String getVerName(Context context) {
            String verName = "";
            try {
                    verName = context.getPackageManager().getPackageInfo("com.mye.gmobile", 0).versionName;
            } catch (NameNotFoundException e) {
                    Log.e(TAG, e.getMessage());
            }
            return verName; 

    }
    
    public static String getAppName(Context context) {
            String verName = context.getResources()
            .getText(R.string.app_name).toString();
            return verName;
    }
}
