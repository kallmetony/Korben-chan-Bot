package com.aaronr92.korben_chan_bot.command;

import com.aaronr92.korben_chan_bot.Emote;
import com.aaronr92.korben_chan_bot.Flag;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class NewYearBoxesCommand extends ListenerAdapter {

    Random random = new Random();
    private int prevRandNum;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("новогодняя")) {
            int randNum = random.nextInt(16);
            if (randNum == prevRandNum)
                randNum = random.nextInt(16);
            if (randNum == 0) {
                event.reply(style() + "«Юпитер Фульгур» на "  + Flag.BRITAIN.getCode() +
                        " Super Conqueror.").queue();
            } else if (randNum == 1) {
                event.reply("Тебе выпал " + Flag.USA.getCode() + " M47 Patton Improved").queue();
            } else if (randNum == 2) {
                event.reply("Тебе выпал " + Flag.CHINA.getCode() + " BZ-176").queue();
            } else if (randNum == 3) {
                event.reply("Тебе выпал " + Flag.FRANCE.getCode() + " Char Mle. 75").queue();
            } else if (randNum == 4) {
                event.reply("Тебе выпал "  + Flag.ITALY.getCode() + " Vipera").queue();
            } else if (randNum == 5) {
                event.reply("Тебе выпал " + Flag.USA.getCode() + " AMBT").queue();
            } else if (randNum == 6) {
                event.reply("Тебе выпал " + Flag.USSR.getCode() + " СУ-2-122").queue();
            } else if (randNum == 7) {
                event.reply("Тебе выпал " + Flag.GERMANY.getCode() + " Pz.Kpfw. KW I (r)").queue();
            } else if (randNum == 8) {
                event.reply("Тебе выпал " + Flag.USA.getCode() + " М3 лёгкий").queue();
            } else if (randNum == 9) {
                event.reply("Тебе выпал " + Flag.GERMANY.getCode() + " Pz.Kpfw. 38H 735 (f)").queue();
            } else if (randNum == 10) {
                event.reply("Тебе выпал " + Flag.USSR.getCode() + " Боевой Узбек").queue();
            } else if (randNum == 11) {
                event.reply(style() + "«Слейпнир» на "  + Flag.SWEDEN.getCode() +
                        " Strv 103B.").queue();
            } else if (randNum == 12) {
                event.reply(style() + "«Астерий» на "  + Flag.ITALY.getCode() +
                        " Controcarro 3 Minotauro.").queue();
            } else if (randNum == 13) {
                event.reply(style() + "«Констеласьон» на "  + Flag.FRANCE.getCode() +
                        " Bat.-Chatillon 155 58. (САУ).").queue();
            } else if (randNum == 14) {
                event.reply(style() + "«Леванте» на "  + Flag.FRANCE.getCode() +
                        " Char Futur 4. (9 лвл).").queue();
            } else if (randNum == 15) {
                event.reply("Тебе выпал ДЕМОНТАЖНЫЙ НАБОР").queue();
            }
            prevRandNum = randNum;
        }
    }

    private String style() {
        return "Тебе выпал 3D-стиль ";
    }
}
