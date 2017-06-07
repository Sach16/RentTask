package com.skpissay.renttask.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.location.Address;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skpissay.renttask.R;
import com.skpissay.renttask.db.PlaceInfo;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by skpissay on 07/06/17.
 */

public class MapsActivity1 extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private EditText mTextBox;
    private Button mSearchBtn;
    private Button mGSC;
    private ImageView mImgList;
    MarkerOptions markerOptions;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);

        mTextBox = (EditText) findViewById(R.id.ET_LOCATION);
        mSearchBtn = (Button) findViewById(R.id.BTN_FIND);
        mSearchBtn.setOnClickListener(this);
        mGSC = (Button) findViewById(R.id.BTN_GSC);
        mGSC.setOnClickListener(this);
        mImgList = (ImageView) findViewById(R.id.LIST_IMG);
        mImgList.setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }, null);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap pMap) {
        mMap = pMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng platLng) {
                latLng = platLng;
                mMap.clear();

// Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

// Creating a marker
                markerOptions = new MarkerOptions();

// Setting the position for the marker
                markerOptions.position(latLng);

// Placing a marker on the touched position
                mMap.addMarker(markerOptions);

// Adding Marker on the touched location with address
                new ReverseGeocodingTask(getBaseContext()).execute(latLng);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTN_FIND:
                String location = mTextBox.getText().toString();

                if (location != null && !location.equals("")) {
                    new GeocoderTask().execute(location);
                }
                break;
            case R.id.BTN_GSC:
                if (latLng != null){
                    PlaceInfo lPlaceInfo = new PlaceInfo();
                    lPlaceInfo.setUniquecode(UUID.randomUUID().toString());
                    lPlaceInfo.setLatitude(latLng.latitude);
                    lPlaceInfo.setLongitude(latLng.longitude);
                    lPlaceInfo.setName(mTextBox.getText().toString());
                    lPlaceInfo.setTime((new Date()).toString());
                    lPlaceInfo.save();
                    List<PlaceInfo> list = PlaceInfo.listAll(PlaceInfo.class);
                    list.size();

                    String uri = "geo:" + latLng.latitude + ","
                            +latLng.latitude + "?q=" + latLng.latitude
                            + "," + latLng.latitude;
                    /*startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(uri)));*/

                    Intent lObjIntent = new Intent(android.content.Intent.ACTION_SEND);
                    lObjIntent.setType("text/plain");
                    lObjIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
                    lObjIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
                    startActivity(Intent.createChooser(lObjIntent, getResources().getString(R.string.app_name)));
                }
                break;
            case R.id.LIST_IMG:
                Intent lIntent = new Intent(this, MapsActivity2.class);
                startActivity(lIntent);
                break;
        }
    }

    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... params) {
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;
            try {
// Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(params[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            super.onPostExecute(addresses);
            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

// Clears all the existing markers on the map
            mMap.clear();

// Adding Markers on Google Map for each matching address
            if (null != addresses)
                for (int i = 0; i < addresses.size(); i++) {

                    Address address = (Address) addresses.get(i);

// Creating an instance of GeoPoint, to display in Google Map
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    String addressText = String.format("%s, %s",
                            address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                            address.getCountryName());

                    markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(addressText);

// Locate the first location
                    if (i == 0) {
                        mMap.addMarker(markerOptions);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 64.0f));
                    }
                }
        }
    }

    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String>{

        Context mContext;

        public ReverseGeocodingTask(Context context){
            super();
            mContext = context;
        }

        @Override
        protected String doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(mContext);
            double latitude = params[0].latitude;
            double longitude = params[0].longitude;

            List <Address>addresses = null;
            String addressText="";
            try {
                addresses = geocoder.getFromLocation(latitude, longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addresses != null && addresses.size() > 0 ){
                Address address = addresses.get(0);

                addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());
            }

            return addressText;
        }

        @Override
        protected void onPostExecute(String addressText) {
// Setting the title for the marker.
// This will be displayed on taping the marker
            markerOptions.title(addressText);
            mTextBox.setText(addressText);
// Placing a marker on the touched position
            mMap.addMarker(markerOptions);

        }
    }
}
