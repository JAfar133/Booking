package ru.wolves.bookingsite.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.repositories.BookingRepo;
import ru.wolves.bookingsite.repositories.PersonRepo;
import ru.wolves.bookingsite.services.BookingService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final PersonRepo personRepo;

    @Autowired
    public BookingServiceImpl(BookingRepo bookingRepo, PersonRepo personRepo) {
        this.bookingRepo = bookingRepo;
        this.personRepo = personRepo;
    }

    public List<Booking> findAllByRoomHall(RoomHall roomHall){
        return bookingRepo.findAllByPlace(roomHall);
    }
    @Override
    public List<Booking> findAllBooking() {
        return bookingRepo.findAll();
    }

    @Cacheable(cacheNames = "bookingCache", key = "#id")
    public Booking findBooking(int id){
        Optional<Booking> booking = bookingRepo.findById(id);
        if(booking.isPresent())
            return booking.get();
        else return null;
    }

    @Transactional
    public void savePersonWithBooking(Person person, Booking booking){
        if(person.getPost().equals("Работник")) person.setCourse(-1);
        booking.setBookedAt(new Date());
        person.setBookingList(new ArrayList<>());
        bookingRepo.save(booking);
        person.getBookingList().add(booking);
        booking.setCustomer(person);
        personRepo.save(person);
    }

    @Transactional
    public void saveBooking(Booking booking){
            bookingRepo.save(booking);
    }

    @Override
    @Transactional
    public void deleteBooking(Booking booking) {
        bookingRepo.delete(booking);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "bookingCache", key="#id")
    public void deleteBooking(int id) {
        bookingRepo.delete(findBooking(id));
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "bookingCache",key = "#booking.id")
    public Booking updateBooking(Booking booking) {
        return bookingRepo.save(booking);
    }
}
