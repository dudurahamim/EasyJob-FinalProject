package com.example.easyjob_finalproject;

public class Person {

    //Defining "Person" class attributes
    protected String fullName;
    protected String email;
    protected String userName;
    protected String password;
    protected String position;
    protected String PhoneNumber;
    protected String startHour, exitHour, lastDateSignHours;

//Default Constructor
    public Person() {
    }

    //Constructor
    public Person(String fullName, String email, String userName, String password, String position, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.position = position;
        PhoneNumber = phoneNumber;
        this.startHour = "";
        this.exitHour = "";
        this.lastDateSignHours = "";
    }

//    public Person(String fullName, String email, String userName, String password, String position, String phoneNumber) {
//        this.fullName = fullName;
//        this.email = email;
//        this.userName = userName;
//        this.password = password;
//        this.position = position;
//        PhoneNumber = phoneNumber;
//    }


    //Getters & Setters:
    //-----------------------------
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
