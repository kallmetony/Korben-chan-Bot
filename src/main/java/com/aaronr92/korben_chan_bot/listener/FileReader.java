package com.aaronr92.korben_chan_bot.listener;


import com.aaronr92.korben_chan_bot.Bot;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class FileReader extends ListenerAdapter {

    Logger log = LoggerFactory.getLogger(FileReader.class);

    File file = new File(Bot.path);

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        try {
            if (!file.exists()) {
                log.info("File created? - {}", file.createNewFile());
            }

            Scanner sc = new Scanner(file);

            log.debug(file.getAbsolutePath());
            log.debug(String.valueOf(file.exists()));
            log.debug("Has next {}", sc.hasNext());

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split("-");
                log.debug("Load {}", Arrays.toString(line));
                MessageListener.keywords.put(line[0], line[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("API is ready!");
    }
}
