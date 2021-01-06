package com.example.sunddenfix.retrofit.utils;

public class UncheckedUtil {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
