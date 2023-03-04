package ru.wolves.bookingsite.services;

import ru.wolves.bookingsite.models.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> findAllBooking();
    Booking findBooking(int id);
    void saveBooking(Booking booking);
    void deleteBooking(Booking booking);
    void deleteBooking(int id);
    Booking updateBooking(Booking booking);

}
