package com.luxter.interprice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static int makeDate(String s) {
        org.joda.time.format.DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern("yyyy/MM/dd");
        org.joda.time.DateTime dt = formatter.parseDateTime(s);
        long jd = org.joda.time.DateTimeUtils.toJulianDayNumber(dt.getMillis());
        return (int)jd;
    }

    public static StringConverter<LocalDate> getConverter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
    }

    public static ObservableList<String> getCurrenciesFromUnderlying(String underlying ) {
        ObservableList<String> currencies = FXCollections.observableArrayList();
        String[] parts = {underlying.substring(0, underlying.length()/2),underlying.substring(underlying.length()/2)};
        currencies.addAll(parts);
        return currencies;
    }

}
