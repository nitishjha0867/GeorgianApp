package com.futureadymedia.alumni.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.futureadymedia.alumni.activity.MainActivity;

/**
 * Created by developer on 9/14/2016.
 */
public class CommonUtils {

    public static void setFragment(Fragment fragment, boolean removeStack, FragmentActivity activity, FrameLayout mContainer, String fragment_tag, String title) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ftTransaction.replace(mContainer.getId(), fragment);
        } else {
            ftTransaction.replace(mContainer.getId(), fragment);
            ftTransaction.addToBackStack(fragment_tag);
        }
        ftTransaction.commit();
//        MainActivity.tvTitle.setText(title);
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
