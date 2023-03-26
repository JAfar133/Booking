package ru.wolves.bookingsite.controllers.mvc;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;

@Controller
public class MainController {
    private final RoomHallServiceImpl roomHallServiceImpl;
    private static Logger log = Logger.getLogger(MainController.class);


    @Autowired
    public MainController(RoomHallServiceImpl roomHallServiceImpl) {
        this.roomHallServiceImpl = roomHallServiceImpl;

    }
    @GetMapping("/")
    public String mainPage(Model model){
        log.debug("Debug message");
        log.info("Info message");
        log.warn("Warn message");
        log.error("Error message");
        log.trace("trace message");

        model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());
        return "index";
    }
}