package com.example.easyjob_finalproject;

import java.util.ArrayList;

public class Manager extends Person{

//Defining "Manager" class attributes
    public String workPlace, cityWorkPlace, streetWorkPlace;
    public String messageRecived, messageSend;
    public String phoneNumber;
    public boolean isHaveGroupOfEmployes;



//Constructor:
        public Manager(String fullName, String email, String userName, String password, String position, String workPlace, String cityWorkPlace, String streetWorkPlace, String messageRecived, String messageSend, boolean isHaveGroupOfEmployes, String phoneNumber) {
        super(fullName, email, userName, password, position, phoneNumber);
        this.workPlace = workPlace;
        this.cityWorkPlace = cityWorkPlace;
        this.streetWorkPlace = streetWorkPlace;
        this.messageRecived = messageRecived;
        this.messageSend = messageSend;
        this.isHaveGroupOfEmployes = isHaveGroupOfEmployes;
        this.phoneNumber = phoneNumber;
    }
}
