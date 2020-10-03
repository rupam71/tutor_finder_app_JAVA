package com.example.tutorfinderapp;

public class ModelClass{
    String Name,Subject,Location,User_ID;

    ModelClass(){

    }


    public ModelClass(String Name,/* String Email, String Phone,*/ String Subject, String Location, /*String Image,*/ String User_ID) {
        this.Name = Name;
        /*this.Email = Email;
        this.Phone = Phone;*/
        this.Subject = Subject;
        this.Location = Location;
       /* this.Image = Image;*/
        this.User_ID = User_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
/*
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }*/

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
/*
    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }*/

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String User_ID) {
        User_ID = User_ID;
    }
}
