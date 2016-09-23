package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futureadymedia.alumni.R;

/**
 * Created by developer on 9/23/2016.
 */
public class FragmentCurrentAddress extends BaseFragment implements View.OnClickListener {

    private Context context;
    private View view;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_current_address, null);

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
