package com.breeziness.timetable.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    public static void saveString(Context context, String name, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveInt(Context context, String name, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveBoolen(Context context, String name, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getString(Context context, String name, String key, String defValue) {
        if (!name.equals("") && context != null) {
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return sp.getString(key, defValue);
        } else {
            return "";
        }

    }

    public static int getInt(Context context, String name, String key, int defValue) {
        if (!name.equals("") && context != null) {
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return sp.getInt(key, defValue);
        } else {
            return 0;
        }
    }    public static boolean getBoolen(Context context, String name, String key, boolean defValue) {
        if (!name.equals("") && context != null) {
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            return sp.getBoolean(key, defValue);
        } else {
            return false;
        }
    }


}
