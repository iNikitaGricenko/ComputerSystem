package com.wolfhack.cloud.business.factory;

import com.wolfhack.cloud.business.dto.UserCreationDTO;
import com.wolfhack.cloud.business.dto.UserResponseDTO;
import com.wolfhack.cloud.business.enums.Role;
import com.wolfhack.cloud.business.factory.mapper.UserMapper;
import com.wolfhack.cloud.business.model.User;
import com.wolfhack.cloud.business.service.IEmailSender;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFactory implements IUserFactory {

    private final UserMapper userMapper;
    private final IEmailSender emailSender;

    @Override
    public User create(UserCreationDTO requestDTO) {
        Object[] args = new Random().ints(9, 1, 10).mapToObj(value -> --value).toArray();
        String activationCode = String.format("%d%d%d-%d%d%d-%d%d%d", args);
        String activationUrl = UUID.randomUUID().toString().replace("-", "/").toLowerCase();

        String defaultPhotoName = "user-icon.png";
        String bcryptedPassword = getPasswordEncoder().encode(requestDTO.getPassword());

        User user = userMapper.toUser(requestDTO);

        user.setPassword(bcryptedPassword);
        user.setRole(Role.USER);
        user.setRegisterDate(LocalDateTime.now());
        user.setPhoto(defaultPhotoName);
        user.setActivationCode(activationCode);
        user.setActivationUrl(activationUrl);
        user.setActive(false);

        sendActivationMail(user);

        return user;
    }

    @Override
    public User create(UserResponseDTO fullUserDto) {
        return userMapper.toUser(fullUserDto);
    }

    @Override
    public UserResponseDTO create(User user) {
        return userMapper.toDto(user);
    }

    @Override
    public User edit(User user, User editor) {
        return user.renovator()
                .update(editor, User::getPassword, password ->
                        user.setPassword(getPasswordEncoder().encode(password)))
                .update(editor, User::getRole, user::setRole)
                .update(editor, User::getPhoto, user::setPhoto)
                .update(editor, User::getLogin, user::setLogin)
                .update(editor, User::getUsername, user::setUsername)
                .build();
    }


    @Async
    protected void sendActivationMail(User entity) {
        try {
            log.info("Sending activation email");
            emailSender.send(entity);
        } catch (FeignException exception) {
            throw new RuntimeException("Error sending activation email");
        }
    }

    private static BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
