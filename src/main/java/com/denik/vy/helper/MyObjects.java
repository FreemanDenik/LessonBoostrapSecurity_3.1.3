package com.denik.vy.helper;

public class MyObjects {
    public static <T, X extends Throwable> T requireNonNullElseThrow(T obj, X exception) throws X {
        if (obj == null)
            throw exception;
        return obj;
    }
}
