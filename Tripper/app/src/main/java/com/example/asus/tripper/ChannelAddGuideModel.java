package com.example.asus.tripper;

public class ChannelAddGuideModel {

    private String channel_guide_propic;
    private String channel_guide_name;
    private String channel_guide_address;
    private String channel_guide_nid;
    private String channel_guide_passport;
    private String channel_guide_phone;


    public ChannelAddGuideModel() {
    }

    public ChannelAddGuideModel(String channel_guide_propic,
                                String channel_guide_name, String channel_guide_address,
                                String channel_guide_nid, String channel_guide_passport, String channel_guide_phone) {
        this.channel_guide_propic = channel_guide_propic;
        this.channel_guide_name = channel_guide_name;
        this.channel_guide_address = channel_guide_address;
        this.channel_guide_nid = channel_guide_nid;
        this.channel_guide_passport = channel_guide_passport;
        this.channel_guide_phone = channel_guide_phone;
    }

    public String getChannel_guide_propic() {
        return channel_guide_propic;
    }

    public void setChannel_guide_propic(String channel_guide_propic) {
        this.channel_guide_propic = channel_guide_propic;
    }

    public String getChannel_guide_name() {
        return channel_guide_name;
    }

    public void setChannel_guide_name(String channel_guide_name) {
        this.channel_guide_name = channel_guide_name;
    }

    public String getChannel_guide_address() {
        return channel_guide_address;
    }

    public void setChannel_guide_address(String channel_guide_address) {
        this.channel_guide_address = channel_guide_address;
    }

    public String getChannel_guide_nid() {
        return channel_guide_nid;
    }

    public void setChannel_guide_nid(String channel_guide_nid) {
        this.channel_guide_nid = channel_guide_nid;
    }

    public String getChannel_guide_passport() {
        return channel_guide_passport;
    }

    public void setChannel_guide_passport(String channel_guide_passport) {
        this.channel_guide_passport = channel_guide_passport;
    }

    public String getChannel_guide_phone() {
        return channel_guide_phone;
    }

    public void setChannel_guide_phone(String channel_guide_phone) {
        this.channel_guide_phone = channel_guide_phone;
    }
}
