package com.jspmastery.functions;

/**
 * Custom Expression Language (EL) Function.
 * Functions used in EL must be public static methods.
 */
public class StringFunctions {
    public static String reverse(String input) {
        if (input == null) return "";
        return new StringBuilder(input).reverse().toString();
    }

    public static String mask(String input) {
        if (input == null || input.length() <= 4) return "****";
        return "****" + input.substring(input.length() - 4);
    }
}
