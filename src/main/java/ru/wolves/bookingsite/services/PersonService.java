package ru.wolves.bookingsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.repositories.PersonRepo;

import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepo personRepo;

    @Autowired
    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Transactional
    public void savePerson(Person person, Booking booking){
        person.setBookingList(new ArrayList<>());
        person.getBookingList().add(booking);
        personRepo.save(person);
        booking.setBookedAt(new Date());
        booking.setCustomer(person);
    }


}
