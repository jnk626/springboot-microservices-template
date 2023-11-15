package it.contrader.notificationService.service;

import it.contrader.notificationService.dto.MailInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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
            try {
                service.sendMail(info.getRecipientName(), info.getRecipientEmail(), Locale.ITALIAN);
            } catch (MessagingException e) {
                // TODO doesn't work as expected but at least doesn't stop the application
                LOGGER.error("Messaging Error: {}", e);
            }
            LOGGER.info("Received: {}", info);
        };
    }
}
