package com.example.asus.tripper;

public class PackagesModel1 {

    public String packagename, date,details, enddate, endtime, fullname, gid, groupmembers,location,meetpoint,packageimage,price,startdate,starttime,time, profileimage;

    public PackagesModel1(){

    }

    public PackagesModel1(String packagename,  String details, String enddate, String endtime,  String groupmembers, String location, String meetpoint, String price, String startdate, String starttime) {
        this.packagename = packagename;
        //this.date = date;
        this.details = details;
        this.enddate = enddate;
        this.endtime = endtime;
        //this.fullname = fullname;
        //this.gid = gid;
        this.groupmembers = groupmembers;
        this.location = location;
        this.meetpoint = meetpoint;
        //this.packageimage = packageimage;
        this.price = price;
        this.startdate = startdate;
        this.starttime = starttime;
        //this.time = time;
        //this.profileimage = profileimage;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

   /* public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }*/

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

   /* public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }*/

    public String getGroupmembers() {
        return groupmembers;
    }

    public void setGroupmembers(String groupmembers) {
        this.groupmembers = groupmembers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeetpoint() {
        return meetpoint;
    }

    public void setMeetpoint(String meetpoint) {
        this.meetpoint = meetpoint;
    }

   /* public String getPackageimage() {
        return packageimage;
    }

    public void setPackageimage(String packageimage) {
        this.packageimage = packageimage;
    }*/

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

   /* public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }*/
}
