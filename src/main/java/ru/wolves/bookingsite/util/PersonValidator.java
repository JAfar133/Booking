package ru.wolves.bookingsite.util;

import org.springframework.stereotype.Component;
import ru.wolves.bookingsite.models.Person;
import ru.wolves.bookingsite.models.dto.JsonResultMessageDTO;

import java.util.regex.Pattern;

@Component
public class PersonValidator {

    public void validate(Person person, JsonResultMessageDTO msg) {


        if(person.getPost().equals("Студент") && (person.getInstitute()==null || person.getInstitute().isEmpty())){
            msg.addError("431","institute","Поле Институт не может быть пустым");
        }
        if(person.getPost().equals("Работник") && (person.getStructure()==null || person.getStructure().isEmpty())){
            msg.addError("432","structure","Поле Структура не может быть пустым");
        }
        Pattern pattern = Pattern.compile("\\+\\d{11}");
        if(!Pattern.matches("^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$",person.getPhoneNumber())){
            msg.addError("433","phoneNumber","Номер телефона не валидный");
        }
    }
}
