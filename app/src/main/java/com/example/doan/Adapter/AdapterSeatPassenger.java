package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Passenger;
import com.example.doan.model.Seats;

import java.util.ArrayList;
import java.util.List;

public class AdapterSeatPassenger extends RecyclerView.Adapter<AdapterSeatPassenger.ViewHolder> {
    private int availableSeats;
    private List<Passenger> passengerList;
    private Seats seats;
    private List<Seats> selectedSeats;
    public AdapterSeatPassenger(int availableSeats){
        this.availableSeats = availableSeats;
        passengerList = new ArrayList<>();
        seats = new Seats();
        selectedSeats = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View passengerView= inflater.inflate(R.layout.item_seat_passenger,parent,false);
        AdapterSeatPassenger.ViewHolder viewHolder = new AdapterSeatPassenger.ViewHolder(passengerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSeatPassenger.ViewHolder holder, int position) {
        holder.textview.setText("Hành khách " + (position + 1) + " - người lớn");
        if (position < passengerList.size()) {
            // Set the passenger name for the corresponding seat
            Passenger passenger = passengerList.get(position);
            holder.txtFullName.setText(passenger.getFullName());
        }
        if (position < selectedSeats.size()) {
            Seats selectedSeatPosition = selectedSeats.get(position);
            holder.txtSeatNumber.setText(selectedSeatPosition.SeatNumber);
        } else {
            holder.txtSeatNumber.setText("Chọn chỗ ngồi");
        }
    }
    public void getPassengerInfo(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }
    public void getSeatPassenger(List<Seats> selectedSeats){
        this.selectedSeats = selectedSeats;
        notifyDataSetChanged();
    }
    public void moveSeatPassenger(List<Seats> selectedSeats){
        this.selectedSeats = selectedSeats;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return availableSeats;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview,txtFullName,txtSeatNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.textview);
            txtFullName = itemView.findViewById(R.id.txtFullName);
            txtSeatNumber = itemView.findViewById(R.id.txtSeatNumber);
        }
    }
}
