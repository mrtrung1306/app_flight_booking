package com.example.doan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.Adapter.AdapterFlightTicketInformation;
import com.example.doan.Adapter.AdapterPassengerTicketInformation;
import com.example.doan.Adapter.AdapterSeatTicketInformation;
import com.example.doan.model.Airports;
import com.example.doan.model.Flight;
import com.example.doan.model.Passenger;
import com.example.doan.model.Seats;
import com.example.doan.url.UrlApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TicketInformationActivity extends AppCompatActivity implements View.OnClickListener{

    public Flight flight;
    public List<Flight> flightList;
    public Flight flightReturn;
    public Airports airports;
    public List<Seats> seatsList;
    public List<Passenger> passengerList;
    public int numberOfPassengers,sumMoney;
    private RecyclerView rcvSeatTicket,rcvPassengerTicket,rcvFlightTicket;

    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_information);

        Intent intent = getIntent();
        sumMoney = intent.getExtras().getInt("sumMoney");
        numberOfPassengers = intent.getExtras().getInt("numberOfPassengers");
        String flightReturn1 = intent.getExtras().getString("flightReturn");
        TextView txtFareAmount = findViewById(R.id.txtFareAmount);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.imgMoreIcon).setOnClickListener(this);
        findViewById(R.id.imgMoreIcon1).setOnClickListener(this);
        findViewById(R.id.imgMoreIcon2).setOnClickListener(this);
        findViewById(R.id.btnPayment).setOnClickListener(this);

        flight = new Flight();
        flightReturn = new Flight();
        flightList = new ArrayList<>();
        airports = new Airports();
        seatsList = new ArrayList<>();
        passengerList = new ArrayList<>();

        txtFareAmount.setText(format.format(sumMoney));
        rcvSeatTicket = (RecyclerView) findViewById(R.id.rcvSeatTicket);
        rcvPassengerTicket = (RecyclerView) findViewById(R.id.rcvPassengerTicket);
        rcvFlightTicket = (RecyclerView) findViewById(R.id.rcvFlightTicket);
        GetJsonTicketArray();
        if(flightReturn1!=null){
            findViewById(R.id.dashed).setVisibility(View.GONE);
            findViewById(R.id.imgAirportIcon).setVisibility(View.GONE);
            findViewById(R.id.dashed2).setVisibility(View.VISIBLE);
            findViewById(R.id.imgAirportIcon2).setVisibility(View.VISIBLE);
            GetJsonTicketReturnArray();
        }
        rcvSeatTicket.setLayoutManager(new LinearLayoutManager(this));
        rcvPassengerTicket.setLayoutManager(new LinearLayoutManager(this));
        rcvFlightTicket.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        RelativeLayout rltLayout3_2 = findViewById(R.id.rltLayout3_2);
        ImageView imgMoreIcon = findViewById(R.id.imgMoreIcon);
        ImageView imgMoreIcon1 = findViewById(R.id.imgMoreIcon1);
        ImageView imgMoreIcon2 = findViewById(R.id.imgMoreIcon2);
        if(view.getId() == R.id.btnPayment){
            Payment();
            showSuccessDialog();
        }
        else if(view.getId() == R.id.imgMoreIcon){
            if(rcvFlightTicket.getVisibility() == View.GONE){
                rcvFlightTicket.setVisibility(View.VISIBLE);
                imgMoreIcon.setBackgroundResource(R.drawable.baseline_expand_less_24);
            }
            else{
                rcvFlightTicket.setVisibility(View.GONE);
                imgMoreIcon.setBackgroundResource(R.drawable.baseline_expand_more_24);
            }
        }
        else if(view.getId() == R.id.imgMoreIcon1){
            if(rltLayout3_2.getVisibility() == View.GONE){
                rltLayout3_2.setVisibility(View.VISIBLE);
                imgMoreIcon1.setBackgroundResource(R.drawable.baseline_expand_less_24);
            }
            else{
                rltLayout3_2.setVisibility(View.GONE);
                imgMoreIcon1.setBackgroundResource(R.drawable.baseline_expand_more_24);
            }
        }
        else if(view.getId() == R.id.imgMoreIcon2){
            if(rcvPassengerTicket.getVisibility() == View.GONE){
                rcvPassengerTicket.setVisibility(View.VISIBLE);
                imgMoreIcon2.setBackgroundResource(R.drawable.baseline_expand_less_24);
            }
            else{
                rcvPassengerTicket.setVisibility(View.GONE);
                imgMoreIcon2.setBackgroundResource(R.drawable.baseline_expand_more_24);
            }
        }
        else if(view.getId() == R.id.btnBack){
            finish();
        }

    }
    public void GetJsonTicketArray(){
        RequestQueue queue = Volley.newRequestQueue(TicketInformationActivity.this);

        String url="http://" + UrlApi.url + ":8080/api/CheckOut/TicketInformation";
        JsonObjectRequest request = new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("flightSelection");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject flightObj = jsonArray.getJSONObject(i);
//                                Flight flight = new Flight();
                                flight.setFlightId(flightObj.getString("flightID"));
                                flight.setFlightNumber(flightObj.getString("flightNumber"));
                                JSONObject airport1Obj = flightObj.getJSONObject("airports1");
                                flight.setDepartureCode(airport1Obj.getString("airportCode"));
                                airports.setDepartureName(airport1Obj.getString("city"));
                                JSONObject airportObj = flightObj.getJSONObject("airports");
                                flight.setArriveCode(airportObj.getString("airportCode"));
                                airports.setArrivalName(airportObj.getString("city"));
                                flight.setDepartureDay(flightObj.getString("departureDay"));
                                flight.setDepartureTime(flightObj.getString("departureTime"));
                                flight.setArrivalTime(flightObj.getString("arrivalTime"));
                                flight.setAvailableSeats(numberOfPassengers);
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
//                                flightList.add(flight);

                                TextView txtDepartureCode = findViewById(R.id.txtDepartureCode);
                                txtDepartureCode.setText(flight.getDepartureCode());


                                TextView txtArrivalCode = findViewById(R.id.txtArrivalCode);
                                txtArrivalCode.setText(flight.getArriveCode());

                                TextView txtAirportName = findViewById(R.id.txtAirportName);
                                txtAirportName.setText("TP " + airports.getDepartureName());

                                TextView txtAirportName1 = findViewById(R.id.txtAirportName1);
                                txtAirportName1.setText("TP " + airports.getArrivalName());
                            }
                            flightList.add(flight);
                            JSONArray jsonSeatsArray = response.getJSONArray("seatSelection");
                            for(int i=0; i<jsonSeatsArray.length(); i++){
                                Seats seats = new Seats();
                                JSONObject seatObj = jsonSeatsArray.getJSONObject(i);
                                seats.setSeatNumber(seatObj.getString("seatNumber"));
                                seatsList.add(seats);
                            }

                            JSONArray jsonPassengerArray = response.getJSONArray("passengersSelection");
                            for(int i=0; i<jsonPassengerArray.length(); i++){
                                Passenger passenger = new Passenger();
                                JSONObject seatObj = jsonPassengerArray.getJSONObject(i);
                                passenger.setFullName(seatObj.getString("fullName"));
                                passenger.setNgaySinh(seatObj.getString("ngaySinh"));
                                passenger.setGender(seatObj.getString("gender"));
                                passenger.setPhone(seatObj.getString("phone"));
                                passenger.setEmail(seatObj.getString("email"));
                                passenger.setAddress(seatObj.getString("address"));
                                passengerList.add(passenger);
                            }

                        }catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        AdapterFlightTicketInformation adapterFlightTicketInformation = new AdapterFlightTicketInformation(flightList,airports);
                        rcvFlightTicket.setAdapter(adapterFlightTicketInformation);

                        AdapterSeatTicketInformation adapterSeatTicketInformation = new AdapterSeatTicketInformation(seatsList,flightList);
                        rcvSeatTicket.setAdapter(adapterSeatTicketInformation);

                        AdapterPassengerTicketInformation adapterPassengerTicketInformation = new AdapterPassengerTicketInformation(passengerList);
                        rcvPassengerTicket.setAdapter(adapterPassengerTicketInformation);
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
    public void GetJsonTicketReturnArray(){
        RequestQueue queue = Volley.newRequestQueue(TicketInformationActivity.this);
//        List<String> fareTypeList = new ArrayList<>();

        String url="http://" + UrlApi.url + ":8080/api/CheckOut/TicketReturnInformation";
        JsonObjectRequest request = new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("flightReturnSelection");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject flightObj = jsonArray.getJSONObject(i);
//                                Flight flight = new Flight();
                                flightReturn.setFlightId(flightObj.getString("flightID"));
                                flightReturn.setFlightNumber(flightObj.getString("flightNumber"));
                                JSONObject airport1Obj = flightObj.getJSONObject("airports1");
                                flightReturn.setDepartureCode(airport1Obj.getString("airportCode"));
                                airports.setDepartureName(airport1Obj.getString("city"));
                                JSONObject airportObj = flightObj.getJSONObject("airports");
                                flightReturn.setArriveCode(airportObj.getString("airportCode"));
                                airports.setArrivalName(airportObj.getString("city"));
                                flightReturn.setDepartureDay(flightObj.getString("departureDay"));
                                flightReturn.setDepartureTime(flightObj.getString("departureTime"));
                                flightReturn.setArrivalTime(flightObj.getString("arrivalTime"));
                                flightReturn.setAvailableSeats(numberOfPassengers);
                                JSONArray faresArray = flightObj.getJSONArray("fares");
                                for(int j=0; j<faresArray.length(); j++){
                                    JSONObject faresObj = faresArray.getJSONObject(j);
                                    String fareType = faresObj.getString("fareType");
                                    String fareAmount = faresObj.getString("fareAmount");
                                    if ("Thương Gia".equals(fareType)) {
                                        flightReturn.setFareThuongGia(fareAmount);
                                    } else if ("Phổ Thông".equals(fareType)) {
                                        flightReturn.setFarePhoThong(fareAmount);
                                    }
                                }
                            }
                            flightList.add(flightReturn);
                            JSONArray jsonSeatsArray = response.getJSONArray("seatReturnSelection");
                            for(int i=0; i<jsonSeatsArray.length(); i++){
                                Seats seats = new Seats();
                                JSONObject seatObj = jsonSeatsArray.getJSONObject(i);
                                seats.setSeatNumber(seatObj.getString("seatNumber"));
                                seatsList.add(seats);
                            }

                        }catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        AdapterFlightTicketInformation adapterFlightTicketInformation = new AdapterFlightTicketInformation(flightList,airports);
                        rcvFlightTicket.setAdapter(adapterFlightTicketInformation);

                        AdapterSeatTicketInformation adapterSeatTicketInformation = new AdapterSeatTicketInformation(seatsList,flightList);
                        rcvSeatTicket.setAdapter(adapterSeatTicketInformation);
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
    public void Payment(){
        Log.d("Payment", "Payment method called");
        RequestQueue queue = Volley.newRequestQueue(TicketInformationActivity.this);
//        List<String> fareTypeList = new ArrayList<>();

        String url="http://" + UrlApi.url + ":8080/api/CheckOut/Booking";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("total",sumMoney);

        }catch (JSONException e) {
            e.printStackTrace();
        }
        String requestBody = jsonBody.toString();
        StringRequest seatRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Volley Response", response.toString());
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error", "Error occurred", error);
                }
            }
        ){
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        seatRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(seatRequest);
    }
    private void showSuccessDialog(){
        ConstraintLayout  successConstraintLayout = findViewById(R.id.successConstraintLayout);
        View view = LayoutInflater.from(TicketInformationActivity.this).inflate(R.layout.dialog_success,successConstraintLayout);
        Button successDone = view.findViewById(R.id.successDone);
        AlertDialog.Builder builder = new AlertDialog.Builder(TicketInformationActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        successDone.findViewById(R.id.successDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                navigateToFirstActivity();
            }
        });
        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    private void navigateToFirstActivity(){
        Intent intent = new Intent(this, BookingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}