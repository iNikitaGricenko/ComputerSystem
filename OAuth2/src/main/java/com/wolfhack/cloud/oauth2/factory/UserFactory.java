package com.wolfhack.cloud.oauth2.factory;

import com.wolfhack.cloud.oauth2.enums.Role;
import com.wolfhack.cloud.oauth2.factory.mapper.UserMapper;
import com.wolfhack.cloud.oauth2.model.dto.UserCreationDTO;
import com.wolfhack.cloud.oauth2.model.dto.UserResponseDTO;
import com.wolfhack.cloud.oauth2.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public interface UserFactory {

    @Slf4j
    @Service
    @RequiredArgsConstructor
    final class UserFactoryImpl implements UserFactory {

        private final UserMapper userMapper;

        @Override
        public User create(UserCreationDTO requestDTO) {
            User user = userMapper.toUser(requestDTO);

            Object[] args = new Random().ints(9, 1, 10).mapToObj(value -> --value).toArray();
            String activationCode = String.format("%d%d%d-%d%d%d-%d%d%d", args);
            String activationUrl = UUID.randomUUID().toString().replace("-", "/").toLowerCase();

            String defaultPhotoName = "user-icon.png";
            String bcryptedPassword = getPasswordEncoder().encode(user.getPassword());

            user.setPassword(bcryptedPassword);
            user.setRole(Role.USER);
            user.setRegisterDate(LocalDateTime.now());
            user.setPhoto(defaultPhotoName);
            user.setActivationCode(activationCode);
            user.setActivationUrl(activationUrl);
            user.setActive(false);
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

        private static BCryptPasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    User create(UserCreationDTO requestDTO);

    User create(UserResponseDTO fullUserDto);

    UserResponseDTO create(User customerOrder);

    User edit(User customerOrder, User editor);
}
