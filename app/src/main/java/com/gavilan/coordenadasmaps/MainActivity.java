package com.gavilan.coordenadasmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

public class MainActivity extends AppCompatActivity {

    private Button btnCoordenadas;
    private TextView txtCoordenadas;
    private FusedLocationProviderClient localizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCoordenadas = findViewById(R.id.btnCoordenadas);
        txtCoordenadas = findViewById(R.id.txtCoordenadas);

        btnCoordenadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitar_permisos();
            }
        });


    }

    private void solicitar_permisos() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permiso de GPS activado", Toast.LENGTH_SHORT).show();
            obtenerCoodenadas();
        }else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
            },1);
        }
    }

    @SuppressLint("MissingPermission")
    private void obtenerCoodenadas() {
        localizador = LocationServices.getFusedLocationProviderClient(this);

        localizador.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    //txtCoordenadas.setText("Lat:"+lat+"\nLon:"+lon);
                    //Toast.makeText(MainActivity.this, "Lat:"+lat+"\nLon:"+lon, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                    intent.putExtra("location",location);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this, "location null", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


}