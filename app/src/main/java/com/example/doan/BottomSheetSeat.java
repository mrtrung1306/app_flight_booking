package com.example.doan;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.Adapter.AdapterSeat;
import com.example.doan.Adapter.AdapterSeatInformation;
import com.example.doan.Adapter.AdapterSeatPassenger;
import com.example.doan.Adapter.AdapterSeatReturnInformation;
import com.example.doan.model.Flight;
import com.example.doan.model.Passenger;
import com.example.doan.model.Seats;
import com.example.doan.my_interface.IClickBottomSheetSeat;
import com.example.doan.my_interface.IClickSeat;
import com.example.doan.url.UrlApi;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottomSheetSeat extends BottomSheetDialogFragment implements  View.OnClickListener {
    private int availableSeats;
    private List<Seats> seatsList;
    private IClickSeat iClickSeat;
    private List<Passenger> passengerList;
    private Seats SelectedSeat;
    private AdapterSeatPassenger adapterSeatPassenger;
    private AdapterSeatInformation adapterSeatInformation;
    private AdapterSeatReturnInformation adapterSeatReturnInformation;
    private List<Seats> selectedSeatsList = new ArrayList<>();
    private Flight flight;
    private IClickBottomSheetSeat iClickBottomSheetSeat;
    public BottomSheetSeat(Flight flight,List<Passenger> passengerList, int availableSeats,List<Seats>seatsList,AdapterSeatInformation adapterSeatInformation,AdapterSeatReturnInformation adapterSeatReturnInformation, IClickSeat iClickSeat){
        this.flight = flight;
        this.passengerList = passengerList;
        this.availableSeats = availableSeats;
        this.seatsList = seatsList;
        this.adapterSeatInformation = adapterSeatInformation;
        this.adapterSeatReturnInformation = adapterSeatReturnInformation;
        this.iClickSeat = iClickSeat;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottomseatlayout,null);

        view.findViewById(R.id.btnExit).setOnClickListener(this);
        view.findViewById(R.id.btnNext).setOnClickListener(this);

        bottomSheetDialog.setContentView(view);
        RecyclerView rcvSeat = view.findViewById(R.id.rcvSeat);
        GridLayoutManager GridLayoutManager = new GridLayoutManager(getContext(),6);

        AdapterSeat adapterSeat = new AdapterSeat(seatsList,availableSeats, new IClickSeat() {
            @Override
            public void onSeatClick(Seats seats) {
                selectedSeatsList.add(seats);
                adapterSeatPassenger.getSeatPassenger(selectedSeatsList);
            }

            @Override
            public void onSeatUnselected(Seats seat) {
                selectedSeatsList.remove(seat);
                adapterSeatPassenger.moveSeatPassenger(selectedSeatsList);
            }
        });
        adapterSeat.getFlight(flight);
        rcvSeat.setAdapter(adapterSeat);
        rcvSeat.setLayoutManager(GridLayoutManager);





        RecyclerView rcvSeatPassenger = view.findViewById(R.id.rcvSeatPassenger);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        adapterSeatPassenger = new AdapterSeatPassenger(availableSeats);
        adapterSeatPassenger.getPassengerInfo(passengerList);
        rcvSeatPassenger.setAdapter(adapterSeatPassenger);
        rcvSeatPassenger.setLayoutManager(layoutManager);
        return bottomSheetDialog;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnExit){
            dismiss();
        }
        else if(view.getId() == R.id.btnNext){
            if (adapterSeatInformation != null) {
                adapterSeatInformation.getSeatNumber(selectedSeatsList);
                adapterSeatInformation.notifyDataSetChanged();
                PostCache();
            }
            if (adapterSeatReturnInformation != null) {
                adapterSeatReturnInformation.getSeatNumber(selectedSeatsList);
                adapterSeatReturnInformation.notifyDataSetChanged();
                PostCacheReturn();
            }
            dismiss();
        }
    }
    public void PostCache(){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = "http://" + UrlApi.url + ":8080/api/CheckOut/SeatSelection";
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonBody = new JSONObject();
        for(Seats objSeat: selectedSeatsList){
            try {
                jsonBody.put("FlightId", flight.flightId);
                jsonArray.put(objSeat.SeatNumber);
                jsonBody.put("SeatNumbers",jsonArray);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        String RequestBody = jsonBody.toString();
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
                    return RequestBody.getBytes("utf-8");
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
        queue.add(seatRequest);
    }
    public void PostCacheReturn(){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = "http://" + UrlApi.url + ":8080/api/CheckOut/SeatReturnSelection";
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonBody = new JSONObject();
        for(Seats objSeat: selectedSeatsList){
            try {
                jsonBody.put("FlightId", flight.flightId);
                jsonArray.put(objSeat.SeatNumber);
                jsonBody.put("SeatNumbers",jsonArray);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        String RequestBody = jsonBody.toString();
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
                    return RequestBody.getBytes("utf-8");
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
        queue.add(seatRequest);
    }
}
