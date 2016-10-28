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
import com.google.gson.Gson;

/**
 * Created by developer on 9/16/2016.
 */
public class FragmentLogin extends BaseFragment implements View.OnClickListener, TextWatcher {

    private Context context;
    private View view;
    private TextView tvClickhere;
    private Button btnLoginSubmit;
    private EditText etEmailLogin, etPasswordLogin;
    private TextInputLayout etEmailLogin_layout, etPasswordLogin_layout;
    private PrefsManager prefsManager;
    private LoginModel loginModel;

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
        btnLoginSubmit = (Button) view.findViewById(R.id.btnLoginSubmit);
        etEmailLogin = (EditText) view.findViewById(R.id.etEmailLogin);
        etPasswordLogin = (EditText) view.findViewById(R.id.etPasswordLogin);
        etEmailLogin_layout = (TextInputLayout) view.findViewById(R.id.etEmailLogin_layout);
        etPasswordLogin_layout = (TextInputLayout) view.findViewById(R.id.etPasswordLogin_layout);
    }

    @Override
    public void setListener() {
        tvClickhere.setOnClickListener(this);
        btnLoginSubmit.setOnClickListener(this);
    }

    @Override
    public void setFont() {

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
                ((MainActivity) context).onFragmentChange(3);
                break;

            case R.id.btnLoginSubmit:
                if(LoginValidation()){
                    Login();
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
            etEmailLogin_layout.setError("Please enter valid email.");
            status = false;
        }
        if(TextUtils.isEmpty(etPasswordLogin.getText().toString().trim())){
            etPasswordLogin_layout.setError("Please Enter Password");
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
