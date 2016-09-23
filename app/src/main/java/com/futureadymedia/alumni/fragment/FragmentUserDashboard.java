package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futureadymedia.alumni.R;

/**
 * Created by developer on 9/20/2016.
 */
public class FragmentUserDashboard extends BaseFragment implements  View.OnClickListener {

    private Context context;
    private View view;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public  View onCreateView(LayoutInflater inflate, ViewGroup container, Bundle SavedInstanceState)
    {
        view = LayoutInflater.from(context).inflate(R.layout.fragmentuserdashboard, null);
        return view;
    }

    @Override
    public void findId() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {

    }
}
