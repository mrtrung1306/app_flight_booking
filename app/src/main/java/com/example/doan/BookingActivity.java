package com.example.doan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doan.SharedViewModel.SharedViewModel;
import com.example.doan.model.Flight;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedViewModel sharedViewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        findViewById(R.id.btnExit).setOnClickListener(this);
        findViewById(R.id.btnSearch).setOnClickListener(this);
        sharedViewMode = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewMode.getDepartureCode().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String departureCode) {
                // Handle changes in departure code
                // Update UI or perform any necessary actions
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()){
                    case 0:
                        fragment = new fragmentRoundTrip();
                        break;
                    case 1:
                        fragment = new FragmentOneWay();
                        break;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnExit){
            finish();
        }
        if(view.getId() == R.id.btnSearch){
            TabLayout tabLayout = findViewById(R.id.tabLayout);
            int selectedTabPosition = tabLayout.getSelectedTabPosition();

            boolean areDaysSelected = false;
            List<Flight> flightList;
            if (selectedTabPosition  == 0) {

                fragmentRoundTrip fragmentRoundTrip = (fragmentRoundTrip)getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                areDaysSelected = fragmentRoundTrip.areDaysSelected();

            } else if (selectedTabPosition == 1) {

                FragmentOneWay fragmentOneWay = (FragmentOneWay)getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                areDaysSelected = fragmentOneWay.areDaysSelected();

            }

            if (!areDaysSelected) {
                showDialog();
            } else {
                if(selectedTabPosition == 0){
                    Intent intent = new Intent(BookingActivity.this, FlightOneWayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("departureCode",sharedViewMode.getDepartureCode().getValue());
                    bundle.putString("arrivalCode",sharedViewMode.getArrivalCode().getValue());
                    bundle.putString("departureDay",sharedViewMode.getDepartureDay().getValue());
                    bundle.putString("arrivalDay",sharedViewMode.getArrivalDay().getValue());
                    bundle.putString("departureCity",sharedViewMode.getDepartureCity().getValue());
                    bundle.putString("arrivalCity",sharedViewMode.getArrivalCity().getValue());
                    bundle.putString("AvailableSeats",sharedViewMode.getAvailableSeats().getValue());

                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (selectedTabPosition == 1) {
                    Intent intent = new Intent(BookingActivity.this, FlightOneWayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("departureCode",sharedViewMode.getDepartureCode().getValue());
                    bundle.putString("arrivalCode",sharedViewMode.getArrivalCode().getValue());
                    bundle.putString("departureDay",sharedViewMode.getDepartureDay().getValue());
                    bundle.putString("departureCity",sharedViewMode.getDepartureCity().getValue());
                    bundle.putString("arrivalCity",sharedViewMode.getArrivalCity().getValue());
                    bundle.putString("AvailableSeats",sharedViewMode.getAvailableSeats().getValue());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        }
    }
    private void showDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_search);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
