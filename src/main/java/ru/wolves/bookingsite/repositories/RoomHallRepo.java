package ru.wolves.bookingsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;

import java.util.List;

public interface RoomHallRepo extends JpaRepository<RoomHall,Integer> {

}
