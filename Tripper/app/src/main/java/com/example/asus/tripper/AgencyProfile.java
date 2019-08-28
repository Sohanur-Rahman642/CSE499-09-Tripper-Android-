package com.example.asus.tripper;

public class AgencyProfile {

    public String name;
    public String email;
    public String location;
    public String buisenss_license;
    public String passport;
    public String phone;
    public String propic;
    //public String profileimage;


    public AgencyProfile() {
    }

    public AgencyProfile(String name, String email, String location, String buisenss_license, String passport, String phone,String propic) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.buisenss_license = buisenss_license;
        this.passport = passport;
        this.phone = phone;
        this.propic = propic;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBuisenss_license() {
        return buisenss_license;
    }

    public void setBuisenss_license(String buisenss_license) {
        this.buisenss_license = buisenss_license;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }
}
