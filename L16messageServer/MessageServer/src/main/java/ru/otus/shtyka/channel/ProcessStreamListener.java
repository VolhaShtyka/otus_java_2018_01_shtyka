package ru.otus.shtyka.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessStreamListener extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ProcessStreamListener.class);

    private final InputStream inputStream;
    private final Process process;

    public ProcessStreamListener(InputStream inputStream, Process process) {
        this.inputStream = inputStream;
        this.process = process;
    }
    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try (InputStreamReader isr = new InputStreamReader(inputStream)) {
            bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                logger.info(line);
            }
        } catch (IOException e) {
            logger.error("Process Handler Error", e);
        }
    }
}