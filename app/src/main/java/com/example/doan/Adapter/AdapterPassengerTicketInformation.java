package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Passenger;

import java.util.List;

public class AdapterPassengerTicketInformation extends RecyclerView.Adapter<AdapterPassengerTicketInformation.ViewHolder> {
    public List<Passenger> passengerList;
    public AdapterPassengerTicketInformation(List<Passenger> passengerList){
        this.passengerList = passengerList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View passengerView= inflater.inflate(R.layout.item_passenger_ticketinformation,parent,false);
        AdapterPassengerTicketInformation.ViewHolder viewHolder = new AdapterPassengerTicketInformation.ViewHolder(passengerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Passenger temp = passengerList.get(position);

        holder.txtUsername.setText(temp.FullName);
        holder.txtNgaySinh.setText(temp.NgaySinh);
        holder.txtGender.setText(temp.Gender);
        holder.txtEmail.setText(temp.Email);
        holder.txtPhone.setText(temp.Phone);
        holder.txtAddress.setText(temp.Address);

        boolean isExpanded = passengerList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return passengerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername,txtNgaySinh,txtGender,txtEmail,txtPhone,txtAddress;
        RelativeLayout expandableLayout;
        ImageView imgMoreIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtNgaySinh = itemView.findViewById(R.id.txtNgaySinh);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            imgMoreIcon = itemView.findViewById(R.id.imgMoreIcon);

            imgMoreIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Passenger passenger = passengerList.get(getAdapterPosition());
                    passenger.setExpanded(!passenger.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
