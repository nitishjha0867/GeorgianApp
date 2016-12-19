package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.model.HouseDetails;
import com.futureadymedia.alumni.model.SchoolDetails;
import com.futureadymedia.alumni.services.RequestURL;
import com.futureadymedia.alumni.services.ServiceAsync;
import com.futureadymedia.alumni.services.ServiceResponse;
import com.futureadymedia.alumni.services.ServiceStatus;
import com.futureadymedia.alumni.utils.PrefsManager;
import com.futureadymedia.alumni.utils.TextFont;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by developer on 10/28/2016.
 */
public class FragmentHouseDetails extends BaseFragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private Context context;
    private View view;
   /* private CheckBox checkHouse;
    private ImageView checkHouse1_img;*/
    private TextView tvHouseDetails,tvSelectHouse;
    private PrefsManager prefsManager;
    private String[] school_selected;
    private String[] house_selected;
    private ArrayList<String> House_selected = new ArrayList<String>();
    private LinearLayout checkGroup;
    private LinearLayout.LayoutParams params, params_check, params_view;
    private Button btnSaveHouse;
    private HouseDetails housemodel;

    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(context).inflate(R.layout.fragment_house_details, null);
        prefsManager = new PrefsManager(context);
        housemodel = new HouseDetails();
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(0, 7, 0, 7);
        params_check = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_check.gravity = Gravity.CENTER;

        params_view = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 10);
       params_view.setMargins(0, 5, 0, 5);

        findId();
        setListener();
        setFont();
        fetchData();
        return view;
    }

    @Override
    public void findId() {
        /*checkHouse = (CheckBox) view.findViewById(R.id.checkHouse1);
        checkHouse1_img = (ImageView) view.findViewById(R.id.checkHouse1_img);*/
        tvSelectHouse = (TextView) view.findViewById(R.id.tvSelectHouse);
        tvHouseDetails = (TextView) view.findViewById(R.id.tvHouseDetails);
        checkGroup = (LinearLayout) view.findViewById(R.id.checkGroup);
        btnSaveHouse = (Button) view.findViewById(R.id.btnSaveHouse);
    }

    @Override
    public void setListener() {
        //checkHouse.setOnCheckedChangeListener(this);
        btnSaveHouse.setOnClickListener(this);
    }

    @Override
    public void setFont() {
        //checkHouse.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvHouseDetails.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvSelectHouse.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        btnSaveHouse.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSaveHouse:
                updateHouse();
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked )
        {
            House_selected.add(buttonView.getTag().toString()+"_"+buttonView.getText().toString());
        }
        else
        {
            House_selected.remove(buttonView.getTag().toString()+"_"+buttonView.getText().toString());
        }

    }

    public void updateHouse(){
        Gson gson = new Gson();
        housemodel.user_id = prefsManager.getUserId();
        housemodel.houses = House_selected;
        final String request = gson.toJson(housemodel);
        final String url = RequestURL.UPDATE_HOUSE_DATA;

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

    public void showIcon(int resid){
        ImageView imv = (ImageView) view.findViewById(resid);
        imv.setImageResource(R.drawable.check);
    }

    public void removeIcon(ImageView ivChck){
        ivChck.setImageResource(R.color.transparent);
    }

    public void fetchData() {

        String uniqueID = prefsManager.getUserId();
        String url = RequestURL.GET_HOUSE_DATA;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", uniqueID);
        ServiceAsync serviceAsync = new ServiceAsync(context, jsonObject.toString(), url, RequestURL.POST, new ServiceStatus() {
            @Override
            public void onSuccess(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                //Log.e("House response", ""+servicesResponse.school_name);
                school_selected = servicesResponse.school_name;
                house_selected = servicesResponse.house_name;
                setOption();
            }

            @Override
            public void onFailed(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;
                Toast.makeText(context, servicesResponse.response_message, Toast.LENGTH_SHORT).show();
            }
        });
        serviceAsync.execute("");
    }

    public void setOption(){
        for(int i = 0; i< school_selected.length; i++)
        {
            String[] houseArray;
            String tagText;
            switch(school_selected[i])
            {
                case "RMS Ajmer":
                    houseArray = getResources().getStringArray(R.array.house_ajmer);
                    tagText = "RMS Ajmer";
                    break;

                case "RMS Banglore":
                    houseArray = getResources().getStringArray(R.array.house_banglore);
                    tagText = "RMS Banglore";
                    break;

                case "RMS Belgaum":
                    houseArray = getResources().getStringArray(R.array.house_belgaum);
                    tagText = "RMS Belgaum";
                    break;

                case "RMS Chail":
                    houseArray = getResources().getStringArray(R.array.house_chail);
                    tagText = "RMS Chail";
                    break;

                case "RMS Dholpur":
                    houseArray = getResources().getStringArray(R.array.house_dholpur);
                    tagText = "RMS Dholpur";
                    break;

                default:
                    houseArray = getResources().getStringArray(R.array.house_chail);
                    tagText = "RMS Chail";
            }

            for(int j = 0; j<houseArray.length; j++)
            {
                LinearLayout ll = new LinearLayout(context);
                ll.setLayoutParams(params);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setGravity(Gravity.CENTER_HORIZONTAL);
                ll.setId(j);
                CheckBox chckBox = new CheckBox(context);
                chckBox.setLayoutParams(params_check);
                chckBox.setText(houseArray[j]);
                chckBox.setCompoundDrawablePadding(40);
                chckBox.setTextSize(20);
                chckBox.setId(j);
                chckBox.setTag(tagText);
                chckBox.setButtonDrawable(R.color.transparent);
                chckBox.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkimageselector, 0);
                chckBox.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
                chckBox.setTextColor(getResources().getColor(R.color.grey));

                Boolean temp_check = setCheckSelection(tagText+"_"+houseArray[j]);
                chckBox.setChecked(temp_check);
                chckBox.setOnCheckedChangeListener(this);
                View view1 = new View(context);
                view1.setLayoutParams(params_view);
                view1.setBackgroundResource(R.drawable.light_seperator);
                ll.addView(chckBox);
                //ll.addView(view1);
                checkGroup.addView(ll);
                //checkGroup.addView(view1);;
            }

        }
    }

    public boolean setCheckSelection(String schoolhousename){
        for(String s: house_selected){
            if(s.equals(schoolhousename)) {
                House_selected.add(schoolhousename);
                return true;
            }
        }
        return false;
    }
}
