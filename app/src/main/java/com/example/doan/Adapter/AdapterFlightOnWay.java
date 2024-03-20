package com.example.doan.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Flight;
import com.example.doan.my_interface.IClickItemFlightOneWay;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AdapterFlightOnWay extends RecyclerView.Adapter<AdapterFlightOnWay.ViewHolder> {
    public List<Flight> flightList;
    private IClickItemFlightOneWay iClickItemFlightOneWay;
    private int selectedPosition = -1;
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat inputFormatTime = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat outputFormatTime = new SimpleDateFormat("HH:mm");

    public AdapterFlightOnWay(List<Flight> flightList, IClickItemFlightOneWay listener){
        this.flightList = flightList;
        this.iClickItemFlightOneWay = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View flightView= inflater.inflate(R.layout.item_flight_one_way,parent,false);
        ViewHolder viewHolder = new ViewHolder(flightView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flight temp = flightList.get(position);

        holder.txtFlightNumber.setText(temp.flightNumber);
        try {
            Date departureTime = inputFormatTime.parse(temp.departureTime);
            String formattedDate = outputFormatTime.format(departureTime);
            holder.txtDepartureTime.setText(formattedDate);
            Date arrivalTime = inputFormatTime.parse(temp.arrivalTime);
            String formattedDate1 = outputFormatTime.format(arrivalTime);

            long durationMillis = arrivalTime.getTime() - departureTime.getTime();
            long hours = TimeUnit.MILLISECONDS.toHours(durationMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60;
            String durationString = hours + " tiếng " + minutes + " phút";

            holder.txtSumTime.setText(durationString);
            holder.txtDepartureTime.setText(formattedDate);
            holder.txtArrivalTime.setText(formattedDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtDepartureCode.setText(temp.departureCode);
        holder.txtArrivalCode.setText(temp.arriveCode);
        try {
            Date date = inputFormat.parse(temp.departureDay);
            String formattedDate = outputFormat.format(date);
            holder.txtDepartureDay.setText(formattedDate);
            holder.txtArrivalDay.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String farePhoThongFormatted = format.format(Double.parseDouble(temp.getFarePhoThong()));
        String fareThuongGiaFormatted = format.format(Double.parseDouble(temp.getFareThuongGia()));
        holder.txtFareType.setText("Phổ thông");
        holder.txtFareAmount.setText(farePhoThongFormatted);
        holder.txtFareType1.setText("Thương Gia");
        holder.txtFareAmount1.setText(fareThuongGiaFormatted);

        holder.btnPhoThong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp.setFareType("Phổ thông");
                iClickItemFlightOneWay.onFlightClick(temp);
                updateButtonAppearance(holder.btnPhoThong, holder.rltLayout2);
                resetButtonAppearance(holder.btnThuongGia, holder.rltLayout3);
                int currentPosition = holder.getAdapterPosition(); // cập nhật vị trí được chọn
                selectedPosition = currentPosition; // Cập nhật vị trí được chọn
                notifyDataSetChanged();
            }
        });
        holder.btnThuongGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp.setFareType("Thương Gia");
                iClickItemFlightOneWay.onFlightClick(temp);
                updateButtonAppearance(holder.btnThuongGia, holder.rltLayout3);
                resetButtonAppearance(holder.btnPhoThong, holder.rltLayout2);
                int currentPosition = holder.getAdapterPosition();
                selectedPosition = currentPosition;
                notifyDataSetChanged();
            }
        });
        if (selectedPosition == position) {
            // Hiển thị trạng thái của layoutItemFligth được chọn
        } else {
            // Ẩn trạng thái của layoutItemFligth khác
            resetButtonAppearance(holder.btnPhoThong, holder.rltLayout2);
            resetButtonAppearance(holder.btnThuongGia, holder.rltLayout3);
        }
    }
    private void updateButtonAppearance(Button button, RelativeLayout layout) {
        button.setText("Đang chọn");
        layout.setBackgroundResource(R.drawable.item_flight_ready);
        button.setBackgroundResource(R.drawable.border_button_ready);
        button.setLayoutParams(new RelativeLayout.LayoutParams(350, 90));
    }
    private void resetButtonAppearance(Button button, RelativeLayout layout) {
        button.setText("Chọn");
        layout.setBackgroundResource(R.color.white);
        button.setBackgroundResource(R.drawable.border_button);
        button.setLayoutParams(new RelativeLayout.LayoutParams(248,83)
        );
    }
    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDepartureDay,txtDepartureTime,txtDepartureCode,txtFlightNumber,
                 txtArrivalDay,txtArrivalTime,txtArrivalCode,txtFareType,txtFareAmount,txtFareAmount1,txtFareType1,txtSumTime;
        Button btnNext,btnThuongGia,btnPhoThong;
        RelativeLayout rltLayout2,rltLayout3,layoutItemFligth;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDepartureDay = itemView.findViewById(R.id.txtDepartureDay);
            txtDepartureTime = itemView.findViewById(R.id.txtDepartureTime);
            txtDepartureCode = itemView.findViewById(R.id.txtDepartureCode);
            txtFlightNumber = itemView.findViewById(R.id.txtFlightNumber);
            txtArrivalDay = itemView.findViewById(R.id.txtArrivalDay);
            txtArrivalTime = itemView.findViewById(R.id.txtArrivalTime);
            txtArrivalCode = itemView.findViewById(R.id.txtAirportCode);
            txtFareType = itemView.findViewById(R.id.txtFareType);
            txtFareType1 = itemView.findViewById(R.id.txtFareType1);
            txtFareAmount = itemView.findViewById(R.id.txtFareAmount);
            txtFareAmount1 = itemView.findViewById(R.id.txtFareAmount1);
            txtSumTime = itemView.findViewById(R.id.txtSumTime);
            btnNext = itemView.findViewById(R.id.btnNext);
            btnThuongGia = itemView.findViewById(R.id.btnThuongGia);
            btnPhoThong = itemView.findViewById(R.id.btnPhoThong);
            rltLayout2 = itemView.findViewById(R.id.rltlayout2);
            rltLayout3 = itemView.findViewById(R.id.rltlayout3);
            layoutItemFligth = itemView.findViewById(R.id.layoutItemFligth);
        }
    }
}
