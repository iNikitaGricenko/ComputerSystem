package com.wolfhack.cloud.oauth2.service.implement;

import com.wolfhack.cloud.oauth2.model.User;

import javax.mail.MessagingException;

public interface EmailSender {

    void send(User user, String subject, String template) throws MessagingException;

}
