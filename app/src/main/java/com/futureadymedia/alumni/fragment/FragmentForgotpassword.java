package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.utils.TextFont;

import org.w3c.dom.Text;

/**
 * Created by developer on 9/16/2016.
 */
public class FragmentForgotpassword extends BaseFragment implements View.OnClickListener, TextWatcher {

    private Context context;
    private View view;
    private LinearLayout llForgot;
    private TextView tvForgotHead, tvForgotBtn;
    private EditText etForgotemail;
    private TextInputLayout etForgotemail_layout;

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
        textWatcher();
        return view;
    }


    @Override
    public void findId() {
        llForgot = (LinearLayout) view.findViewById(R.id.llForgot);
        tvForgotHead = (TextView) view.findViewById(R.id.tvForgotHead);
        tvForgotBtn = (TextView) view.findViewById(R.id.tvForgotBtn);
        etForgotemail = (EditText) view.findViewById(R.id.etForgotemail);
        etForgotemail_layout = (TextInputLayout) view.findViewById(R.id.etForgotemail_layout);
    }

    @Override
    public void setListener() {
        llForgot.setOnClickListener(this);
    }

    @Override
    public void setFont() {
        tvForgotHead.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvForgotBtn.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        etForgotemail.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        etForgotemail_layout.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
    }

    private void textWatcher(){
        etForgotemail.addTextChangedListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.llForgot:
                ((MainActivity) context).onFragmentChange(4);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
