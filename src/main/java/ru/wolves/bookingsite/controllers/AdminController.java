package ru.wolves.bookingsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PatchExchange;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookingServiceImpl bookingService;

    @Autowired
    public AdminController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String adminPage(@ModelAttribute("bookingEdit")Booking booking, Model model){
        model.addAttribute("bookings",bookingService.findAllUnConfirmedBooking());
        return "admin-page";
    }
    @PatchMapping("/{id}")
    public String confirmed(@PathVariable("id") Long id, Model model){
        Booking booking = bookingService.findBooking(id);
        if (booking!=null){
            booking.setConfirmed(true);
            bookingService.updateBooking(booking);
        }
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String reject(@PathVariable("id") Long id, Model model){
        bookingService.deleteBooking(id);
        return "redirect:/admin";
    }
}
