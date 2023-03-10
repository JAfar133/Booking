package ru.wolves.bookingsite.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wolves.bookingsite.models.Booking;
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
            model.addAttribute("free_rooms",true);
            return "rooms";
        }
        List<RoomHall> rooms = roomHallService.findAllRoomHall();
        model.addAttribute("free_rooms",false);
        model.addAttribute("rooms", rooms);
        model.addAttribute(rooms);
        return "rooms";
    }
    @GetMapping("/{engName}")
    public String roomPage(@PathVariable("engName") String engName,
                           @ModelAttribute("booking") Booking booking, Model model){
        RoomHall room = roomHallService.findByEngName(engName);
        model.addAttribute("room",room);
        booking.setPlace(room);
        return "room-details";
    }
    @PostMapping("/{engName}")
    public String booking(@PathVariable("engName") String engName,
                          @Valid @ModelAttribute("booking") Booking booking,
                          BindingResult bindingResult, HttpServletRequest request, Model model){
        bookingValidator.validate(booking,bindingResult);
        HttpSession session = request.getSession();
        RoomHall room = roomHallService.findByEngName(engName);
        model.addAttribute("room",room);
        if(bindingResult.hasErrors()) {
            session.setAttribute("booking_with_error",booking);
            return "room-details";
        }
        session.setAttribute("booking_with_error",null);
        session.setAttribute("booking",booking);
        return "redirect:/booking";
    }
}
