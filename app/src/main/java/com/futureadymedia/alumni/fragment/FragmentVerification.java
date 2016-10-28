package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;

/**
 * Created by developer on 9/20/2016.
 */
public class FragmentVerification extends BaseFragment implements View.OnClickListener {

    private View view;
    private Context context;
    private Button btnVerificationSubmit;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = LayoutInflater.from(context).inflate(R.layout.fragment_verification_code, null);

        findId();
        setFont();
        setListener();

        return view;
    }


    @Override
    public void findId() {
        btnVerificationSubmit = (Button)view.findViewById(R.id.btnVerificationSubmit);
    }

    @Override
    public void setListener() {
        btnVerificationSubmit.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnVerificationSubmit:
                ((MainActivity) context).onFragmentChange(5);
                break;
        }
    }
}
