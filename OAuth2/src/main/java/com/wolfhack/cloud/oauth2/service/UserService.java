package com.wolfhack.cloud.oauth2.service;

import com.wolfhack.cloud.oauth2.model.dto.UserCreationDTO;
import com.wolfhack.cloud.oauth2.model.dto.UserResponseDTO;
import com.wolfhack.cloud.oauth2.exception.UserExistsException;
import com.wolfhack.cloud.oauth2.exception.UserNotFoundException;
import com.wolfhack.cloud.oauth2.factory.implement.UserFactoryInterface;
import com.wolfhack.cloud.oauth2.repository.UserRepository;
import com.wolfhack.cloud.oauth2.service.implement.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final UserFactoryInterface userFactory;

    @Override
    public UserResponseDTO save(UserCreationDTO user) {
        userRepository.findByLogin(user.getLogin())
                .ifPresent(found -> {throw new UserExistsException();});

        return userFactory.create(userRepository.save(userFactory.create(user)));
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
}
