package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Flight;
import com.example.doan.model.Seats;
import com.example.doan.my_interface.IClickSeat;

import java.util.ArrayList;
import java.util.List;

public class AdapterSeat extends RecyclerView.Adapter<AdapterSeat.ViewHolder> {
    private List<Seats> seatsList;
    private IClickSeat iClickSeat;
    private List<Integer> selectedSeats;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private int availableSeats;

    private Flight objFlight;
    public AdapterSeat(List<Seats> seats,int availableSeats,IClickSeat iClickSeat){
        this.seatsList = seats;
        this.availableSeats = availableSeats;
        this.iClickSeat = iClickSeat;
        this.selectedSeats = new ArrayList<>();
        objFlight = new Flight();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View seatView= inflater.inflate(R.layout.item_seat,parent,false);
        AdapterSeat.ViewHolder viewHolder = new AdapterSeat.ViewHolder(seatView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSeat.ViewHolder holder, int position) {
        Seats temp = seatsList.get(position);
        int position1 = holder.getAdapterPosition();
        if ((position + 4) % 6 == 0) {
            holder.textViewMiddle.setVisibility(View.VISIBLE);
            holder.textViewMiddle.setText("" + ((position + 4)/6));
        }
        if(0 == Integer.parseInt(temp.SeatAvailable)){
            if("Thương Gia".equals(temp.SeatClass)){
                holder.itemSeat.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.blue));
            }
            else if("Phổ thông".equals(temp.SeatClass)){
                holder.itemSeat.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.pink));
            }
        }
        else{
            holder.itemSeat.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.grey));
            holder.itemSeat.setEnabled(false);
        }
        holder.itemSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = holder.getAdapterPosition();
                if((objFlight.FareType).equals(temp.SeatClass)){
                    if (selectedSeats.contains(selectedPosition)) {
                        // Người dùng bỏ chọn ghế
                        selectedSeats.remove(Integer.valueOf(selectedPosition));
                        iClickSeat.onSeatUnselected(temp);
                        if ("Thương Gia".equals(temp.SeatClass)) {
                            holder.itemSeat.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.blue));
                        } else if ("Phổ thông".equals(temp.SeatClass)) {
                            holder.itemSeat.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.pink));
                        }
                        availableSeats++; // Tăng số ghế trống khi bỏ chọn
                    } else {
                        // Người dùng chọn ghế mới
                        if (availableSeats > 0) {
                            selectedSeats.add(selectedPosition);
                            holder.itemSeat.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), R.color.green));
                            iClickSeat.onSeatClick(temp);
                            availableSeats--; // Giảm số ghế trống khi chọn
                            Toast.makeText(view.getContext(), "position" + position1, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "Chọn số lượng ghế vượt quá người đi", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(view.getContext(), "Chọn khoang phổ thông/thương gia trước khi chọn ghế", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return seatsList.size();
    }

    public void getFlight(Flight flight) {
        objFlight = flight;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemSeat;
        TextView textViewMiddle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemSeat = itemView.findViewById(R.id.itemSeat);
            textViewMiddle = itemView.findViewById(R.id.textViewMiddle);
        }
    }
}

//        if (selectedSeats.contains(position)) {
//            holder.itemSeat.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.green));
//        } else {
//            if ("Thương Gia".equals(temp.SeatClass)) {
//                holder.itemSeat.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.blue));
//            } else if ("Phổ thông".equals(temp.SeatClass)) {
//                holder.itemSeat.setBackgroundTintList(ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.pink));
//            }
//        }

