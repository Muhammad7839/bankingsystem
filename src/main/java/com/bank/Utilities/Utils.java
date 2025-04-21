package com.bank.Utilities;

public class Utils {
    public static String exceptionToString(Exception e) {
        String[] S = e.toString().split(":");
        if (S.length == 2)
            return S[1];
        return e.toString();
    }
}
