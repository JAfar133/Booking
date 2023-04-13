package ru.wolves.bookingsite.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.wolves.bookingsite.models.Booking;
import ru.wolves.bookingsite.models.dto.JsonResultMessageDTO;
import ru.wolves.bookingsite.services.impl.BookingServiceImpl;

@Component
public class BookingValidator {
    private final BookingServiceImpl bookingServiceImpl;
    @Autowired
    public BookingValidator(BookingServiceImpl bookingServiceImpl) {
        this.bookingServiceImpl = bookingServiceImpl;
    }


    public void validate(Booking booking, JsonResultMessageDTO msg) {
        if(booking.getTimeStart() == null){
            msg.addError("420","timeStart","Поле timeStart не заполнено");
        }
        if(booking.getTimeEnd() == null){
            msg.addError("421","timeEnd","Поле timeEnd не заполнено");
        }
        if(booking.getDate() == null){
            msg.addError("422","date","Поле date не заполнено");
        }
        if(booking.getTimeStart().getTime() - booking.getTimeEnd().getTime() >= 0){
            msg.addError("423","timeEnd","Время окончания совпадает или меньше начала");
            return;
        }
        placeIsNotFree(booking,msg);

    }

    private void placeIsNotFree(Booking booking, JsonResultMessageDTO msg){
        bookingServiceImpl.findAllByRoomHall(booking.getPlace()).forEach(booking1 -> {
            if (booking1.getDate().equals(booking.getDate()) && !booking.equals(booking1)) {
                if(booking1.getTimeStart().before(booking.getTimeStart())) {
                    if (booking1.getTimeEnd().after(booking.getTimeStart())) {
                        msg.addError("424","place","Помещение занято в это время");
                    }
                } else {
                    if(booking.getTimeEnd().after(booking1.getTimeStart())  )
                        msg.addError("424","place","Помещение занято в это время");

                }
            }
        });
    }
}
