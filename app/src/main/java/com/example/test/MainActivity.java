package com.example.test;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getGeocode(View view){
        EditText textInput = findViewById(R.id.editText);
        String address = textInput.getText().toString();
        String parsedAddress = address.replaceAll("\\s", "+");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + parsedAddress + "&key=AIzaSyB3ZauVyCg_Bu2FhoQcipksZAjk0zDaIbQ";


        Context mContext = getApplicationContext();


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String resp = ("Response: " + response.toString());
                        int x = 5;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }

                });
        
        requestQueue.add(jsonObjectRequest);

    }

}
