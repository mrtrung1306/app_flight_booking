package com.example.doan.model;

import java.io.Serializable;

public class Passenger implements Serializable {
    public String FullName;
    public String Phone;
    public String Email;
    public String Gender;
    public String NgaySinh;
    public String Address;
    public boolean expanded;

    public Passenger(){

    }
    public Passenger(String fullName, String phone, String email, String gender, String ngaySinh, String address) {
        FullName = fullName;
        Phone = phone;
        Email = email;
        Gender = gender;
        NgaySinh = ngaySinh;
        Address = address;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
