package com.debanjan.Spring_JPA.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Logger {

    public static <T> void logEntity(T obj) {
        if (obj == null) {
            log("Entity is null");
            return;
        }

        Class<?> clazz = obj.getClass();

        log("========================================");
        log("Logging entity of type: " + clazz.getName());
        log("----------------------------------------");

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            // Skip static fields
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String fieldName = field.getName();
            Object value = null;

            try {
                if (Modifier.isPrivate(field.getModifiers())) {
                    // Try to find a getter for private fields
                    value = getViaGetter(clazz, obj, field);
                    if (value == null) {
                        // Fallback to direct field access if no getter or null
                        field.setAccessible(true);
                        value = field.get(obj);
                    }
                } else {
                    // Non-private: access directly
                    field.setAccessible(true);
                    value = field.get(obj);
                }
            } catch (Exception e) {
                value = "[Error reading: " + e.getClass().getSimpleName() + " - " + e.getMessage() + "]";
            }

            log(fieldName + " = " + value);
        }

        log("========================================");
    }

    private static Object getViaGetter(Class<?> clazz, Object obj, Field field) {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();

        String capitalized = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

        String getterName1 = "get" + capitalized;
        String getterName2 = "is" + capitalized; // for boolean fields

        try {
            // Try getXxx()
            Method getter = clazz.getMethod(getterName1);
            return getter.invoke(obj);
        } catch (NoSuchMethodException ignored) {
            // Try isXxx() for booleans
            if (fieldType == boolean.class || fieldType == Boolean.class) {
                try {
                    Method getter = clazz.getMethod(getterName2);
                    return getter.invoke(obj);
                } catch (Exception ignored2) {
                    // fall through
                }
            }
        } catch (Exception e) {
            // Any other reflection problem
            return "[Getter error: " + e.getClass().getSimpleName() + "]";
        }

        return null; // No suitable getter found
    }

    public static void log(String message) {
        System.out.println("##LOG: " + message);
    }
}
