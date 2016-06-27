package com.abs.samih.fcm_ex01;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnGo;
    private EditText etToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        btnGo=(Button)findViewById(R.id.btnGo);
        btnGo.setOnClickListener(clickListener);
        etToSearch=(EditText)findViewById(R.id.etToSearch);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==btnGo)
            {
                searchTask(etToSearch.getText().toString());
            }
        }
    };

    private void searchTask(final String st)
    {
        AsyncTask<Void,Integer,List<Address>> asyncTask=new AsyncTask<Void, Integer, List<Address>>() {
            @Override
            protected List<Address> doInBackground(Void... params) {
                List<Address> locations=null;
                //add   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/> to manifest
                //
                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    locations=geocoder.getFromLocationName(st,3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return locations;
            }

            @Override
            protected void onPostExecute(List<Address> locations) {
                for (Address address:locations)
                {
                    LatLng sydney = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(sydney).title(address.getAddressLine(0)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }
            }
        }.execute();
    }

}
