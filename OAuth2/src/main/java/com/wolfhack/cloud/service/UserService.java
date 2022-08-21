package com.wolfhack.cloud.service;

import com.wolfhack.cloud.exception.UserExistsException;
import com.wolfhack.cloud.exception.UserNotFoundException;
import com.wolfhack.cloud.model.User;
import com.wolfhack.cloud.model.UserSecurity;
import com.wolfhack.cloud.repository.UserRepository;
import com.wolfhack.cloud.service.implement.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

import static com.wolfhack.cloud.model.Role.USER;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    @Override
    public User save(User user) throws IOException {
        userRepository.findByLogin(user.getLogin())
                .ifPresentOrElse(found -> {throw new UserExistsException();},
                        () -> {
                            String bcryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
                            user.setPassword(bcryptedPassword);
                            user.setRole(USER);
                            user.setRegisterDate(LocalDateTime.now());
                            user.setPhoto("user-icon.png");
                        });
        return userRepository.save(user);
    }

    @SneakyThrows
    @Override
    public User edit(User user) {
        return userRepository.findById(user.getId())
                .map(foundUser -> {
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    Optional.ofNullable(user.getPassword())
                            .ifPresent(password -> foundUser.setPassword(bCryptPasswordEncoder.encode(password)));
                    Optional.ofNullable(user.getUsername()).ifPresent(foundUser::setUsername);
                    Optional.ofNullable(user.getRole()).ifPresent(foundUser::setRole);
                    Optional.ofNullable(user.getPhoto()).ifPresent(foundUser::setPhoto);
                    return userRepository.save(user);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new);
    }
}
