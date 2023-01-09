package com.aaronr92.korben_chan_bot.model;

public enum TankClass {
    LIGHT("ЛТ"),
    MEDIUM("СТ"),
    HEAVY("ТТ"),
    TANK_DESTROYER("ПТ"),
    ARTILLERY("ПТ-САУ");

    private final String name;

    TankClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}