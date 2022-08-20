package com.aaronr92.listeners;

import com.aaronr92.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MessageListener extends ListenerAdapter {

    Random random = new Random();
    Logger log = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();
        if (encodeMessage(message).toLowerCase().contains("эх")) {
            event.getChannel().sendMessage(encodeMessage("Черчилль 3, даа сука!")).queue();
        }
        if (encodeMessage(message).toLowerCase().equals("!хуй")) {
            event.getChannel().sendMessage(encodeMessage("зато какой!")).queue();
        }
        if (encodeMessage(message).toLowerCase().contains("бот пидор")) {
            event.getChannel().sendMessage(encodeMessage("Сам пидор!")).queue();
        }
        if (encodeMessage(message).toLowerCase().contains("грядка") ||
                encodeMessage(message).toLowerCase().contains("клумба") ||
                encodeMessage(message).toLowerCase().contains("нир ю"))  {
            event.getMessage().reply(encodeMessage("А кто это?")).queue();
        }
        if (encodeMessage(message).toLowerCase().equals("!член")) {
            event.getMessage().reply(random.nextInt(3, 45) + encodeMessage(" см!")).queue();
        }
        if (encodeMessage(message).toLowerCase().equals("!семья")) {
            event.getChannel().sendMessage(encodeMessage("Это главное!")).queue();
        }
        if (encodeMessage(message).toLowerCase().equals("!мать")) {
            event.getMessage().reply(encodeMessage("не имба")).queue();
        }
        if (encodeMessage(message).toLowerCase().equals("!корбен")) {
            event.getChannel().sendMessage(Emote.KORBEN_AHUEL.getCode()).queue();
        }
        if (encodeMessage(message).toLowerCase().startsWith("!черчилль")) {
            event.getChannel().sendMessage(encodeMessage("Дикер макс вообще-то")).queue();
        }
        if (encodeMessage(message).toLowerCase().startsWith("!дикер")) {
            event.getChannel().sendMessage(encodeMessage("А я думал черчилль")).queue();
        }
        if (encodeMessage(message).toLowerCase().startsWith("!джов")) {
            event.getChannel().sendMessage(Emote.JOVE.getCode() + encodeMessage(" Гав!")).queue();
        }
        if (encodeMessage(message).toLowerCase().startsWith("!бар")) {
            event.getChannel().sendMessage(Emote.THE_BARBARIAN.getCode()).queue();
        }
        if (encodeMessage(message).toLowerCase().equals("!ютуб")) {
            event.getChannel().sendMessage(encodeMessage("https://www.youtube.com/c/KorbenDallasNoMercy")).queue();
        }
        if (encodeMessage(message).toLowerCase().equals("!трово")) {
            event.getChannel().sendMessage(encodeMessage("https://trovo.live/s/KorbenDallas")).queue();
        }
        if (encodeMessage(message).toLowerCase().equals("!донат")) {
            event.getMessage().reply(encodeMessage("https://www.donationalerts.com/r/korbendetka")).queue();
        }
        if (encodeMessage(message).toLowerCase().contains("иди нахуй")) {
            event.getMessage().reply(encodeMessage("Сам иди!")).queue();
        }
        if (encodeMessage(message).toLowerCase().startsWith("!удалить")) {
            int count = 0;
            try {
                count = Integer.parseInt(encodeMessage(message).split(" ")[1]);
            } catch (NumberFormatException e) {
                event.getMessage().reply(encodeMessage("Дурак? Напиши цифрами количество смс!")).queue();
                return;
            }
            if (count > 99) {
                event.getMessage().reply(encodeMessage("Максимум - 100 сообщений")).queue();
                return;
            } else if (count < 1) {
                event.getMessage().reply(encodeMessage("Минимум - 1 сообщение")).queue();
                return;
            }
            List<Message> history = event.getChannel().getHistoryBefore(event.getMessage(), count).complete().getRetrievedHistory();
            log.warn("Purging {} messages! By {}", history.size(), event.getAuthor());
            for (Message m :
                    history) {
                m.delete().reason("Cleared by command").queueAfter(2, TimeUnit.SECONDS);
            }
            event.getMessage().reply(encodeMessage("Все смс удалены!")).queue();
        }
    }

    private String encodeMessage(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
