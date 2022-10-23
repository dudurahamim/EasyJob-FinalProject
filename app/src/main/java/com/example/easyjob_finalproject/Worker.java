package com.example.easyjob_finalproject;

public class Worker extends Person{


    public String managerUserName;
    private String sunday, monday, tuesday, wednesday, thursday, friday, saturday, lastMessage;

    public Worker(String fullName, String email, String userName, String password, String position, String phoneNumber, String managerUserName, String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String lastMessage) {
        super(fullName, email, userName, password, position, phoneNumber);
        this.managerUserName = managerUserName;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.lastMessage = lastMessage;
    }

//    public Worker(String fullName, String email, String userName, String password, String position, String phoneNumber, String managerUserName, String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String lastMessage) {
//        super(fullName, email, userName, password, position, phoneNumber);
//        this.managerUserName = managerUserName;
//        this.sunday = sunday;
//        this.monday = monday;
//        this.tuesday = tuesday;
//        this.wednesday = wednesday;
//        this.thursday = thursday;
//        this.friday = friday;
//        this.saturday = saturday;
//        this.lastMessage = lastMessage;
//    }

    public String getManagerUserName() {
        return managerUserName;
    }

    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
