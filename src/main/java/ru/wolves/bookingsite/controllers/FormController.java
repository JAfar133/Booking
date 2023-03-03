package ru.wolves.bookingsite.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.services.BookingService;
import ru.wolves.bookingsite.services.PersonService;
import ru.wolves.bookingsite.services.RoomHallService;
import ru.wolves.bookingsite.util.BookingValidator;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class FormController {
    private final PersonService personService;
    private final RoomHallService roomHallService;
    private final BookingService bookingService;
    private final BookingValidator bookingValidator;

    @Autowired
    public FormController(PersonService personService, RoomHallService roomHallService, BookingService bookingService, BookingValidator bookingValidator) {
        this.personService = personService;
        this.roomHallService = roomHallService;
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
    }
    @GetMapping("/")
    public String placeForm(@ModelAttribute("booking") Booking booking,
                            @ModelAttribute("roomHall") RoomHall roomHall, Model model){
        model.addAttribute("halls",roomHallService.findAllRoomHall());
        return "formControl/place_form";
    }
    @PostMapping("/")
    public String savePlace(@Valid @ModelAttribute("booking") Booking booking,
                            BindingResult bindingResult, Model model,
                            RedirectAttributes redirectAttrs){

        bookingValidator.validate(booking,bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("halls",roomHallService.findAllRoomHall());
            return "formControl/place_form";
        }

        bookingService.saveBooking(booking);
        redirectAttrs.addFlashAttribute("booking",booking);
        return "redirect:/booking";

    }

    @GetMapping("/booking")
    public String personForm(@ModelAttribute("person") Person person,
                             @ModelAttribute("booking") Booking booking,
                             Model model){
        return "formControl/person_form";
    }

    @PostMapping("/booking/{booking_id}")
    public String addBooking(@PathVariable("booking_id") int booking_id,
                             @Valid @ModelAttribute("person") Person person,
                             BindingResult bindingResult, Model model){
        Booking booking = bookingService.findBooking(booking_id).get();
        personService.savePerson(person, booking);
        return "booking/success";
    }
}
