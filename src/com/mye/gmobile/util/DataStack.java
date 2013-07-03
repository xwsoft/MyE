package com.mye.gmobile.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author xwsoft
 * @version date£º2013-4-22 ÏÂÎç2:57:11
 * 
 */
public class DataStack {

	public static void updateBooleanData(Context context, String xmlName,
			String key, boolean value) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		sp.edit().putBoolean(key, value).commit();
	}

	public static void updateFloatData(Context context, String xmlName,
			String key, float value) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		sp.edit().putFloat(key, value).commit();
	}

	public static void updateStringData(Context context, String xmlName,
			String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		sp.edit().putString(key, value).commit();
	}

	public static void updateIntData(Context context, String xmlName,
			String key, int value) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		sp.edit().putInt(key, value).commit();
	}

	public static void updateLongData(Context context, String xmlName,
			String key, long value) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		sp.edit().putLong(key, value).commit();
	}

	public static String getString(Context context, String xmlName, String key) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getString(key, null);
	}

	public static String getString(Context context, String xmlName, String key,
			String defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getString(key, defaultValue);
	}

	public static boolean getBoolean(Context context, String xmlName, String key) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getBoolean(key, false);
	}

	public static boolean getBoolean(Context context, String xmlName,
			String key, boolean defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getBoolean(key, defaultValue);
	}

	public static float getFloat(Context context, String xmlName, String key) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getFloat(key, 0);
	}

	public static float getFloat(Context context, String xmlName, String key,
			float defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getFloat(key, defaultValue);
	}

	public static int getInt(Context context, String xmlName, String key) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getInt(key, 0);
	}

	public static int getInt(Context context, String xmlName, String key,
			int defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getInt(key, defaultValue);
	}

	public static long getLong(Context context, String xmlName, String key) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getLong(key, 0);
	}

	public static long getLong(Context context, String xmlName, String key,
			long defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		return sp.getLong(key, defaultValue);
	}

	public static void clear(Context context, String xmlName) {
		SharedPreferences sp = context.getSharedPreferences(xmlName, 0);
		sp.edit().clear().commit();
	}
}
