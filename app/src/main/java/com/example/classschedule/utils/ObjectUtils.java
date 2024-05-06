package com.example.classschedule.utils;

import java.lang.reflect.Field;

@SuppressWarnings("unchecked")
public class ObjectUtils {
    public static <T> T copyObj(T obj) {
        try {
            T copy = (T) obj.getClass().newInstance();
            for(Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(copy, field.get(obj));
            }
            return copy;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean allNonEmpty(Object obj) {
        try {
            for(Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(obj) == null || field.getType().equals(String.class) && "".equals(field.get(obj))) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean allEmpty(Object obj) {
        try {
            for(Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(obj) != null && field.getType().equals(String.class) && !"".equals(field.get(obj))) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static void resetAllStringNullToEmpty(Object obj) {
        try {
            for(Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.getType().equals(String.class) && field.get(obj) == null) {
                    field.set(obj, "");
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
