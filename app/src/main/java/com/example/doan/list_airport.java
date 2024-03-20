package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.Adapter.AdapterAirport;
import com.example.doan.model.Airports;
import com.example.doan.my_interface.IClickItemAirport;
import com.example.doan.url.UrlApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

public class list_airport extends AppCompatActivity implements View.OnClickListener{

    private List<Airports> airportsList;
    private AdapterAirport adapterAirport;
    private RecyclerView rvAirport;
    private SearchView searchView;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_airport);

        findViewById(R.id.btnExit).setOnClickListener(this);
        progressBar= findViewById(R.id.progressBarId);

        airportsList = new ArrayList<>();

        rvAirport = (RecyclerView) findViewById(R.id.rcvAirport);
        GetJsonAirportArray();
        rvAirport.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.action_search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapterAirport.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterAirport.getFilter().filter(s);
                return false;
            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnExit){
            finish();
        }
    }
    public void onAirportClick1(Airports airports) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user", airports);

        String airportType = getIntent().getStringExtra("airport_type");
        bundle.putString("airport_type", airportType);

        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
    public void GetJsonAirportArray(){
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(list_airport.this);
        String url="http://" + UrlApi.url + ":8080/api/Flights/GetAirport";
        JsonObjectRequest request = new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject airport = jsonArray.getJSONObject(i);
                                Airports airports = new Airports();
                                airports.setCity(airport.getString("city"));
                                airports.setairportCode(airport.getString("airportCode"));
                                airportsList.add(airports);
                                progressBar.setVisibility(View.GONE);

                            }
                        }catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        adapterAirport = new AdapterAirport(airportsList, new IClickItemAirport() {
                            @Override
                            public void onAirportClick(Airports airports) {
                                onAirportClick1(airports);
                            }
                        });
                        rvAirport.setAdapter(adapterAirport);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
        queue.add(request);
    }
}