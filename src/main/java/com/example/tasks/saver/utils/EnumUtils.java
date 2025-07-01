package com.example.tasks.saver.utils;

public class EnumUtils {
    private EnumUtils(){}
    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if( c != null && string != null ) {
                return Enum.valueOf(c, string.trim().toUpperCase());
        }
        return null;
    }
}
