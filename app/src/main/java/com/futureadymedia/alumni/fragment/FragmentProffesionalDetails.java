package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.adapter.CustomAdapter;
import com.futureadymedia.alumni.model.ProffesionalDetails;
import com.futureadymedia.alumni.services.RequestURL;
import com.futureadymedia.alumni.services.ServiceAsync;
import com.futureadymedia.alumni.services.ServiceResponse;
import com.futureadymedia.alumni.services.ServiceStatus;
import com.futureadymedia.alumni.utils.PrefsManager;
import com.google.gson.Gson;

/**
 * Created by developer on 9/30/2016.
 */
public class FragmentProffesionalDetails extends BaseFragment implements View.OnClickListener, TextWatcher {

    private Context context;
    private View view;
    private Spinner spinnerSalutation, spinnerIndustry;
    private EditText etOrganisation, etDesignation;
    private TextInputLayout etOrganisation_layout, etDesignation_layout;
    private Button btnSaveProffesionalDetails;
    private LinearLayout llErrorSalutation, llErrorIndustry;
    private ProffesionalDetails proffDetails;
    private PrefsManager prefsManager;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = LayoutInflater.from(context).inflate(R.layout.fragment_proffesional_details, null);
        prefsManager = new PrefsManager(context);
        findId();
        setFont();
        setListener();
        setSalutationSpinner();
        setIndustrySpinner();
        textWatcher();
        return view;

    }

    @Override
    public void findId() {
        spinnerSalutation = (com.futureadymedia.alumni.widgets.HintSpinner)view.findViewById(R.id.spinnerSalutation);
        spinnerIndustry = (com.futureadymedia.alumni.widgets.HintSpinner)view.findViewById(R.id.spinnerIndustry);
        etOrganisation = (EditText)view.findViewById(R.id.etOrganisation);
        etDesignation = (EditText)view.findViewById(R.id.etDesignation);
        etOrganisation_layout = (TextInputLayout)view.findViewById(R.id.etOrganisation_layout);
        etDesignation_layout = (TextInputLayout)view.findViewById(R.id.etDesignation_layout);
        llErrorSalutation = (LinearLayout) view.findViewById(R.id.llErrorSalutation);
        llErrorIndustry = (LinearLayout) view.findViewById(R.id.llErrorIndustry);
        btnSaveProffesionalDetails = (Button)view.findViewById(R.id.btnSaveProffesionalDetails);
    }

    @Override
    public void setListener() {
       btnSaveProffesionalDetails.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSaveProffesionalDetails:
                if(checkValidation())
                {
                    saveData();
                }
                else
                {
                    //Else part here
                }
                break;
        }
    }

    public void setSalutationSpinner(){
        CustomAdapter adapter = new CustomAdapter((MainActivity) context, R.layout.spinner_item, getResources().getStringArray(R.array.salutation));
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerSalutation.setAdapter(adapter);
    }

    public void setIndustrySpinner(){
        CustomAdapter adapter = new CustomAdapter((MainActivity) context, R.layout.spinner_item, getResources().getStringArray(R.array.industries_list));
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerIndustry.setAdapter(adapter);
    }

    public void textWatcher(){
        etOrganisation.addTextChangedListener(this);
        etDesignation.addTextChangedListener(this);
        spinnerIndustry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    llErrorIndustry.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSalutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    llErrorSalutation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean checkValidation(){
        llErrorIndustry.setVisibility(View.GONE);
        llErrorSalutation.setVisibility(View.GONE);
        boolean status = true;

        if(TextUtils.isEmpty(etOrganisation.getText().toString().trim())){
            etOrganisation_layout.setErrorEnabled(true);
            etOrganisation_layout.setError("Enter your Organisation");
            status = false;
        }
        if(TextUtils.isEmpty(etDesignation.getText().toString().trim())){
            etDesignation_layout.setErrorEnabled(true);
            etDesignation_layout.setError("Enter your Designation");
            status = false;
        }
        if(spinnerSalutation.getSelectedItemPosition()<0)
        {
            llErrorSalutation.setVisibility(View.VISIBLE);
            status = false;
        }
        if(spinnerIndustry.getSelectedItemPosition()<0)
        {
            llErrorIndustry.setVisibility(View.VISIBLE);
            status = false;
        }
        return status;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(TextUtils.getTrimmedLength(etOrganisation.getText().toString())>0)
        {
            etOrganisation_layout.setError(null);
            etOrganisation_layout.setErrorEnabled(false);
        }
        if (TextUtils.getTrimmedLength(etDesignation.getText().toString())>0){
            etDesignation_layout.setError(null);
            etDesignation_layout.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void saveData(){
        proffDetails = new ProffesionalDetails();
        proffDetails.salutation = spinnerSalutation.getSelectedItem().toString();
        proffDetails.industry = spinnerIndustry.getSelectedItem().toString();
        proffDetails.organisation = etOrganisation.getText().toString();
        proffDetails.designation = etDesignation.getText().toString();
        proffDetails.user_id = prefsManager.getUserId();

        Gson gson = new Gson();
        String request = gson.toJson(proffDetails);
        String url = RequestURL.PROFFESIONAL_UPLOAD;

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
}
