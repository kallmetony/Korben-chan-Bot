package com.aaronr92.korben_chan_bot.repository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class RepositoryUtil {
    public static EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("Discord-bot");
    }
}
