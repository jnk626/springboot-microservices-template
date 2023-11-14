package it.contrader.notificationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@PropertySource("classpath:mail.properties")
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine engine;
    @Value("${mail.username}")
    private String EMAIL_USERNAME;

    public void sendMail(
            final String recipientName, final String recipientEmail, final Locale locale
    ) throws MessagingException {
        // Context used
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setFrom(EMAIL_USERNAME);
        message.setTo(recipientEmail);
        message.setSubject("Test");
        final String htmlContent = this.engine.process("html/standard-mail.html", ctx);
        message.setText(htmlContent, true);

        this.mailSender.send(mimeMessage);
    }
}
