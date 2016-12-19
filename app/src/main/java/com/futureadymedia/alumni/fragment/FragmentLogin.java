package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.model.LoginModel;
import com.futureadymedia.alumni.model.SignupModel;
import com.futureadymedia.alumni.services.RequestURL;
import com.futureadymedia.alumni.services.ServiceAsync;
import com.futureadymedia.alumni.services.ServiceResponse;
import com.futureadymedia.alumni.services.ServiceStatus;
import com.futureadymedia.alumni.utils.CommonUtils;
import com.futureadymedia.alumni.utils.Constants;
import com.futureadymedia.alumni.utils.PrefsManager;
import com.futureadymedia.alumni.utils.TextFont;
import com.google.gson.Gson;

/**
 * Created by developer on 9/16/2016.
 */
public class FragmentLogin extends BaseFragment implements View.OnClickListener, TextWatcher {

    private Context context;
    private View view;
    private TextView tvClickhere, tvLoginBtn, tvForgot;
    private EditText etEmailLogin, etPasswordLogin;
    private TextInputLayout etEmailLogin_layout, etPasswordLogin_layout;
    private PrefsManager prefsManager;
    private LoginModel loginModel;
    private LinearLayout llLogin;

    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_login, null);
        prefsManager = new PrefsManager(context);

        findId();
        setFont();
        setListener();
        textWatcher();
        return view;
    }

    @Override
    public void findId() {
        tvClickhere = (TextView) view.findViewById(R.id.tvClickhere);
        etEmailLogin = (EditText) view.findViewById(R.id.etEmailLogin);
        etPasswordLogin = (EditText) view.findViewById(R.id.etPasswordLogin);
        etEmailLogin_layout = (TextInputLayout) view.findViewById(R.id.etEmailLogin_layout);
        etPasswordLogin_layout = (TextInputLayout) view.findViewById(R.id.etPasswordLogin_layout);
        llLogin = (LinearLayout) view.findViewById(R.id.llLogin);
        tvLoginBtn = (TextView) view.findViewById(R.id.tvLoginBtn);
        tvForgot = (TextView) view.findViewById(R.id.tvForgot);
    }

    @Override
    public void setListener() {
        tvClickhere.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
        llLogin.setOnClickListener(this);
    }

    @Override
    public void setFont() {
        tvClickhere.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        etEmailLogin.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        etPasswordLogin.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvLoginBtn.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvForgot.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        etEmailLogin_layout.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        etPasswordLogin_layout.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));

    }

    private void textWatcher(){
        etEmailLogin.addTextChangedListener(this);
        etPasswordLogin.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.tvClickhere:
            case R.id.tvForgot:
                ((MainActivity) context).onFragmentChange(3);
                break;

            case R.id.llLogin:
                if(LoginValidation()){
                    Login();
                }
                else
                {
                    Toast.makeText(context, "Kindly Enter your Email and password", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public boolean LoginValidation(){
        boolean status = true;
        if (TextUtils.isEmpty(etEmailLogin.getText().toString().trim())){
            etEmailLogin_layout.setError("Please enter email.");
            status = false;
        }else if (!TextUtils.isEmpty(etEmailLogin.getText().toString().trim()) && !CommonUtils.isValidEmail(etEmailLogin.getText().toString().trim())){
            //etEmailLogin_layout.setError("Please enter valid email.");
            status = false;
        }
        if(TextUtils.isEmpty(etPasswordLogin.getText().toString().trim())){
           // etPasswordLogin_layout.setError("Please Enter Password");
            status = false;
        }

        return status;
    }

    public void Login(){
        loginModel = new LoginModel();
        loginModel.email = etEmailLogin.getText().toString().trim();
        loginModel.password = etPasswordLogin.getText().toString().trim();
        Gson gson = new Gson();
        String request = gson.toJson(loginModel);
        String url = RequestURL.LOGIN_USER;

        ServiceAsync serviceAsync = new ServiceAsync(context, request, url, RequestURL.POST, new ServiceStatus() {
            @Override
            public void onSuccess(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                Log.e("Response", ""+servicesResponse);
                if(servicesResponse.status.equals("success"))
                {
                    prefsManager.setUserId(servicesResponse.uid);

                   // ((MainActivity)context).onFragmentChange(6);

                    startActivity(new Intent(context, MainActivity.class)
                            .putExtra(Constants.FRAGMENT_SECTION, Constants.FRAGMENT_USERBOARD)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                else
                {
                    Toast.makeText(context, "Wrong Credentials\nTry again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailed(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                Log.e("Response", ""+servicesResponse);
                Toast.makeText(context, servicesResponse.response_message, Toast.LENGTH_SHORT).show();
            }

            // Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        });
        serviceAsync.execute("");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.getTrimmedLength(etEmailLogin.getText().toString()) > 0) {
            etEmailLogin_layout.setError(null);
            etEmailLogin_layout.setErrorEnabled(false);
        }
        if (TextUtils.getTrimmedLength(etPasswordLogin.getText().toString()) > 0) {
            etPasswordLogin_layout.setError(null);
            etPasswordLogin_layout.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
