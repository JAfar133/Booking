package ru.wolves.bookingsite.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;

import java.time.LocalDate;
import java.util.Date;

public class BookingDTO {

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "Europe/Moscow")
    @DateTimeFormat(pattern = "HH:mm")
    private Date timeStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "Europe/Moscow")
    @DateTimeFormat(pattern = "HH:mm")
    private Date timeEnd;

    private Long placeId;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }
}
