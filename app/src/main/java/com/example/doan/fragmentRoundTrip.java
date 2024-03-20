package com.example.doan;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.doan.SharedViewModel.SharedViewModel;
import com.example.doan.model.Airports;

import java.text.SimpleDateFormat;
import java.util.Date;

public class fragmentRoundTrip extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    int count=1;
    String sum;
    TextView txtDepartureCode;
    TextView txtArrivalCode;
    TextView txtDepartureDay;
    TextView txtArrivalDay;
    TextView txtAvailableSeats;
    public fragmentRoundTrip() {
        // Required empty public constructor
    }
    public static fragmentRoundTrip newInstance(String param1, String param2) {
        fragmentRoundTrip fragment = new fragmentRoundTrip();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_round_trip, container, false);

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        SharedViewModel viewModel = new
                ViewModelProvider(getActivity()).get(SharedViewModel.class);

        view.findViewById(R.id.departure).setOnClickListener(this);
        view.findViewById(R.id.arrival).setOnClickListener(this);
        view.findViewById(R.id.departureday).setOnClickListener(this);
        view.findViewById(R.id.arrivalday).setOnClickListener(this);
        view.findViewById(R.id.layoutPassenger).setOnClickListener(this);

        RelativeLayout rltDepartureDay= view.findViewById(R.id.departureday);
        RelativeLayout rltArrivalDay= view.findViewById(R.id.arrivalday);
        txtDepartureCode= view.findViewById(R.id.txtAirportCode);
        TextView txtCity = view.findViewById(R.id.txtcity);
        txtArrivalCode= view.findViewById(R.id.txtAirportCode1);
        TextView txtCity1 = view.findViewById(R.id.txtCity1);
        txtDepartureDay = view.findViewById(R.id.txtdepartureday1);
        txtArrivalDay = view.findViewById(R.id.txtarrivalday1);
        txtAvailableSeats = view.findViewById(R.id.txtPassenger1);
        viewModel.setAvailableSeats(txtAvailableSeats.getText().toString());

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle bundle = result.getData().getExtras();
                        Airports airport = (Airports) bundle.getSerializable("object_user");
                        String airportType = bundle.getString("airport_type");
                        String airportDay= bundle.getString("airport_day");
                        if ("departure".equals(airportType)) {
                            // Handle departure
                            txtDepartureCode.setText(airport.airportCode);
                            txtCity.setText("TP "+airport.City);
                            viewModel.setDepartureCode(txtDepartureCode.getText().toString());
                            viewModel.setDepartureCity(txtCity.getText().toString());
                        } else if ("arrival".equals(airportType)) {
                            txtArrivalCode.setText(airport.airportCode);
                            txtCity1.setText("TP "+airport.City);
                            viewModel.setArrivalCode(txtArrivalCode.getText().toString());
                            viewModel.setArrivalCity(txtCity1.getText().toString());
                        }
                        else if ("day".equals(airportDay)) {
                            Date a = (Date) bundle.getSerializable("departure");
                            Date b =(Date) bundle.getSerializable("arrvival");
                            String departureDay = simpleDate.format(a);
                            String arrivalDay = simpleDate.format(b);
                            txtDepartureDay.setText(departureDay);
                            txtArrivalDay.setText(arrivalDay);
                            rltDepartureDay.setBackground(null);
                            rltArrivalDay.setBackground(null);
                            viewModel.setDepartureDay(txtDepartureDay.getText().toString());
                            viewModel.setArrivalDay(txtArrivalDay.getText().toString());
                        }
                    }
                }
        );
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.departure) {
            Intent intent = new Intent(requireActivity(), list_airport.class);
            intent.putExtra("airport_type", "departure");
            activityResultLauncher.launch(intent);
        } else if (view.getId() == R.id.arrival) {
            Intent intent = new Intent(requireActivity(), list_airport.class);
            intent.putExtra("airport_type", "arrival");
            activityResultLauncher.launch(intent);
        }
        else if(view.getId() == R.id.departureday ||view.getId() == R.id.arrivalday){
            Intent intent = new Intent(requireActivity(),CalendarActivity.class);
            intent.putExtra("airport_day", "day");
            activityResultLauncher.launch(intent);
        }
        else if(view.getId()== R.id.layoutPassenger){
            showDialog();
        }
    }
    public boolean areDaysSelected() {
        TextView txtDeparture = getView().findViewById(R.id.txtdepartureday1);
        TextView txtArrival = getView().findViewById(R.id.txtarrivalday1);

        return !txtDeparture.getText().toString().equals("+") && !txtArrival.getText().toString().equals("+");
    }
    private void showDialog() {
        final Dialog dialog = new Dialog(requireContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(false);
        TextView txtcount = dialog.findViewById(R.id.txtcount);
        txtcount.setText(""+count);
        dialog.findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                txtcount.setText(""+count);
            }
        });
        dialog.findViewById(R.id.btnRemove).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(count <0 ) count=0;
                else count--;
                txtcount.setText(""+count);
            }
        });
        dialog.findViewById(R.id.btnSuccess).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                SharedViewModel viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
                sum = txtcount.getText().toString();
                TextView txtPassenger1 = requireView().findViewById(R.id.txtPassenger1);
                txtPassenger1.setText(sum);
                viewModel.setAvailableSeats(txtAvailableSeats.getText().toString());
                dialog.dismiss();
            }
        });
    }
}