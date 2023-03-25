package ru.wolves.bookingsite.exceptions;

public class PlaceIsNotFreeException extends Exception{
    public  PlaceIsNotFreeException(String message){
        super(message);
    }
}
