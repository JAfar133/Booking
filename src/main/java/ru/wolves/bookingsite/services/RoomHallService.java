package ru.wolves.bookingsite.services;

import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.RoomHall;

import java.util.List;

public interface RoomHallService {
    RoomHall findRoom(int id);
    RoomHall findRoom(String name);
    List<RoomHall> findAllRoomHall();
    void saveRoom(RoomHall roomHall);

    RoomHall updateRoom(RoomHall roomhall);

    void deleteRoomHall(int id);
    List<RoomHall> findFreeRooms(Booking booking);
    RoomHall findByEngName(String engName);

}
