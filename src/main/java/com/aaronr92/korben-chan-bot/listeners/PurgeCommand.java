package com.aaronr92.listeners;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PurgeCommand extends ListenerAdapter {

//    @Override
//    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
//        int count = Integer.parseInt(event.get.split(" ")[1]);
//        System.out.println(count);
//        if (count > 100) {
//            event.getMessage().reply(encodeMessage("Максимум - 100 сообщений")).queue();
//            return;
//        } else if (count < 1) {
//            event.getMessage().reply(encodeMessage("Минимум - 1 сообщение")).queue();
//            return;
//        }
//        List<Message> history = event.getChannel().getHistoryBefore(event.getMessage(), count).complete().getRetrievedHistory();
//        System.out.println(history.size());
//        for (Message m :
//                history) {
//            m.delete().reason("Cleared by command").queue();
//        }
//    }
}
