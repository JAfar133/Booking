package ru.wolves.bookingsite.exceptions;

public class BookingNotFoundException extends Exception{
    public BookingNotFoundException(String message) {
        super(message);
    }
}
