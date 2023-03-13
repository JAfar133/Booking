package ru.wolves.bookingsite.services;

import ru.wolves.bookingsite.models.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> findAllBooking();
    Booking findBooking(Long id);
    void saveBooking(Booking booking);
    void deleteBooking(Booking booking);
    void deleteBooking(Long id);
    Booking updateBooking(Booking booking);
    List<Booking> findAllUnConfirmedBooking();
    List<Booking> findAllConfirmedBooking();

}
