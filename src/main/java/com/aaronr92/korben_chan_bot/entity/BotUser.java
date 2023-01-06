package com.aaronr92.korben_chan_bot.entity;

import jakarta.persistence.*;

@Entity(name = "bot_user")
public class BotUser {
    @Id
    @GeneratedValue(generator="UserSeq")
    @SequenceGenerator(name="UserSeq", sequenceName="USER_SEQ", allocationSize = 1)
    private long id;

    @Column(name = "discord_id", unique = true, nullable = false)
    private long discordId;

    public BotUser() {}

    public BotUser(long id, long discordId) {
        this.id = id;
        this.discordId = discordId;
    }

    public long getId() {
        return id;
    }

    public BotUser setId(long id) {
        this.id = id;
        return this;
    }

    public long getDiscordId() {
        return discordId;
    }

    public BotUser setDiscordId(long discordId) {
        this.discordId = discordId;
        return this;
    }

    @Override
    public String toString() {
        return "BotUser{" +
                "id=" + id +
                ", discordId=" + discordId +
                '}';
    }
}
