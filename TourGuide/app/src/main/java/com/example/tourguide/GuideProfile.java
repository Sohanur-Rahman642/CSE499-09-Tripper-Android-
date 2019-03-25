package com.example.tourguide;

public class GuideProfile {

    public String name;
    public String address;
    public String email;
    public String phone;
    public String nid;



    public GuideProfile(String name, String address, String email, String phone, String nid){
        this.name=name;
        this.address=address;
        this.email=email;
        this.phone=phone;
        this.nid=nid;

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
    public String getNid(){
        return nid;
    }
    public void setNid(String nid){
        this.nid=nid;
    }
    public GuideProfile(){

    }
}
