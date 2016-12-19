package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.utils.TextFont;

/**
 * Created by developer on 9/14/2016.
 */
public class FragmentSplash3 extends BaseFragment implements View.OnClickListener {

    Context context;
    DrawerLayout drawerLayout;
    View view;
    Button enterButton;
    TextView tvWelcome, tvBond, tvFingertips;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(context).inflate(R.layout.fragment_splash3, null);



        findId();
        setFont();
        setListener();
        //setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void findId() {
        //enterButton = (Button)view.findViewById(R.id.enterButton);
        tvBond = (TextView)view.findViewById(R.id.tvBond);
        tvFingertips = (TextView)view.findViewById(R.id.tvFingertips);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void setFont() {
        tvBond.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvFingertips.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        MainActivity.setTitle("Welcome");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("SPLASH", "inside pause");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.e("SPLASH", "inside start");
    }
}
