package ru.wolves.bookingsite.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;

import java.time.LocalDate;

@Controller
@RequestMapping("/object")
public class ObjectController {

    private final BookingServiceImpl bookingService;
    private final RoomHallServiceImpl roomHallService;

    @Autowired
    public ObjectController(BookingServiceImpl bookingService, RoomHallServiceImpl roomHallService) {
        this.bookingService = bookingService;
        this.roomHallService = roomHallService;
    }

    @GetMapping("/free-date")
    public String getFreeRooms(@RequestParam(required = false)
                               @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate date,
                               @RequestParam(required = false) RoomHall roomHall,
                               Model model){
        date = date != null ? date : LocalDate.now();
        roomHall = roomHall != null ? roomHall : roomHallService.findRoom(1L);

        var bookings = bookingService.findAllBookingWithPlaceAndDate(roomHall,date);
        model.addAttribute("bookings", bookings.isEmpty() ? null : bookings);
        model.addAttribute("date",date);
        model.addAttribute("roomHall",roomHall);
        model.addAttribute("halls", roomHallService.findAllRoomHall());
        return "free-date-page";
    }
}
