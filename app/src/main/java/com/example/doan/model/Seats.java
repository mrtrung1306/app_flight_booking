package com.example.doan.model;

import java.io.Serializable;

public class Seats implements Serializable {
    public int SeatId;
    public int FlightID;
    public String SeatNumber;
    public String SeatClass;
    public String SeatAvailable;

    public Seats(){

    }

    public Seats(int seatId, int flightID, String seatNumber, String seatClass, String seatAvailable) {
        SeatId = seatId;
        FlightID = flightID;
        SeatNumber = seatNumber;
        SeatClass = seatClass;
        SeatAvailable = seatAvailable;
    }

    public int getSeatId() {
        return SeatId;
    }

    public void setSeatId(int seatId) {
        SeatId = seatId;
    }

    public int getFlightID() {
        return FlightID;
    }

    public void setFlightID(int flightID) {
        FlightID = flightID;
    }

    public String getSeatNumber() {
        return SeatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        SeatNumber = seatNumber;
    }

    public String getSeatClass() {
        return SeatClass;
    }

    public void setSeatClass(String seatClass) {
        SeatClass = seatClass;
    }

    public String getSeatAvailable() {
        return SeatAvailable;
    }

    public void setSeatAvailable(String seatAvailable) {
        SeatAvailable = seatAvailable;
    }
}
