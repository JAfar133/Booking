package ru.wolves.bookingsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.repositories.RoomHallRepo;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoomHallService {

    private final RoomHallRepo roomHallRepo;

    @Autowired
    public RoomHallService(RoomHallRepo roomHallRepo) {
        this.roomHallRepo = roomHallRepo;
    }

    public List<RoomHall> findAllRoomHall(){
        return roomHallRepo.findAll();
    }
}
