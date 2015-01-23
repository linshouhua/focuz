package com.focus.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BasePreferences {
    private SharedPreferences mPref;

    public BasePreferences(Context context, String fileName) {
        mPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        Editor editor = mPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defValue) {
        return mPref.getString(key, defValue);
    }

    public void clear() {
        Editor editor = mPref.edit();
        editor.clear();
        editor.commit();
    }

    public void remove(String key) {
        Editor editor = mPref.edit();
        editor.remove(key);
        editor.commit();
    }
}
