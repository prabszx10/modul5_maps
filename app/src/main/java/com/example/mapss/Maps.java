package com.example.mapss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Maps extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ImageButton current,hospital,policestation,school;
    private SearchView cari;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private Integer angka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        current= findViewById(R.id.currentLocation);
        hospital=findViewById(R.id.hospital);
        policestation = findViewById(R.id.policestation);
        school= findViewById(R.id.school);
        cari = findViewById(R.id.searchView);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(cari.getQuery().toString());
            }
        });



        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angka = 0;
                fetchLastLocation();
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angka = 1;
                fetchLastLocation();
            }


        });

        policestation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angka = 2;
                fetchLastLocation();
            }


        });

        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angka = 3;
                fetchLastLocation();
            }


        });
    }

    private void validation(String cari) {
        if(cari.equals("rumah sakit")){
            angka = 1;
            fetchLastLocation();
        }
        else if(cari.equals("kantor polisi")){
            angka = 2;
            fetchLastLocation();
        }
        else if(cari.equals("sekolah")){
            angka = 3;
            fetchLastLocation();
        }
    }

    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()
                            +""+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(Maps.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        if(angka == 0){
            LatLng latLng= new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here.");

            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
            googleMap.addMarker(markerOptions);

        }

        else if(angka == 1){
            LatLng rs1= new LatLng(3.3084598,117.6008489);
            MarkerOptions markerRs1 = new MarkerOptions().position(rs1).title("Rumah Sakit Ilyas Tarakan").snippet("Jl. RE Martadinata No.29, Pamusian, Tarakan Tengah, Kota Tarakan, Kalimantan Utara");
            markerRs1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            googleMap.addMarker(markerRs1);

            LatLng rs2= new LatLng(3.3179732,117.6049099);
            MarkerOptions markerRs2 = new MarkerOptions().position(rs2).title("RSUD Tarakan").snippet("Jl. Pulau Irian No.1, Kp. Satu Skip, Tarakan Tengah, Kota Tarakan, Kalimantan Utara");
            markerRs2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            googleMap.addMarker(markerRs2);


            LatLng rs3= new LatLng(3.3145683,117.6049933);
            MarkerOptions markerRs3 = new MarkerOptions().position(rs3).title("PMI KOTA TARAKAN").snippet("Jl. Pulau Irian, Kp. Satu Skip, Tarakan Tengah, Kota Tarakan, Kalimantan Utara");
            markerRs3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            googleMap.addMarker(markerRs3);

            LatLng mid= new LatLng(3.3131273,117.6025519);
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(mid));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mid,16));
            Toast.makeText(getApplicationContext(),"Rumah Sakit",Toast.LENGTH_SHORT).show();
        }


        else if(angka == 2){
            LatLng polisi1= new LatLng(3.3071029,117.5839178);
            MarkerOptions markerpolisi1 = new MarkerOptions().position(polisi1).title("Polsek Tarakan Barat").snippet("Jl. Jendral Sudirman, Karang Anyar, Barat, Kota Tarakan, Kalimantan Utara 77123");
            markerpolisi1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            googleMap.addMarker(markerpolisi1);

            LatLng polisi2= new LatLng(3.3110058,117.5903863);
            MarkerOptions markerpolisi2 = new MarkerOptions().position(polisi2).title("Polres").snippet("Jl. RE Martadinata No.29, Pamusian, Tarakan Tengah, Kota Tarakan, Kalimantan Utara");
            markerpolisi2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            googleMap.addMarker(markerpolisi2);


            LatLng polisi3= new LatLng(3.3140347,117.5812953);
            MarkerOptions markerpolisi3 = new MarkerOptions().position(polisi3).title("Kantor Polisi Samsat").snippet("Jl. Mulawarman / Kamboja No. 68, Karang Anyar, Tarakan Bar., Kota Tarakan, Kalimantan Timur");
            markerpolisi3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            googleMap.addMarker(markerpolisi3);

            LatLng mid= new LatLng(3.3119758,117.5857783);
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(mid));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mid,15));
            Toast.makeText(getApplicationContext(),"Kantor Polisi",Toast.LENGTH_SHORT).show();
        }


        else if(angka == 3){
            LatLng sekolah1= new LatLng(3.3080011,117.5957336);
            MarkerOptions markersekolah1 = new MarkerOptions().position(sekolah1).title("SMP Negeri 1 Tarakan").snippet("Pamusian, Tarakan Tengah, Kota Tarakan, Kalimantan Timur");
            markersekolah1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            googleMap.addMarker(markersekolah1);

            LatLng sekolah2=new LatLng(3.3083967,117.5957829);
            MarkerOptions markersekolah2 = new MarkerOptions().position(sekolah2).title("SMPN 7 TARAKAN").snippet("Pamusian, Tarakan Tengah, Kota Tarakan, Kalimantan Utara");
            markersekolah2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            googleMap.addMarker(markersekolah2);


            LatLng sekolah3= new LatLng(3.3084168,117.5954446);
            MarkerOptions markersekolah3 = new MarkerOptions().position(sekolah3).title("SMK Negeri 1 Tarakan").snippet("JL. P. Diponegoro, Pamusian, Tarakan Tengah, Kota Tarakan, Kalimantan Utara");
            markersekolah3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            googleMap.addMarker(markersekolah3);

            LatLng mid= new LatLng(3.3083732,117.5955214);
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(mid));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mid,19));
            Toast.makeText(getApplicationContext(),"Sekolah",Toast.LENGTH_SHORT).show();

        }

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }
}
