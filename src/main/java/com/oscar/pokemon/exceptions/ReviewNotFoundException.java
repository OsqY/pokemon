package com.oscar.pokemon.exceptions;

public class ReviewNotFoundException extends RuntimeException{

    private static final Long serialVersionUID = 2L;

    public ReviewNotFoundException(String message){
        super(message);
    }
}
