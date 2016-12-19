package com.futureadymedia.alumni.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.futureadymedia.alumni.fragment.FragmentInfo;
import com.futureadymedia.alumni.fragment.FragmentLogin;
import com.futureadymedia.alumni.fragment.FragmentSignup;
import com.futureadymedia.alumni.fragment.FragmentSplash1;
import com.futureadymedia.alumni.fragment.FragmentSplash2;
import com.futureadymedia.alumni.fragment.FragmentSplash3;
import com.futureadymedia.alumni.fragment.FragmentSplash4;
import com.futureadymedia.alumni.fragment.FragmentSplash5;

/**
 * Created by developer on 5/6/2016.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 5;
    private FragmentSplash1 splash1;
    private FragmentSplash2 splash2;
    private FragmentSplash3 splash3;
    private FragmentSplash4 splash4;
    private FragmentSplash5 splash5;


    public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
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
                if(splash1==null) {
                    splash1 = new FragmentSplash1();
                }
                return splash1;

            case 1: // Fragment # 1 - This will show SecondFragment different title
                if(splash5 ==null) {
                    splash5 = new FragmentSplash5();
                }
                return splash5;

            case 2: // Fragment # 2 - This will show ThirdFragment different title
                if(splash3 ==null) {
                    splash3 = new FragmentSplash3();
                }
                return splash3;

            case 3: // Fragment # 3 - This will show ThirdFragment different title
                if(splash4 ==null) {
                    splash4 = new FragmentSplash4();
                }
                return splash4;

            case 4: // Fragment # 2 - This will show ThirdFragment different title
                if(splash2 ==null) {
                    splash2 = new FragmentSplash2();
                }
                return splash2;

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

