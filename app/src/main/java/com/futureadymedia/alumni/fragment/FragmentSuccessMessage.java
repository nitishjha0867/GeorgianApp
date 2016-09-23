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
 * Created by developer on 9/21/2016.
 */
public class FragmentSuccessMessage extends BaseFragment implements View.OnClickListener {

    private View view;
    private Context context;
    private Button  btnTryAgain;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_success_message, null);

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
        MenuItem item2=menu.findItem(R.id.action_profile);
        MenuItem item3=menu.findItem(R.id.action_logout);
        item.setVisible(false);
        item1.setVisible(false);
        item2.setVisible(false);
        item3.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void findId() {
        btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);
    }

    @Override
    public void setListener() {
        btnTryAgain.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnTryAgain:
                ((MainActivity) context).onFragmentChange(6);
                break;
        }
    }
}
