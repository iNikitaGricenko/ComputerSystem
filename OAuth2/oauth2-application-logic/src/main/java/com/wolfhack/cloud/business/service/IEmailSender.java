package com.wolfhack.cloud.business.service;

import com.wolfhack.cloud.business.model.User;

public interface IEmailSender {

    void send(User user);

}
