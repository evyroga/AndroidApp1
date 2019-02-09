package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String LAT_VALUE = "LAT_VALUE";
    private static final String LNG_VALUE = "LNG_VALUE";

    private GoogleMap mMap;
    JSONObject RESPONSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onBackButton(View view) {
        Intent randomIntent = new Intent(this, MainActivity.class);

        startActivity(randomIntent);

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
        //Vivian driving
        mMap = googleMap;

        Double latValue = getIntent().getDoubleExtra(LAT_VALUE, 0);
        Double lngValue = getIntent().getDoubleExtra(LNG_VALUE, 0);

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(latValue, lngValue);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker at Location"));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        getWeatherDate(latValue, lngValue);
        //end of Vivian driving, Nick driving now

    }

    public void getWeatherDate(Double latValue, Double lngValue){
        String url = "https://api.darksky.net/forecast/fb9d38dc4d2ed7b06cc708f43b4a1d4a/" + latValue.toString() + "," + lngValue.toString();

        Context mContext = getApplicationContext();

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        RESPONSE = response;
                        try {
                           JSONObject currently = response.getJSONObject("currently");
                           Double temp = currently.getDouble("temperature");
                           Double humidity = currently.getDouble("humidity");
                           Double windSpeed = currently.getDouble("windSpeed");
                           Double precipitation = currently.getDouble("precipProbability");

                           String temperatureString = "Temperature: " + temp.toString();
                           String humidityString = "Humidity: " + humidity.toString();
                           String windSpeedString = "Wind Speed: " + windSpeed.toString();
                           String precipitationString = "Precipitation: " + precipitation.toString();

                           TextView temperatureView = findViewById(R.id.temperatureView);
                           TextView humidityView = findViewById(R.id.humidityView);
                           TextView windSpeedView = findViewById(R.id.windSpeedView);
                           TextView precipitationView = findViewById(R.id.precipitationView);

                           temperatureView.setText(temperatureString);
                           humidityView.setText(humidityString);
                           windSpeedView.setText(windSpeedString);
                           precipitationView.setText(precipitationString);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }

                });


        requestQueue.add(jsonObjectRequest);
    }
}
//end of Nick driving
