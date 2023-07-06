package com.wolfhack.cloud.business.scheduler;

import com.wolfhack.cloud.business.adapter.IInputUser;
import com.wolfhack.cloud.business.adapter.IOutputUser;
import com.wolfhack.cloud.business.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.HOURS;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class SchedulerTasks {

    private final IOutputUser outputUser;
    private final IInputUser inputUser;

    @Async
    @Scheduled(initialDelay = 12, fixedDelay = 12, timeUnit = HOURS)
    public void removeInactiveUsers() {
        log.info("Scheduler removeInactiveUsers is started");
        LocalDateTime dateTime = LocalDateTime.now().minusHours(12);
        List<User> allByActivationCodeIsNotNullAndRegisterDateIsLessThan =
                outputUser.findAllByActiveIsFalseAndRegisterDateIsLessThan(dateTime);
        inputUser.deleteAll(allByActivationCodeIsNotNullAndRegisterDateIsLessThan);
    }

    @Async
    @Scheduled(initialDelay = 24, fixedDelay = 24, timeUnit = HOURS)
    public void removeActivationCodeAndUrlFromActiveUsers() {
        log.info("Scheduler removeActivationCodeAndUrlFromActiveUsers is started");
        Stream<User> userStream = outputUser.findAllByActiveIsTrueAndActivationCodeIsNotNull().stream()
                .peek(user -> {
                    user.setActivationCode("");
                    user.setActivationUrl("");
                });
        inputUser.saveAll(userStream.collect(Collectors.toList()));
    }

}
