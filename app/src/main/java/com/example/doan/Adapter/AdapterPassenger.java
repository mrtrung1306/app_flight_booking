package com.example.doan.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.FillPassengerActivity;
import com.example.doan.MainActivity;
import com.example.doan.R;
import com.example.doan.SharedViewModel.SharedViewModel;
import com.example.doan.model.Passenger;
import com.example.doan.my_interface.IClickItemPassenger;

import java.util.Calendar;
import java.util.List;

public class AdapterPassenger extends RecyclerView.Adapter<AdapterPassenger.ViewHolder> {
    private int availableSeats;
    private IClickItemPassenger iClickItemPassenger;
    private Passenger objPassenger;
    public AdapterPassenger(int availableSeats,IClickItemPassenger iClickItemPassenger){
        this.availableSeats = availableSeats;
        this.iClickItemPassenger = iClickItemPassenger;
        objPassenger = new Passenger();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View passengerView= inflater.inflate(R.layout.item_passenger,parent,false);
        ViewHolder viewHolder = new AdapterPassenger.ViewHolder(passengerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtTextview1.setText("Hành khách " + (position + 1) + " - người lớn");
        int position1 = holder.getAdapterPosition();
        holder.relativeLayoutPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"position"+ positon,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, FillPassengerActivity.class);
//                ((Activity) context).startActivityForResult(intent,1);
                iClickItemPassenger.onPassengerInfoUpdated(position1);
            }
        });
        if(objPassenger.FullName == null){
            holder.txtPassenger.setText("Nhập thông tin");
        }
        else{
            holder.txtPassenger.setText(objPassenger.FullName);
        }

    }
    @Override
    public int getItemCount() {
        return availableSeats;
    }
    public void updatePassengerInfo(int position,Passenger passenger) {
        objPassenger = passenger;
        notifyItemChanged(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTextview1,txtPassenger;
        RelativeLayout relativeLayoutPassenger;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayoutPassenger = itemView.findViewById(R.id.itemLayoutPassenger);
            txtTextview1 = itemView.findViewById(R.id.textview1);
            txtPassenger = itemView.findViewById(R.id.txtPassenger);
        }
    }
}
