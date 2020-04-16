package com.hokagelab.www.cataloguemovie.scheduler;

import android.content.Context;
import android.content.SharedPreferences;

public class AlarmPreference {
    private final String PREF_NAME = "com.hokagelab.myalarmmanager.AlarmPreference";
    private final String KEY_ONE_TIME_DATE = "oneTimDate";
    private final String KEY_ONE_TIME_TIME = "oneTimeTime";
    private final String KEY_ONE_TIME_MESSAGE = "oneTimeMessage";
    private final String KEY_REPEATING_TIME = "repeatingTime";
    private final String KEY_REPEATING_MESSAGE = "repeatingMessage";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;


    public AlarmPreference(Context context){
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }
    public void setOneTimeDate(String date){
        editor.putString(KEY_ONE_TIME_DATE, date);
        editor.commit();
    }
    public String getOneTimeDate(){
        return mSharedPreferences.getString(KEY_ONE_TIME_DATE, null);
    }
    public void setOneTimeTime(String time){
        editor.putString(KEY_ONE_TIME_TIME, time);
        editor.commit();
    }
    public String getOneTimeTime(){
        return mSharedPreferences.getString(KEY_ONE_TIME_TIME, null);
    }
    public void setOneTimeMessage(String message){
        editor.putString(KEY_ONE_TIME_MESSAGE, message);
        editor.commit();
    }
    public String getOneTimeMessage(){
        return mSharedPreferences.getString(KEY_ONE_TIME_MESSAGE, null);
    }
    public void setRepeatingTime(String time){
        editor.putString(KEY_REPEATING_TIME, time);
        editor.commit();
    }
    public String getRepeatingTime(){
        return mSharedPreferences.getString(KEY_REPEATING_TIME, null);
    }
    public void setRepeatingMessage(String message){
        editor.putString(KEY_REPEATING_MESSAGE, message);
        editor.commit();
    }
    public String getRepeatingMessage(){
        return mSharedPreferences.getString(KEY_REPEATING_MESSAGE, null);
    }
    public void clear(){
        editor.clear();
        editor.commit();
    }
}