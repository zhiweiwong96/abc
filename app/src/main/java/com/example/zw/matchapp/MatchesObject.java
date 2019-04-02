package com.example.zw.matchapp;

public class MatchesObject {
    private String userId;
    private String name;
    private String profileImageUrl;

    private String phone;

    public MatchesObject (String userId, String name, String profileImageUrl,String phone){
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.phone = phone;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserID(String userID){
        this.userId = userId;
    }

    public String getProfileImageUrl(){ return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl){ this.profileImageUrl = profileImageUrl; }

    public String getName(){ return name;}
    public void setName(String name){this.name = name;}

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

}
