package com.example.doan.my_interface;

import com.example.doan.model.Seats;

public interface IClickSeat {
    void onSeatClick(Seats seats);
    void onSeatUnselected(Seats seat);
}
