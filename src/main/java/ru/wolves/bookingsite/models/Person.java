package ru.wolves.bookingsite.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле не может быть пустным")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Поле не может быть пустным")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Поле не может быть пустным")
    @Column(name = "middle_name")
    private String middleName;

    @NotEmpty
    @Column(name = "post")
    private String post;

    @Column(name = "phone")
    @Pattern(regexp = "\\+\\d{11}", message = "Введите существующий номер")
    private String phoneNumber;
    @Column(name = "institute")
    private String institute;
    @Column(name = "course")
    private int course;
    @Column(name = "structure")
    private String structure;

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookingList;

    public Person(String firstName, String lastName, String middleName, String post, String phoneNumber, String institute, int course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.post = post;
        this.phoneNumber = phoneNumber;
        this.institute = institute;
        this.course = course;
    }

    public Person(String firstName, String lastName, String middleName, String post, String phoneNumber, String structure) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.post = post;
        this.phoneNumber = phoneNumber;
        this.structure = structure;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(middleName, person.middleName) && Objects.equals(phoneNumber, person.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, middleName, phoneNumber);
    }
}
