package com.futureadymedia.alumni.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.adapter.NavigationDrawerAdapter;
import com.futureadymedia.alumni.utils.CommonUtils;

/**
 * Created by developer on 9/14/2016.
 */
public class FragmentSplash extends BaseFragment implements View.OnClickListener {

    Context context;
    DrawerLayout drawerLayout;
    View view;
    Button enterButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(context).inflate(R.layout.fragment_splash, null);

       /* drawerLayout = (DrawerLayout) this.getActivity().findViewById(R.id.drawer_layout);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);*/

        /****** Create Thread that will sleep for 5 seconds *************/
        /*Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(5*1000);

                    // After 5 seconds redirect to another intent
                  /*  MapViewFragment fragment2 = new MapViewFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment1, fragment2);
                    fragmentTransaction.commit();*/

                   /* CommonUtils.setFragment(new MapViewFragment(), false, getActivity(), MainActivity.flContainer, "splash");

                    //Remove activity
                   // finish();

                } catch (Exception e) {

                }
            }
        };
        background.start();*/

        findId();
        setFont();
        setListener();

        setHasOptionsMenu(true);

        return view;
    }



    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_search);
        MenuItem item1=menu.findItem(R.id.action_settings);
        item.setVisible(false);
        item1.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void findId() {
        enterButton = (Button)view.findViewById(R.id.enterButton);
    }

    @Override
    public void setListener() {
        enterButton.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enterButton:
                ((MainActivity) context).onFragmentChange(1);
                break;
        }
    }
}
