package com.wolfhack.cloud.client;

import com.wolfhack.cloud.dto.FullUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "userClient", url = "localhost:8080/user-service/api/user")
public interface UserClient {
    @RequestMapping(value = "/{id}", method = GET)
    FullUserDto getOne(@PathVariable Long id);

    @RequestMapping(value = "/login", method = GET)
    FullUserDto findByLogin(@RequestParam String login);
}
