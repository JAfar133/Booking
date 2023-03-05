package ru.wolves.bookingsite.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.servicesImpl.BookingServiceImpl;
import ru.wolves.bookingsite.servicesImpl.PersonServiceImpl;
import ru.wolves.bookingsite.servicesImpl.RoomHallServiceImpl;
import ru.wolves.bookingsite.util.BookingValidator;
import ru.wolves.bookingsite.util.PersonValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class FormController {
    private final PersonServiceImpl personServiceImpl;
    private final RoomHallServiceImpl roomHallServiceImpl;
    private final BookingServiceImpl bookingServiceImpl;
    private final BookingValidator bookingValidator;
    private final PersonValidator personValidator;

    @Autowired
    public FormController(PersonServiceImpl personServiceImpl, RoomHallServiceImpl roomHallServiceImpl, BookingServiceImpl bookingServiceImpl, BookingValidator bookingValidator, PersonValidator personValidator) {
        this.personServiceImpl = personServiceImpl;
        this.roomHallServiceImpl = roomHallServiceImpl;
        this.bookingServiceImpl = bookingServiceImpl;
        this.bookingValidator = bookingValidator;
        this.personValidator = personValidator;
    }
    @GetMapping("/")
    public String placeForm(@ModelAttribute("booking") Booking booking,
                            @ModelAttribute("roomHall") RoomHall roomHall, Model model){
        model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());

        return "formControl/place_form";
    }
    @PostMapping("/")
    public String savePlace(@Valid @ModelAttribute("booking") Booking booking,
                            BindingResult bindingResult, Model model, HttpServletRequest request){

        bookingValidator.validate(booking,bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());
            return "formControl/place_form";
        }
        HttpSession session = request.getSession();
        session.setAttribute("booking",booking);
        return "redirect:/booking";

    }

    @GetMapping("/booking")
    public String personForm(@ModelAttribute("person") Person person, Model model
            , HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.isNew()) {
            session.invalidate();
            return "redirect:/";
        }
        Person personFromSession = (Person) session.getAttribute("person");
        if(personFromSession!=null){
            model.addAttribute("person",personFromSession);
        }
        Booking booking = (Booking) session.getAttribute("booking");
        if(booking == null) return "redirect:/";

        model.addAttribute("booking",booking);
        return "formControl/person_form";
    }

    @PostMapping("/booking")
    public String addBooking(@Valid @ModelAttribute("person") Person person,
                             BindingResult bindingResult, HttpServletRequest request){
        HttpSession session = request.getSession();
        Booking booking = (Booking) session.getAttribute("booking");
        personValidator.validate(person,bindingResult);
        bookingValidator.validate(booking,bindingResult);
        if(bindingResult.hasErrors()) {
            return "formControl/person_form";
        }
        bookingServiceImpl.savePersonWithBooking(person, booking);
        session.setAttribute("person",person);
        return "booking/success";
    }
}