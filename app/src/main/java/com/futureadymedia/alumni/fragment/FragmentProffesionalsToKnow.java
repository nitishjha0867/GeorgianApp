package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by developer on 10/1/2016.
 */
public class FragmentProffesionalsToKnow extends BaseFragment implements View.OnClickListener {

    private Context context;
    private View view;
    private Spinner spinnerIndustry, spinnerSchool, spinnerHouse, spinnerBatch;
    private Button btnFind;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = LayoutInflater.from(context).inflate(R.layout.fragment_proffesionals_to_know, null);

        findId();
        setListener();
        setFont();

        setSpinner(spinnerIndustry, R.array.industries_list);
        setSpinner(spinnerSchool, R.array.school_name);
        setSpinner(spinnerHouse, R.array.house_name);
        setYearSpinner(spinnerBatch);

        return view;
    }

    @Override
    public void findId() {
        spinnerIndustry = (Spinner) view.findViewById(R.id.spinnerIndustry);
        spinnerSchool = (Spinner) view.findViewById(R.id.spinnerSchool);
        spinnerHouse = (Spinner) view.findViewById(R.id.spinnerHouse);
        spinnerBatch = (Spinner) view.findViewById(R.id.spinnerBatch);
        btnFind = (Button)view.findViewById(R.id.btnFind);
    }

    @Override
    public void setListener() {
        btnFind.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnFind:
                ((MainActivity)context).onFragmentChange(14);
                break;
        }
    }

    public void setSpinner(Spinner spinnerLocal, int resID)
    {
        CustomAdapter adapter = new CustomAdapter((MainActivity) context, R.layout.spinner_item, getResources().getStringArray(resID));
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerLocal.setAdapter(adapter);
    }

    public void setYearSpinner(Spinner spinnerLocal)
    {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        String[] year_array = years.toArray(new String[years.size()]);
        CustomAdapter adapter = new CustomAdapter((MainActivity) context, R.layout.spinner_item, year_array);
        spinnerLocal.setAdapter(adapter);
    }
}
