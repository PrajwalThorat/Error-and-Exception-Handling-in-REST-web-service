package com.stackroute.exceptions;

public class BlogAlreadyExistsException extends Exception{

    String message;

    public BlogAlreadyExistsException(String message){
        this.message = message;
    }

    public  BlogAlreadyExistsException(){

    }


}
