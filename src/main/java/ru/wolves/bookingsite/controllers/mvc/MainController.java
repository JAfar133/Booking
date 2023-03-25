package ru.wolves.bookingsite.controllers.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;

@Controller
public class MainController {
    private final RoomHallServiceImpl roomHallServiceImpl;
    private static Logger log = LoggerFactory.getLogger(MainController.class);


    @Autowired
    public MainController(RoomHallServiceImpl roomHallServiceImpl) {
        this.roomHallServiceImpl = roomHallServiceImpl;

    }
    @GetMapping("/")
    public String mainPage(Model model){
        model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());
        return "index";
    }
}