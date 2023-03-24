package ru.wolves.bookingsite.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.dto.JsonResultMessageDTO;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.dto.BookingDTO;
import ru.wolves.bookingsite.models.dto.PersonDTO;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;
import ru.wolves.bookingsite.util.BookingValidator;
import ru.wolves.bookingsite.util.PersonValidator;

@RestController
@RequestMapping("/api")
public class BookingRestController {

    private final BookingServiceImpl bookingService;
    private final BookingValidator bookingValidator;
    private final PersonValidator personValidator;
    private final RoomHallServiceImpl roomHallService;

    @Autowired
    public BookingRestController(BookingServiceImpl bookingService, BookingValidator bookingValidator, PersonValidator personValidator, RoomHallServiceImpl roomHallService) {
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
        this.personValidator = personValidator;
        this.roomHallService = roomHallService;
    }

    @PostMapping
    public ResponseEntity<?> validBookingAndAddToSession(
            @Valid @RequestBody BookingDTO bookingDTO, Errors errors, HttpServletRequest request){
        Booking booking = convertToBooking(bookingDTO);
        booking.setPlace(roomHallService.findRoom(bookingDTO.getPlaceId()));
        booking.setId(null);
        HttpSession session = request.getSession();
        JsonResultMessageDTO result = new JsonResultMessageDTO();

        bookingValidator.validate(booking, result);
        if(errors.hasErrors() || result.hasErrors()){
            return getErrorResponse(result, errors);
        }


        session.setAttribute("booking",booking);
        result.setMsg("Success");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/booking")
    public ResponseEntity<?> saveBookingWithPersonDetails(
            @Valid @RequestBody PersonDTO personDTO, Errors errors, HttpServletRequest request){
        Person person = convertToPerson(personDTO);
        JsonResultMessageDTO result = new JsonResultMessageDTO();
        HttpSession session = request.getSession();
        Booking booking = (Booking) session.getAttribute("booking");

        if(booking!=null) {
            bookingValidator.validate(booking, result);
            personValidator.validate(person, result);
        }
        if(errors.hasErrors() || result.hasErrors()){
            return getErrorResponse(result, errors);
        }

        booking.setComment(personDTO.getComment());
        bookingService.savePersonWithBooking(person,booking);

        session.removeAttribute("booking");
        result.setMsg("Success");
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<?> getErrorResponse(JsonResultMessageDTO result, Errors errors){
        result.setMsg("Error");
        errors.getAllErrors().stream()
                .forEach(x-> result
                        .addError(
                                x.getCode(),
                                errors.getFieldError().getField(),
                                x.getDefaultMessage()));
        return ResponseEntity.badRequest().body(result);
    }
    private Person convertToPerson(PersonDTO personDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(personDTO,Person.class);
    }
    private Booking convertToBooking(BookingDTO bookingDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(bookingDTO,Booking.class);
    }
}
