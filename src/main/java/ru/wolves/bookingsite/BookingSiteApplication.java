package ru.wolves.bookingsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BookingSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingSiteApplication.class, args);
    }

}
