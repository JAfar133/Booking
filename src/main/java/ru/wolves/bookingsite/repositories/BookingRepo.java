package ru.wolves.bookingsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.RoomHall;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Long> {
    List<Booking> findAllByPlace(RoomHall roomHall);
    List<Booking> findAllByDate(LocalDate date);
    List<Booking> findAllByConfirmedIsFalse();
    List<Booking> findAllByConfirmedIsTrue();
}
