package ru.wolves.bookingsite.models.dto;

import java.util.HashSet;
import java.util.Set;

public class JsonResultMessageDTO {
    private String msg;
    private Set<CustomError> errors;
    private Object object;

    public JsonResultMessageDTO() {
        errors = new HashSet<>();
    }

    public String getMsg() {
        return msg;
    }
    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Set<CustomError> getErrors() {
        return errors;
    }
    public void addError(String code, String field, String message){
        errors.add(new CustomError(code,field,message));
    }
    public void setErrors(Set<CustomError> errors) {
        this.errors = errors;
    }
    public boolean hasErrors(){
        return !errors.isEmpty();
    }
}
