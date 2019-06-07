package com.example.asus.tripper;

public class ConfirmedTripsModel {

    public String confirm_type_user;

    public ConfirmedTripsModel(){

    }

    public ConfirmedTripsModel(String confirm_type_user) {
        this.confirm_type_user = confirm_type_user;
    }

    public String getConfirm_type_user() {
        return confirm_type_user;
    }

    public void setConfirm_type_user(String confirm_type_user) {
        this.confirm_type_user = confirm_type_user;
    }
}
