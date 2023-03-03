package ru.wolves.bookingsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wolves.bookingsite.Exceptions.TimeAlreadyBooked;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.repositories.BookingRepo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepo bookingRepo;

    @Autowired
    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public List<Booking> findAllByRoomHall(RoomHall roomHall){
        return bookingRepo.findAllByPlace(roomHall);
    }
    public List<Booking> findAll(){
        return bookingRepo.findAll();
    }

    public Optional<Booking> findBooking(int id){
        return bookingRepo.findById(id);
    }

    @Transactional
    public void saveBooking(Booking booking){
            bookingRepo.save(booking);
    }
}
