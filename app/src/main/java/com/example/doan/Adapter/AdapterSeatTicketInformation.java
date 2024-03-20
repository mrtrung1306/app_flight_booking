package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Flight;
import com.example.doan.model.Seats;

import java.util.List;

    public class AdapterSeatTicketInformation extends RecyclerView.Adapter<AdapterSeatTicketInformation.ViewHolder> {

        public List<Seats> seatsList;
        public Flight flight;
        public List<Flight> flightList;
        public AdapterSeatTicketInformation(List<Seats> seatsList,List<Flight> flightList){
            this.seatsList = seatsList;
//            this.flight = flight;
            this.flightList = flightList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View seatView= inflater.inflate(R.layout.item_seat_ticketinformation,parent,false);
            AdapterSeatTicketInformation.ViewHolder viewHolder = new AdapterSeatTicketInformation.ViewHolder(seatView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Seats temp = seatsList.get(position);
            if (position < seatsList.size()) {
                Flight selectedFlightPosition = flightList.get(position);
                holder.itemLayoutSeatTicket.setText(temp.SeatNumber + " từ " + selectedFlightPosition.getDepartureCode() + " đến " + selectedFlightPosition.getArriveCode());

            }
//            holder.itemLayoutSeatTicket.setText(temp.SeatNumber + " từ " + flight.getDepartureCode() + " đến " + flight.getArriveCode());
        }

        @Override
        public int getItemCount() {
            return seatsList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemLayoutSeatTicket;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemLayoutSeatTicket =itemView.findViewById(R.id.itemLayoutSeatTicket);
            }
        }
    }
