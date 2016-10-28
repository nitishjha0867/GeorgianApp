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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.model.CurrentAddress;
import com.futureadymedia.alumni.services.RequestURL;
import com.futureadymedia.alumni.services.ServiceAsync;
import com.futureadymedia.alumni.services.ServiceResponse;
import com.futureadymedia.alumni.services.ServiceStatus;
import com.futureadymedia.alumni.utils.Constants;
import com.futureadymedia.alumni.utils.PrefsManager;
import com.google.gson.Gson;

/**
 * Created by developer on 9/23/2016.
 */
public class FragmentCurrentAddress extends BaseFragment implements View.OnClickListener, TextWatcher, CompoundButton.OnCheckedChangeListener {

    private Context context;
    private View view;
    private EditText etAddress1, etAddress2, etPinCode, etCity, etState, etCountry;
    private Switch swtchAddressType;
    private TextInputLayout etAddress1_layout, etAddress2_layout, etPinCode_layout, etCity_layout, etState_layout, etCountry_layout;
    private Button btnSaveAddress;
    private CurrentAddress currentaddress;
    private PrefsManager prefsManager;
    private String address_type;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_current_address, null);
        prefsManager = new PrefsManager(context);
        findId();
        setListener();
        setFont();
        textWatcher();
        return view;
    }

    @Override
    public void findId() {
        etAddress1 = (EditText)view.findViewById(R.id.etAddress1);
        etAddress2 = (EditText)view.findViewById(R.id.etAddress2);
        etPinCode = (EditText)view.findViewById(R.id.etPinCode);
        etCity = (EditText)view.findViewById(R.id.etCity);
        etState = (EditText)view.findViewById(R.id.etState);
        etCountry = (EditText)view.findViewById(R.id.etCountry);

        etAddress1_layout = (TextInputLayout)view.findViewById(R.id.etAddress1_layout);
        etAddress2_layout = (TextInputLayout)view.findViewById(R.id.etAddress2_layout);
        etPinCode_layout = (TextInputLayout)view.findViewById(R.id.etPinCode_layout);
        etCity_layout = (TextInputLayout)view.findViewById(R.id.etCity_layout);
        etState_layout = (TextInputLayout)view.findViewById(R.id.etState_layout);
        etCountry_layout = (TextInputLayout)view.findViewById(R.id.etCountry_layout);

        swtchAddressType = (Switch)view.findViewById(R.id.swtchAddressType);

        btnSaveAddress = (Button)view.findViewById(R.id.btnSaveAddress);
    }

    private void textWatcher(){
        etAddress1.addTextChangedListener(this);
        etAddress2.addTextChangedListener(this);
        etPinCode.addTextChangedListener(this);
        etCity.addTextChangedListener(this);
        etState.addTextChangedListener(this);
        etCountry.addTextChangedListener(this);
    }

    @Override
    public void setListener() {
        btnSaveAddress.setOnClickListener(this);
        swtchAddressType.setOnCheckedChangeListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnSaveAddress:
                if(addressValidate())
                {
                    saveAddress();
                }
                break;
        }
    }

    public boolean addressValidate(){
        boolean status = true;
        if (TextUtils.isEmpty(etAddress1.getText().toString().trim())) {
            etAddress1_layout.setError("Enter your Address");
            status = false;
        }
        if(TextUtils.isEmpty(etAddress2.getText().toString().trim()))
        {
            etAddress2_layout.setError("Enter your locality");
            status = false;
        }
        if(TextUtils.isEmpty(etPinCode.getText().toString().trim()))
        {
            etPinCode_layout.setError("Enter Pin Code");
            status = false;
        }
        if(TextUtils.isEmpty(etCity.getText().toString().trim()))
        {
            etCity_layout.setError("Enter City");
            status = false;
        }
        if(TextUtils.isEmpty(etState.getText().toString().trim()))
        {
            etState_layout.setError("Enter State");
            status = false;
        }
        if(TextUtils.isEmpty(etCountry.getText().toString().trim()))
        {
            etCountry_layout.setError("Enter Country");
            status = false;
        }

        return status;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.getTrimmedLength(etAddress1.getText().toString()) > 0) {
            etAddress1_layout.setError(null);
            etAddress1_layout.setErrorEnabled(false);
        }

        if (TextUtils.getTrimmedLength(etAddress2.getText().toString()) > 0) {
            etAddress2_layout.setError(null);
            etAddress2_layout.setErrorEnabled(false);
        }

        if(TextUtils.getTrimmedLength(etPinCode.getText().toString())>0){
            etPinCode_layout.setError(null);
            etPinCode_layout.setErrorEnabled(false);
        }

        if(TextUtils.getTrimmedLength(etCity.getText().toString())>0){
            etCity_layout.setError(null);
            etCity_layout.setErrorEnabled(false);
        }

        if(TextUtils.getTrimmedLength(etState.getText().toString())>0)
        {
            etState_layout.setError(null);
            etState_layout.setErrorEnabled(false);
        }
        if(TextUtils.getTrimmedLength(etCountry.getText().toString())>0)
        {
            etCountry_layout.setError(null);
            etCountry_layout.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void saveAddress(){
        currentaddress = new CurrentAddress();
        currentaddress.address1 = etAddress1.getText().toString();
        currentaddress.address2 = etAddress2.getText().toString();
        currentaddress.pincode = etPinCode.getText().toString();
        currentaddress.city = etCity.getText().toString();
        currentaddress.state = etState.getText().toString();
        currentaddress.country = etCountry.getText().toString();
        currentaddress.user_id = prefsManager.getUserId();
        currentaddress.address_type = address_type;

        Gson gson = new Gson();
        String request = gson.toJson(currentaddress);
        String url = RequestURL.ADDRESS_UPLOAD;

        ServiceAsync serviceAsync = new ServiceAsync(context, request, url, RequestURL.POST, new ServiceStatus() {
            @Override
            public void onSuccess(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                Log.e("Response", ""+servicesResponse);

            }

            @Override
            public void onFailed(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                Log.e("Response", ""+servicesResponse);
                Toast.makeText(context, servicesResponse.response_message, Toast.LENGTH_SHORT).show();
            }

        });
        serviceAsync.execute("");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.swtchAddressType:
                if (isChecked) {
                    address_type = "Home";
                }
                else
                {
                    address_type = "Office";
                }
                break;
        }
    }
}
