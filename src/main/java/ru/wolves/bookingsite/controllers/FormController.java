package ru.wolves.bookingsite.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;
import ru.wolves.bookingsite.util.BookingValidator;
import ru.wolves.bookingsite.util.PersonValidator;

@Controller
public class FormController {
    private final RoomHallServiceImpl roomHallServiceImpl;
    private final BookingServiceImpl bookingServiceImpl;
    private final BookingValidator bookingValidator;
    private final PersonValidator personValidator;

    @Autowired
    public FormController(RoomHallServiceImpl roomHallServiceImpl, BookingServiceImpl bookingServiceImpl,
                          BookingValidator bookingValidator, PersonValidator personValidator) {
        this.roomHallServiceImpl = roomHallServiceImpl;
        this.bookingServiceImpl = bookingServiceImpl;
        this.bookingValidator = bookingValidator;
        this.personValidator = personValidator;
    }
    @GetMapping("/")
    public String placeForm(@ModelAttribute("booking") Booking booking, @ModelAttribute Person person, Model model){
        model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());
        model.addAttribute("errors", true); // Костыль TODO
        return "index";
    }
    @PostMapping("/")
    public String booking(@Valid @ModelAttribute("booking") Booking booking, BindingResult bindingResult,
                          @ModelAttribute Person person, Model model, HttpServletRequest request){

        bookingValidator.validate(booking,bindingResult);
        HttpSession session = request.getSession();
        if(bindingResult.hasErrors()) {
            session.setAttribute("booking_with_error",booking);
            model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());
            model.addAttribute("errors", true);
            return "index";
        }
        model.addAttribute("errors", false);
        model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());
        session.setAttribute("booking",booking);
        return "index";
    }

    @PostMapping("/booking")
    public String saveBooking(@Valid @ModelAttribute("person") Person person,
                              BindingResult bindingResult,
                              @ModelAttribute("booking") Booking booking1,
                              HttpServletRequest request, Model model, RedirectAttributes atrs){
        HttpSession session = request.getSession();
        Booking booking = (Booking) session.getAttribute("booking");

        personValidator.validate(person,bindingResult);
        bookingValidator.validate(booking,bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());
            return "index";
        }
        booking.setComment(booking1.getComment());
        bookingServiceImpl.savePersonWithBooking(person, booking);

        session.setAttribute("person",person);
        atrs.addFlashAttribute("isSuccess",true);
        return "redirect:"+request.getHeader("referer");
    }
}