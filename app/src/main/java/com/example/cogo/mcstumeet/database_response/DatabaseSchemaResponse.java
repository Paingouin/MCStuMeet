package com.example.cogo.mcstumeet.database_response;

public class DatabaseSchemaResponse {
    public String doc_id, sender, receiver, accepted;

    public void setDoc_id(String doc_id){
        this.doc_id = doc_id;
    }

    public void setSender(String username) {
        this.sender = username;
    }

    public void setReceiver(String username){
        this.receiver = username;
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

    public String getAccepted() {
        return accepted;
    }

    public String getDoc_id(){
        return doc_id;
    }
}
