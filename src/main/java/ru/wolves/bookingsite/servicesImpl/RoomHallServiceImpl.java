package ru.wolves.bookingsite.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.repositories.RoomHallRepo;
import ru.wolves.bookingsite.services.RoomHallService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoomHallServiceImpl implements RoomHallService {

    private final RoomHallRepo roomHallRepo;

    @Autowired
    public RoomHallServiceImpl(RoomHallRepo roomHallRepo) {
        this.roomHallRepo = roomHallRepo;
    }

    @Override
    @Cacheable(cacheNames = "booking", key = "#id")
    public RoomHall findRoom(int id) {
        Optional<RoomHall> roomHall = roomHallRepo.findById(id);
        if(roomHall.isPresent())
            return roomHall.get();
        else return null;
    }

    @Override
    public RoomHall findRoom(String name) {
        Optional<RoomHall> roomHall = roomHallRepo.findByName(name);
        if(roomHall.isPresent())
            return roomHall.get();
        else return null;
    }

    @Cacheable(cacheNames = "bookinglist")
    public List<RoomHall> findAllRoomHall(){
        return roomHallRepo.findAll();
    }

    @Override
    @Transactional
    public void saveRoom(RoomHall roomHall) {
        roomHallRepo.save(roomHall);
    }

    @Override
    @Transactional
    public RoomHall updateRoom(RoomHall roomhall) {
        return roomHallRepo.save(roomhall);
    }

    @Override
    @Transactional
    public void deleteRoomHall(int id) {
        Optional<RoomHall> roomHall = roomHallRepo.findById(id);
        if(roomHall.isPresent())
            roomHallRepo.delete(roomHall.get());
    }
}
