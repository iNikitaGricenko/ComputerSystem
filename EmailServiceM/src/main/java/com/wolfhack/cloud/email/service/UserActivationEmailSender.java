package com.wolfhack.cloud.email.service;

import com.wolfhack.cloud.email.model.User;
import com.wolfhack.cloud.email.service.interfaces.IEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class UserActivationEmailSender implements IEmailSender<User> {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	private static final String EMAIL_TEMPLATE = "activation_mail";

	@Override
	public void send(User user, String subject) {
			Context context = new Context();
			context.setVariable("activationCode", user.getActivationCode());
			context.setVariable("website", "localhost:8080/");

			String process = templateEngine.process(EMAIL_TEMPLATE, context);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			try {
				helper.setFrom("noreply@domain.com");
				helper.setTo(user.getEmail());
				helper.setSubject(subject);
				helper.setText(process, true);

				mailSender.send(helper.getMimeMessage());
			} catch (MessagingException e) {
				throw new RuntimeException("Could not send email");
			}
	}
}
