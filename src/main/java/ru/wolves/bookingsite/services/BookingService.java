package ru.wolves.bookingsite.services;

import ru.wolves.bookingsite.exceptions.BookingNotFoundException;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.RoomHall;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<Booking> findAllBooking();
    Booking findBooking(Long id) throws BookingNotFoundException;
    void saveBooking(Booking booking);
    void deleteBooking(Booking booking) throws BookingNotFoundException;
    void deleteBooking(Long id) throws BookingNotFoundException;
    void deleteOldBooking();
    Booking updateBooking(Booking booking) throws BookingNotFoundException;
    List<Booking> findAllUnConfirmedBooking();
    List<Booking> findAllConfirmedBooking();
    List<Booking> findAllBookingWithPlaceAndDate(RoomHall roomHall, LocalDate date);

}
