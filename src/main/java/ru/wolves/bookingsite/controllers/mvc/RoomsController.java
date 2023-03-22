package ru.wolves.bookingsite.controllers.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;
import ru.wolves.bookingsite.util.BookingValidator;

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

    // TODO
    @GetMapping
    public String roomsPage(@RequestParam(value = "free_rooms",defaultValue = "false", required = false) Boolean b,
                            Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Booking booking = (Booking) session.getAttribute("booking_with_error");
        if (b == true){
            if(booking==null){
                return "redirect:/rooms";
            }
            model.addAttribute("rooms", roomHallService.findFreeRooms(booking));
            model.addAttribute("booking_with_error",booking);
            return "rooms";
        }
        List<RoomHall> rooms = roomHallService.findAllRoomHall();
        model.addAttribute("free_rooms",false);
        model.addAttribute("rooms", rooms);
        model.addAttribute(rooms);
        return "rooms";
    }
    @GetMapping("/{engName}")
    public String roomPage(@PathVariable("engName") String engName, Model model){
        RoomHall room = roomHallService.findByEngName(engName);
        model.addAttribute("room",room);
        return "room-details";
    }
}
