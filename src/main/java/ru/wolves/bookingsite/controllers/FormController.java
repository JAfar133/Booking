package ru.wolves.bookingsite.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.services.BookingService;
import ru.wolves.bookingsite.services.PersonService;
import ru.wolves.bookingsite.services.RoomHallService;
import ru.wolves.bookingsite.util.BookingValidator;
import ru.wolves.bookingsite.util.PersonValidator;

@Controller
public class FormController {
    private final PersonService personService;
    private final RoomHallService roomHallService;
    private final BookingService bookingService;
    private final BookingValidator bookingValidator;
    private final PersonValidator personValidator;
    private Booking booking;

    @Autowired
    public FormController(PersonService personService, RoomHallService roomHallService, BookingService bookingService, BookingValidator bookingValidator, PersonValidator personValidator) {
        this.personService = personService;
        this.roomHallService = roomHallService;
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
        this.personValidator = personValidator;
    }
    @GetMapping("/")
    public String placeForm(@ModelAttribute("booking") Booking booking,
                            @ModelAttribute("roomHall") RoomHall roomHall, Model model){
        model.addAttribute("halls",roomHallService.findAllRoomHall());
        this.booking = null;
        return "formControl/place_form";
    }
    @PostMapping("/")
    public String savePlace(@Valid @ModelAttribute("booking") Booking booking,
                            BindingResult bindingResult, Model model){

        bookingValidator.validate(booking,bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("halls", roomHallService.findAllRoomHall());
            return "formControl/place_form";
        }
        this.booking = booking;
        return "redirect:/booking";

    }

    @GetMapping("/booking")
    public String personForm(@ModelAttribute("person") Person person, Model model){
        if(booking == null) return "redirect:/";
        model.addAttribute("booking",booking);
        return "formControl/person_form";
    }

    @PostMapping("/booking")
    public String addBooking(@Valid @ModelAttribute("person") Person person,
                             BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors()) {
            return "formControl/person_form";
        }
        personService.savePersonWithBooking(person, booking);
        return "booking/success";
    }
}