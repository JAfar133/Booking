package ru.wolves.bookingsite.services;

import ru.wolves.bookingsite.models.Person;

import java.util.List;

public interface PersonService {
    Person findPerson(Long id);
    List<Person> findAll();
    Person updatePerson(Person person);
    void savePerson(Person person);
    void deletePerson(Long id);
}
