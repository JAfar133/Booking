package ru.wolves.bookingsite.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.repositories.BookingRepo;
import ru.wolves.bookingsite.repositories.PersonRepo;
import ru.wolves.bookingsite.services.PersonService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {
    private final PersonRepo personRepo;
    private final BookingRepo bookingRepo;

    @Autowired
    public PersonServiceImpl(PersonRepo personRepo, BookingRepo bookingRepo) {
        this.personRepo = personRepo;
        this.bookingRepo = bookingRepo;
    }

    @Cacheable(cacheNames = "person", key="#id")
    public Person findPerson(int id){
        Optional<Person> person = personRepo.findById(id);
        if(person.isPresent())
            return person.get();
        else return null;
    }

    @Override
    public List<Person> findAll() {
        return personRepo.findAll();
    }

    @Override
    @Transactional
    public Person updatePerson(Person person) {
            return personRepo.save(person);
    }

    @Override
    @Transactional
    public void savePerson(Person person) {
        if(personRepo.findById(person.getId()).isPresent())
            personRepo.save(person);
    }

    @Override
    @Transactional
    public void deletePerson(int id) {
        personRepo.delete(findPerson(id));
    }
}
