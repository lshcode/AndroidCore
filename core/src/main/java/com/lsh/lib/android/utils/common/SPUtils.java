package com.lsh.lib.android.utils.common;

import android.content.SharedPreferences;

import com.lsh.lib.android.app.BApplication;

import java.util.Set;

/**
 * sharedperference
 * Author:liush
 * Date:2016/8/25
 */
public class SPUtils {
    private static String NAME = "B_SPUtils";
    private static SharedPreferences sharedPreferences;

    public SPUtils() {
        sharedPreferences = BApplication.instence.getSharedPreferences(NAME, 0);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final SPUtils INSTANCE = new SPUtils();
    }

    //获取单例
    public static SPUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public void add(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.commit();
    }

    public void addSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }


    public String getString(String key, String defaultValue) {

        return sharedPreferences.getString(key, defaultValue);

    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");

    }

    public int getInt(String key, int defaultValue) {

        return sharedPreferences.getInt(key, defaultValue);

    }

    public int getInt(String key) {

        return sharedPreferences.getInt(key, -1);

    }

    public boolean getBoolean(String key, boolean defaultValue) {

        return sharedPreferences.getBoolean(key, defaultValue);

    }

    public boolean getBoolean(String key) {

        return sharedPreferences.getBoolean(key, false);

    }


    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
