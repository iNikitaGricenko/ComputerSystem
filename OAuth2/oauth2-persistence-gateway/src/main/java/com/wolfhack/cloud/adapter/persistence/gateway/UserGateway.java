package com.wolfhack.cloud.adapter.persistence.gateway;

import com.wolfhack.cloud.adapter.persistence.entity.EntityUser;
import com.wolfhack.cloud.adapter.persistence.mapper.EntityUserMapper;
import com.wolfhack.cloud.adapter.persistence.repository.UserRepository;
import com.wolfhack.cloud.business.adapter.IInputUser;
import com.wolfhack.cloud.business.adapter.IOutputUser;
import com.wolfhack.cloud.business.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGateway implements IInputUser, IOutputUser {

    private final UserRepository userRepository;
    private final EntityUserMapper userMapper;

    @Override
    public User save(User user) {
        EntityUser entity = userMapper.convertToEntityUser(user);
        return userMapper.convertToBusinessUser(userRepository.save(entity));
    }

    @Override
    public void deleteAll(List<User> users) {
        users.stream().map(userMapper::convertToEntityUser).forEach(userRepository::delete);
    }

    @Override
    public void saveAll(List<User> users) {
        users.stream().map(userMapper::convertToEntityUser).forEach(userRepository::save);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login).map(userMapper::convertToBusinessUser);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(userMapper::convertToBusinessUser);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::convertToBusinessUser);
    }

    @Override
    public List<User> findAllByActiveIsFalseAndRegisterDateIsLessThan(LocalDateTime dateTime) {
        return userRepository.findAllByActiveIsFalseAndRegisterDateIsLessThan(dateTime).stream()
                .map(userMapper::convertToBusinessUser).collect(Collectors.toList());
    }

    @Override
    public List<User> findAllByActiveIsTrueAndActivationCodeIsNotNull() {
        return userRepository.findAllByActiveIsTrueAndActivationCodeIsNotNull().stream()
                .map(userMapper::convertToBusinessUser).collect(Collectors.toList());
    }
}
