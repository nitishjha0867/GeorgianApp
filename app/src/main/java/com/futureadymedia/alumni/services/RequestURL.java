package com.futureadymedia.alumni.services;

/**
 * Created by ashutoshkumar on 26/5/15.
 */
public class RequestURL {

     public static final String BASE_URL = "http://ssprojects.online/georgian_panel/";



    public static String POST="POST";
    public static String GET="GET";
    public static String PUT="PUT";
    public static String DELETE="DELETE";


    public static final int CONNECTION_TIME_OUT = 30000;
    public static final String UTF = "UTF-8";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE = "CONTENT_TYPE";
    public static final String SUCCESS_CODE = "200";


   /* public static final String SIGN_IN = BASE_URL.trim()+"uid_check.php";
    public static final String SYNC_CLIENT = BASE_URL.trim()+"client_insert.php";
    public static final String SYNC_APPOINTMENT = BASE_URL.trim()+"appointment_insert.php";
    public static final String UPDATE_PROFILE = BASE_URL.trim()+"update_profile.php";
    public static final String SYNC_PROMOS = BASE_URL.trim()+"fetch_promos.php";*/

    public static  final String ADD_UPDATE_USER = BASE_URL.trim()+"add_update_user_data.php";
    public static  final String LOGIN_USER = BASE_URL.trim()+"login_user.php";
    public static final String PROFILE_UPLOAD = BASE_URL.trim()+"profile_data.php";
    public static final String SCHOOL_UPLOAD = BASE_URL.trim()+"save_school.php";
    public static final String ADDRESS_UPLOAD = BASE_URL.trim()+"save_address.php";
    public static final String PROFFESIONAL_UPLOAD = BASE_URL.trim()+"save_proffesional.php";
    public static final String GET_PERSONAL_DATA = BASE_URL.trim()+"fetch_personal_info.php";
    public static final String UPDATE_SCHOOL_DATA = BASE_URL.trim()+"update_school_data.php";
    public static final String GET_SCHOOL_DATA = BASE_URL.trim()+"fetch_school_data.php";
    public static final String GET_HOUSE_DATA = BASE_URL.trim()+"fetch_house_data.php";
    public static final String UPDATE_HOUSE_DATA = BASE_URL.trim()+"update_house_data.php";

}
