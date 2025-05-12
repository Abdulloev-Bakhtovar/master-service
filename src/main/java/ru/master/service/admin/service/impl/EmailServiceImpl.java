package ru.master.service.admin.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import ru.master.service.admin.service.EmailService;
import ru.master.service.auth.service.VerificationService;
import ru.master.service.exception.AppException;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${application.email.username}")
    private String from;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final VerificationService verificationService;

    @Override
    public void sendEmail(String to) {
        try {
            MimeMessage mimeMessage = createMimeMessage(to);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new AppException("Failed to send email to " + to, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private MimeMessage createMimeMessage(String to) throws MessagingException {
        Map<String, Object> properties = new HashMap<>();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, UTF_8.name());

        properties.put("activation_code", verificationService.saveCode(to));

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("Сброс пароля админа");
        helper.setText(templateEngine.process(
                "reset_password", context),
                true
        );

        return mimeMessage;
    }
}
