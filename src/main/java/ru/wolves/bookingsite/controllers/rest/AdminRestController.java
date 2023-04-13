package ru.wolves.bookingsite.controllers.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wolves.bookingsite.exceptions.BookingNotFoundException;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.dto.BookingDTO;
import ru.wolves.bookingsite.models.dto.JsonResultMessageDTO;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;
import ru.wolves.bookingsite.util.BookingValidator;

@RestController
@RequestMapping("/admin/api")
public class AdminRestController {

    private final BookingServiceImpl bookingService;
    private final BookingValidator bookingValidator;

    @Autowired
    public AdminRestController(BookingServiceImpl bookingService, BookingValidator bookingValidator) {
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
    }

    @PatchMapping("/booking/{id}")
    public ResponseEntity<?> confirm(@PathVariable("id") Long id) throws BookingNotFoundException {
        JsonResultMessageDTO result = new JsonResultMessageDTO();

        Booking booking = bookingService.findBooking(id);
        booking.setConfirmed(true);
        bookingService.updateBooking(booking);
        result.setMsg("Successfully confirmed booking with id " + id);
        result.setObject(convertToBookingDTO(booking));
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/booking/{id}")
    public ResponseEntity<?> reject(@PathVariable("id") Long id) throws BookingNotFoundException {
        JsonResultMessageDTO result = new JsonResultMessageDTO();

        bookingService.deleteBooking(id);
        result.setMsg("Successfully deleted booking with id " + id);
        return ResponseEntity.ok(result);

    }

    @PatchMapping("/change-date/{id}")
    public ResponseEntity<?> updateDate(@PathVariable("id") Long id,
                             @RequestBody BookingDTO bookingDTO, Model model) throws BookingNotFoundException {
        JsonResultMessageDTO result = new JsonResultMessageDTO();
        Booking booking = bookingService.findBooking(id);
        Booking validBooking = convertToBooking(bookingDTO);
        validBooking.setPlace(booking.getPlace());
        bookingValidator.validate(validBooking, result);
        if(result.hasErrors()){
            result.setMsg("Error");
            return ResponseEntity.badRequest().body(result);
        }
        booking.setDate(bookingDTO.getDate());
        booking.setTimeStart(bookingDTO.getTimeStart());
        booking.setTimeEnd(bookingDTO.getTimeEnd());
        booking.setConfirmed(true);
        bookingService.updateBooking(booking);

        result.setMsg("Successfully updated booking with id " + id);
        result.setObject(convertToBookingDTO(booking));
        model.addAttribute("bookings",bookingService.findAllUnConfirmedBooking());
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler
    private ResponseEntity<?> handle(BookingNotFoundException e){
        JsonResultMessageDTO result = new JsonResultMessageDTO();
        result.setMsg("Error");
        result.addError("","id",e.getMessage());
        return ResponseEntity.badRequest().body(result);
    }
    private Booking convertToBooking(BookingDTO bookingDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(bookingDTO,Booking.class);
    }
    private BookingDTO convertToBookingDTO(Booking booking){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(booking,BookingDTO.class);
    }


}
