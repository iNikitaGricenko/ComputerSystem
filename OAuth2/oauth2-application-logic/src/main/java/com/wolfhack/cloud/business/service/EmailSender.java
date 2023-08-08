package com.wolfhack.cloud.business.service;

import com.wolfhack.cloud.business.client.EmailClient;
import com.wolfhack.cloud.business.dto.UserEmailData;
import com.wolfhack.cloud.business.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender implements IEmailSender {

    private final EmailClient emailClient;

    @Override
    public void send(User user) {
        UserEmailData userEmailData = new UserEmailData(user.getActivationCode(), user.getLogin());
        emailClient.send(userEmailData);
    }
}
