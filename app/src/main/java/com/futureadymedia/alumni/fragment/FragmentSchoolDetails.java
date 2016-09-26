package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by developer on 9/23/2016.
 */
public class FragmentSchoolDetails extends BaseFragment implements View.OnClickListener{

    private Context context;
    private View view;
    private Spinner spinnerSchool1, spinnerSchoolTo1, spinnerSchoolFrom1;
    private TextView tvMoreSchool;
    private LinearLayout llSchool1, mainLinear;
    private int childCount;

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
        setSpinner(spinnerSchool1, spinnerSchoolFrom1, spinnerSchoolTo1);

       /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
        R.array.school_name, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerSchool.setAdapter(adapter);
        spinnerSchoolTo.setAdapter(adapter);
        spinnerSchoolFrom.setAdapter(adapter);*/


       // Log.e("SpinnerFragment", ":::schoolspinner SELECTED POSITION:::" + spinnerSchool.getSelectedItemPosition());

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
        spinnerSchool1 = (Spinner)view.findViewById(R.id.spinnerSchool1);
        spinnerSchoolTo1 = (Spinner)view.findViewById(R.id.spinnerSchoolTo1);
        spinnerSchoolFrom1 = (Spinner)view.findViewById(R.id.spinnerSchoolFrom1);
        tvMoreSchool = (TextView) view.findViewById(R.id.tvMoreSchool);
        mainLinear = (LinearLayout) view.findViewById(R.id.mainLinear);
        llSchool1 = (LinearLayout) view.findViewById(R.id.llSchool1);
    }

    @Override
    public void setListener() {
        tvMoreSchool.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.tvMoreSchool:
                childCount = mainLinear.getChildCount()+1;
                if(childCount <= 5)
                {
                    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    View addView = inflater.inflate(R.layout.more_school, null);
                    LinearLayout layoutschool = (LinearLayout)addView.findViewById(R.id.llSchool1);
                    switch(childCount)
                    {
                        case 1:
                            layoutschool.setId(R.id.llSchool1);
                            break;

                        case 2:
                            layoutschool.setId(R.id.llSchool2);
                            TextView tvSchoolno = (TextView)addView.findViewById(R.id.tvSchoolno1);
                            TextInputLayout textInputLayout = (TextInputLayout)layoutschool.findViewById(R.id.etRoll_layout);
                            EditText edit1 = (EditText)textInputLayout.findViewById(R.id.etRoll1);
                            com.futureadymedia.alumni.widgets.HintSpinner spinnerSchool = (com.futureadymedia.alumni.widgets.HintSpinner)layoutschool.findViewById(R.id.spinnerSchool1);
                            com.futureadymedia.alumni.widgets.HintSpinner spinnerFrom = (com.futureadymedia.alumni.widgets.HintSpinner)layoutschool.findViewById(R.id.spinnerSchoolFrom1);
                            com.futureadymedia.alumni.widgets.HintSpinner spinnerTo = (com.futureadymedia.alumni.widgets.HintSpinner)layoutschool.findViewById(R.id.spinnerSchoolTo1);
                            tvSchoolno.setId(R.id.tvSchoolno2);
                            tvSchoolno.setText("School "+childCount);
                            edit1.setId(R.id.etRoll2);
                            spinnerSchool.setId(R.id.spinnerSchool2);
                            spinnerFrom.setId(R.id.spinnerSchoolFrom2);
                            spinnerTo.setId(R.id.spinnerSchoolTo2);
                            setSpinner(spinnerSchool, spinnerFrom, spinnerTo);
                            break;

                        case 3:
                            Toast.makeText(getActivity(), "Case"+childCount,
                                    Toast.LENGTH_LONG).show();
                            break;

                        case 4:
                            Toast.makeText(getActivity(), "Case"+childCount,
                                    Toast.LENGTH_LONG).show();
                            break;

                        case 5:
                            Toast.makeText(getActivity(), "Case"+childCount,
                                    Toast.LENGTH_LONG).show();
                            break;
                    }

                    mainLinear.addView(addView);
                }
                else
                {
                    Toast.makeText(getActivity(), "Limit reached",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void setSpinner(Spinner spinner1, Spinner spinner2, Spinner spinner3)
    {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        String[] year_array = years.toArray(new String[years.size()]);

        CustomAdapter adapter = new CustomAdapter((MainActivity) context, R.layout.spinner_item, getResources().getStringArray(R.array.school_name));
        CustomAdapter adapter1 = new CustomAdapter((MainActivity) context, R.layout.spinner_item, year_array);

        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter1);
        spinner3.setAdapter(adapter1);

    }

}
