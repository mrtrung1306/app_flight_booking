package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.BottomSheetSeat;
import com.example.doan.R;
import com.example.doan.model.Flight;
import com.example.doan.model.Passenger;
import com.example.doan.model.Seats;
import com.example.doan.my_interface.IClickItemSeatInformation;
import com.example.doan.my_interface.IClickSeat;
import com.example.doan.url.UrlApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterSeatInformation extends RecyclerView.Adapter<AdapterSeatInformation.ViewHolder> {

    private int availableSeats;
    private IClickItemSeatInformation iClickItemSeatInformation;
    private AdapterSeatReturnInformation adapterSeatReturnInformation;
    private Context context;
    private Flight objFlight;
    public List<Seats> seatsList;
    private List<Passenger> passengerList;
    public AdapterSeatInformation(Context context, int availableSeats, IClickItemSeatInformation iClickItemSeatInformation){
        this.availableSeats = availableSeats;
        this.iClickItemSeatInformation = iClickItemSeatInformation;
        this.context = context;
        objFlight = new Flight();
        seatsList = new ArrayList<>();
        passengerList = new ArrayList();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View passengerView= inflater.inflate(R.layout.item_seat_information,parent,false);
        AdapterSeatInformation.ViewHolder viewHolder = new AdapterSeatInformation.ViewHolder(passengerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSeatInformation.ViewHolder holder, int position) {
        holder.textview2.setText("Hành khách " + (position + 1) + " - người lớn");
        holder.itemLayoutSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetJsonSeatArray();
            }
        });
        if(position < seatsList.size()){
            Seats selectedSeatPosition = seatsList.get(position);
                holder.txtSeatNumber.setText(selectedSeatPosition.SeatNumber);
        }
        else {
            holder.txtSeatNumber.setText("Chọn chỗ ngồi");
        }
    }

    @Override
    public int getItemCount() {
        return availableSeats;
    }
    public void getFlight(Flight flight) {
        objFlight = flight;
    }
    public void getPassengerInfo(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }
    public void GetJsonSeatArray(){
       RequestQueue queue = Volley.newRequestQueue(context);

       String url="http://" + UrlApi.url + ":8080/api/CheckOut/SeatsDiagram?flightId=" + objFlight.flightId;
       JsonObjectRequest request = new JsonObjectRequest(
               url,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       try {
                           seatsList.clear();
                           JSONArray jsonArray = response.getJSONArray("data");
                           for(int i=0; i<jsonArray.length(); i++){
                               JSONObject seatObj = jsonArray.getJSONObject(i);
                               Seats seats = new Seats();
                               seats.setSeatId(seatObj.getInt("seatID"));
                               seats.setFlightID(seatObj.getInt("flightID"));
                               seats.setSeatNumber(seatObj.getString("seatNumber"));
                               seats.setSeatClass(seatObj.getString("seatClass"));
                               seats.setSeatAvailable(seatObj.getString("seatAvailable"));
                               seatsList.add(seats);
                           }
                           clickOpenBottomSheetSeat();
                       }catch (JSONException ex) {
                           ex.printStackTrace();
                       }
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
    private void clickOpenBottomSheetSeat(){
        BottomSheetSeat bottomSheetSeat = new BottomSheetSeat(objFlight,passengerList,availableSeats,seatsList,this,adapterSeatReturnInformation, new IClickSeat() {
            @Override
            public void onSeatClick(Seats seat) {
            }

            @Override
            public void onSeatUnselected(Seats seat) {
                seatsList.add(seat);
            }
        });
        bottomSheetSeat.show(((AppCompatActivity) context).getSupportFragmentManager(),bottomSheetSeat.getTag());
        bottomSheetSeat.setCancelable(false);
    }
    public void getSeatNumber(List<Seats> seatsList) {
        this.seatsList = seatsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview2,txtSeatNumber;
        RelativeLayout itemLayoutSeat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLayoutSeat = itemView.findViewById(R.id.itemLayoutSeat);
            textview2 = itemView.findViewById(R.id.textview2);
            txtSeatNumber = itemView.findViewById(R.id.txtSeatNumber);
        }
    }
}
/*private void showDialog() {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomseatlayout);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(false);
    }*/
