package com.futureadymedia.alumni.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by developer on 10/6/2016.
 */
public class PrefsManager {

    public final static String USER_ID = "KEY_USER_UID";
    public final static String REFERENCE_CODE = "KEY_REFERENCE_CODE";
    public final static String USER_NAME = "KEY_USER_NAME";
    public final static String USER_EMAIL = "KEY_USER_EMAIL";
    public final static String USER_MOBILE = "KEY_USER_MOBILE";
    public final static String USER_PROFILE_PIC = "KEY_USER_PROFILE_PIC";
    public final static String SCHOOL_DETAILS = "KEY_SCHOOL_DETAILS";
    public final static String HOUSE_DETAILS = "KEY_HOUSE_DETAILS";
    public final static String PROFFESIONAL_DETAILS = "KEY_HOUSE_DETAILS";
    public final static String CURRENT_ADDRESS= "KEY_CURRENT_ADDRESS";

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


    //*****************FOR USER DETAILS*************************//

    //*****************FOR USER NAME*************************//
    public String getUserName() {
        return getValue(USER_NAME);
    }

    public void setUserName(String username) {
        setValue(USER_NAME, username);
    }


    //*****************FOR USER EMAIL************************//
    public String getUserEmail() {
        return getValue(USER_EMAIL);
    }

    public void setUserEmail(String useremail) {
        setValue(USER_EMAIL, useremail);
    }


    //*****************FOR USER MOBILE************************//
    public String getUserMobile() {
        return getValue(USER_MOBILE);
    }

    public void setUserMobile(String usermobile) {
        setValue(USER_MOBILE, usermobile);
    }


    //*******************FOR USER PROFILE PIC *******************//
    public String getUserProfilePic() {
        return getValue(USER_PROFILE_PIC);
    }

    public void setUserProfilePic(String userprofilepic) {
        setValue(USER_PROFILE_PIC, userprofilepic);
    }


    //*******************FOR USER SCHOOL DETAILS *******************//
    public String getSchoolDetails() {
        return getValue(SCHOOL_DETAILS);
    }

    public void setSchoolDetails(String schooldetails) {
        setValue(SCHOOL_DETAILS, schooldetails);
    }


    //*******************FOR USER HOUSE DETAILS *******************//
    public String getHouseDetails() {
        return getValue(SCHOOL_DETAILS);
    }

    public void setHouseDetails(String housedetails) {
        setValue(HOUSE_DETAILS, housedetails);
    }


    //*******************FOR USER PROFFESIONAL DETAILS *******************//
    public String getProffesionalDetails() {
        return getValue(PROFFESIONAL_DETAILS);
    }

    public void setProffesionalDetails(String proffesionaldetails) {
        setValue(PROFFESIONAL_DETAILS, proffesionaldetails);
    }


    //*******************FOR USER CURRENT ADDRESS *******************//
    public String getCurrentAddress() {
        return getValue(CURRENT_ADDRESS);
    }

    public void setCurrentAddress(String currentaddress) {
        setValue(CURRENT_ADDRESS, currentaddress);
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
