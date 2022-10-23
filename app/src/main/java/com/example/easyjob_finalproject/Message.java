package com.example.easyjob_finalproject;

//Defining the "message" class
public class Message {

   // Defining the "message" object attributes:
    public String managerUserName;
    public String workerUserName;
    public String textMessage;
    public String nameOfSender;
    public String lastMessage;


    //Constructor
    public Message(String managerUserName, String workerUserName, String textMessage, String nameOfSender, String lastMessage) {
        this.managerUserName = managerUserName;
        this.workerUserName = workerUserName;
        this.textMessage = textMessage;
        this.nameOfSender = nameOfSender;
        this.lastMessage = lastMessage;
    }


    //=================================================
    //Getters & Setters
    //=================================================
    public String getManagerUserName() {
        return managerUserName;
    }

    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
    }

    public String getWorkerUserName() {
        return workerUserName;
    }

    public void setWorkerUserName(String workerUserName) {
        this.workerUserName = workerUserName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getNameOfSender() {
        return nameOfSender;
    }

    public void setNameOfSender(String nameOfSender) {
        this.nameOfSender = nameOfSender;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
