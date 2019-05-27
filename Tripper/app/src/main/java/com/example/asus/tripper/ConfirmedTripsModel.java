package com.example.asus.tripper;

public class ConfirmedTripsModel {

    public String confirm_type;

    public ConfirmedTripsModel(){

    }

    public ConfirmedTripsModel(String confirm_type) {
        this.confirm_type = confirm_type;
    }

    public String getConfirm_type() {
        return confirm_type;
    }

    public void setConfirm_type(String confirm_type) {
        this.confirm_type = confirm_type;
    }
}
