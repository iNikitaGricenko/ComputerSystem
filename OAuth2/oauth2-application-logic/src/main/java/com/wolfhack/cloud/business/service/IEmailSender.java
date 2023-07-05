package com.wolfhack.cloud.business.service;

import com.wolfhack.cloud.business.model.User;

import javax.mail.MessagingException;

public interface IEmailSender {

    void send(User user, String subject, String template) throws MessagingException;

}
