package com.example.asus.tripper;

public class FindAgencyGuideModel {

    public String profileimage, phone, ownerName, location;

    public FindAgencyGuideModel(){

    }

    public FindAgencyGuideModel(String profileimage,  String ownerName, String location) {
        this.profileimage = profileimage;

        this.ownerName = ownerName;
        this.location = location;

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerNmae(String ownerNmae) {
        this.ownerName = ownerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
