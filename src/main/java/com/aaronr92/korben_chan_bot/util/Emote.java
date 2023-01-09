package com.aaronr92.korben_chan_bot.util;

public enum Emote {

    KORBEN_AHUEL("<:korben_ahuel:1007576541221367828>"),
    JOVE("<:gav_blya:1009205950026100736>"),
    THE_BARBARIAN("<:a_vse:1009076517646643220>");

    final String code;

    Emote(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
