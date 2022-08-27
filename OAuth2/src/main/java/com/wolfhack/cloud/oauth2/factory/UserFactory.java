package com.wolfhack.cloud.oauth2.factory;

import com.wolfhack.cloud.oauth2.model.dto.UserCreationDTO;
import com.wolfhack.cloud.oauth2.model.dto.UserResponseDTO;
import com.wolfhack.cloud.oauth2.factory.mapper.UserMapper;
import com.wolfhack.cloud.oauth2.factory.implement.UserFactoryInterface;
import com.wolfhack.cloud.oauth2.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.wolfhack.cloud.oauth2.model.Role.USER;

@Service
@RequiredArgsConstructor
public class UserFactory implements UserFactoryInterface {

    private final UserMapper userMapper;

    @Override
    public User create(UserCreationDTO requestDTO) {
        User user = userMapper.toUser(requestDTO);
        String bcryptedPassword = getPasswordEncoder().encode(user.getPassword());
        user.setPassword(bcryptedPassword);
        user.setRole(USER);
        user.setRegisterDate(LocalDateTime.now());
        user.setPhoto("user-icon.png");
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
        Optional.ofNullable(editor.getPassword())
                .ifPresent(password -> user.setPassword(getPasswordEncoder().encode(password)));
        Optional.ofNullable(editor.getRole()).ifPresent(user::setRole);
        Optional.ofNullable(editor.getPhoto()).ifPresent(user::setPhoto);
        Optional.ofNullable(editor.getLogin()).ifPresent(user::setLogin);
        Optional.ofNullable(editor.getUsername()).ifPresent(user::setUsername);
        return user;
    }

    private static BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
