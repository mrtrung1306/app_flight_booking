package com.example.doan.function_volley;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.url.UrlApi;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FlightVolley {
    public void FlightSearch(Context context,TextView textView,String departureCode, String arriveCode,String departureDay,String availableSeats){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + UrlApi.url + ":8080/api/Flights/FlightSearch";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText(error.getMessage());
                    }
                }
        )
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("departureCode",departureCode);
                stringMap.put("arriveCode",arriveCode);
                stringMap.put("departureDay",departureDay);
                stringMap.put("availableSeats",availableSeats);
                return stringMap;
            }
        };
        queue.add(stringRequest);
    }

}
