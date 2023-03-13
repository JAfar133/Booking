package ru.wolves.bookingsite;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import ru.wolves.bookingsite.controllers.FormController;
import ru.wolves.bookingsite.controllers.RoomsController;
import ru.wolves.bookingsite.models.Booking;

import java.time.LocalDate;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
class BookingRepoSiteApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoomsController roomsController;

    @Autowired
    private FormController formController;

    @Test
    void contextMainLoads() throws Exception{
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Бронирование помещений")));
    }
    @Test
    void contextRoomsLoads() throws Exception{
        this.mockMvc.perform(get("/rooms"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2>Наши помещения</h2>")));
    }
    @Test
    void redirectTest() throws Exception {
        this.mockMvc.perform(get("/booking"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void bookingTest() throws Exception{
        var session = new MockHttpSession();
        session.setAttribute("booking",new Booking(
                LocalDate.now(), new Date(), new Date()));
        this.mockMvc.perform(get("/booking")
                .session(session))
                .andExpect(content().string(containsString("Пожалуйста предоставьте следующую информацию")))
                .andDo(print());
    }

}
