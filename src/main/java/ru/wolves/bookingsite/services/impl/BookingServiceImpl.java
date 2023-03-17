package ru.wolves.bookingsite.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.repositories.BookingRepo;
import ru.wolves.bookingsite.repositories.PersonRepo;
import ru.wolves.bookingsite.services.BookingService;

import java.time.LocalDate;
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

    public Booking findBooking(Long id){
        Optional<Booking> booking = bookingRepo.findById(id);
        if(booking.isPresent())
            return booking.get();
        else return null;
    }

    @Transactional
    public void savePersonWithBooking(Person person, Booking booking){
        if(person.getPost().equals("Работник"))
            person.setCourse(-1);
        booking.setBookedAt(new Date());
        person.setBookingList(new ArrayList<>());
        person.setLastNameAndInitials(getLastNameInitials(person));
        personRepo.save(person);
        person.getBookingList().add(booking);
        booking.setCustomer(person);
        bookingRepo.save(booking);
    }

    private String getLastNameInitials(Person person){
        var lastName = person.getLastName();
        var firstName = person.getFirstName();
        var middleName = person.getMiddleName();
        StringBuffer fio = new StringBuffer(lastName);
        fio.append(" ")
                .append(firstName.charAt(0))
                .append(".")
                .append(middleName.charAt(0)).append(".");
        return fio.toString();
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
    public void deleteBooking(Long id) {
        Person person = findBooking(id).getCustomer();
        bookingRepo.delete(findBooking(id));
        personRepo.delete(person);
    }

    @Override
    @Transactional
    public Booking updateBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    @Override
    public List<Booking> findAllUnConfirmedBooking() {
        return bookingRepo.findAllByConfirmedIsFalse();
    }
    @Override
    public List<Booking> findAllConfirmedBooking() {
        return bookingRepo.findAllByConfirmedIsTrue();
    }

    @Override
    public List<Booking> findAllBookingWithPlaceAndDate(RoomHall roomHall, LocalDate date) {
        return bookingRepo.findAllByPlaceAndDate(Sort.by("timeStart"), roomHall, date);
    }
}
