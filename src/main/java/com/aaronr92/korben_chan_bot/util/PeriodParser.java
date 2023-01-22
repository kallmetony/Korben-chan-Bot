package com.aaronr92.korben_chan_bot.util;

public class PeriodParser {
    /**
     * Accepts string period from json and returns russian representation of it
     * @param period string to be parsed
     * @return russian representation of this period
     * @throws IllegalArgumentException if input string is not valid
     */
    public static String parse(String period) throws IllegalArgumentException {
        switch (period) {
            case "ONE" -> {
                return "Один час";
            }
            case "TWO" -> {
                return "Два часа";
            }
            case "THREE" -> {
                return "Три часа";
            }
            case "FOUR" -> {
                return "Четыре часа";
            }
            case "FIVE" -> {
                return "Пять часов";
            } case "SIX" -> {
                return "Шесть часов";
            } default -> throw new IllegalArgumentException();
        }
    }
}
