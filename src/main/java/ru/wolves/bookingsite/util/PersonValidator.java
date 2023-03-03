package ru.wolves.bookingsite.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.wolves.bookingsite.models.Person;

@Component
public class PersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(person.getPost().equals("Студент") && (person.getInstitute()==null || person.getInstitute().isEmpty())){
            errors.rejectValue("institute","","Поле не может быть пустым");
        }
        if(person.getPost().equals("Работник") && (person.getStructure()==null || person.getStructure().isEmpty())){
            errors.rejectValue("structure","","Поле не может быть пустым");
        }
    }
}
