package com.example.demo.Exceptions;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String text){
        super(text);
    }
}
