package ru.wolves.bookingsite.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.wolves.bookingsite.Exceptions.TimeAlreadyBooked;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.services.BookingService;

@Component
public class BookingValidator implements Validator {
    private final BookingService bookingService;

    @Autowired
    public BookingValidator(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Booking.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Booking booking = (Booking) target;

        if(booking.getDate()==null)
            errors.rejectValue("date","","Вы не выбрали дату");
        if(booking.getTimeStart()==null)
            errors.rejectValue("timeStart","","Выберите время начала бронирования");
        if(booking.getTimeEnd()==null)
            errors.rejectValue("timeEnd","","Выберите время окончания бронирования");

        bookingService.findAllByRoomHall(booking.getPlace()).forEach(booking1 -> {
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
