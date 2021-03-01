package com.pass.webscrappingwikipediaciudades;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AccesoADatos implements Runnable {

    static String nombre_ciudad;

    public static String obtenerCoordenadasCiudad() {

        String lat = "";
        String longi = "";
        String latLong = "";

        Document doc;
        try {
            doc = Jsoup.connect("https://es.wikipedia.org/wiki/" + nombre_ciudad).get();
            Elements coordLat = doc.getElementsByClass("latitude");
            Elements coordLong = doc.getElementsByClass("longitude");
            lat = coordLat.get(0).text();
            longi = coordLong.get(0).text();
            latLong = lat + "," + longi;
            Log.d("WEBSCRAPPING_", latLong);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLong;
    }

    @Override
    public void run() {
        MapsActivity.LatLong = obtenerCoordenadasCiudad();
        MapsActivity.actualizarMapa();
    }
}
