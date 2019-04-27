package com.example.asus.tripper;

public class GuideProfile {

    public String username;
    public String fullname;
    public String address;
    public String country;
    public String phone;
    //public String profileimage;



    public GuideProfile(String username, String fullname, String address, String country, String phone){
        this.username=username;
        this.fullname=fullname;
        this.address=address;
        this.country=country;
        this.phone=phone;
        //this.profileimage=profileimage;


    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }

    public String getFullname(){
        return fullname;
    }
    public void setFullname(String fullname){
        this.fullname=fullname;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country=country;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    /*public String getProfileimage(){
        return profileimage;
    }
    public void setProfileimage(String profileimage){
        this.profileimage=profileimage;
    }*/

    public GuideProfile(){

    }
}
