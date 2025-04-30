package com.abdullayev.demoshops.controllers;

import com.abdullayev.demoshops.dto.UserDto;
import com.abdullayev.demoshops.exceptions.AlreadyExistsException;
import com.abdullayev.demoshops.exceptions.UserNotFoundException;
import com.abdullayev.demoshops.models.User;
import com.abdullayev.demoshops.requests.user.UserCreateRequest;
import com.abdullayev.demoshops.requests.user.UserUpdateRequest;
import com.abdullayev.demoshops.responses.ApiResponse;
import com.abdullayev.demoshops.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("User found!", userDto));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong!", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreateRequest request) {
        try {
            UserDto user = userService.createUser(request);
            return ResponseEntity.ok(new ApiResponse("User created!", user));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong!", e.getMessage()));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
        try {
            UserDto user = userService.updateUser(request, userId);
            return ResponseEntity.ok(new ApiResponse("User updated!", user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong!", e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted!", null));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong!", e.getMessage()));
        }
    }
}
