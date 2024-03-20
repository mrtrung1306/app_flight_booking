package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Airports;
import com.example.doan.model.Flight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdapterFlightTicketInformation extends RecyclerView.Adapter<AdapterFlightTicketInformation.ViewHolder> {
    public List<Flight> flightList;
    public Airports airports;
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat inputFormatTime = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat outputFormatTime = new SimpleDateFormat("HH:mm");
    public AdapterFlightTicketInformation(List<Flight> flightList,Airports airports){
        this.flightList= flightList;
        this.airports = airports;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View flightView= inflater.inflate(R.layout.item_flight_ticketinformation,parent,false);
        AdapterFlightTicketInformation.ViewHolder viewHolder = new AdapterFlightTicketInformation.ViewHolder(flightView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flight temp= flightList.get(position);
        try {
            Date departureTime = inputFormatTime.parse(temp.getDepartureTime());
            String formattedDate = outputFormatTime.format(departureTime);

            Date date = inputFormat.parse(temp.getDepartureDay());
            String formattedDate2 = outputFormat.format(date) ;

            Date arrivalTime = inputFormatTime.parse(temp.getArrivalTime());
            String formattedDate1 = outputFormatTime.format(arrivalTime);
            long durationMillis = arrivalTime.getTime() - departureTime.getTime();
            long hours = TimeUnit.MILLISECONDS.toHours(durationMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60;
            String durationString = hours + " tiếng " + minutes + " phút";

            holder.txtDepartureDay.setText(formattedDate2 + " " + formattedDate);
            holder.txtDepartureCode.setText(temp.getDepartureCode());
            holder.txtCity.setText("TP " + airports.getDepartureName());
            holder.txtSumTime1.setText(durationString);
            holder.txtFlightNumber.setText(temp.getFlightNumber());
            holder.txtArrivalDay.setText(formattedDate2 + " " + formattedDate1);
            holder.txtArrivalCode.setText(temp.getArriveCode());
            holder.txtCity1.setText("TP " + airports.getArrivalName());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(position ==0){
            holder.textView.setText("Chiều đi");
        }
        else{
            holder.textView.setText("Chiều về");
        }
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDepartureDay,txtDepartureCode, txtCity,txtSumTime1,txtFlightNumber,txtArrivalDay,txtArrivalCode,txtCity1,textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDepartureDay = itemView.findViewById(R.id.txtDepartureDay);
            txtDepartureCode = itemView.findViewById(R.id.txtDepartureCode);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtSumTime1 = itemView.findViewById(R.id.txtSumTime1);
            txtFlightNumber = itemView.findViewById(R.id.txtFlightNumber);
            txtArrivalDay = itemView.findViewById(R.id.txtArrivalDay);
            txtArrivalCode = itemView.findViewById(R.id.txtArrivalCode);
            txtCity1 = itemView.findViewById(R.id.txtCity1);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
