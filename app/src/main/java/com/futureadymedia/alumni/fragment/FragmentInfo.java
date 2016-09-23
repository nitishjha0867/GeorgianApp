package com.futureadymedia.alumni.fragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.utils.CommonUtils;
import com.futureadymedia.alumni.utils.Constants;

/**
 * Created by developer on 9/15/2016.
 */
public class FragmentInfo extends BaseFragment implements View.OnClickListener {

    private Context context;
    private View view;
    private Button Tc, btnSignup;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(context).inflate(R.layout.fragment_info, null);

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
        Tc = (Button)view.findViewById(R.id.btnTc);
        btnSignup = (Button)view.findViewById(R.id.btnSignup);
    }

    @Override
    public void setListener() {
        Tc.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e("APPDEBUG", "Inside resume0");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("APPDEBUG", "Inside pause0");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnTc:
                FragmentDialog newFragment = FragmentDialog.newInstance();
                newFragment.show(getFragmentManager(), "dialog");
                break;

            case R.id.btnSignup:
                //FragmentSignupLogin startScanFragment = new FragmentSignupLogin();
               // CommonUtils.setFragment(startScanFragment, false, getActivity(), MainActivity.flContainer, "signup");
                ((MainActivity) context).onFragmentChange(2);
                /*startActivity(new Intent(context, MainActivity.class)
                        .putExtra(Constants.FRAGMENT_SECTION, Constants.FRAGMENT_SIGNUPLOGIN));*/
        }
    }
}
