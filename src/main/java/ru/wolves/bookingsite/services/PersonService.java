package ru.wolves.bookingsite.services;

import ru.wolves.bookingsite.models.Person;

import java.util.List;

public interface PersonService {
    Person findPerson(int id);
    List<Person> findAll();
    Person updatePerson(Person person);
    void savePerson(Person person);
    void deletePerson(int id);
}
