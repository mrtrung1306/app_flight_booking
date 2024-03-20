package com.example.doan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Airports implements Serializable {
    public String City;
    public String airportCode;
    public String departureName;
    public String arrivalName;
    public Airports(){

    }
    public Airports(String city,String code,String departureName,String arrivalName){
        City = city;
        airportCode = code;
        this.departureName = departureName;
        this.arrivalName = arrivalName;
    }
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getairportCode() {
        return airportCode;
    }

    public void setairportCode(String code) {
        airportCode = code;
    }

    public String getDepartureName() {
        return departureName;
    }

    public void setDepartureName(String departureName) {
        this.departureName = departureName;
    }

    public String getArrivalName() {
        return arrivalName;
    }

    public void setArrivalName(String arrivalName) {
        this.arrivalName = arrivalName;
    }
}
