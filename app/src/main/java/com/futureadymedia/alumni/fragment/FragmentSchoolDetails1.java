package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import com.futureadymedia.alumni.utils.Constants;
import com.futureadymedia.alumni.utils.PrefsManager;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by developer on 9/23/2016.
 */
public class FragmentSchoolDetails1 extends BaseFragment implements View.OnClickListener{

    private Context context;
    private View view;
    private Spinner spinnerSchool1, spinnerSchoolTo1, spinnerSchoolFrom1, spinnerSchool2, spinnerSchoolTo2, spinnerSchoolFrom2, spinnerSchool3, spinnerSchoolTo3, spinnerSchoolFrom3, spinnerSchool4, spinnerSchoolTo4, spinnerSchoolFrom4, spinnerSchool5, spinnerSchoolTo5, spinnerSchoolFrom5;
    private TextView tvMoreSchool, tvMoreHouse, tvLessHouse, tvLessSchool, tvSchoolno, tvHouseno;
    private LinearLayout llSchool1, mainLinear, llAddHouse, layoutSchool, layoutHouse;
    private int schoolCount, houseCount;
    private LayoutInflater inflater;
    private View addSchool, addHouse;
    private TextInputLayout textInputLayout;
    private EditText etRoll1, etRoll2, etRoll3, etRoll4, etRoll5;
    private Button btnSaveSchool;
    private SchoolDetails schoolModel;
    private PrefsManager prefsManager;


    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = LayoutInflater.from(context).inflate(R.layout.fragment_school_details, null);

        findId();
        setListener();
        setFont();
        schoolCount = mainLinear.getChildCount();
        houseCount = llAddHouse.getChildCount();
        Log.e("School Count", ""+schoolCount);
        layoutSchool.setTag("layout_school_"+schoolCount);
        setSchoolSpinner(spinnerSchool1);
        setYearSpinner(spinnerSchoolFrom1, spinnerSchoolTo1);
        prefsManager = new PrefsManager(context);
        schoolModel = new SchoolDetails();

        spinnerSchool1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Log.e("SpinnerFragment", ":::schoolspinner SELECTED POSITION:::" + String.valueOf(spinnerSchool1.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Log.e("SpinnerFragment", ":::schoolspinner SELECTED POSITION:::" + String.valueOf(spinnerSchool1.getSelectedItemPosition()));
            }

        });




        return view;
    }

    @Override
    public void findId() {
        tvMoreSchool = (TextView) view.findViewById(R.id.tvMoreSchool);
        mainLinear = (LinearLayout) view.findViewById(R.id.mainLinear);
        layoutSchool = (LinearLayout) view.findViewById(R.id.llSchool1);

        llAddHouse = (LinearLayout) view.findViewById(R.id.llAddHouse);
        spinnerSchool1 = (com.futureadymedia.alumni.widgets.HintSpinner) view.findViewById(R.id.spinnerSchool1);
        spinnerSchoolFrom1 = (com.futureadymedia.alumni.widgets.HintSpinner) view.findViewById(R.id.spinnerSchoolFrom1);
        spinnerSchoolTo1 = (com.futureadymedia.alumni.widgets.HintSpinner) view.findViewById(R.id.spinnerSchoolTo1);
        tvLessSchool = (TextView) view.findViewById(R.id.tvLessSchool);
        btnSaveSchool = (Button) view.findViewById(R.id.btnSaveSchool);
        inflater = (LayoutInflater)getActivity().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        etRoll1 = (EditText)view.findViewById(R.id.etRoll1);
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


    public void addSchool(int childCount){
        tvLessSchool.setVisibility(View.VISIBLE);

        addSchool = inflater.inflate(R.layout.more_school, null);
        layoutSchool = (LinearLayout)addSchool.findViewById(R.id.llSchool1);
        switch (childCount)
        {
            case 1:
                etRoll2 = (EditText)layoutSchool.findViewById(R.id.etRoll1);
                spinnerSchool2 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchool1);
                spinnerSchoolFrom2 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchoolFrom1);
                spinnerSchoolTo2 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchoolTo1);
                etRoll2.setId(R.id.etRoll2);
                spinnerSchool2.setId(R.id.spinnerSchool2);
                setSchoolSpinner(spinnerSchool2);
                setYearSpinner(spinnerSchoolFrom2, spinnerSchoolTo2);
                break;

            case 2:
                etRoll3 = (EditText)layoutSchool.findViewById(R.id.etRoll1);
                spinnerSchool3 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchool1);
                spinnerSchoolFrom3 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchoolFrom1);
                spinnerSchoolTo3 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchoolTo1);
                spinnerSchool3.setId(R.id.spinnerSchool3);
                etRoll3.setId(R.id.etRoll3);
                setSchoolSpinner(spinnerSchool3);
                setYearSpinner(spinnerSchoolFrom3, spinnerSchoolTo3);
                break;

            case 3:
                etRoll4 = (EditText)layoutSchool.findViewById(R.id.etRoll1);
                spinnerSchool4 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchool1);
                spinnerSchoolFrom4 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchoolFrom1);
                spinnerSchoolTo4 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchoolTo1);
                spinnerSchool4.setId(R.id.spinnerSchool4);
                etRoll4.setId(R.id.etRoll4);
                setSchoolSpinner(spinnerSchool4);
                setYearSpinner(spinnerSchoolFrom4, spinnerSchoolTo4);
                break;

            case 4:
                etRoll5 = (EditText)layoutSchool.findViewById(R.id.etRoll1);
                spinnerSchool5 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchool1);
                spinnerSchoolFrom5 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchoolFrom1);
                spinnerSchoolTo5 = (com.futureadymedia.alumni.widgets.HintSpinner) layoutSchool.findViewById(R.id.spinnerSchoolTo1);
                spinnerSchool5.setId(R.id.spinnerSchool5);
                etRoll5.setId(R.id.etRoll5);
                setSchoolSpinner(spinnerSchool5);
                setYearSpinner(spinnerSchoolFrom5, spinnerSchoolTo5);
                break;
        }



        mainLinear.addView(addSchool);
    }


    @Override
    public void onClick(View v) {
        //Toast.makeText(getActivity(), "clicked"+v.getTag(), Toast.LENGTH_SHORT).show();
        switch(v.getId())
        {
            case R.id.tvMoreSchool:
                v.getTag();

                if(schoolCount < 5)
                {

                   addSchool(schoolCount);
                    schoolCount = mainLinear.getChildCount();
                    Log.e("School Count", ""+schoolCount);
               }
                else
                {
                    Toast.makeText(getActivity(), "Limit reached",
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tvLessSchool:
                if(schoolCount>1)
                {
                    Toast.makeText(getActivity(), "Clicked "+schoolCount, Toast.LENGTH_SHORT).show();
                    schoolCount = removeView(schoolCount, mainLinear, tvLessSchool);
                    Log.e("schoolclient", ""+schoolCount);
                }
                else
                {
                    Toast.makeText(getActivity(), "Clicked "+schoolCount, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnSaveSchool:
                ArrayList<String> listSchool = new ArrayList<String>();
                if(etRoll1 != null)
                {
                    schoolModel.roll1 = etRoll1.getText().toString().trim().isEmpty()?"":etRoll1.getText().toString().trim();
                    schoolModel.school1 = spinnerSchool1.getSelectedItem().toString().isEmpty()?"":spinnerSchool1.getSelectedItem().toString();
                    schoolModel.from1 = spinnerSchoolFrom1.getSelectedItem().toString().isEmpty()?"":spinnerSchoolFrom1.getSelectedItem().toString();
                    schoolModel.to1 = spinnerSchoolTo1.getSelectedItem().toString().isEmpty()?"":spinnerSchoolTo1.getSelectedItem().toString();
                }
                else
                {
                    schoolModel.roll1 = "";
                    schoolModel.school1 = "";
                    schoolModel.from1 = "";
                    schoolModel.to1 = "";
                }
                if(etRoll2 != null)
                {
                    schoolModel.roll2 = etRoll2.getText().toString().trim().isEmpty()?"":etRoll2.getText().toString().trim();
                    schoolModel.school2 = spinnerSchool2.getSelectedItem().toString().isEmpty()?null:spinnerSchool2.getSelectedItem().toString();
                    schoolModel.from2 = spinnerSchoolFrom2.getSelectedItem().toString().isEmpty()?null:spinnerSchoolFrom2.getSelectedItem().toString();
                    schoolModel.to2 = spinnerSchoolTo2.getSelectedItem().toString().isEmpty()?null:spinnerSchoolTo2.getSelectedItem().toString();
                }
                else
                {
                    schoolModel.roll2 = "";
                    schoolModel.school2 = "";
                    schoolModel.from2 = "";
                    schoolModel.to2 = "";
                }
                if(etRoll3 != null)
                {
                    schoolModel.roll3 = etRoll3.getText().toString().trim().isEmpty()?"":etRoll3.getText().toString().trim();
                    schoolModel.school3 = spinnerSchool3.getSelectedItem().toString().isEmpty()?"":spinnerSchool3.getSelectedItem().toString();
                    schoolModel.from3 = spinnerSchoolFrom3.getSelectedItem().toString().isEmpty()?"":spinnerSchoolFrom3.getSelectedItem().toString();
                    schoolModel.to3 = spinnerSchoolTo3.getSelectedItem().toString().isEmpty()?"":spinnerSchoolTo3.getSelectedItem().toString();
                }
                else
                {
                    schoolModel.roll3 = "";
                    schoolModel.school3 = "";
                    schoolModel.from3 = "";
                    schoolModel.to3 = "";
                }
                if(etRoll4 != null)
                {
                    schoolModel.roll4 = etRoll4.getText().toString().trim().isEmpty()?"":etRoll1.getText().toString().trim();
                    schoolModel.school4 = spinnerSchool4.getSelectedItem().toString().isEmpty()?"":spinnerSchool4.getSelectedItem().toString();
                    schoolModel.from4 = spinnerSchoolFrom4.getSelectedItem().toString().isEmpty()?"":spinnerSchoolFrom4.getSelectedItem().toString();
                    schoolModel.to4 = spinnerSchoolTo4.getSelectedItem().toString().isEmpty()?"":spinnerSchoolTo4.getSelectedItem().toString();
                }
                else
                {
                    schoolModel.roll4 = "";
                    schoolModel.school4 = "";
                    schoolModel.from4 = "";
                    schoolModel.to4 = "";
                }
                if(etRoll5 != null)
                {
                    schoolModel.roll5 = etRoll5.getText().toString().trim().isEmpty()?"":etRoll5.getText().toString().trim();
                    schoolModel.school5 = spinnerSchool5.getSelectedItem().toString().isEmpty()?"":spinnerSchool5.getSelectedItem().toString();
                    schoolModel.from5 = spinnerSchoolFrom5.getSelectedItem().toString().isEmpty()?"":spinnerSchoolFrom5.getSelectedItem().toString();
                    schoolModel.to5 = spinnerSchoolTo5.getSelectedItem().toString().isEmpty()?"":spinnerSchoolTo5.getSelectedItem().toString();
                }
                else
                {
                    schoolModel.roll5 = "";
                    schoolModel.school5 = "";
                    schoolModel.from5 = "";
                    schoolModel.to5 = "";
                }
                schoolModel.user_id = prefsManager.getUserId() ;
                saveSchoolDetails();
                break;

        }

       // Log.e("VIEWTAG", ""+v.getTag());
    }

    public void saveSchoolDetails(){
        Gson gson = new Gson();
        String request = gson.toJson(schoolModel);
        String url = RequestURL.SCHOOL_UPLOAD;

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

            // Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        });
        serviceAsync.execute("");
    }


    public int removeView(int childCount, LinearLayout llParent, TextView tvLess)
    {
        Log.e("Child Count",""+childCount);
        llParent.removeViewAt((childCount-1));
        childCount --;
        if(childCount < 1)
        {
            tvLess.setVisibility(View.INVISIBLE);
        }
        return childCount;
    }

    public void setYearSpinner(Spinner spinner2, Spinner spinner3)
    {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        String[] year_array = years.toArray(new String[years.size()]);
        CustomAdapter adapter1 = new CustomAdapter((MainActivity) context, R.layout.spinner_item, year_array);
        spinner2.setAdapter(adapter1);
        spinner3.setAdapter(adapter1);

    }

    public void setSchoolSpinner(Spinner spinner1)
    {
        CustomAdapter adapter = new CustomAdapter((MainActivity) context, R.layout.spinner_item, getResources().getStringArray(R.array.school_name));
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(adapter);
    }


}
