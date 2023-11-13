package it.contrader.notificationService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);
    @Bean
    public Consumer<String> mailInput() {
        return string -> {
            LOGGER.warn("Received: {}", string);
        };
    }
}
