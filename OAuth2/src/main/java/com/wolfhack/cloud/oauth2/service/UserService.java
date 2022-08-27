package com.wolfhack.cloud.oauth2.service;

import com.wolfhack.cloud.oauth2.model.User;
import com.wolfhack.cloud.oauth2.model.dto.UserCreationDTO;
import com.wolfhack.cloud.oauth2.model.dto.UserResponseDTO;
import com.wolfhack.cloud.oauth2.exception.UserExistsException;
import com.wolfhack.cloud.oauth2.exception.UserNotFoundException;
import com.wolfhack.cloud.oauth2.factory.implement.UserFactoryInterface;
import com.wolfhack.cloud.oauth2.repository.UserRepository;
import com.wolfhack.cloud.oauth2.service.implement.EmailSender;
import com.wolfhack.cloud.oauth2.service.implement.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final UserFactoryInterface userFactory;
    private final EmailSender emailSender;

    @Override
    public UserResponseDTO save(UserCreationDTO user) {
        userRepository.findByLogin(user.getLogin())
                .ifPresent(found -> {throw new UserExistsException();});

        User entity = userFactory.create(user);
        sendMail(entity, "Activation code", "activation_mail");

        return userFactory.create(userRepository.save(entity));
    }

    @SneakyThrows
    @Override
    public UserResponseDTO edit(UserResponseDTO user) {
        return userFactory.create(userRepository.save(userRepository.findById(user.getId())
                .map(foundUser -> userFactory.edit(foundUser, userFactory.create(user)))
                .orElseThrow(UserNotFoundException::new)));
    }

    @Override
    public Page<UserResponseDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userFactory::create);
    }

    @Override
    public UserResponseDTO getOne(Long id) {
        return userFactory.create(userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserResponseDTO findByLogin(String login) {
        return userFactory.create(userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new));
    }

    @Async
    protected void sendMail(User entity, String activation_code, String activation_mail) {
        CompletableFuture.runAsync(() -> {
            try {
                log.info("Sending activation email");
                emailSender.send(entity, activation_code, activation_mail);
            } catch (MessagingException exception) {
                throw new RuntimeException(exception);
            }
        });
    }
}
