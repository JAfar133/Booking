package ru.wolves.bookingsite.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.*;
import ru.wolves.bookingsite.models.Booking;

import java.beans.PropertyEditor;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookingValidatorTest {

    @Autowired
    private BookingValidator bookingValidator;
    @Test
    void validate() {
        List<String> errors = new ArrayList<>();
        var booking = new Booking();
        var timeStart = new Date(0,0,0,13,0);
        var timeEnd = new Date(0,0,0,11,0);
        booking.setTimeStart(timeStart);
        booking.setTimeEnd(timeEnd);
        booking.setDate(LocalDate.now());
        timeEndMoreThenTimeStart(booking,errors);
        Assert.assertEquals(errors.size()==0,false);
    }


    public void timeEndMoreThenTimeStart(Booking booking, List<String> errors) {
        if(booking.getTimeStart().getTime() - booking.getTimeEnd().getTime() >= 0){
            errors.add("have error");
        }
    }

    private void placeIsNotFree(Booking booking, Booking booking1,List<String> errors){
            if (booking1.isConfirmed() && booking1.getDate().equals(booking.getDate())) {
                if(booking1.getTimeStart().getTime() <= booking.getTimeStart().getTime()) {
                    if (booking1.getTimeEnd().getTime() > booking.getTimeStart().getTime()) {
                        errors.add("placeIsNotFree");
                    }
                } else {
                    if(booking.getTimeEnd().getTime() > booking1.getTimeStart().getTime())
                        errors.add("placeIsNotFree");
                }
            }
        }
}
