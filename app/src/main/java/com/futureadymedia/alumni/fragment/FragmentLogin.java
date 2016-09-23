package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;

/**
 * Created by developer on 9/16/2016.
 */
public class FragmentLogin extends BaseFragment implements View.OnClickListener {

    private Context context;
    private View view;
    private TextView tvClickhere;
    private Button btnLoginSubmit;

    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_login, null);

        findId();
        setFont();
        setListener();

        return view;
    }

    @Override
    public void findId() {
        tvClickhere = (TextView) view.findViewById(R.id.tvClickhere);
        btnLoginSubmit = (Button) view.findViewById(R.id.btnLoginSubmit);
    }

    @Override
    public void setListener() {
        tvClickhere.setOnClickListener(this);
        btnLoginSubmit.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.tvClickhere:
                ((MainActivity) context).onFragmentChange(3);
                break;
            case R.id.btnLoginSubmit:
                ((MainActivity) context).onFragmentChange(7);
                break;
        }
    }
}
