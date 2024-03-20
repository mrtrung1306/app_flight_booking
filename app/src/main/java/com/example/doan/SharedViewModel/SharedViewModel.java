package com.example.doan.SharedViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> departureCode  = new MutableLiveData<>();
    private MutableLiveData<String> arrivalCode  = new MutableLiveData<>();
    private MutableLiveData<String> departureDay  = new MutableLiveData<>();
    private MutableLiveData<String> arrivalDay  = new MutableLiveData<>();
    private MutableLiveData<String> availableSeats  = new MutableLiveData<>();

    private MutableLiveData<String> departureCity  = new MutableLiveData<>();
    private MutableLiveData<String> arrivalCity  = new MutableLiveData<>();

    public void setDepartureCode(String code) {
        departureCode.setValue(code);
    }

    public MutableLiveData<String> getDepartureCode() {
        return departureCode;
    }
    public void setArrivalCode(String code) {
        arrivalCode.setValue(code);
    }

    public MutableLiveData<String> getArrivalCode() {
        return arrivalCode;
    }
    public void setDepartureDay(String code) {
        departureDay.setValue(code);
    }

    public MutableLiveData<String> getDepartureDay() {
        return departureDay;
    }
    public void setArrivalDay(String code) {
        arrivalDay.setValue(code);
    }

    public MutableLiveData<String> getArrivalDay() {
        return arrivalDay;
    }
    public void setAvailableSeats(String code) {
        availableSeats.setValue(code);
    }

    public MutableLiveData<String> getAvailableSeats() {
        return availableSeats;
    }
    public void setDepartureCity(String code) {
        departureCity.setValue(code);
    }

    public MutableLiveData<String> getDepartureCity() {
        return departureCity;
    }
    public void setArrivalCity(String code) {
        arrivalCity.setValue(code);
    }

    public MutableLiveData<String> getArrivalCity() {
        return arrivalCity;
    }
}
