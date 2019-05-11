package com.example.asus.tripper;

public class FindGuideModel {

    public String profileimage, phone, username, country;

    public FindGuideModel(){

    }

    public FindGuideModel(String profileimage, String phone, String username, String country) {
        this.profileimage = profileimage;
        this.phone = phone;
        this.username = username;
        this.country = country;

    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
