package com.futureadymedia.alumni.activity;

/**
 * Created by developer on 9/16/2016.
 */
import android.support.v4.app.FragmentActivity;


public abstract class BaseActivity extends FragmentActivity {
    public abstract void findId();
    public abstract void setListeners();
    public abstract void setFont();
}