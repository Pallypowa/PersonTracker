package com.piko.testexercise.exceptions;

public class RecordDoesNotExistException extends RuntimeException{
    public RecordDoesNotExistException(){
        super("This record does not exist");
    }
}
