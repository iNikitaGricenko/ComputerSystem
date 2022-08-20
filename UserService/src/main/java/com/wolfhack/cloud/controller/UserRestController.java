package com.wolfhack.cloud.controller;

import com.wolfhack.cloud.dto.UserCreationDTO;
import com.wolfhack.cloud.dto.UserResponseDTO;
import com.wolfhack.cloud.dto.mapper.UserMapper;
import com.wolfhack.cloud.exception.UserExistsException;
import com.wolfhack.cloud.exception.UserNotFoundException;
import com.wolfhack.cloud.handler.error.ErrorBody;
import com.wolfhack.cloud.handler.error.ValidationErrorBody;
import com.wolfhack.cloud.model.User;
import com.wolfhack.cloud.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    @Parameter(name = "user", schema = @Schema(implementation = UserCreationDTO.class))
    public UserResponseDTO registration(@Valid @RequestBody UserCreationDTO user) throws IOException {
        return userMapper.toDto(userService.save(userMapper.toUser(user)));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    public UserResponseDTO changeProfile(@Valid @RequestBody UserResponseDTO user) {
        return userMapper.toDto(userService.edit(userMapper.toUser(user)));
    }

    @GetMapping
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userService.getAll(pageable).map(userMapper::toDto);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    public UserResponseDTO findOne(@PathVariable Long id) {
        return userMapper.toDto(userService.getOne(id));
    }

    @GetMapping("/login")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    @Parameter(name = "login", example = "test@domain.com")
    public UserResponseDTO findByLogin(@RequestParam String login) {
        return userMapper.toDto(userService.findByLogin(login));
    }

}
