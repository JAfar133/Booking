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
    public ResponseEntity<?> confirmed(@PathVariable("id") Long id){
        JsonResultMessageDTO result = new JsonResultMessageDTO();
        try {
            Booking booking = bookingService.findBooking(id);
            booking.setConfirmed(true);
            bookingService.updateBooking(booking);
            result.setMsg("Success");
            return ResponseEntity.ok(result);
        }catch (BookingNotFoundException e){
            return exceptionResponse(result,e);
        }
    }
    @DeleteMapping("/booking/{id}")
    public ResponseEntity<?> reject(@PathVariable("id") Long id){
        JsonResultMessageDTO result = new JsonResultMessageDTO();
        try {
            bookingService.deleteBooking(id);
            result.setMsg("Success");
            return ResponseEntity.ok(result);
        } catch (BookingNotFoundException e) {
            return exceptionResponse(result,e);
        }
    }

    @PatchMapping("/change-date/{id}")
    public ResponseEntity<?> updateDate(@PathVariable("id") Long id,
                             @RequestBody BookingDTO booking, Model model){
        JsonResultMessageDTO result = new JsonResultMessageDTO();
        Booking booking1;
        try {
            booking1 = bookingService.findBooking(id);
        } catch (BookingNotFoundException e) {
            return exceptionResponse(result,e);
        }
        bookingValidator.validate(convertToBooking(booking), result);
        if(result.hasErrors()){
            result.setMsg("Error");
            return ResponseEntity.badRequest().body(result);
        }
        booking1.setDate(booking.getDate());
        booking1.setTimeStart(booking.getTimeStart());
        booking1.setTimeEnd(booking.getTimeEnd());

        booking1.setConfirmed(true);
        bookingService.updateBooking(booking1);

        result.setMsg("Success");
        model.addAttribute("bookings",bookingService.findAllUnConfirmedBooking());
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<?> exceptionResponse(JsonResultMessageDTO result,
                                                BookingNotFoundException e){
        result.setMsg("Error");
        result.addError("","id",e.getMessage());
        return ResponseEntity.badRequest().body(result);
    }
    private Booking convertToBooking(BookingDTO bookingDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(bookingDTO,Booking.class);
    }

}
