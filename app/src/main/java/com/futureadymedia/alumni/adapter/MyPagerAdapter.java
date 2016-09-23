package com.futureadymedia.alumni.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.futureadymedia.alumni.fragment.FragmentLogin;
import com.futureadymedia.alumni.fragment.FragmentSignup;

/**
 * Created by developer on 5/6/2016.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 2;
    private FragmentSignup signupFragment;
    private FragmentLogin loginFragment;


    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
       Log.e("APPDEBUG1", ""+position);
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                if(signupFragment==null) {
                    signupFragment = new FragmentSignup();
                }
                return signupFragment;

            case 1: // Fragment # 0 - This will show FirstFragment different title
                if(loginFragment ==null) {
                    loginFragment = new FragmentLogin();
                }
                return loginFragment;

            default:
                return null;
        }
    }

   /* @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);

        Log.e("Testin", ""+position);
        // save the appropriate reference depending on position
       /* switch (position) {
            case 0:
                m1stFragment = (FragmentA) createdFragment;
                break;
            case 1:
                m2ndFragment = (FragmentB) createdFragment;
                break;

        }*/

       /* switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                if(signupFragment==null) {
                    signupFragment = new FragmentSignup();
                }

            case 1: // Fragment # 0 - This will show FirstFragment different title
                if(loginFragment ==null) {
                    loginFragment = new FragmentLogin();
                }

        }

        return createdFragment;
    }

   /* public int getItemPosition(Object object) {
        return POSITION_NONE;
    }*/

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }



}

