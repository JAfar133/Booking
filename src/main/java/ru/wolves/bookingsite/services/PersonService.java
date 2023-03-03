package ru.wolves.bookingsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.repositories.BookingRepo;
import ru.wolves.bookingsite.repositories.PersonRepo;

import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepo personRepo;
    private final BookingRepo bookingRepo;

    @Autowired
    public PersonService(PersonRepo personRepo, BookingRepo bookingRepo) {
        this.personRepo = personRepo;
        this.bookingRepo = bookingRepo;
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


}
