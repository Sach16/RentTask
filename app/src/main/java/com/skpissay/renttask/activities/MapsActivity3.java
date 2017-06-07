package com.skpissay.renttask.activities;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.skpissay.renttask.R;
import com.skpissay.renttask.db.PlaceInfo;

/**
 * Created by skpissay on 07/06/17.
 */
public class MapsActivity3 extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener{

    public static final String OBJ_PLACE = "OBJ_PLACE";

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private TextView mPlaceText;
    private PlaceInfo mPlaceInfo;
    private LatLng latLng;
    private MarkerOptions markerOptions;
    ImageView m_cBackImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps3);

        mPlaceInfo = (new Gson()).fromJson(getIntent().getStringExtra(OBJ_PLACE), PlaceInfo.class);
        mPlaceText = (TextView) findViewById(R.id.PLACE_TXT);
        mPlaceText.setText(mPlaceInfo.getName());

        m_cBackImg = (ImageView) findViewById(R.id.BACK_IMG);
        m_cBackImg.setOnClickListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        latLng = new LatLng(mPlaceInfo.getLatitude(), mPlaceInfo.getLongitude());

        mMap.clear();

// Animating to the touched position
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 48.0f));

// Creating a marker
        markerOptions = new MarkerOptions();

// Setting the position for the marker
        markerOptions.position(latLng);

// Placing a marker on the touched position
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BACK_IMG:
                onBackPressed();
                break;
        }
    }
}
