package ru.wolves.bookingsite.controllers.mvc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.wolves.bookingsite.services.impl.RoomHallServiceImpl;

@Controller
public class FormController {
    private final RoomHallServiceImpl roomHallServiceImpl;


    @Autowired
    public FormController(RoomHallServiceImpl roomHallServiceImpl) {
        this.roomHallServiceImpl = roomHallServiceImpl;

    }
    @GetMapping("/")
    public String placeForm(Model model){
        model.addAttribute("halls", roomHallServiceImpl.findAllRoomHall());
        return "index";
    }
}