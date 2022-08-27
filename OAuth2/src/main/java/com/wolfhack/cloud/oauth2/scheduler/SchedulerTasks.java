package com.wolfhack.cloud.oauth2.scheduler;

import com.wolfhack.cloud.oauth2.model.User;
import com.wolfhack.cloud.oauth2.repository.UserRepository;
import com.wolfhack.cloud.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.*;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class SchedulerTasks {

    private final UserRepository userRepository;

    @Async
    @Scheduled(initialDelay = 12, fixedDelay = 12, timeUnit = HOURS)
    public void removeInactiveUsers() {
        log.info("Scheduler removeInactiveUsers is started");
        LocalDateTime dateTime = LocalDateTime.now().minusHours(12);
        List<User> allByActivationCodeIsNotNullAndRegisterDateIsLessThan =
                userRepository.findAllByActiveIsFalseAndRegisterDateIsLessThan(dateTime);
        userRepository.deleteAll(allByActivationCodeIsNotNullAndRegisterDateIsLessThan);
    }

    @Async
    @Scheduled(initialDelay = 24, fixedDelay = 24, timeUnit = HOURS)
    public void removeActivationCodeAndUrlFromActiveUsers() {
        log.info("Scheduler removeActivationCodeAndUrlFromActiveUsers is started");
        userRepository.saveAll(
                userRepository.findAllByActiveIsTrueAndActivationCodeIsNotNull().stream()
                        .peek(user -> {
                            user.setActivationCode("");
                            user.setActivationUrl("");
                        }).collect(Collectors.toList()));
    }

}
