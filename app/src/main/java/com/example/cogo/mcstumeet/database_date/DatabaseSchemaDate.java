package com.example.cogo.mcstumeet.database_date;

public class DatabaseSchemaDate {
    public String doc_id, sender, receiver, time, location;

    public void setDoc_id(String doc_id){
        this.doc_id = doc_id;
    }

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

    public String getDoc_id(){
        return doc_id;
    }
}
