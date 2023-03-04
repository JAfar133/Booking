package ru.wolves.bookingsite.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.servicesImpl.BookingServiceImpl;

import java.util.Date;

@Component
public class BookingValidator implements Validator {
    private final BookingServiceImpl bookingServiceImpl;

    @Autowired
    public BookingValidator(BookingServiceImpl bookingServiceImpl) {
        this.bookingServiceImpl = bookingServiceImpl;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Booking.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Booking booking = (Booking) target;

        if(booking.getDate().getYear() < new Date().getYear()){
            errors.rejectValue("date","","");
        }
        bookingServiceImpl.findAllByRoomHall(booking.getPlace()).forEach(booking1 -> {
            if (booking1.getDate() != null && booking1.getDate().equals(booking.getDate())) {
                if(booking1.getTimeStart().getTime() <= booking.getTimeStart().getTime()) {
                    if (booking1.getTimeEnd().getTime() > booking.getTimeStart().getTime()) {
                        errors.rejectValue("place","",
                                "Помещение занято в это время. Выберите другое помещение или измените время");
                    }
                } else {
                    if(booking.getTimeEnd().getTime() > booking1.getTimeStart().getTime())
                        errors.rejectValue("place","",
                                "Помещение занято в это время. Выберите другое помещение или измените время");
                }
            }
        });
    }
}
