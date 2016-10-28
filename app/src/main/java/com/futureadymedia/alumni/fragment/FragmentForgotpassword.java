package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;

/**
 * Created by developer on 9/16/2016.
 */
public class FragmentForgotpassword extends BaseFragment implements View.OnClickListener {

    private Context context;
    private View view;
    private Button btnForgotemail;

    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_forgotpassword, null);


        findId();
        setFont();
        setListener();

        return view;
    }


    @Override
    public void findId() {
        btnForgotemail = (Button) view.findViewById(R.id.btnForgotemail);
    }

    @Override
    public void setListener() {
        btnForgotemail.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnForgotemail:
                ((MainActivity) context).onFragmentChange(4);
                break;
        }
    }
}
