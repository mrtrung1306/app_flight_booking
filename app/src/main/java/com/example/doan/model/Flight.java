package com.example.doan.model;

import java.io.Serializable;

public class Flight implements Serializable {
    public String flightId;
    public String flightNumber;

    public String departureCode;
    public String arriveCode;

    public String departureDay;
    public String departureTime;
    public String arrivalTime;
    public String FareType;
    public String fareThuongGia;
    public String farePhoThong;;
    public int availableSeats;
    public Flight(){

    }

    public Flight(String flightId,String flightNumber, String departureCode, String arriveCode, String departureDay, String departureTime, String arrivalTime, String fareType,
                  String fareThuongGia, String farePhoThong, int availableSeats) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureCode = departureCode;
        this.arriveCode = arriveCode;
        this.departureDay = departureDay;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        FareType = fareType;
        this.fareThuongGia = fareThuongGia;
        this.farePhoThong = farePhoThong;
        this.availableSeats = availableSeats;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureCode() {
        return departureCode;
    }

    public void setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
    }

    public String getArriveCode() {
        return arriveCode;
    }

    public void setArriveCode(String arriveCode) {
        this.arriveCode = arriveCode;
    }

    public String getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(String departureDay) {
        this.departureDay = departureDay;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFareType() {
        return FareType;
    }

    public void setFareType(String fareType) {
        FareType = fareType;
    }

    public String getFareThuongGia() {
        return fareThuongGia;
    }

    public void setFareThuongGia(String fareThuongGia) {
        this.fareThuongGia = fareThuongGia;
    }

    public String getFarePhoThong() {
        return farePhoThong;
    }

    public void setFarePhoThong(String farePhoThong) {
        this.farePhoThong = farePhoThong;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
