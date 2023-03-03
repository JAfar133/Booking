package ru.wolves.bookingsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wolves.bookingsite.models.Person;


public interface PersonRepo extends JpaRepository<Person, Integer> {
}
