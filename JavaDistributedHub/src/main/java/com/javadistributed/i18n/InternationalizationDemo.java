package com.javadistributed.i18n;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class InternationalizationDemo {
    public static void printLocalizedInfo(Locale locale) {
        System.out.println("\n==========================================");
        System.out.println("Locale: " + locale.getDisplayName());
        System.out.println("==========================================");

        // ResourceBundle for localized messages
        ResourceBundle bundle = ResourceBundle.getBundle("com.javadistributed.i18n.messages", locale);
        System.out.println("Greeting: " + bundle.getString("greeting"));
        System.out.println("Inquiry:  " + bundle.getString("farewell"));

        // Number Formatting
        double currencyAmount = 1450000.75;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        System.out.println("Currency: " + currencyFormatter.format(currencyAmount));

        // Number Formatting (standard)
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);
        System.out.println("Number:   " + numberFormatter.format(currencyAmount));

        // Date and Time Formatting
        Date now = new Date();
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        System.out.println("Date:     " + dateFormatter.format(now));
    }

    public static void main(String[] args) {
        System.out.println("--- Java Internationalization (I18N) & Localization (L10N) ---");

        // English (US)
        printLocalizedInfo(Locale.US);

        // French (France)
        printLocalizedInfo(Locale.FRANCE);

        // German (Germany)
        printLocalizedInfo(Locale.GERMANY);

        // Hindi (India)
        printLocalizedInfo(new Locale("hi", "IN"));
    }
}
