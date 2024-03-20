package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.Adapter.AdapterFlightOnWay;
import com.example.doan.Adapter.AdapterFlightRoundTrip;
import com.example.doan.model.Flight;
import com.example.doan.my_interface.IClickFlightRoundTrip;
import com.example.doan.my_interface.IClickItemFlightOneWay;
import com.example.doan.url.UrlApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FlightRoundTripActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Flight> flightList;
    private RecyclerView rcvFlightRoundTrip;
    private Flight selectedFlightReturn,selectedFlight;
    public String arrivalCity,departureCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_round_trip);

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnNext).setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String departureCode = bundle.getString("departureCode");
        String arriveCode = bundle.getString("arrivalCode");
        String arrivalDay = bundle.getString("arrivalDay");
        String availableSeats = bundle.getString("AvailableSeats");
        selectedFlight = (Flight) bundle.getSerializable("object_flight");
        arrivalCity = bundle.getString("arrivalCity");
        departureCity = bundle.getString("departureCity");
        flightList = new ArrayList<>();

        rcvFlightRoundTrip = (RecyclerView) findViewById(R.id.rcvFlightRoundTrip);
        GetJsonAirportArray(departureCode,arriveCode,arrivalDay,availableSeats);
        rcvFlightRoundTrip.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnBack){
            finish();
        }
        if(view.getId() == R.id.btnNext){
            Intent intent = new Intent(FlightRoundTripActivity.this,PassengerInformationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_flight",selectedFlight);
            bundle.putString("departureCity", departureCity);
            bundle.putString("arrivalCity", arrivalCity);
            bundle.putSerializable("object_flight_return",selectedFlightReturn);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
    public void GetJsonAirportArray(String departureCode,String arriveCode,String arrivalDay,String availableSeats){
        RequestQueue queue = Volley.newRequestQueue(FlightRoundTripActivity.this);
        List<String> fareTypeList = new ArrayList<>();

        String url="http://" + UrlApi.url + ":8080/api/Flights/FlightSearchReturn?departureCode=" + departureCode + "&arriveCode="
                + arriveCode + "&arrivalDay=" + arrivalDay + "&availableSeats=" + availableSeats;
        JsonObjectRequest request = new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject flightObj = jsonArray.getJSONObject(i);
                                Flight flight = new Flight();
                                flight.setFlightId(flightObj.getString("flightID"));
                                flight.setFlightNumber(flightObj.getString("flightNumber"));
                                JSONObject airport1Obj = flightObj.getJSONObject("airports1");
                                flight.setDepartureCode(airport1Obj.getString("airportCode"));
                                JSONObject airportObj = flightObj.getJSONObject("airports");
                                flight.setArriveCode(airportObj.getString("airportCode"));
                                flight.setDepartureDay(flightObj.getString("departureDay"));
                                flight.setDepartureTime(flightObj.getString("departureTime"));
                                flight.setArrivalTime(flightObj.getString("arrivalTime"));
                                JSONArray faresArray = flightObj.getJSONArray("fares");
                                for(int j=0; j<faresArray.length(); j++){
                                    JSONObject faresObj = faresArray.getJSONObject(j);
                                    String fareType = faresObj.getString("fareType");
                                    String fareAmount = faresObj.getString("fareAmount");
                                    if ("Thương Gia".equals(fareType)) {
                                        flight.setFareThuongGia(fareAmount);
                                    } else if ("Phổ Thông".equals(fareType)) {
                                        flight.setFarePhoThong(fareAmount);
                                    }
                                }
                                flightList.add(flight);

                            }
                        }catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        AdapterFlightRoundTrip adapterAirport = new AdapterFlightRoundTrip(flightList, new IClickFlightRoundTrip() {
                            @Override
                            public void onFlightClick(Flight flights) {
                                findViewById(R.id.layoutBtnNext).setVisibility(View.VISIBLE);
                                    selectedFlightReturn = flights;
                            }
                        });
                        rcvFlightRoundTrip.setAdapter(adapterAirport);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(request);
    }
}