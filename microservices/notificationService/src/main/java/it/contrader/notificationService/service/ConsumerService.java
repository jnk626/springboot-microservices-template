package it.contrader.notificationService.service;

import it.contrader.notificationService.dto.MailInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.function.Consumer;

@Service
public class ConsumerService {
    @Autowired
    private MailService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);
    @Bean
    public Consumer<MailInfoDTO> mailSender() {
        return info -> {
            service.sendMail(info.getRecipientName(), info.getRecipientEmail(), Locale.ITALIAN);
            LOGGER.warn("Received: {}", info);
        };
    }
}
