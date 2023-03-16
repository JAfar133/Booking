package ru.wolves.bookingsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;
import ru.wolves.bookingsite.util.BookingValidator;

import java.time.LocalDate;
import java.util.Date;

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
    @PatchMapping("/{id}")
    public String confirmed(@PathVariable("id") Long id){
        Booking booking = bookingService.findBooking(id);
        if (booking!=null){
            booking.setConfirmed(true);
            bookingService.updateBooking(booking);
        }
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String reject(@PathVariable("id") Long id){
        bookingService.deleteBooking(id);
        return "redirect:/admin";
    }
    @PatchMapping("/change-date/{id}")
    public String updateDate(@PathVariable("id") Long id,
                             @ModelAttribute Booking booking,
                             BindingResult bindingResult, Model model){
        Booking booking1 = bookingService.findBooking(id);
        if (booking!=null){
            booking1.setDate(booking.getDate());
            booking1.setTimeStart(booking.getTimeStart());
            booking1.setTimeEnd(booking.getTimeEnd());
        }
        bookingValidator.validate(booking1, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("hasErrors",true);
            model.addAttribute("booking_error_id",id);
            model.addAttribute("bookings",bookingService.findAllUnConfirmedBooking());
            return "admin-page";
        }
        booking1.setConfirmed(true);
        bookingService.updateBooking(booking1);

        return "redirect:/admin";
    }
}
