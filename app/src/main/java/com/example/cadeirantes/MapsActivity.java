package com.example.cadeirantes;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.cadeirantes.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.TypeFilter;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocationPermission();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        MapsActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final Button api = findViewById(R.id.btnApi);
        final Button volta = findViewById(R.id.button);
        api.setOnClickListener(view -> {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }

            }catch (Exception e){
                Toast.makeText(MapsActivity.this, "Não é possível filtrar", Toast.LENGTH_SHORT).show();

            }
            getLocationPermission();

            getDeviceLocation();

        });
        volta.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(MapsActivity.this, MainActivity2.class);
                startActivity(intent);

            }catch (Exception e){
                Toast.makeText(MapsActivity.this, "Não é possível voltar", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng auditorioibi = new LatLng(-23.5855891, -46.6587443);
        mMap.addMarker(new MarkerOptions().position(auditorioibi).title("Auditório Ibirapuera - Oscar Niemeyer"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(auditorioibi));

        LatLng bibliotecavilla = new LatLng(-23.5467595,-46.7295042);
        mMap.addMarker(new MarkerOptions().position(bibliotecavilla).title("Biblioteca Parque Villa-Lobos"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bibliotecavilla));

        LatLng centrotrad = new LatLng(-23.5111821,-46.6820325);
        mMap.addMarker(new MarkerOptions().position(centrotrad).title("CTN - Centro de Tradições Nordestinas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(centrotrad));

        LatLng parqueibi = new LatLng(    -23.5874162,-46.6598223);
        mMap.addMarker(new MarkerOptions().position(parqueibi).title("Parque Ibirapuera"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(parqueibi));


        LatLng villa = new LatLng(    -23.547119,-46.726668);
        mMap.addMarker(new MarkerOptions().position(villa).title("Parque Villa Lobos"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(villa));

        LatLng zoo = new LatLng(    -23.650851,-46.620682);
        mMap.addMarker(new MarkerOptions().position(zoo).title("Zoologico"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(zoo));

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
    }

    private static final String TAG = "MapsActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION =  Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: Procurando a localização do aparelho");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: Localização achada!");
                        Location currentLocation = (Location) task.getResult();

                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                DEFAULT_ZOOM);

                    }else{
                        Log.d(TAG, "onComplete: Sua localização é nula");
                        Toast.makeText(MapsActivity.this, "Localização não encontrada", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: Movendo camera para: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }



    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;

            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;

                }
            }
        }
    }

}




