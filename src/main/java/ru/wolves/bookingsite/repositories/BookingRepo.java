package ru.wolves.bookingsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.RoomHall;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Integer> {
    List<Booking> findAllByPlace(RoomHall roomHall);
}
