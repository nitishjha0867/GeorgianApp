package com.futureadymedia.alumni.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;

/**
 * Created by developer on 9/22/2016.
 */
public class FragmentUserProfile extends BaseFragment implements View.OnClickListener {

    private View view;
    private Context context;
    private ImageButton btnUpload;
    private Button btnCurrentAddress, btnSchoolDetails;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_user_profile, null);

        findId();
        setFont();
        setListener();

        return view;
    }
    @Override
    public void findId() {
        btnUpload = (ImageButton)view.findViewById(R.id.btnUpload);
        btnCurrentAddress = (Button)view.findViewById(R.id.btnCurrentAddress);
        btnSchoolDetails = (Button)view.findViewById(R.id.btnSchoolDetails);
    }

    @Override
    public void setListener() {
        btnUpload.setOnClickListener(this);
        btnCurrentAddress.setOnClickListener(this);
        btnSchoolDetails.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnUpload:
                Log.e("yes", "clicked");
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            case R.id.btnCurrentAddress:
                ((MainActivity)context).onFragmentChange(9);
                break;

            case R.id.btnSchoolDetails:
                ((MainActivity)context).onFragmentChange(10);
        }
    }

   /* public void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }*/

   /* @Override
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
