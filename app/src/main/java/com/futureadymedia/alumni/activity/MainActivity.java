package com.futureadymedia.alumni.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.fragment.FragmentBatchMates;
import com.futureadymedia.alumni.fragment.FragmentCurrentAddress;
import com.futureadymedia.alumni.fragment.FragmentDirectoryListing;
import com.futureadymedia.alumni.fragment.FragmentDrawer;
import com.futureadymedia.alumni.fragment.FragmentForgotpassword;
import com.futureadymedia.alumni.fragment.FragmentGeorgiansNearby;
import com.futureadymedia.alumni.fragment.FragmentHouseMates;
import com.futureadymedia.alumni.fragment.FragmentInfo;
import com.futureadymedia.alumni.fragment.FragmentErrorMessage;
import com.futureadymedia.alumni.fragment.FragmentProffesionalDetails;
import com.futureadymedia.alumni.fragment.FragmentProffesionalResult;
import com.futureadymedia.alumni.fragment.FragmentProffesionalsToKnow;
import com.futureadymedia.alumni.fragment.FragmentSchoolDetails;
import com.futureadymedia.alumni.fragment.FragmentSchoolDetails1;
import com.futureadymedia.alumni.fragment.FragmentSchoolMates;
import com.futureadymedia.alumni.fragment.FragmentSendInvite;
import com.futureadymedia.alumni.fragment.FragmentSignupLogin;
import com.futureadymedia.alumni.fragment.FragmentSplash;
import com.futureadymedia.alumni.fragment.FragmentSuccessMessage;
import com.futureadymedia.alumni.fragment.FragmentUserDashboard;
import com.futureadymedia.alumni.fragment.FragmentUserProfile;
import com.futureadymedia.alumni.fragment.FragmentVerification;
import com.futureadymedia.alumni.fragment.MapViewFragment;
import com.futureadymedia.alumni.listeners.FragmentChangeListener;
import com.futureadymedia.alumni.utils.CommonUtils;
import com.futureadymedia.alumni.utils.Constants;
import com.futureadymedia.alumni.utils.DrawerLocker;
import com.futureadymedia.alumni.utils.PrefsManager;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, DrawerLayout.DrawerListener, FragmentChangeListener, FragmentManager.OnBackStackChangedListener, DrawerLocker {

    private static String TAG = MainActivity.class.getSimpleName();

    public static Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    public static FrameLayout flContainer;
    private Fragment fragment;
    private int position = 1;
    private FragmentManager fm;
    private PrefsManager prefsManager;
    private Context context;
    private Menu menu;
    public static TextView tvTitle;
    public String title;
    public static HashMap<String, List<String>> schoolDetails = new HashMap<String,List<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        prefsManager = new PrefsManager(context);
        flContainer = (FrameLayout) findViewById(R.id.container_body);

        if (getIntent() != null) {
            position = getIntent().getIntExtra(Constants.FRAGMENT_SECTION, Constants.FRAGMENT_SPALSH);
        }

        Log.e("Dashboard", "positioin===========>" + position);

        title = getString(R.string.app_name);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView)mToolbar.findViewById(R.id.tvTitle);


        setSupportActionBar(mToolbar);


        toggleEnable();

        fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(this);


        if(prefsManager.getUserId().length() != 0)
        {
            ((DrawerLocker) context).setDrawerEnabled(true);

            position = 6;

        }
        else
        {
            ((DrawerLocker) context).setDrawerEnabled(false);
        }
        setLoadingFragment(position, null);

        // display the first navigation drawer view on app launch
       // displayView(0);
        //FragmentDrawer.mDrawerToggle.setDrawerIndicatorEnabled(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(prefsManager.getUserId().length() != 0) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }*/

        switch(id)
        {
            case R.id.action_settings:
                break;

            case R.id.action_profile:
                onFragmentChange(8);
                break;

            case R.id.action_logout:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch(position){
            case 0:
                setLoadingFragment(12, view);
                break;

            case 1:
                setLoadingFragment(13, view);
                break;

            case 2:
                setLoadingFragment(16, view);
                break;

            case 3:
                setLoadingFragment(18, view);
                break;

            case 4:
                setLoadingFragment(17, view);
                break;

            case 5:
                setLoadingFragment(15, view);
                break;

            case 7:
                setLoadingFragment(19, view);
                break;
        }

    }

    private void setLoadingFragment(final int position, View view) {
        Log.e("Position", "" + position);
        fragment = null;
        /*FragmentDrawer.mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);*/
        switch (position) {
            case 0:
                fragment = new FragmentSplash();
                title = "Welcome";
                CommonUtils.setFragment(fragment, true, MainActivity.this, flContainer, "Splash", title);
                // Log.e("Nav click", "Case"+position);
                break;

            case 1:
                fragment = new FragmentInfo();
                title = "GEORGIAN CONNECT";
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "Info", title);
                // Log.e("Nav click", "Case"+position);
                break;
            case 2:
                fragment = new FragmentSignupLogin();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "signuplogin", title);
                break;
            case 3:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentForgotpassword();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "forgotpassword", title);
                break;

            case 4:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentVerification();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "verificationfragment", title);
                break;

            case 5:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentErrorMessage();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "errormessagefragment", title);
                break;

            case 6:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                title = "GEORGIAN CONNECT";
                fragment = new FragmentUserDashboard();
                ((DrawerLocker) context).setDrawerEnabled(true);
                CommonUtils.setFragment(fragment, true, MainActivity.this, flContainer, "dashboardfragment", title);
                break;

            case 7:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentSuccessMessage();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "successmessagefragment", title);
                break;

            case 8:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentUserProfile();
                CommonUtils.setFragment(fragment, true, MainActivity.this, flContainer, "userprofile", title);
                break;

            case 9:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentCurrentAddress();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "currentaddress", title);
                break;

            case 10:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentSchoolDetails();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "schooldetails", title);
                break;

            case 11:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentProffesionalDetails();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "proffesionaldetails", title);
                break;

            case 12:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentGeorgiansNearby();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "georgiansnearby", title);
                break;

            case 13:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentProffesionalsToKnow();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "proffesionalstoknow", title);
                break;

            case 14:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentProffesionalResult();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "proffesionalresult", title);
                break;

            case 15:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                fragment = new FragmentDirectoryListing();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "directorylisting", title);
                break;

            case 16:
                fragment = new FragmentBatchMates();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "batchmates", title);
                break;

            case 17:
                fragment = new FragmentSchoolMates();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "schoolmates", title);
                break;

            case 18:
                fragment = new FragmentHouseMates();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "housemates", title);
                break;

            case 19:
                fragment = new FragmentSendInvite();
                CommonUtils.setFragment(fragment, false, MainActivity.this, flContainer, "sendinvite", title);
                break;

            default:
                break;
        }
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {

            case 0:
                fragment = new FragmentSplash();
                // Log.e("Nav click", "Case"+position);
                break;

            case 1:
                fragment = new MapViewFragment();
                title = getString(R.string.title_home);
               // Log.e("Nav click", "Case"+position);
                break;
            case 2:
              /*  fragment = new FriendsFragment();
                title = getString(R.string.title_friends);*/
                Log.e("Nav click", "Case"+position);
                break;
            case 3:
              /*  fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                Log.e("Nav click", "Case"+position);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onFragmentChange(int position) {
        Log.e("Test", ""+position);
        setLoadingFragment(position, null);
    }

    @Override
    public void onFragmentChange(int position, List<String> stringList) {
        Log.e("Test", "inside fragment change1");
        setLoadingFragment(position, null);
    }

    @Override
    public void onBackStackChanged() {
        fm = getSupportFragmentManager();
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
        {
            String fragmentTag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();
            if(fragmentTag.equals("forgotpassword") || fragmentTag.equals("verificationfragment") || fragmentTag.equals("messagefragment"))
            {
                backEnabled(R.drawable.ic_action_cancel);
            }
            else
            {
               // toggleEnable();
            }
            // shouldDisplayHomeUp();
            Log.e("BACK", "caleed backstack"+fragmentTag+"-----"+getSupportFragmentManager().getBackStackEntryCount());
        }

    }


    @Override
    public void onBackPressed() {
        //getSupportFragmentManager().popBackStack();

        super.onBackPressed();
    }

    public void toggleEnable(){
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }

    public void backEnabled(int resid)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Log.e("BACK", "frgtpswdclicked");
        FragmentDrawer.mDrawerToggle.setDrawerIndicatorEnabled(false);
        mToolbar.setNavigationIcon(resid);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getSupportFragmentManager();
                fm.popBackStackImmediate();
            }
        });
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        FragmentDrawer.mDrawerLayout.setDrawerLockMode(lockMode);
        FragmentDrawer.mDrawerToggle.setDrawerIndicatorEnabled(enabled);
        FragmentDrawer.mDrawerToggle.syncState();
    }

    public static void setTitle(String title){
        tvTitle.setText(title);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
