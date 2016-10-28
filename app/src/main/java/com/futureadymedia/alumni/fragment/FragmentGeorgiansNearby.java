package com.futureadymedia.alumni.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.futureadymedia.alumni.activity.MainActivity;
import com.futureadymedia.alumni.utils.PermissionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by developer on 9/30/2016.
 */
public class FragmentGeorgiansNearby extends BaseFragment implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnInfoWindowClickListener,  GoogleMap.OnMarkerDragListener {

    private Context context;
    private View view;
    private Button btnBatchMate, btnHouseMate, btnSchoolMate, btnAllMate;
    public static String MAP_TAG = "MAPcycle";

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;

    MapView mMapView;
    private GoogleMap gMap = null;
    private View rootView;
    private Marker myMarker;

    private Marker mPerth;

    private Marker mSydney;

    private Marker mBrisbane;

    private Marker mAdelaide;

    private Marker mMelbourne;


    private double HOME_LAT =  19.2078186;
    private double HOME_LONG =  72.869271;
    private double HOME1_LAT =  19.2082904;
    private double HOME1_LONG =  72.8641981;
    private Double OFF_LAT =  19.117921;
    private Double OFF_LONG =  72.8544203;
    private Double OFF1_LAT =  20.2082904;
    private Double OFF1_LONG =  73.8641981;


    private static final LatLng HOME = new LatLng(19.2078186,72.8632803);

    private static final LatLng HOME1 = new LatLng(19.2082904,72.8641981);

    private static final LatLng OFFICE = new LatLng(19.117921,72.8544203);

    private static final LatLng OFFICE1 = new LatLng(20.2082904,73.8641981);

    /** Demonstrates customizing the info window and/or its contents. */
    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;

        private final View mContents;

        CustomInfoWindowAdapter() {
            mWindow = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getActivity().getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            /*if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_window) {
                // This means that getInfoContents will be called.
                return null;
            }*/
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
           /* if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_contents) {
                // This means that the default info contents will be used.
                return null;
            }*/
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int badge;
            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            if (marker.equals(mBrisbane)) {
                badge = R.drawable.badge_qld;
            } else if (marker.equals(mAdelaide)) {
                badge = R.drawable.badge_sa;
            } else if (marker.equals(mSydney)) {
                badge = R.drawable.badge_nsw;
            } else if (marker.equals(mMelbourne)) {
                badge = R.drawable.badge_victoria;
            } else if (marker.equals(mPerth)) {
                badge = R.drawable.badge_wa;
            } else {
                // Passing 0 to setImageResource will clear the image view.
                badge = 0;
            }
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }


    /**
     * Keeps track of the last selected marker (though it may no longer be selected).  This is
     * useful for refreshing the info window.
     */
    private Marker mLastSelectedMarker;

    private final List<Marker> mMarkerRainbow = new ArrayList<Marker>();

    private TextView mTopText;

    private TextView mTagText;

    private SeekBar mRotationBar;

    private CheckBox mFlatBox;

    private RadioGroup mOptions;

    private final Random mRandom = new Random();

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_georgians_nearby, null);

        findId();
        setFont();
        setListener();


        mMapView.onCreate(savedInstanceState);
        Log.e(MAP_TAG, "before Sync started");


       /* mOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mLastSelectedMarker != null && mLastSelectedMarker.isInfoWindowShown()) {
                    // Refresh the info window when the info window's content has changed.
                    mLastSelectedMarker.showInfoWindow();
                }
            }
        });*/

        mMapView.getMapAsync(this);

        mMapView.onResume();

        return view;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        gMap = map;
        Log.e(MAP_TAG, "before addMArkerCall");
        //gMap.setMyLocationEnabled(true);
        addMarkersToMap(HOME_LAT, HOME_LONG);

        enableMyLocation();
        gMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        gMap.setOnMyLocationButtonClickListener(this);
        gMap.setOnInfoWindowClickListener(this);
        gMap.setOnMarkerDragListener(this);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(HOME_LAT, HOME_LONG)).zoom(15).build();
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //map.setContentDescription("Map with lots of markers.");
        /*gMap.setMyLocationEnabled(true);
        LatLng Myhome = new LatLng(19.2078186,72.8632);
        LatLng Myhome1 = new LatLng(19,72);
        gMap.addMarker(new MarkerOptions().position(Myhome).title("Marker Title").snippet("Marker Description").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        gMap.addMarker(new MarkerOptions().position(Myhome1).title("Marker Title").snippet("Marker Description").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(Myhome).zoom(15).build();
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

       /* if (mMapView.getViewTreeObserver().isAlive()) {
            mMapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    LatLngBounds bounds = new LatLngBounds.Builder()
                            .include(PERTH)
                            .include(SYDNEY)
                            .include(ADELAIDE)
                            .include(BRISBANE)
                            .include(MELBOURNE)
                            .build();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mMapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mMapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                }
            });
        }*/
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission((MainActivity)getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (gMap != null) {
            // Access to the location has been granted to the app.
            gMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        final LocationManager manager = (LocationManager)getActivity().getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }



    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }


    private void addMarkersToMap(double lati, double longi) {
        Log.e(MAP_TAG, "inside addMArkerCall "+lati+ " "+longi);
        gMap.addMarker(new MarkerOptions().position(new LatLng(lati, longi)).title("Marker Title").snippet("Marker Description").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // Uses a colored icon.
       /* mBrisbane = gMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane")
                .snippet("Population: 2,074,200")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // Uses a custom icon with the info window popping out of the center of the icon.
        mSydney = gMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney")
                .snippet("Population: 4,627,300")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow))
                .infoWindowAnchor(0.5f, 0.5f));

        // Creates a draggable marker. Long press to drag.
        mMelbourne = gMap.addMarker(new MarkerOptions()
                .position(MELBOURNE)
                .title("Melbourne")
                .snippet("Population: 4,137,400")
                .draggable(true));

        // A few more markers for good measure.
        mPerth = gMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth")
                .snippet("Population: 1,738,800"));
        mAdelaide = gMap.addMarker(new MarkerOptions()
                .position(ADELAIDE)
                .title("Adelaide")
                .snippet("Population: 1,213,000"));

        // Creates a marker rainbow demonstrating how to create default marker icons of different
        // hues (colors).
        float rotation = mRotationBar.getProgress();
        boolean flat = mFlatBox.isChecked();

        int numMarkersInRainbow = 12;
        for (int i = 0; i < numMarkersInRainbow; i++) {
            Marker marker = gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(
                            -30 + 10 * Math.sin(i * Math.PI / (numMarkersInRainbow - 1)),
                            135 - 10 * Math.cos(i * Math.PI / (numMarkersInRainbow - 1))))
                    .title("Marker " + i)
                    .icon(BitmapDescriptorFactory.defaultMarker(i * 360 / numMarkersInRainbow))
                    .flat(flat)
                    .rotation(rotation));
            //marker.setTag(0);
            mMarkerRainbow.add(marker);
        }*/
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getFragmentManager(), "dialog");
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getActivity(), "Click Info Window", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onMarkerDragStart(Marker marker) {
        mTopText.setText("onMarkerDragStart");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        mTopText.setText("onMarkerDragEnd");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        mTopText.setText("onMarkerDrag.  Current Position: " + marker.getPosition());
    }


    @Override
    public void findId() {
        mMapView = (MapView)view.findViewById(R.id.mapView);
        btnBatchMate = (Button)view.findViewById(R.id.btnBatchMate);
        btnHouseMate = (Button)view.findViewById(R.id.btnHouseMate);
        btnSchoolMate = (Button)view.findViewById(R.id.btnSchoolMate);
        btnAllMate = (Button)view.findViewById(R.id.btnAllMate);
    }

    @Override
    public void setListener() {
        btnBatchMate.setOnClickListener(this);
        btnHouseMate.setOnClickListener(this);
        btnSchoolMate.setOnClickListener(this);
        btnAllMate.setOnClickListener(this);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnBatchMate:
                addMarkersToMap(HOME1_LAT, HOME1_LONG);
                break;

            case R.id.btnHouseMate:
                break;

            case R.id.btnSchoolMate:
                break;

            case R.id.btnAllMate:
                break;
        }
    }

}
