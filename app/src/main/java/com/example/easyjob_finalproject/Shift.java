package com.example.easyjob_finalproject;

public class Shift {

    public String startHour;
    public String endHour;
    public String date;
    public String userName;
    public String sumOfHours;
    public double sumOfMonthHours;

    public Shift(String startHour, String endHour, String date, String userName, String sumOfHours) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.date = date;
        this.userName = userName;
        this.sumOfHours = sumOfHours;
    }

    public double getSumOfMonthHours() {
        return sumOfMonthHours;
    }

    public void setSumOfMonthHours(double sumOfMonthHours) {
        this.sumOfMonthHours = sumOfMonthHours;
    }

    public String getSumOfHours() {
        return sumOfHours;
    }

    public void setSumOfHours(String sumOfHours) {
        this.sumOfHours = sumOfHours;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
