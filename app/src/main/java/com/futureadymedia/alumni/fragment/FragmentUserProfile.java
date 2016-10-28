package com.futureadymedia.alumni.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.model.PersonalProfile;
import com.futureadymedia.alumni.model.SignupModel;
import com.futureadymedia.alumni.services.RequestURL;
import com.futureadymedia.alumni.services.ServiceAsync;
import com.futureadymedia.alumni.services.ServiceResponse;
import com.futureadymedia.alumni.services.ServiceStatus;
import com.futureadymedia.alumni.utils.Constants;
import com.futureadymedia.alumni.utils.PrefsManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.android.camera.CropImageIntentBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by developer on 9/22/2016.
 */
public class FragmentUserProfile extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View view;
    private Context context;
    private ImageButton btnUpload;
    private Button btnCurrentAddress, btnSchoolDetails, btnProffesionalDetails, btnSaveDetails;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private Uri filePath;
    private Bitmap bitmap;
    private ImageView imageView;
    private SignupModel personalInfo;
    private TextView tvName, tvEmail, tvMobile, tvEditEmail, tvEditMobile;
    private EditText etEmail, etMobile;
    private Switch switchEmail, switchMobile;
    private PrefsManager prefsManager;
    private AlertDialog.Builder confirmation, success, failure;
    private AlertDialog alertdialog;
    private Boolean email_privacy = false, mobile_privacy = false;
    private ImageView ivDP;
    private static int REQUEST_CROP_PICTURE = 2;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_user_profile, null);
        prefsManager = new PrefsManager(context);
        confirmation = new AlertDialog.Builder(context);
        success = new AlertDialog.Builder(context);
        failure = new AlertDialog.Builder(context);
        success.setTitle("Success");
        success.setMessage("Data Updated successfully!");
        failure.setTitle("Failed");
        failure.setMessage("Data was not updated");
        findId();
        setFont();
        setListener();
        setData();

        return view;
    }

    @Override
    public void findId() {
        btnUpload = (ImageButton) view.findViewById(R.id.btnUpload);
        btnCurrentAddress = (Button) view.findViewById(R.id.btnCurrentAddress);
        btnSchoolDetails = (Button) view.findViewById(R.id.btnSchoolDetails);
        btnProffesionalDetails = (Button) view.findViewById(R.id.btnProffesionalDetails);
        btnSaveDetails = (Button) view.findViewById(R.id.btnSaveDetails);

        tvEmail = (TextView)view.findViewById(R.id.tvEmail);
        tvMobile = (TextView)view.findViewById(R.id.tvMobile);
        tvEditEmail = (TextView)view.findViewById(R.id.tvEditEmail);
        tvEditMobile = (TextView)view.findViewById(R.id.tvEditMobile);
        tvName = (TextView)view.findViewById(R.id.tvName);

        etEmail = (EditText)view.findViewById(R.id.etEmail);
        etMobile = (EditText)view.findViewById(R.id.etMobile);

        switchEmail = (Switch)view.findViewById(R.id.switchEmail);
        switchMobile = (Switch)view.findViewById(R.id.switchMobile);

        ivDP = (ImageView)view.findViewById(R.id.ivDP);
    }

    @Override
    public void setListener() {
        btnUpload.setOnClickListener(this);
        btnCurrentAddress.setOnClickListener(this);
        btnSchoolDetails.setOnClickListener(this);
        btnProffesionalDetails.setOnClickListener(this);
        btnSaveDetails.setOnClickListener(this);
        tvEditEmail.setOnClickListener(this);
        tvEditMobile.setOnClickListener(this);
        switchEmail.setOnCheckedChangeListener(this);
        switchMobile.setOnCheckedChangeListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpload:
                Log.e("Image", "btnclicked");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMG);
                break;

            case R.id.btnCurrentAddress:
                ((MainActivity) context).onFragmentChange(9);
                break;

            case R.id.btnSchoolDetails:
                ((MainActivity) context).onFragmentChange(10);
                break;

            case R.id.btnProffesionalDetails:
                ((MainActivity) context).onFragmentChange(11);
                break;

            case R.id.btnSaveDetails:
                uploadData();
                break;

            case R.id.tvEditEmail:
                tvEmail.setVisibility(View.GONE);
                etEmail.setVisibility(View.VISIBLE);
                break;

            case R.id.tvEditMobile:
                tvMobile.setVisibility(View.GONE);
                etMobile.setVisibility(View.VISIBLE);
                break;

           /* case R.id.switchEmail:
                if(switchEmail.isChecked())
                {
                    email_privacy = true;
                }
                else
                {
                    email_privacy = false;
                }
                Log.e("PrivacyEmail", ""+email_privacy);
                break;

            case R.id.switchMobile:
                if(switchMobile.isChecked())
                {
                    mobile_privacy = true;
                }
                else
                {
                    mobile_privacy = false;
                }
                Log.e("PrivacyMob", ""+mobile_privacy);
                break;*/
        }
    }

    public void setData(){
        String uniqueID = prefsManager.getUserId();
        String url = RequestURL.GET_PERSONAL_DATA;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", uniqueID);
        ServiceAsync serviceAsync = new ServiceAsync(context, jsonObject.toString(), url, RequestURL.POST, new ServiceStatus() {
            @Override
            public void onSuccess(Object o) {
                ServiceResponse servicesResponse = (ServiceResponse) o;

                //Log.i("Response", ""+servicesResponse.uid);

                if(servicesResponse.status.equals("success"))
                {
                    tvEmail.setText(servicesResponse.email);
                    etEmail.setText(servicesResponse.email);
                    tvMobile.setText(servicesResponse.mobile);
                    etMobile.setText(servicesResponse.mobile);
                    tvName.setText(servicesResponse.full_name);
                    switchMobile.setChecked(servicesResponse.mobile_privacy);
                    switchEmail.setChecked(servicesResponse.email_privacy);
                }
                else
                {
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

    public void uploadData() {
        personalInfo = new SignupModel();
        personalInfo.fullname = tvName.getText().toString();
        personalInfo.email = etEmail.getText().toString();
        personalInfo.mobile = etMobile.getText().toString();
        personalInfo.email_privacy = email_privacy;
        personalInfo.mobile_privacy = mobile_privacy;
        personalInfo.user_id = prefsManager.getUserId();
        Gson gson = new Gson();
        final String request = gson.toJson(personalInfo);
        final String url = RequestURL.ADD_UPDATE_USER;
       /* TextView title = new TextView(context);
        title.setText("Confirm!");
        title.setGravity(Gravity.CENTER_HORIZONTAL);*/
        confirmation.setMessage("Are you sure?\nyou want to update the data");
        confirmation.setTitle("Confirm");
        //confirmation.setCustomTitle(title);
        confirmation.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                alertdialog.dismiss();
                ServiceAsync serviceAsync = new ServiceAsync(context, request, url, RequestURL.POST, new ServiceStatus() {
                    @Override
                    public void onSuccess(Object o) {
                        ServiceResponse servicesResponse = (ServiceResponse) o;
                        if (servicesResponse.status.equals("success")) {
                            alertdialog = success.show();
                            alertdialog.show();
                            etEmail.setVisibility(View.GONE);
                            etMobile.setVisibility(View.GONE);
                            tvEmail.setVisibility(View.VISIBLE);
                            tvMobile.setVisibility(View.VISIBLE);
                            final Timer timer2 = new Timer();
                            timer2.schedule(new TimerTask() {
                                public void run() {
                                    alertdialog.dismiss();
                                    timer2.cancel(); //this will cancel the timer of the system
                                }
                            }, 2000); // the timer will count 2 seconds....
                        } else {
                            alertdialog = failure.show();
                            alertdialog.show();
                            etEmail.setVisibility(View.GONE);
                            etMobile.setVisibility(View.GONE);
                            tvEmail.setVisibility(View.VISIBLE);
                            tvMobile.setVisibility(View.VISIBLE);
                            final Timer timer2 = new Timer();
                            timer2.schedule(new TimerTask() {
                                public void run() {
                                    alertdialog.dismiss();
                                    timer2.cancel(); //this will cancel the timer of the system
                                }
                            }, 2000);
                        }
                    }

                    @Override
                    public void onFailed(Object o) {
                        ServiceResponse servicesResponse = (ServiceResponse) o;
                        Log.e("Response", "" + servicesResponse);
                        Toast.makeText(context, servicesResponse.response_message, Toast.LENGTH_SHORT).show();
                    }

                    // Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
                });
                serviceAsync.execute("");

            }
        });
        confirmation.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                Log.e("Clicked", "cancel");
            }
        });

        alertdialog = confirmation.create();
        alertdialog.show();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File croppedImageFile = new File(context.getFilesDir(), "test.jpg");
        Log.e("Inside", "activity result");
        if (requestCode == RESULT_LOAD_IMG  && resultCode == MainActivity.RESULT_OK && data != null && data.getData() != null) {
            /*Log.e("Inside", "IF");
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), filePath);
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                Log.e("bitmap", ""+bitmap);
                ivDP.setBackground(ob);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            Uri croppedImage = Uri.fromFile(croppedImageFile);

            CropImageIntentBuilder cropImage = new CropImageIntentBuilder(200, 200, croppedImage);
            cropImage.setOutlineColor(0xFF03A9F4);
            cropImage.setSourceImage(data.getData());

            startActivityForResult(cropImage.getIntent(context), REQUEST_CROP_PICTURE);
        }
        else if((requestCode == REQUEST_CROP_PICTURE  && resultCode == MainActivity.RESULT_OK))
        {
            Log.e("Image path", ""+croppedImageFile.getAbsolutePath());
            Log.e("Image bitmap decoded", ""+BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath()));
            ivDP.setImageBitmap(BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath()));
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.switchEmail:
                if (isChecked) {
                    email_privacy = true;
                }
                else
                {
                    email_privacy = false;
                }
                break;

            case R.id.switchMobile:
                if (isChecked) {
                    mobile_privacy = true;
                }
                else
                {
                    mobile_privacy = false;
                }
                break;
        }
    }




    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView)view.findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }*/
}
