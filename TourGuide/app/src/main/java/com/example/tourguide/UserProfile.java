package com.example.tourguide;

public class UserProfile {
    public String name;
    public String address;
    public String email;
    public String phone;



    public UserProfile(String name, String address, String email, String phone){
        this.name=name;
        this.address=address;
        this.email=email;
        this.phone=phone;

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public UserProfile(){

    }

}
