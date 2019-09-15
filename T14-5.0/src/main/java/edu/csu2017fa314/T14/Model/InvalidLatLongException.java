package edu.csu2017fa314.T14.Model;

public class InvalidLatLongException extends Exception {
    public InvalidLatLongException(String errorMessage){
        super(errorMessage);
    }
}
