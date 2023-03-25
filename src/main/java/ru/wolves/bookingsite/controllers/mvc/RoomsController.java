package ru.wolves.bookingsite.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;
import ru.wolves.bookingsite.util.BookingValidator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomsController {

    private final RoomHallServiceImpl roomHallService;
    private final BookingValidator bookingValidator;

    @Autowired
    public RoomsController(RoomHallServiceImpl roomHallService, BookingValidator bookingValidator) {
        this.roomHallService = roomHallService;
        this.bookingValidator = bookingValidator;
    }

    @GetMapping
    public String roomsPage(@RequestParam(required = false)
                                @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate date,
                            @RequestParam(required = false)
                                @DateTimeFormat(pattern = "HH:mm") Date timeStart,
                            @RequestParam(required = false)
                                @DateTimeFormat(pattern = "HH:mm") Date timeEnd,
                            Model model){
        if (date!=null && timeStart!=null && timeEnd!=null){
            Booking booking = new Booking(date,timeStart,timeEnd);
            model.addAttribute("rooms", roomHallService.findFreeRooms(booking));
            model.addAttribute("free_rooms",true);
            model.addAttribute("booking",booking);
            return "rooms";
        }
        List<RoomHall> rooms = roomHallService.findAllRoomHall();
        model.addAttribute("free_rooms",false);
        model.addAttribute("rooms", rooms);
        model.addAttribute(rooms);
        return "rooms";
    }
    @GetMapping("/{engName}")
    public String roomDetailsPage(@PathVariable("engName") String engName, Model model){
        RoomHall room = roomHallService.findByEngName(engName);
        model.addAttribute("room",room);
        return "room-details";
    }
}
