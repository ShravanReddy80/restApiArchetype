package com.ref.api.util.errors;

public class CustomMessage {
 
    private String message;
 
    public CustomMessage(String message){
        this.message = message;
    }
 
    public String getErrorMessage() {
        return message;
    }
 
}