package com.youyou.xiaofeibao.framework.preferences.item;


import com.youyou.xiaofeibao.framework.preferences.SharedPreferencesWrapper;

/**
 * @author jingzuo
 */
public class BooleanPreferences extends BasePreferences {

    private boolean defaultValue;

    public BooleanPreferences(SharedPreferencesWrapper sharedPreferencesWrapper, String sharedPreferencesKey, boolean sharedPreferencesDefaultValue) {
        super(sharedPreferencesWrapper, sharedPreferencesKey);
        defaultValue = sharedPreferencesDefaultValue;
    }

    public void set(boolean value) {
        getSharedPreferences().edit().putBoolean(getSharedPreferencesKey(), value).commit();
    }

    public boolean get() {
        return getSharedPreferences().getBoolean(getSharedPreferencesKey(), getDefaultValue());
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }
}
