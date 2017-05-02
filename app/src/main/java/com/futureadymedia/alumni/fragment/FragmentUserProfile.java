package com.futureadymedia.alumni.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.model.SignupModel;
import com.futureadymedia.alumni.services.RequestURL;
import com.futureadymedia.alumni.services.ServiceAsync;
import com.futureadymedia.alumni.services.ServiceResponse;
import com.futureadymedia.alumni.services.ServiceStatus;
import com.futureadymedia.alumni.utils.PrefsManager;
import com.futureadymedia.alumni.utils.TextFont;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.android.camera.CropImageIntentBuilder;
import com.vansuita.library.GaussianBlur;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by developer on 9/22/2016.
 */
public class FragmentUserProfile extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View view, viewEmail;
    private Context context;
    private ImageView btnUpload;
    private Button btnCurrentAddress, btnSchoolDetails, btnProffesionalDetails, btnSaveDetails;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private Uri filePath;
    private Bitmap bitmap;
    private ImageView imageView, ivSchoolDetails;
    private SignupModel personalInfo;
    private TextView tvName, tvName1, tvEmail, tvEmail1, tvMobile, tvEditEmail, tvEditMobile, tvChangedpText, tvSaveDetails, tvSchoolDetails;
    private EditText etEmail, etMobile;
    private Switch switchEmail, switchMobile;
    private PrefsManager prefsManager;
    private AlertDialog.Builder confirmation, success, failure;
    private AlertDialog alertdialog;
    private Boolean email_privacy = false, mobile_privacy = false;
    private ImageView ivDP;
    private static int REQUEST_CROP_PICTURE = 2;
    private File croppedImageFile;
    private LinearLayout llProfilePic;
    private String Profile_pic;
    private LinearLayout llSchoolDetails;

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

       // GaussianBlur.with(context).maxSixe(400).radius(25).put(R.drawable.dummy_image, llProfilePic);


       // GaussianBlur.


        return view;
    }

    @Override
    public void findId() {
        //btnUpload = (TextView) view.findViewById(R.id.btnUpload);
        /*btnCurrentAddress = (Button) view.findViewById(R.id.btnCurrentAddress);
        btnSchoolDetails = (Button) view.findViewById(R.id.btnSchoolDetails);
        btnProffesionalDetails = (Button) view.findViewById(R.id.btnProffesionalDetails);
        btnSaveDetails = (Button) view.findViewById(R.id.btnSaveDetails);*/

        tvChangedpText = (TextView)view.findViewById(R.id.tvChangedpText);
        tvEmail = (TextView)view.findViewById(R.id.tvEmail);
        tvEmail1 = (TextView)view.findViewById(R.id.tvEmail1);
        tvMobile = (TextView)view.findViewById(R.id.tvMobile);
        /*tvEditEmail = (TextView)view.findViewById(R.id.tvEditEmail);
        tvEditMobile = (TextView)view.findViewById(R.id.tvEditMobile);*/
        tvName = (TextView)view.findViewById(R.id.tvName);
        tvName1 = (TextView)view.findViewById(R.id.tvName1);

        etEmail = (EditText)view.findViewById(R.id.etEmail);
        etMobile = (EditText)view.findViewById(R.id.etMobile);

        /*switchEmail = (Switch)view.findViewById(R.id.switchEmail);
        switchMobile = (Switch)view.findViewById(R.id.switchMobile);*/

        ivDP = (ImageView)view.findViewById(R.id.ivDP);
        llProfilePic = (LinearLayout) view.findViewById(R.id.llProfilePic);

        viewEmail =  view.findViewById(R.id.viewEmail);

        tvSaveDetails = (TextView)view.findViewById(R.id.tvSaveDetails);

        llSchoolDetails = (LinearLayout)view.findViewById(R.id.llSchoolDetails);

        ivSchoolDetails = (ImageView)view.findViewById(R.id.ivSchoolDetails);

        tvSchoolDetails = (TextView)view.findViewById(R.id.tvSchoolDetails);
    }

    @Override
    public void setListener() {
        ivDP.setOnClickListener(this);
        /*btnCurrentAddress.setOnClickListener(this);
        btnSchoolDetails.setOnClickListener(this);
        btnProffesionalDetails.setOnClickListener(this);*/
        tvSaveDetails.setOnClickListener(this);
        tvEmail.setOnClickListener(this);
        tvMobile.setOnClickListener(this);
        /*switchEmail.setOnCheckedChangeListener(this);
        switchMobile.setOnCheckedChangeListener(this);*/
        llSchoolDetails.setOnClickListener(this);
    }

    @Override
    public void setFont() {
        tvChangedpText.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvName.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvName1.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvEmail.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
        tvEmail1.setTypeface(TextFont.setFontFamily(context, TextFont.BARIOL_REGULAR));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivDP:
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

            case R.id.tvSaveDetails:
                uploadData();
                break;

            case R.id.tvEmail:
                tvEmail.setVisibility(View.GONE);
                etEmail.setVisibility(View.VISIBLE);
                viewEmail.setVisibility(View.GONE);
                break;

            case R.id.tvMobile:
                tvMobile.setVisibility(View.GONE);
                etMobile.setVisibility(View.VISIBLE);
                break;

            case R.id.llSchoolDetails:
                llSchoolDetails.setBackgroundResource(R.drawable.profile_square_filled);
                ivSchoolDetails.setImageResource(R.drawable.dashboard_school_white);
                tvSchoolDetails.setTextColor(ContextCompat.getColor(context, R.color.white));
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
        tvName.setText(prefsManager.getUserName());
        tvName1.setText(prefsManager.getUserName());
        tvEmail.setText(prefsManager.getUserEmail());
        tvEmail1.setText(prefsManager.getUserEmail());
        etEmail.setText(prefsManager.getUserEmail());
        tvMobile.setText(prefsManager.getUserMobile());
        etMobile.setText(prefsManager.getUserMobile());
        if(!prefsManager.getUserProfilePic().equals(""))
        {
            ivDP.setImageResource(R.color.transparent);
            loadImageFromStorage("/data/data/com.futureadymedia.alumni/app_imageDir", prefsManager.getUserProfilePic());
        }
        else
        {

        }

       /* String uniqueID = prefsManager.getUserId();
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
                    tvEmail1.setText(servicesResponse.email);
                    etEmail.setText(servicesResponse.email);
                    tvMobile.setText(servicesResponse.mobile);
                    etMobile.setText(servicesResponse.mobile);
                    tvName.setText(toTitleCase(servicesResponse.full_name));
                    tvName1.setText(toTitleCase(servicesResponse.full_name));
                   /* switchMobile.setChecked(servicesResponse.mobile_privacy);
                    switchEmail.setChecked(servicesResponse.email_privacy);*/
                  /* loadImageFromStorage("/data/data/com.futureadymedia.alumni/app_imageDir", servicesResponse.profile_pic);
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
        serviceAsync.execute("");*/
    }

    public void uploadData() {
        try {
            Profile_pic = getStringImage(BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath()));
        } catch (Exception e) {
        }

        personalInfo = new SignupModel();
        personalInfo.fullname = tvName.getText().toString();
        personalInfo.email = etEmail.getText().toString();
        personalInfo.mobile = etMobile.getText().toString();
        personalInfo.email_privacy = email_privacy;
        personalInfo.mobile_privacy = mobile_privacy;
        personalInfo.profile_pic = Profile_pic;
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
                byte[] decodedString = Base64.decode(Profile_pic, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                //ivDP.setImageBitmap(decodedByte);
                Log.e("ACTUAL PATH", saveToInternalStorage(decodedByte));
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
                            viewEmail.setVisibility(View.VISIBLE);
                            MainActivity.saveData(servicesResponse);
                            /*tvName.setText(servicesResponse.full_name);
                            tvName1.setText(servicesResponse.full_name);
                            tvEmail.setText(servicesResponse.email);
                            tvEmail1.setText(servicesResponse.email);
                            tvMobile.setText(servicesResponse.mobile);
                            loadImageFromStorage("/data/data/com.futureadymedia.alumni/app_imageDir", servicesResponse.profile_pic);*/
                            setData();
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
                            viewEmail.setVisibility(View.VISIBLE);
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


    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public void loadImageFromStorage(String path, String db_profile_pic)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            Bitmap catBitmap = GaussianBlur.with(context).radius(25).noScaleDown(false).render(b);
            llProfilePic.setBackground(new BitmapDrawable(context.getResources(), catBitmap));
            ivDP.setImageBitmap(b);
            Profile_pic = getStringImage(b);
        }
        catch (FileNotFoundException e)
        {
           // e.printStackTrace();
            String db_val_profile_pic = db_profile_pic;
            Profile_pic = db_profile_pic;
            byte[] decodedString = Base64.decode(db_val_profile_pic, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Bitmap catBitmap = GaussianBlur.with(context).radius(25).noScaleDown(false).render(decodedByte);
            llProfilePic.setBackground(new BitmapDrawable(context.getResources(), catBitmap));
            ivDP.setImageBitmap(decodedByte);
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        croppedImageFile = new File(context.getFilesDir(), "test.jpg");
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
            Bitmap catBitmap = GaussianBlur.with(context).radius(25).noScaleDown(false).render(BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath()));
            llProfilePic.setBackground(new BitmapDrawable(context.getResources(), catBitmap));
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

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
