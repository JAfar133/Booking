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
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;
import ru.wolves.bookingsite.services.impl.PersonServiceImpl;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;
import ru.wolves.bookingsite.util.BookingValidator;
import ru.wolves.bookingsite.util.PersonValidator;

@Controller
public class FormController {
    private final PersonServiceImpl personServiceImpl;
    private final RoomHallServiceImpl roomHallServiceImpl;
    private final BookingServiceImpl bookingServiceImpl;
    private final BookingValidator bookingValidator;
    private final PersonValidator personValidator;

    @Autowired
    public FormController(PersonServiceImpl personServiceImpl, RoomHallServiceImpl roomHallServiceImpl,
                          BookingServiceImpl bookingServiceImpl, BookingValidator bookingValidator,
                          PersonValidator personValidator) {
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

        return "index";
    }
    @PostMapping("/")
    public String savePlace(@Valid @ModelAttribute("booking") Booking booking,
                            BindingResult bindingResult, Model model, HttpServletRequest request){

        bookingValidator.validate(booking,bindingResult);
        HttpSession session = request.getSession();
        if(bindingResult.hasErrors()) {
            session.setAttribute("booking_with_error",booking);
            model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());
            return "index";
        }
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
        return "person-details";
    }

    @PostMapping("/booking")
    public String addBooking(@Valid @ModelAttribute("person") Person person,
                             @ModelAttribute("booking") Booking booking1,
                             BindingResult bindingResult, HttpServletRequest request){
        HttpSession session = request.getSession();
        Booking booking = (Booking) session.getAttribute("booking");

        personValidator.validate(person,bindingResult);
        bookingValidator.validate(booking,bindingResult);
        if(bindingResult.hasErrors()) {
            return "person-details";
        }
        booking.setComment(booking1.getComment());
        bookingServiceImpl.savePersonWithBooking(person, booking);

        session.setAttribute("person",person);
        session.setAttribute("booking",null);
        return "booking/success";
    }
}