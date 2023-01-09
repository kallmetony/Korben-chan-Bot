package com.aaronr92.korben_chan_bot.util;

public class Util {

    public static String getTankClass(String rawType) {
        switch (rawType) {
            case "TANK_DESTROYER" -> {
                return "ПТ";
            }
            case "MEDIUM" -> {
                return "СТ";
            }
            case "LIGHT" -> {
                return "ЛТ";
            }
            case "HEAVY" -> {
                return "ТТ";
            }
            case "ARTILLERY" -> {
                return "ПТ-САУ";
            }
        }
        return null;
    }
}
