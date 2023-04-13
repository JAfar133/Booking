package ru.wolves.bookingsite.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class BookingRestController {

    private final BookingServiceImpl bookingService;
    private final BookingValidator bookingValidator;
    private final PersonValidator personValidator;
    private final RoomHallServiceImpl roomHallService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public BookingRestController(BookingServiceImpl bookingService, BookingValidator bookingValidator,
                                 PersonValidator personValidator, RoomHallServiceImpl roomHallService,
                                 ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
        this.personValidator = personValidator;
        this.roomHallService = roomHallService;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        objectMapper.registerModule(new JavaTimeModule());
    }

    @PostMapping
    public ResponseEntity<?> validBookingAndAddToSession(
            @RequestBody BookingDTO bookingDTO){
        Booking booking = convertToBooking(bookingDTO);
        booking.setPlace(roomHallService.findRoom(bookingDTO.getPlaceId()));
        JsonResultMessageDTO result = new JsonResultMessageDTO();

        bookingValidator.validate(booking, result);
        if(result.hasErrors()){
            return getErrorResponse(result);
        }

        result.setMsg("Booking is valid");
        result.setObject(convertToBookingDTO(booking));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/booking")
    public ResponseEntity<?> saveBookingWithPersonDetails(
            @RequestBody Map<String, Object> requestBody, HttpSession session) {
        JsonResultMessageDTO result = new JsonResultMessageDTO();

        BookingDTO bDTO = objectMapper.convertValue(
                requestBody.get("bookingDTO"), BookingDTO.class);
        Booking booking = convertToBooking(bDTO);
        Person person = convertToPerson(objectMapper.convertValue(
                requestBody.get("personDTO"), PersonDTO.class));

        bookingValidator.validate(booking, result);
        personValidator.validate(person, result);

        if(result.hasErrors()){
            return getErrorResponse(result);
        }
        booking.setPlace(roomHallService.findRoom(bDTO.getPlaceId()));
        bookingService.savePersonWithBooking(person,booking);
        session.setAttribute("person",person);
        result.setObject(convertToBookingDTO(booking));
        result.setMsg("Booking saved");

        return ResponseEntity.ok(result);
    }
    @CrossOrigin(origins = "http://localhost:8082")
    @GetMapping("/bookings")
    public ResponseEntity<?> findAllBookings(){
        System.out.println("Im here");
        List<Booking> allBooking = bookingService.findAllBooking();
        List<BookingDTO> bookings = new ArrayList<>();
        for(Booking b: allBooking) {
            bookings.add(convertToBookingDTO(b));
        }
        if(allBooking==null) {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(bookings);
    }
    @GetMapping("/unconfirmed-bookings")
    public ResponseEntity<?> findAllUnconfirmedBookings(){
        List<Booking> allBooking = bookingService.findAllUnConfirmedBooking();
        if(allBooking==null) {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(allBooking);
    }

    private ResponseEntity<?> getErrorResponse(JsonResultMessageDTO result){
        result.setMsg("Error");
        return ResponseEntity.badRequest().body(result);
    }

    private Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO,Person.class);
    }
    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person,PersonDTO.class);
    }
    private Booking convertToBooking(BookingDTO bookingDTO){
        return modelMapper.map(bookingDTO,Booking.class);
    }
    private BookingDTO convertToBookingDTO(Booking booking){
        return modelMapper.map(booking,BookingDTO.class);
    }
}
