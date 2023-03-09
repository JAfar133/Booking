package ru.wolves.bookingsite.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomsController {

    private final RoomHallServiceImpl roomHallService;

    @Autowired
    public RoomsController(RoomHallServiceImpl roomHallService) {
        this.roomHallService = roomHallService;
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
        model.addAttribute("room",roomHallService.findByEngName(engName));
        return "room-details";
    }
}
