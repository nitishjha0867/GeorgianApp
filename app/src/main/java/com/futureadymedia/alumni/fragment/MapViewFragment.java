package com.futureadymedia.alumni.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.futureadymedia.alumni.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by developer on 9/12/2016.
 */
public class MapViewFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    private Marker myMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                myMarker = googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
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
    public boolean onMarkerClick(final Marker marker) {

    if (marker.equals(myMarker)) {
        // This causes the marker at Perth to bounce into position when it is clicked.
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 2 * t);

                if (t > 0.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    } /*else if (marker.equals(mAdelaide)) {
        // This causes the marker at Adelaide to change color and alpha.
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(mRandom.nextFloat() * 360));
        marker.setAlpha(mRandom.nextFloat());
    }*/

    // Markers have a z-index that is settable and gettable.
   /* float zIndex = marker.getZIndex() + 1.0f;
    marker.setZIndex(zIndex);
    Toast.makeText(this, marker.getTitle() + " z-index set to " + zIndex,
    Toast.LENGTH_SHORT).show();*/

    // Markers can store and retrieve a data object via the getTag/setTag methods.
    // Here we use it to retrieve the number of clicks stored for this marker.
    //Integer clickCount = (Integer) marker.getTag();
    // Check if a click count was set.
    /*if (clickCount != null) {
        clickCount = clickCount + 1;
        // Markers can store and retrieve a data object via the getTag/setTag methods.
        // Here we use it to store the number of clicks for this marker.
        marker.setTag(clickCount);
        mTagText.setText(marker.getTitle() + " has been clicked " + clickCount + " times.");
    } else {
        mTagText.setText("");
    }

    mLastSelectedMarker = marker;*/
    // We return false to indicate that we have not consumed the event and that we wish
    // for the default behavior to occur (which is for the camera to move such that the
    // marker is centered and for the marker's info window to open, if it has one).
    return false;
}
}
