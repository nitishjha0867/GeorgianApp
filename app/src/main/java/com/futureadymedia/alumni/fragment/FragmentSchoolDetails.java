package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.futureadymedia.alumni.model.SchoolDetails;
import com.futureadymedia.alumni.services.RequestURL;
import com.futureadymedia.alumni.services.ServiceAsync;
import com.futureadymedia.alumni.services.ServiceResponse;
import com.futureadymedia.alumni.services.ServiceStatus;
import com.futureadymedia.alumni.utils.PrefsManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by developer on 10/18/2016.
 */
public class FragmentSchoolDetails extends BaseFragment implements View.OnClickListener, TextWatcher {

    private View view;
    private Context context;
    private LinearLayout llSchool1, llSchool2, llSchool3, llSchool4, llSchool5, mainLinear;
    private TextView tvSchoolno1, tvSchoolno2, tvSchoolno3, tvSchoolno4, tvSchoolno5, tvMoreSchool, tvLessSchool;
    private TextInputLayout etRoll_layout1, etRoll_layout2,etRoll_layout3, etRoll_layout4, etRoll_layout5;
    private EditText etRoll1, etRoll2, etRoll3, etRoll4, etRoll5;
    private Spinner spinnerSchool1, spinnerSchool2, spinnerSchool3, spinnerSchool4, spinnerSchool5, spinnerSchoolFrom1, spinnerSchoolFrom2, spinnerSchoolFrom3, spinnerSchoolFrom4, spinnerSchoolFrom5, spinnerSchoolTo1, spinnerSchoolTo2, spinnerSchoolTo3, spinnerSchoolTo4, spinnerSchoolTo5;
    private int childCount;
    private Button btnSaveSchool;
    private SchoolDetails schoolModel;
    private PrefsManager prefsManager;
    private Boolean first_school_status = false;
    private CustomAdapter adapter;
    private CustomAdapter adapter1;
    private String[] schoolArray;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = LayoutInflater.from(context).inflate(R.layout.fragment_school_details, null);
        prefsManager = new PrefsManager(context);
        findId();
        setFont();
        setListener();
        setSchoolSpinner();
        setYearSpinner();
        schoolModel = new SchoolDetails();
        setData();
        return view;
    }

    @Override
    public void findId() {
        llSchool1 = (LinearLayout)view.findViewById(R.id.llSchool1);
        llSchool2 = (LinearLayout)view.findViewById(R.id.llSchool2);
        llSchool3 = (LinearLayout)view.findViewById(R.id.llSchool3);
        llSchool4 = (LinearLayout)view.findViewById(R.id.llSchool4);
        llSchool5 = (LinearLayout)view.findViewById(R.id.llSchool5);
        mainLinear = (LinearLayout)view.findViewById(R.id.mainLinear);

        tvSchoolno1 = (TextView)view.findViewById(R.id.tvSchoolno1);
        tvSchoolno2 = (TextView)view.findViewById(R.id.tvSchoolno2);
        tvSchoolno3 = (TextView)view.findViewById(R.id.tvSchoolno3);
        tvSchoolno4 = (TextView)view.findViewById(R.id.tvSchoolno4);
        tvSchoolno5 = (TextView)view.findViewById(R.id.tvSchoolno5);
        tvMoreSchool = (TextView)view.findViewById(R.id.tvMoreSchool);
        tvLessSchool = (TextView)view.findViewById(R.id.tvLessSchool);

        etRoll_layout1 = (TextInputLayout)view.findViewById(R.id.etRoll_layout1);
        etRoll_layout2 = (TextInputLayout)view.findViewById(R.id.etRoll_layout2);
        etRoll_layout3 = (TextInputLayout)view.findViewById(R.id.etRoll_layout3);
        etRoll_layout4 = (TextInputLayout)view.findViewById(R.id.etRoll_layout4);
        etRoll_layout5 = (TextInputLayout)view.findViewById(R.id.etRoll_layout5);

        etRoll1 = (EditText) view.findViewById(R.id.etRoll1);
        etRoll2 = (EditText) view.findViewById(R.id.etRoll2);
        etRoll3 = (EditText) view.findViewById(R.id.etRoll3);
        etRoll4 = (EditText) view.findViewById(R.id.etRoll4);
        etRoll5 = (EditText) view.findViewById(R.id.etRoll5);

        spinnerSchool1 = (Spinner) view.findViewById(R.id.spinnerSchool1);
        spinnerSchool2 = (Spinner) view.findViewById(R.id.spinnerSchool2);
        spinnerSchool3 = (Spinner) view.findViewById(R.id.spinnerSchool3);
        spinnerSchool4 = (Spinner) view.findViewById(R.id.spinnerSchool4);
        spinnerSchool5 = (Spinner) view.findViewById(R.id.spinnerSchool5);

        spinnerSchoolFrom1 = (Spinner) view.findViewById(R.id.spinnerSchoolFrom1);
        spinnerSchoolFrom2 = (Spinner) view.findViewById(R.id.spinnerSchoolFrom2);
        spinnerSchoolFrom3 = (Spinner) view.findViewById(R.id.spinnerSchoolFrom3);
        spinnerSchoolFrom4 = (Spinner) view.findViewById(R.id.spinnerSchoolFrom4);
        spinnerSchoolFrom5 = (Spinner) view.findViewById(R.id.spinnerSchoolFrom5);

        spinnerSchoolTo1 = (Spinner) view.findViewById(R.id.spinnerSchoolTo1);
        spinnerSchoolTo2 = (Spinner) view.findViewById(R.id.spinnerSchoolTo2);
        spinnerSchoolTo3 = (Spinner) view.findViewById(R.id.spinnerSchoolTo3);
        spinnerSchoolTo4 = (Spinner) view.findViewById(R.id.spinnerSchoolTo4);
        spinnerSchoolTo5 = (Spinner) view.findViewById(R.id.spinnerSchoolTo5);

        btnSaveSchool = (Button) view.findViewById(R.id.btnSaveSchool);
    }

    @Override
    public void setListener() {
        tvMoreSchool.setOnClickListener(this);
        tvLessSchool.setOnClickListener(this);
        btnSaveSchool.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    public void setSchoolSpinner(){
        schoolArray = getResources().getStringArray(R.array.school_name);
        adapter = new CustomAdapter((MainActivity) context, R.layout.spinner_item, schoolArray);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerSchool1.setAdapter(adapter);
        spinnerSchool2.setAdapter(adapter);
        spinnerSchool3.setAdapter(adapter);
        spinnerSchool4.setAdapter(adapter);
        spinnerSchool5.setAdapter(adapter);
    }

    public void setYearSpinner()
    {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        String[] year_array = years.toArray(new String[years.size()]);
        adapter1 = new CustomAdapter((MainActivity) context, R.layout.spinner_item, year_array);
        spinnerSchoolFrom1.setAdapter(adapter1);
        spinnerSchoolFrom2.setAdapter(adapter1);
        spinnerSchoolFrom3.setAdapter(adapter1);
        spinnerSchoolFrom4.setAdapter(adapter1);
        spinnerSchoolFrom5.setAdapter(adapter1);
        spinnerSchoolTo1.setAdapter(adapter1);
        spinnerSchoolTo2.setAdapter(adapter1);
        spinnerSchoolTo3.setAdapter(adapter1);
        spinnerSchoolTo4.setAdapter(adapter1);
        spinnerSchoolTo5.setAdapter(adapter1);

    }

    public void resetspinners(Spinner schoolspinner, Spinner from, Spinner to){
       // schoolArray = new String[0];
        schoolArray = getResources().getStringArray(R.array.school_name);
        adapter = new CustomAdapter((MainActivity) context, R.layout.spinner_item, schoolArray);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        schoolspinner.setAdapter(adapter);
        schoolspinner.setPrompt("Select School");

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        String[] year_array = years.toArray(new String[years.size()]);
        adapter1 = new CustomAdapter((MainActivity) context, R.layout.spinner_item, year_array);
        from.setAdapter(adapter1);
        to.setAdapter(adapter1);
        from.setPrompt("From");
        to.setPrompt("To");
    }

    @Override
    public void onClick(View v) {
        childCount = 0;
        for(int i = 0; i < 5; i++)
        {
            if(mainLinear.getChildAt(i).getVisibility() == View.VISIBLE) {
                childCount++;
            }
        }
        switch(v.getId())
        {
            case R.id.tvMoreSchool:
                Log.e("more", ""+childCount);
                if(childCount < 5)
                {
                    switch(childCount)
                    {
                        case 1:
                            tvLessSchool.setVisibility(View.VISIBLE);
                            llSchool2.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            tvLessSchool.setVisibility(View.VISIBLE);
                            llSchool3.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            tvLessSchool.setVisibility(View.VISIBLE);
                            llSchool4.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            tvLessSchool.setVisibility(View.VISIBLE);
                            llSchool5.setVisibility(View.VISIBLE);
                            tvMoreSchool.setVisibility(View.GONE);
                            break;


                    }
                }
                break;

            case R.id.tvLessSchool:
                Log.e("ChildCount", ""+childCount);
                if(childCount <= 5)
                {
                    switch(childCount)
                    {
                        case 1:
                            tvLessSchool.setVisibility(View.GONE);
                            tvMoreSchool.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            tvLessSchool.setVisibility(View.GONE);
                            llSchool2.setVisibility(View.GONE);
                            etRoll2.setText("");
                            resetspinners(spinnerSchool2, spinnerSchoolFrom2, spinnerSchoolTo2);
                            tvMoreSchool.setVisibility(View.VISIBLE);

                            break;

                        case 3:
                            tvLessSchool.setVisibility(View.VISIBLE);
                            llSchool3.setVisibility(View.GONE);
                            etRoll3.setText("");
                            resetspinners(spinnerSchool3, spinnerSchoolFrom3, spinnerSchoolTo3);
                            tvMoreSchool.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            tvLessSchool.setVisibility(View.VISIBLE);
                            llSchool4.setVisibility(View.GONE);
                            etRoll4.setText("");
                            resetspinners(spinnerSchool4, spinnerSchoolFrom4, spinnerSchoolTo4);
                            tvMoreSchool.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            tvLessSchool.setVisibility(View.VISIBLE);
                            llSchool5.setVisibility(View.GONE);
                            etRoll5.setText("");
                            resetspinners(spinnerSchool5, spinnerSchoolFrom5, spinnerSchoolTo5);
                            tvMoreSchool.setVisibility(View.VISIBLE);
                            break;
                    }
                }
                break;

            case R.id.btnSaveSchool:
                if(isSpinnerEmpty(spinnerSchool1, spinnerSchoolFrom1, spinnerSchoolTo1) && !(TextUtils.isEmpty(etRoll1.getText().toString().trim()))) {
                    schoolModel.school1 = spinnerSchool1.getSelectedItem().toString();
                    schoolModel.from1 = spinnerSchoolFrom1.getSelectedItem().toString();
                    schoolModel.to1 = spinnerSchoolTo1.getSelectedItem().toString();
                    schoolModel.roll1 = etRoll1.getText().toString();

                    first_school_status = true;
                }

                if(isSpinnerEmpty(spinnerSchool2, spinnerSchoolFrom2, spinnerSchoolTo2) && !(TextUtils.isEmpty(etRoll2.getText().toString().trim())) && (llSchool2.getVisibility() == View.VISIBLE))
                {
                    schoolModel.school2 = spinnerSchool2.getSelectedItem().toString();
                    schoolModel.from2 = spinnerSchoolFrom2.getSelectedItem().toString();
                    schoolModel.to2 = spinnerSchoolTo2.getSelectedItem().toString();
                    schoolModel.roll2 = etRoll2.getText().toString();
                }
                else
                {
                    schoolModel.school2 = "";
                    schoolModel.from2 = "";
                    schoolModel.to2 = "";
                    schoolModel.roll2 = "";
                }

                if(isSpinnerEmpty(spinnerSchool3, spinnerSchoolFrom3, spinnerSchoolTo3) && !(TextUtils.isEmpty(etRoll3.getText().toString().trim())) && (llSchool3.getVisibility() == View.VISIBLE))
                {
                    schoolModel.school3 = spinnerSchool3.getSelectedItem().toString();
                    schoolModel.from3 = spinnerSchoolFrom3.getSelectedItem().toString();
                    schoolModel.to3 = spinnerSchoolTo3.getSelectedItem().toString();
                    schoolModel.roll3 = etRoll3.getText().toString();
                }
                else
                {
                    schoolModel.school3 = "";
                    schoolModel.from3 = "";
                    schoolModel.to3 = "";
                    schoolModel.roll3 = "";
                }

                if(isSpinnerEmpty(spinnerSchool4, spinnerSchoolFrom4, spinnerSchoolTo4) && !(TextUtils.isEmpty(etRoll4.getText().toString().trim())) && (llSchool4.getVisibility() == View.VISIBLE))
                {
                    schoolModel.school4 = spinnerSchool4.getSelectedItem().toString();
                    schoolModel.from4 = spinnerSchoolFrom4.getSelectedItem().toString();
                    schoolModel.to4 = spinnerSchoolTo4.getSelectedItem().toString();
                    schoolModel.roll4 = etRoll4.getText().toString();
                }
                else
                {
                    schoolModel.school4 = "";
                    schoolModel.from4 = "";
                    schoolModel.to4 = "";
                    schoolModel.roll4 = "";
                }

                if(isSpinnerEmpty(spinnerSchool5, spinnerSchoolFrom5, spinnerSchoolTo5) && !(TextUtils.isEmpty(etRoll5.getText().toString().trim())) && (llSchool5.getVisibility() == View.VISIBLE))
                {
                    schoolModel.school5 = spinnerSchool5.getSelectedItem().toString();
                    schoolModel.from5 = spinnerSchoolFrom5.getSelectedItem().toString();
                    schoolModel.to5 = spinnerSchoolTo5.getSelectedItem().toString();
                    schoolModel.roll5 = etRoll5.getText().toString();
                }
                else
                {
                    schoolModel.school5 = "";
                    schoolModel.from5 = "";
                    schoolModel.to5 = "";
                    schoolModel.roll5 = "";
                }

                if(first_school_status) {
                    uploadSchooldata();
                }
                else
                {
                    Toast.makeText(context, "Kindly Add your first School", Toast.LENGTH_LONG).show();
                }

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

    public boolean isSpinnerEmpty(Spinner schoolSpinner, Spinner fromSpinner, Spinner toSpinner){
        if((schoolSpinner.getSelectedItemPosition() >= 0) && (fromSpinner.getSelectedItemPosition() >= 0) && (toSpinner.getSelectedItemPosition() >= 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void uploadSchooldata(){
        schoolModel.user_id = prefsManager.getUserId();
        Gson gson = new Gson();
        final String request = gson.toJson(schoolModel);
        final String url = RequestURL.UPDATE_SCHOOL_DATA;

        ServiceAsync serviceAsync = new ServiceAsync(context, request, url, RequestURL.POST, new ServiceStatus() {
            @Override
            public void onSuccess(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                Log.e("Responses", "" + servicesResponse);
            }

            @Override
            public void onFailed(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                Log.e("Responsee", "" + servicesResponse);
                //Toast.makeText(context, servicesResponse.response_message, Toast.LENGTH_SHORT).show();
            }

            // Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        });
        serviceAsync.execute("");
    }

    public void setData(){
        String uniqueID = prefsManager.getUserId();
        String url = RequestURL.GET_SCHOOL_DATA;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", uniqueID);
        ServiceAsync serviceAsync = new ServiceAsync(context, jsonObject.toString(), url, RequestURL.POST, new ServiceStatus() {
            @Override
            public void onSuccess(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                if(servicesResponse.status.equals("success"))
                {
                    switch(servicesResponse.school_count)
                    {
                        case 1:
                            llSchool1.setVisibility(View.VISIBLE);
                            etRoll1.setText(servicesResponse.roll1);
                            spinnerSchool1.setSelection(adapter.getPosition(servicesResponse.school1));
                            spinnerSchoolFrom1.setSelection(adapter1.getPosition(servicesResponse.from1));
                            spinnerSchoolTo1.setSelection(adapter1.getPosition(servicesResponse.to1));

                            tvLessSchool.setVisibility(View.GONE);
                            tvMoreSchool.setVisibility(View.VISIBLE);
                            break;

                        case 2:
                            llSchool1.setVisibility(View.VISIBLE);
                            llSchool2.setVisibility(View.VISIBLE);
                            etRoll1.setText(servicesResponse.roll1);
                            spinnerSchool1.setSelection(adapter.getPosition(servicesResponse.school1));
                            spinnerSchoolFrom1.setSelection(adapter1.getPosition(servicesResponse.from1));
                            spinnerSchoolTo1.setSelection(adapter1.getPosition(servicesResponse.to1));
                            etRoll2.setText(servicesResponse.roll2);
                            spinnerSchool2.setSelection(adapter.getPosition(servicesResponse.school2));
                            spinnerSchoolFrom2.setSelection(adapter1.getPosition(servicesResponse.from2));
                            spinnerSchoolTo2.setSelection(adapter1.getPosition(servicesResponse.to2));

                            tvLessSchool.setVisibility(View.VISIBLE);
                            tvMoreSchool.setVisibility(View.VISIBLE);
                            break;

                        case 3:
                            llSchool1.setVisibility(View.VISIBLE);
                            llSchool2.setVisibility(View.VISIBLE);
                            llSchool3.setVisibility(View.VISIBLE);
                            etRoll1.setText(servicesResponse.roll1);
                            spinnerSchool1.setSelection(adapter.getPosition(servicesResponse.school1));
                            spinnerSchoolFrom1.setSelection(adapter1.getPosition(servicesResponse.from1));
                            spinnerSchoolTo1.setSelection(adapter1.getPosition(servicesResponse.to1));
                            etRoll2.setText(servicesResponse.roll2);
                            spinnerSchool2.setSelection(adapter.getPosition(servicesResponse.school2));
                            spinnerSchoolFrom2.setSelection(adapter1.getPosition(servicesResponse.from2));
                            spinnerSchoolTo2.setSelection(adapter1.getPosition(servicesResponse.to2));
                            etRoll3.setText(servicesResponse.roll3);
                            spinnerSchool3.setSelection(adapter.getPosition(servicesResponse.school3));
                            spinnerSchoolFrom3.setSelection(adapter1.getPosition(servicesResponse.from3));
                            spinnerSchoolTo3.setSelection(adapter1.getPosition(servicesResponse.to3));

                            tvLessSchool.setVisibility(View.VISIBLE);
                            tvMoreSchool.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            llSchool1.setVisibility(View.VISIBLE);
                            llSchool2.setVisibility(View.VISIBLE);
                            llSchool3.setVisibility(View.VISIBLE);
                            llSchool4.setVisibility(View.VISIBLE);
                            etRoll1.setText(servicesResponse.roll1);
                            spinnerSchool1.setSelection(adapter.getPosition(servicesResponse.school1));
                            spinnerSchoolFrom1.setSelection(adapter1.getPosition(servicesResponse.from1));
                            spinnerSchoolTo1.setSelection(adapter1.getPosition(servicesResponse.to1));
                            etRoll2.setText(servicesResponse.roll2);
                            spinnerSchool2.setSelection(adapter.getPosition(servicesResponse.school2));
                            spinnerSchoolFrom2.setSelection(adapter1.getPosition(servicesResponse.from2));
                            spinnerSchoolTo2.setSelection(adapter1.getPosition(servicesResponse.to2));
                            etRoll3.setText(servicesResponse.roll3);
                            spinnerSchool3.setSelection(adapter.getPosition(servicesResponse.school3));
                            spinnerSchoolFrom3.setSelection(adapter1.getPosition(servicesResponse.from3));
                            spinnerSchoolTo3.setSelection(adapter1.getPosition(servicesResponse.to3));
                            etRoll4.setText(servicesResponse.roll4);
                            spinnerSchool4.setSelection(adapter.getPosition(servicesResponse.school4));
                            spinnerSchoolFrom4.setSelection(adapter1.getPosition(servicesResponse.from4));
                            spinnerSchoolTo4.setSelection(adapter1.getPosition(servicesResponse.to4));

                            tvLessSchool.setVisibility(View.VISIBLE);
                            tvMoreSchool.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            llSchool1.setVisibility(View.VISIBLE);
                            llSchool2.setVisibility(View.VISIBLE);
                            llSchool3.setVisibility(View.VISIBLE);
                            llSchool4.setVisibility(View.VISIBLE);
                            llSchool5.setVisibility(View.VISIBLE);
                            etRoll1.setText(servicesResponse.roll1);
                            spinnerSchool1.setSelection(adapter.getPosition(servicesResponse.school1));
                            spinnerSchoolFrom1.setSelection(adapter1.getPosition(servicesResponse.from1));
                            spinnerSchoolTo1.setSelection(adapter1.getPosition(servicesResponse.to1));
                            etRoll2.setText(servicesResponse.roll2);
                            spinnerSchool2.setSelection(adapter.getPosition(servicesResponse.school2));
                            spinnerSchoolFrom2.setSelection(adapter1.getPosition(servicesResponse.from2));
                            spinnerSchoolTo2.setSelection(adapter1.getPosition(servicesResponse.to2));
                            etRoll3.setText(servicesResponse.roll3);
                            spinnerSchool3.setSelection(adapter.getPosition(servicesResponse.school3));
                            spinnerSchoolFrom3.setSelection(adapter1.getPosition(servicesResponse.from3));
                            spinnerSchoolTo3.setSelection(adapter1.getPosition(servicesResponse.to3));
                            etRoll4.setText(servicesResponse.roll4);
                            spinnerSchool4.setSelection(adapter.getPosition(servicesResponse.school4));
                            spinnerSchoolFrom4.setSelection(adapter1.getPosition(servicesResponse.from4));
                            spinnerSchoolTo4.setSelection(adapter1.getPosition(servicesResponse.to4));
                            etRoll5.setText(servicesResponse.roll5);
                            spinnerSchool5.setSelection(adapter.getPosition(servicesResponse.school5));
                            spinnerSchoolFrom5.setSelection(adapter1.getPosition(servicesResponse.from5));
                            spinnerSchoolTo5.setSelection(adapter1.getPosition(servicesResponse.to5));

                            tvLessSchool.setVisibility(View.VISIBLE);
                            tvMoreSchool.setVisibility(View.GONE);
                            break;
                    }
                }
            }

            @Override
            public void onFailed(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                Toast.makeText(context, servicesResponse.response_message, Toast.LENGTH_SHORT).show();

            }
        });
        serviceAsync.execute("");
    }
}
