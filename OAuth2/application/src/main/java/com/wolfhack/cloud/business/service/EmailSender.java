package com.wolfhack.cloud.business.service;

import com.wolfhack.cloud.business.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailSender implements IEmailSender {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void send(User user, String subject, String template) throws MessagingException {
        Context context = new Context();
        context.setVariable("activationCode", user.getActivationCode());
        context.setVariable("website", "localhost:8080/");

        String process = templateEngine.process(template, context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper =  new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom("noreply@domain.com");
        helper.setTo(user.getLogin());
        helper.setSubject(subject);
        helper.setText(process, true);

        mailSender.send(mimeMessage);
    }
}
