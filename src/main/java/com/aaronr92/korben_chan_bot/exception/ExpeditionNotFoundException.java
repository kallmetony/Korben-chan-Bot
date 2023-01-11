package com.aaronr92.korben_chan_bot.exception;

public class ExpeditionNotFoundException extends RuntimeException {
    public ExpeditionNotFoundException() {
        super("Expedition not found!");
    }
}
