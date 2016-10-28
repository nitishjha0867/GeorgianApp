package com.futureadymedia.alumni.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by developer on 10/6/2016.
 */
public class PrefsManager {

    public final static String USER_ID = "KEY_USER_UID";
    public final static String REFERENCE_CODE = "KEY_REFERENCE_CODE";

    Context context;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public PrefsManager(Context context) {
        super();
        this.context = context;

        this.preferences = this.context.getSharedPreferences(Constants.APP_PREF, context.MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    //*****************FOR USER LOGIN**********************************//
    public String getUserId() {
        return getValue(USER_ID);
    }

    public void setUserId(String userid) {
        setValue(USER_ID, userid);
    }


    /**
     * Retrieving the value from the preference for the respective key.
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as boolean.
     */
    public boolean getBooleanValue(String key) {
        return this.preferences.getBoolean(key, false);
    }

    /**
     * Saving the preference
     * @param key : Key of the preference.
     * @param value : Value of the preference.
     */
    public void setBooleanValue(String key, boolean value) {
        this.editor.putBoolean(key, value);
        this.editor.commit();
    }

    /**
     * Retrieving the value from the preference for the respective key.
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as string.
     */
    public String getValue(String key) {
        return this.preferences.getString(key, "");
    }

    /**
     * Saving the preference
     * @param key : Key of the preference.
     * @param value : Value of the preference.
     */
    public void setValue(String key, String value) {
        this.editor.putString(key, value);
        this.editor.commit();
    }

    /**
     * Retrieving the value from the preference for the respective key.
     * @param key : Key for which the value is to be retrieved
     * @return return value for the respective key as string.
     */
    public int getIntValue(String key) {
        return this.preferences.getInt(key, 100);
    }

    /**
     * Saving the preference
     * @param key : Key of the preference.
     * @param value : Value of the preference.
     */
    public void setIntValue(String key, int value) {
        this.editor.putInt(key, value);
        this.editor.commit();
    }

    /**
     * Removes all the fields from SharedPrefs
     */
    public void clearPrefs() {
        this.editor.clear();
        this.editor.commit();

    }

    /**
     *Remove the preference for the perticular key
     * @param key : Key for which the preference to be cleared.
     */
    public void removeFromPreference(String key){
        this.editor.remove(key);
        this.editor.commit();
    }
}
