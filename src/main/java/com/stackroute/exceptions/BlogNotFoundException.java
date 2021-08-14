package com.stackroute.exceptions;

public class BlogNotFoundException extends Exception{

    String message;

    public BlogNotFoundException(String message){
        this.message = message;
    }
    public BlogNotFoundException(){

    }
}
