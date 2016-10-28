package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
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
public class FragmentSignup extends BaseFragment implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {

    private Context context;
    private View view;
    private TextInputLayout etFullName_layout, etMobile_layout, etEmail_layout, etPassword_layout, etConfirmPassword_layout, etIntroCode_layout;
    private EditText etFullName, etMobile, etEmail, etPassword, etConfirmPassword, etIntroCode;
    private Button btnSignupSubmit;
    private SignupModel signupmodel;
    private PrefsManager prefsManager;

    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = LayoutInflater.from(context).inflate(R.layout.fragment_signup, null);
        prefsManager = new PrefsManager(context);
        findId();
        setFont();
        setListener();
        textWatcher();
        focusChangeListener();

        return view;
    }
    @Override
    public void findId() {
        etFullName = (EditText)view.findViewById(R.id.etFullName);
        etMobile = (EditText)view.findViewById(R.id.etMobile);
        etEmail = (EditText)view.findViewById(R.id.etEmail);
        etPassword = (EditText)view.findViewById(R.id.etPassword);
        etConfirmPassword = (EditText)view.findViewById(R.id.etConformPassword);
        etIntroCode = (EditText)view.findViewById(R.id.etIntroCode);

        btnSignupSubmit = (Button)view.findViewById(R.id.btnSignupSubmit);

        etFullName_layout = (TextInputLayout)view.findViewById(R.id.etFullName_layout);
        etMobile_layout = (TextInputLayout)view.findViewById(R.id.etMobile_layout);
        etEmail_layout = (TextInputLayout)view.findViewById(R.id.etEmail_layout);
        etPassword_layout = (TextInputLayout)view.findViewById(R.id.etPassword_layout);
        etConfirmPassword_layout = (TextInputLayout)view.findViewById(R.id.etConformPassword_layout);
        etIntroCode_layout = (TextInputLayout)view.findViewById(R.id.etIntroCode_layout);
    }

    @Override
    public void setListener() {
        btnSignupSubmit.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    public void focusChangeListener(){
        etIntroCode.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignupSubmit:
               // ((MainActivity) context).onFragmentChange(7);
                if(checkValidation())
                {
                    submitForm();
                }

                else
                {
                    Toast.makeText(context, "Kindly Fill the Complete Form to signup", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    private void submitForm() {
        signupmodel = new SignupModel();
        signupmodel.fullname = etFullName.getText().toString().trim();
        signupmodel.mobile = etMobile.getText().toString().trim();
        signupmodel.password = etPassword.getText().toString().trim();
        signupmodel.email = etEmail.getText().toString().trim();
        signupmodel.introductioncode = etIntroCode.getText().toString().trim();
        signupmodel.email_privacy = false;
        signupmodel.mobile_privacy = false;
        Gson gson = new Gson();
        String request = gson.toJson(signupmodel);
        String url = RequestURL.ADD_UPDATE_USER;

        ServiceAsync serviceAsync = new ServiceAsync(context, request, url, RequestURL.POST, new ServiceStatus() {
            @Override
            public void onSuccess(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                Log.e("Responseeee", ""+servicesResponse.status);
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

    public boolean checkValidation(){
        boolean status = true;
        if (TextUtils.isEmpty(etFullName.getText().toString().trim())) {
            etFullName_layout.setError("Enter Full Name");
            status = false;
        }
         if (TextUtils.isEmpty(etMobile.getText().toString().trim())){
            etMobile_layout.setError("Please enter mobile number.");
            status =  false;
        }else if (TextUtils.getTrimmedLength(etMobile.getText().toString())<=9){
            etMobile_layout.setError("Please enter 10 digits number.");
            status = false;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString().trim())){
            etEmail_layout.setError("Please enter email.");
            status = false;
        }else if (!TextUtils.isEmpty(etEmail.getText().toString().trim()) && !CommonUtils.isValidEmail(etEmail.getText().toString().trim())){
            etEmail_layout.setError("Please enter valid email.");
            status = false;
        }
         if(TextUtils.isEmpty(etIntroCode.getText().toString().trim())){
            etIntroCode_layout.setError("Please Enter Intro Code");
             status = false;
        }
         if(TextUtils.isEmpty(etPassword.getText().toString().trim())){
            etPassword_layout.setError("Please Enter Password");
            status = false;
        }
        if(((etConfirmPassword.getText().toString()).equals(etPassword.getText().toString())) && !(TextUtils.isEmpty(etPassword.getText().toString().trim())))
        {
            etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick_green,0);
        }
        else
        {
            etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.cross_red,0);

            status = false;
        }
        return status;
    }


    private void textWatcher(){
        etFullName.addTextChangedListener(this);
        etEmail.addTextChangedListener(this);
        etMobile.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
        etConfirmPassword.addTextChangedListener(this);
        etIntroCode.addTextChangedListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (TextUtils.getTrimmedLength(etFullName.getText().toString()) > 0) {
            etFullName_layout.setError(null);
            etFullName_layout.setErrorEnabled(false);
        }
        if (TextUtils.getTrimmedLength(etMobile.getText().toString()) > 0) {
            etMobile_layout.setError(null);
            etMobile_layout.setErrorEnabled(false);
        }
        if (TextUtils.getTrimmedLength(etEmail.getText().toString()) > 0) {
            etEmail_layout.setError(null);
            etEmail_layout.setErrorEnabled(false);
        }
        if (TextUtils.getTrimmedLength(etPassword.getText().toString()) > 0) {
            etPassword_layout.setError(null);
            etPassword_layout.setErrorEnabled(false);
        }
        if (TextUtils.getTrimmedLength(etIntroCode.getText().toString()) > 0) {
            etIntroCode_layout.setError(null);
            etIntroCode_layout.setErrorEnabled(false);
        }


    }

    @Override
    public void afterTextChanged(Editable s) {
        if(((etConfirmPassword.getText().toString()).equals(etPassword.getText().toString())) && (!TextUtils.isEmpty(etPassword.getText().toString().trim())))
        {
            etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick_green,0);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        /*switch(v.getId())
        {
            case R.id.etIntroCode :
                if((etConfirmPassword.getText().toString()).equals(etPassword.getText().toString()))
                {
                    etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick_green,0);
                }
                else
                {
                    etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.cross_red,0);
                }
                break;
        }*/


    }


}

