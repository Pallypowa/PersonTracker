package com.piko.testexercise.exceptions;

public class AddressLimitException extends RuntimeException{
    public AddressLimitException(){
        super("A person can only have 2 addresses (a temporary and a permanent one).");
    }
}
