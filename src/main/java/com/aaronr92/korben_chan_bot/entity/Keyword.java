package com.aaronr92.korben_chan_bot.entity;

import jakarta.persistence.*;

@Entity
public class Keyword {
    @Id
    @GeneratedValue(generator="KeywordSeq")
    @SequenceGenerator(name="KeywordSeq", sequenceName="KEYWORD_SEQ", allocationSize = 1)
    private long id;

    @Column(unique = true, nullable = false)
    private String word;

    @Column(unique = true, nullable = false)
    private String reply;

    public Keyword() {}

    public Keyword(long id, String word, String reply) {
        this.id = id;
        this.word = word;
        this.reply = reply;
    }

    public long getId() {
        return id;
    }

    public Keyword setId(long id) {
        this.id = id;
        return this;
    }

    public String getWord() {
        return word;
    }

    public Keyword setWord(String word) {
        this.word = word;
        return this;
    }

    public String getReply() {
        return reply;
    }

    public Keyword setReply(String reply) {
        this.reply = reply;
        return this;
    }
}
