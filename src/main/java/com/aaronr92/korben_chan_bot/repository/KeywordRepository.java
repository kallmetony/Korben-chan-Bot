package com.aaronr92.korben_chan_bot.repository;

import com.aaronr92.korben_chan_bot.entity.BotUser;
import com.aaronr92.korben_chan_bot.entity.Keyword;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class KeywordRepository {

    private static Logger log = LoggerFactory.getLogger(KeywordRepository.class);

    public static Keyword save(Keyword keyword) {
        try (EntityManagerFactory entityManagerFactory = RepositoryUtil.getEntityManagerFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();

            entityManager.persist(keyword);

            transaction.commit();
            log.info("Key-value pair added: {} - {}", keyword.getWord(), keyword.getReply());

            return keyword;
        }
    }

    public static List<Keyword> findAll() {
        try (EntityManagerFactory entityManagerFactory = RepositoryUtil.getEntityManagerFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Query query = entityManager.createQuery("select k from Keyword k");

            return query.getResultList();
        }
    }

    public static boolean contains(String word) {
        try (EntityManagerFactory entityManagerFactory = RepositoryUtil.getEntityManagerFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Query query = entityManager
                    .createQuery("select k from Keyword k where k.word = '" + word + "'");

            return query.getResultList().size() >= 1;
        }
    }
}
