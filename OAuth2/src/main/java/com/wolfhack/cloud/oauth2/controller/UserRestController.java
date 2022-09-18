package com.wolfhack.cloud.oauth2.controller;

import com.wolfhack.cloud.oauth2.model.dto.UserCreationDTO;
import com.wolfhack.cloud.oauth2.model.dto.UserResponseDTO;
import com.wolfhack.cloud.oauth2.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.oauth2.exception.handler.error.ValidationErrorBody;
import com.wolfhack.cloud.oauth2.service.implement.UserServiceInterface;
import com.wolfhack.cloud.oauth2.wrapper.RestPage;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceInterface userService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    @Parameter(name = "user", schema = @Schema(implementation = UserCreationDTO.class))
    public UserResponseDTO registration(@Valid @RequestBody UserCreationDTO user) {
        return userService.save(user);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ValidationErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    public UserResponseDTO changeProfile(@Valid @RequestBody UserResponseDTO user) {
        return userService.edit(user);
    }

    @GetMapping
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return new RestPage<>(userService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    public UserResponseDTO findOne(@PathVariable Long id) {
        return userService.getOne(id);
    }

    @GetMapping("/login")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    @Parameter(name = "login", example = "test@domain.com")
    public UserResponseDTO findByLogin(@RequestParam String login) {
        return userService.findByLogin(login);
    }

}
