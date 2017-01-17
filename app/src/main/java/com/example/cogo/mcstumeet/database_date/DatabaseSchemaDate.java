package com.example.cogo.mcstumeet.database_date;

/**
 * Created by Gamze on 17.01.2017.
 */

public class DatabaseSchemaDate {
    public String sender, receiver, time, location, accepted;

    public void setSender(String username) {
        this.sender = username;
    }

    public void setReceiver(String username){
        this.receiver = username;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setAccepted(String accepted){
        this.accepted = accepted;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getAccepted() {
        return accepted;
    }
}
