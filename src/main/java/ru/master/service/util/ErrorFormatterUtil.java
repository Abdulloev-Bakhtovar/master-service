package ru.master.service.util;

public class ErrorFormatterUtil {

    public static String format(String template, Object... args) {
        return String.format(template, args);
    }
}
