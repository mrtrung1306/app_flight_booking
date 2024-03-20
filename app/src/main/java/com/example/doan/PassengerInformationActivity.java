package com.example.doan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.Adapter.AdapterPassenger;
import com.example.doan.Adapter.AdapterSeatInformation;
import com.example.doan.Adapter.AdapterSeatPassenger;
import com.example.doan.Adapter.AdapterSeatReturnInformation;
import com.example.doan.model.Flight;
import com.example.doan.model.Passenger;
import com.example.doan.model.Seats;
import com.example.doan.my_interface.IClickItemPassenger;
import com.example.doan.my_interface.IClickItemSeatInformation;
import com.example.doan.url.UrlApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

    public class PassengerInformationActivity extends AppCompatActivity implements View.OnClickListener {
    public int numberOfPassengers;
    public AdapterPassenger adapterPassenger;
    public AdapterSeatInformation adapterSeatInformation;
    public AdapterSeatReturnInformation adapterSeatReturnInformation;
    public AdapterSeatPassenger adapterSeatPassenger;
    public List<Passenger> passengerList;
    public Passenger obj;
    public Flight flight,flightReturn;
    public List<Seats> seatsList;
    public int sumMoney;
    SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_information);
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        TextView txtFareAmount = findViewById(R.id.txtFareAmount);
        TextView txtDepartureCode = findViewById(R.id.txtDepartureCode);
        TextView txtDepartureCity = findViewById(R.id.txtDepartureCity);
        TextView txtArrivalCode = findViewById(R.id.txtArrivalCode);
        TextView txtArrivalCity = findViewById(R.id.txtArrivalCity);
        TextView txtDepartureCodeReturn = findViewById(R.id.txtDepartureCodeReturn);
        TextView txtDepartureCityReturn = findViewById(R.id.txtDepartureCityReturn);
        TextView txtArrivalCodeReturn = findViewById(R.id.txtArrivalCodeReturn);
        TextView txtArrivalCityReturn = findViewById(R.id.txtArrivalCityReturn);

        seatsList = new ArrayList<>();

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnNext).setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        flight = (Flight) bundle.getSerializable("object_flight");
        String arrivalCity = bundle.getString("arrivalCity");
        String departureCity = bundle.getString("departureCity");
        flightReturn = (Flight) bundle.getSerializable("object_flight_return");
        numberOfPassengers = flight.availableSeats;

        txtDepartureCode.setText(flight.departureCode);
        txtArrivalCode.setText(flight.arriveCode);
        txtDepartureCity.setText(departureCity);
        txtArrivalCity.setText(arrivalCity);

        if(flightReturn != null){
            RelativeLayout rltLayout5 = findViewById(R.id.rltLayout5);
            View border3 = findViewById(R.id.border3);
            RecyclerView rcvSeatReturn = findViewById(R.id.rcvSeatReturn);
            rltLayout5.setVisibility(View.VISIBLE);
            border3.setVisibility(View.VISIBLE);
            rcvSeatReturn.setVisibility(View.VISIBLE);

            txtDepartureCodeReturn.setText(flightReturn.departureCode);
            txtArrivalCodeReturn.setText(flightReturn.arriveCode);
            txtDepartureCityReturn.setText(arrivalCity);
            txtArrivalCityReturn.setText(departureCity);
            if("Phổ thông".equals(flight.getFareType())){
                if("Phổ thông".equals(flightReturn.getFareType())){
                    txtFareAmount.setText(format.format((Double.parseDouble(flight.farePhoThong) * numberOfPassengers) + (Double.parseDouble(flightReturn.farePhoThong) * numberOfPassengers)));
                    sumMoney = (int) ((Double.parseDouble(flight.farePhoThong) * numberOfPassengers) + (Double.parseDouble(flightReturn.farePhoThong) * numberOfPassengers));
                }
                else{
                    txtFareAmount.setText(format.format((Double.parseDouble(flight.farePhoThong) * numberOfPassengers) + (Double.parseDouble(flightReturn.fareThuongGia) * numberOfPassengers)));
                    sumMoney = (int) ((Double.parseDouble(flight.farePhoThong) * numberOfPassengers) + (Double.parseDouble(flightReturn.fareThuongGia) * numberOfPassengers));

                }
            }
            else{
                if("Phổ thông".equals(flightReturn.getFareType())){
                    txtFareAmount.setText(format.format((Double.parseDouble(flight.fareThuongGia) * numberOfPassengers) + (Double.parseDouble(flightReturn.farePhoThong) * numberOfPassengers)));
                    sumMoney = (int) ((Double.parseDouble(flight.fareThuongGia) * numberOfPassengers) + (Double.parseDouble(flightReturn.farePhoThong) * numberOfPassengers));
                }
                else{
                    txtFareAmount.setText(format.format((Double.parseDouble(flight.fareThuongGia) * numberOfPassengers) + (Double.parseDouble(flightReturn.fareThuongGia) * numberOfPassengers)));
                    sumMoney = (int) ((Double.parseDouble(flight.fareThuongGia) * numberOfPassengers) + (Double.parseDouble(flightReturn.fareThuongGia) * numberOfPassengers));
                }
            }
        }
        else{
            if("Phổ thông".equals(flight.getFareType())){
                txtFareAmount.setText(format.format(Double.parseDouble(flight.farePhoThong) * numberOfPassengers));
                sumMoney = (int) Double.parseDouble(flight.farePhoThong) * numberOfPassengers;
            }
            else{
                txtFareAmount.setText(format.format(Double.parseDouble(flight.fareThuongGia) * numberOfPassengers));
                sumMoney = (int) Double.parseDouble(flight.fareThuongGia) * numberOfPassengers;
            }
        }

        passengerList = new ArrayList<>(numberOfPassengers);
        for (int i = 0; i < numberOfPassengers; i++) {
            passengerList.add(new Passenger());
        }
        RecyclerView rcvPassenger = (RecyclerView) findViewById(R.id.rcvPassenger);
        adapterPassenger = new AdapterPassenger(numberOfPassengers, new IClickItemPassenger() {
            @Override
            public void onPassengerInfoUpdated(int position) {
                Toast.makeText(PassengerInformationActivity.this,"position"+ position,Toast.LENGTH_SHORT).show();
                Passenger selectedPassenger = passengerList.get(position);

                Intent intent = new Intent(PassengerInformationActivity.this, FillPassengerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
//                bundle1.putSerializable("Passenger",obj);
                bundle.putSerializable("Passenger", selectedPassenger);
                intent.putExtras(bundle);
                ((Activity) PassengerInformationActivity.this).startActivityForResult(intent,1);
            }
        });
        rcvPassenger.setAdapter(adapterPassenger);
        rcvPassenger.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView rcvSeat = (RecyclerView) findViewById(R.id.rcvSeat);
        adapterSeatInformation = new AdapterSeatInformation(PassengerInformationActivity.this,numberOfPassengers, new IClickItemSeatInformation() {
            @Override
            public void onSeat(int position) {
            }
        });
        adapterSeatInformation.getFlight(flight);
        rcvSeat.setAdapter(adapterSeatInformation);
        rcvSeat.setLayoutManager(new LinearLayoutManager(this));
//        GetJsonSeatArray();

        RecyclerView rcvSeatReturn = (RecyclerView) findViewById(R.id.rcvSeatReturn);
        adapterSeatReturnInformation = new AdapterSeatReturnInformation(PassengerInformationActivity.this,numberOfPassengers, new IClickItemSeatInformation() {
            @Override
            public void onSeat(int position) {
            }
        });
        adapterSeatReturnInformation.getFlight(flightReturn);
        rcvSeatReturn.setAdapter(adapterSeatReturnInformation);
        rcvSeatReturn.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // resultCode được set bởi DetailActivity
        // RESULT_OK chỉ ra rằng kết quả này đã thành công
        if(resultCode == Activity.RESULT_OK) {
            // Nhận dữ liệu từ Intent trả về
            Bundle bundle = data.getExtras();
            obj = (Passenger)bundle.getSerializable("Passenger");
            int position = bundle.getInt("position");
            // Sử dụng kết quả result bằng cách hiện Toast
            if (position >= 0 && position < passengerList.size()) {
                passengerList.set(position, obj);
                // Update the adapter with the new data
                adapterPassenger.updatePassengerInfo(position, obj);
                adapterSeatInformation.getPassengerInfo(passengerList);
            }
            Toast.makeText(this,"position " + position + " Result: " + obj.FullName, Toast.LENGTH_LONG).show();
        } else {
            // DetailActivity không thành công, không có data trả về.
        }
    }
    public void PostCache(){
        RequestQueue queue = Volley.newRequestQueue(PassengerInformationActivity.this);

        String flightUrl="http://" + UrlApi.url + ":8080/api/CheckOut/FlightSelection?flightId=" + flight.flightId;
        JsonObjectRequest flightRequest = new JsonObjectRequest(
                flightUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(flightRequest);
        if(flightReturn!=null){
            String flightReturnUrl="http://" + UrlApi.url + ":8080/api/CheckOut/FlightReturnSelection?flightId=" + flightReturn.flightId;
            JsonObjectRequest flightReturnRequest = new JsonObjectRequest(
                    flightReturnUrl,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );
            queue.add(flightReturnRequest);
        }


        String passengerUrl = "http://" + UrlApi.url + ":8080/api/CheckOut/ProcessBooking";
        JSONArray jsonArray = new JSONArray();
        for(Passenger objPassenger: passengerList){
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("FullName", objPassenger.FullName);
                Date date = inputFormat.parse(objPassenger.NgaySinh);
                jsonBody.put("NgaySinh",outputFormat.format(date));
                jsonBody.put("Gender",objPassenger.Gender);
                jsonBody.put("Phone",objPassenger.Phone);
                jsonBody.put("Email",objPassenger.Email);
                jsonBody.put("Address",objPassenger.Address);
                jsonArray.put(jsonBody);
            }catch(JSONException e){
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        String requestBody = jsonArray.toString();
        StringRequest passengerRequest = new StringRequest(Request.Method.POST, passengerUrl, new Response.Listener<String>() {
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
        queue.add(passengerRequest);
    }
    private boolean isPassengerInfoIncomplete(Passenger passenger) {
        return passenger.FullName == null || passenger.FullName.trim().isEmpty() ||
                passenger.NgaySinh == null || passenger.NgaySinh.trim().isEmpty() ||
                passenger.Gender == null || passenger.Gender.trim().isEmpty() ||
                passenger.Phone == null || passenger.Phone.trim().isEmpty() ||
                passenger.Email == null || passenger.Email.trim().isEmpty() ||
                passenger.Address == null || passenger.Address.trim().isEmpty();
    }
    private boolean isSeatInfoIncomplete(Seats seats) {
        return seats.SeatNumber == null || seats.SeatNumber.trim().isEmpty();
    }
    private void showErrorPassengerDialog(){
        ConstraintLayout successConstraintLayout = findViewById(R.id.layoutDialog);
        View view = LayoutInflater.from(PassengerInformationActivity.this).inflate(R.layout.dialog_error,successConstraintLayout);
        Button errorDone = view.findViewById(R.id.buttonAction);
        AlertDialog.Builder builder = new AlertDialog.Builder(PassengerInformationActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        errorDone.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if(alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
        private void showErrorSeatDialog(){
            ConstraintLayout successConstraintLayout = findViewById(R.id.layoutDialog);
            View view = LayoutInflater.from(PassengerInformationActivity.this).inflate(R.layout.dialog_error_seat,successConstraintLayout);
            Button errorSeatDone = view.findViewById(R.id.buttonAction);
            AlertDialog.Builder builder = new AlertDialog.Builder(PassengerInformationActivity.this);
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();

            errorSeatDone.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            if(alertDialog.getWindow()!=null){
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnBack){
            finish();
        }
        if(view.getId() == R.id.btnNext){
            boolean hasIncompletePassengerInfo = false;
            boolean hasIncompleteSeatInfo = false;
            for (Passenger passenger : passengerList) {
                if (isPassengerInfoIncomplete(passenger)) {
                    hasIncompletePassengerInfo = true;
                    break;
                }
            }
            for (Seats seats : seatsList) {
                if (isSeatInfoIncomplete(seats)) {
                    hasIncompleteSeatInfo = true;
                    break;
                }
            }

            if (hasIncompletePassengerInfo) {
                showErrorPassengerDialog();
            }
            else if(hasIncompleteSeatInfo) {
                showErrorSeatDialog();
            } else {
                // All passenger information is complete, proceed to the next activity
                PostCache();
                Intent intent = new Intent(PassengerInformationActivity.this, TicketInformationActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("sumMoney", sumMoney);
                intent.putExtra("numberOfPassengers", numberOfPassengers);
                bundle.putString("flightOnWay",flight.flightId);
                if(flightReturn!=null){
                    bundle.putString("flightReturn",flightReturn.flightId);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}