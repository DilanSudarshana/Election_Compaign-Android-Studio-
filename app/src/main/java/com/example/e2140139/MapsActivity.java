package com.example.e2140139;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.e2140139.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    private ActivityMapsBinding binding;
    userDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        LatLng Piliyandala = new LatLng(6.90113878250122, 79.8779373168945);
        mMap.addMarker(new MarkerOptions().position(Piliyandala).title("Piliyandala"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Piliyandala));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        setPin();
        locationClick();
    }
    public void setPin(){
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                try {
                    LatLng pos = new LatLng(latLng.latitude, latLng.longitude);
                    mMap.addMarker(new MarkerOptions().position(pos).title("Servey Location"));

                    Intent intent = new Intent(MapsActivity.this, DataRecordActivity.class);
                    intent.putExtra("lat",String.valueOf(latLng.latitude));
                    intent.putExtra("lon",String.valueOf(latLng.longitude));
                    startActivityForResult(intent,1);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void locationClick() {
        db = new userDbHandler(this);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String title = marker.getTitle();
                LatLng position = marker.getPosition();
                try {
                    Cursor result = db.DisplayData(String.valueOf(position.latitude), String.valueOf(position.longitude));
                    if (result.getCount() == 0) {
                        showMessage("Error", "No data found");
                    } else {
                        StringBuffer buffer = new StringBuffer();
                        while (result.moveToNext()) {
                            buffer.append("Id: " + result.getString(0) + "\n");
                            buffer.append("Latitude: " + result.getString(1) + "\n");
                            buffer.append("Longitude: " + result.getString(2) + "\n");
                            buffer.append("Party: " + result.getString(3) + "\n");
                            buffer.append("Date and Time: " + result.getString(4) + "\n");
                            buffer.append("Number of Votes: " + result.getString(5) + "\n");
                            buffer.append("Expected votes: " + result.getString(6) + "\n");
                            buffer.append("Feedback Type: " + result.getString(7) + "\n");
                            buffer.append("Most Support Party: " + result.getString(8) + "\n");
                            buffer.append("Additional Details: " + result.getString(9) + "\n");
                        }
                        showMessage("Data", buffer.toString());
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}

