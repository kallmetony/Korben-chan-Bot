package com.aaronr92.korben_chan_bot.exception;

public class AlreadyInExpeditionException extends RuntimeException {
    public AlreadyInExpeditionException() {
        super("User already in expedition");
    }
}
