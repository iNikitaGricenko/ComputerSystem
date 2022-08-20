package com.wolfhack.cloud.service;

import com.wolfhack.cloud.exception.UserExistsException;
import com.wolfhack.cloud.exception.UserNotFoundException;
import com.wolfhack.cloud.model.User;
import com.wolfhack.cloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

import static com.wolfhack.cloud.model.Role.USER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) throws IOException {
        userRepository.findByLogin(user.getLogin())
                .ifPresentOrElse(found -> {throw new UserExistsException();},
                        () -> {
                            Date date = new Date(Calendar.getInstance().getTime().getTime());
                            String bcryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
                            user.setPassword(bcryptedPassword);
                            user.setRole(USER);
                            user.setRegisterDate(date);
                            user.setPhoto("user-icon.png");
                        });
        return userRepository.save(user);
    }

    @SneakyThrows
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

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new);
    }
}
