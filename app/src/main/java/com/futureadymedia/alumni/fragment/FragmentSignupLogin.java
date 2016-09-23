package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.adapter.MyPagerAdapter;
import com.futureadymedia.alumni.utils.CommonUtils;
import com.futureadymedia.alumni.utils.Constants;
import com.futureadymedia.alumni.widgets.NonSwipeableViewPager;

import org.w3c.dom.Text;

/**
 * Created by developer on 9/16/2016.
 */
public class FragmentSignupLogin extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private Context context;
    private View view;
    private NonSwipeableViewPager nonSwipeableViewPager;
    private MyPagerAdapter adapterViewPager;
    private TextView tvLogin, tvSignup;
    private int loadingPos;
    private LinearLayout linearSignup, linearLogin;
    private String Debug = "APPDEBUG";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.login_signup_common, null);
        Log.e(Debug, "onCreate");
        findId();
        setFont();
        setListener();

        nonSwipeableViewPager.addOnPageChangeListener(this);
        nonSwipeableViewPager.setOffscreenPageLimit(2);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        nonSwipeableViewPager.setAdapter(adapterViewPager);
        nonSwipeableViewPager.setCurrentItem(0);

        loadingPos = 0;

        setHasOptionsMenu(true);
        ActiveTab(0);
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

    public void ActiveTab(int position)
    {
        switch(position){
            case 0:
                tvSignup.setBackgroundResource(R.color.colorPrimary);
                tvLogin.setBackgroundResource(R.color.transparent);
                break;
            case 1:
                tvLogin.setBackgroundResource(R.color.colorPrimary);
                tvSignup.setBackgroundResource(R.color.transparent);
                break;
        }
    }
    @Override
    public void findId() {
        nonSwipeableViewPager = (NonSwipeableViewPager) view.findViewById(R.id.viewPagerScan);
        tvLogin = (TextView)view.findViewById(R.id.tvLogin);
        tvSignup = (TextView)view.findViewById(R.id.tvSignup);
        linearSignup = (LinearLayout) view.findViewById(R.id.linearSignup);
        linearLogin = (LinearLayout) view.findViewById(R.id.linearLogin);

    }

    @Override
    public void setListener() {
       /* tvSignup.setOnClickListener(this);
        tvLogin.setOnClickListener(this);*/
        linearLogin.setOnClickListener(this);
        linearSignup.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.linearLogin :
                nonSwipeableViewPager.setCurrentItem(1, true);
                break;

            case R.id.linearSignup :
                nonSwipeableViewPager.setCurrentItem(0, true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e(Debug, "onpagescrolled"+position);

    }

    @Override
    public void onPageSelected(int position) {
        Log.e(Debug, "onpageselected");
        ActiveTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.e(Debug, "onpagescrollstatechanged");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(Debug, "Inside resume1");
        //adapterViewPager.instantiateItem(MainActivity.flContainer, 0);

       /* getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                /*if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.e(Debug, "Inside back");
                    startActivity(new Intent(context, MainActivity.class)
                            .putExtra(Constants.FRAGMENT_SECTION, Constants.FRAGMENT_INFO));
                    return true;
               /* }*/
               /* return false;
            }
        });*/
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e(Debug, "Inside pause1");
        //adapterViewPager.destroyItem(view, );
    }


}
