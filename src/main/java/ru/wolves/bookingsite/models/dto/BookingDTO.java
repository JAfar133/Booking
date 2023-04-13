package ru.wolves.bookingsite.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.RoomHall;

import java.time.LocalDate;
import java.util.Date;

public class BookingDTO {

    private Long id;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "Europe/Moscow")
    @DateTimeFormat(pattern = "HH:mm")
    private Date timeStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "Europe/Moscow")
    @DateTimeFormat(pattern = "HH:mm")
    private Date timeEnd;

    private Long placeId;
    @JsonDeserialize(using = RoomHallDeserializer.class)
    private RoomHall place;
    private PersonDTO customer;
    private String comment;
    private boolean confirmed;



    public BookingDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PersonDTO getCustomer() {
        return customer;
    }

    public void setCustomer(PersonDTO customer) {
        this.customer = customer;
    }

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date bookedAt;

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public RoomHall getPlace() {
        return place;
    }

    public void setPlace(RoomHall place) {
        this.place = place;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Date getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Date bookedAt) {
        this.bookedAt = bookedAt;
    }
}
