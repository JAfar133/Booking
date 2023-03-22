package ru.wolves.bookingsite.models.dto;

import java.util.Objects;

public class CustomError {
    private String msg;
    private String code;
    private String field;

    public CustomError(String code, String field, String msg) {
        this.msg = msg;
        this.code = code;
        this.field = field;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomError that = (CustomError) o;
        return Objects.equals(msg, that.msg) && Objects.equals(code, that.code) && Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg, code, field);
    }
}
