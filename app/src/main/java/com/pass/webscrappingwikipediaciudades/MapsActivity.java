package com.pass.webscrappingwikipediaciudades;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    TextInputEditText entrada = null;
    Button buscar = null;
    static String LatLong;
    static String Lat;
    static String Longi;
    Context contexto = this;
    static LatLng obj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        entrada = findViewById(R.id.entrada);
        buscar = findViewById(R.id.Buscar);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (entrada.getText().toString() == null) {
                    Toast t = new Toast(contexto);
                    t.setText("Introduce una ciudad en el buscador");
                    t.show();
                }
                AccesoADatos.nombre_ciudad = entrada.getText().toString();
                Thread hilo = new Thread(new AccesoADatos());
                hilo.start();
                

                //  Log.d("Res", AccesoADatos.obtenerCoordenadasCiudad(entrada.getText().toString()));


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
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public static void actualizarMapa() {
        String[] Lat_Long = LatLong.split(",");
        Log.d("RES", Arrays.toString(Lat_Long));

        Lat = Lat_Long[0];
        Double segundosLat = Double.parseDouble(Lat.substring(Lat.length() - 4, Lat.length() - 2));
        segundosLat = segundosLat / 60;
        Log.d("RES", String.valueOf(segundosLat));

        Double minutosLat = Double.parseDouble(Lat.substring(Lat.length() - 7, Lat.length() - 5));
        minutosLat = minutosLat / 3600;
        Log.d("RES", String.valueOf(minutosLat));

        Longi = Lat_Long[1];

        Double segundosLong = Double.parseDouble(Longi.substring(Longi.length() - 4, Longi.length() - 2));
        segundosLong = segundosLong / 60;
        Log.d("RES", String.valueOf(segundosLong));

        Double minutosLong = Double.parseDouble(Longi.substring(Longi.length() - 7, Longi.length() - 5));
        minutosLong = minutosLong / 3600;
        Log.d("RES", String.valueOf(minutosLong));


        Double lat;
        Double longi;
        if (Lat.length() == 10) {
            lat = Double.parseDouble(Lat.substring(0, 2));
            lat = lat + segundosLat + minutosLat;
        } else {

            lat = Double.parseDouble(Lat.substring(0, 1));
            lat = lat + segundosLat + minutosLat;
        }

        if (Longi.length() == 10) {
            longi = Double.parseDouble(Longi.substring(0, 2));
            longi = longi + segundosLong + minutosLong;
        } else {

            longi = Double.parseDouble(Longi.substring(0, 1));
            longi = longi + segundosLong + minutosLong;
        }


        obj = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(obj).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(obj));
    }

}