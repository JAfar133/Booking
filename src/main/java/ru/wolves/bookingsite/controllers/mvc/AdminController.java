package ru.wolves.bookingsite.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;
import ru.wolves.bookingsite.util.BookingValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookingServiceImpl bookingService;
    private final BookingValidator bookingValidator;

    @Autowired
    public AdminController(BookingServiceImpl bookingService, BookingValidator bookingValidator) {
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
    }
    @GetMapping
    public String adminPage(@ModelAttribute Booking booking, Model model){
        model.addAttribute("bookings",bookingService.findAllUnConfirmedBooking());
        model.addAttribute("hasError",false);
        return "admin-page";
    }
}
