package com.example.tutorfinderapp;

public class ModelClassT {
    String Name,User_ID;

    public ModelClassT() {
    }
    public ModelClassT(String Name, String User_ID) {
        this.Name = Name;
        this.User_ID = User_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }
}
