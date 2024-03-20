package com.example.doan.my_interface;

import com.example.doan.model.Seats;

import java.util.List;

public interface IClickBottomSheetSeat {
    void onBottomSheetDismiss(List<Seats> selectedSeatsList);
}
