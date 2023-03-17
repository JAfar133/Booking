package ru.wolves.bookingsite.util;

import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;

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
        if(booking.getTimeStart() == null){
            reject(errors,"timeStart","Заполните поле");
        }
        if(booking.getTimeEnd() == null){
            reject(errors,"timeEnd","Заполните поле");
        }
        if(booking.getDate() == null){
            reject(errors,"date","Заполните поле");
        }
        if(booking.getTimeStart().getTime() - booking.getTimeEnd().getTime() >= 0){
            reject(errors,"timeEnd","Время окончания не может быть меньше или совпадать с началом");
            return;
        }
        placeIsNotFree(booking,errors);

    }

    private void placeIsNotFree(Booking booking, Errors errors){
        bookingServiceImpl.findAllByRoomHall(booking.getPlace()).forEach(booking1 -> {
            if (booking1.getDate().equals(booking.getDate())) {
                if(booking1.getTimeStart().getTime() <= booking.getTimeStart().getTime()) {
                    if (booking1.getTimeEnd().getTime() > booking.getTimeStart().getTime()) {
                        reject(errors,"place","Помещение занято в это время. Выберите другое помещение или измените время");
                    }
                } else {
                    if(booking.getTimeEnd().getTime() > booking1.getTimeStart().getTime())
                        reject(errors,"place","Помещение занято в это время. Выберите другое помещение или измените время");
                }
            }
        });
    }
    private void reject(Errors errors, String field, String message){
        try {
            errors.rejectValue(field,"",message);
        }catch (NotReadablePropertyException e){
            errors.reject("101","unexcepted error");
            System.out.println(e.getLocalizedMessage());
        }
    }
}
