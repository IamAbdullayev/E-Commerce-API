package com.abdullayev.demoshops.services.user;

import com.abdullayev.demoshops.dto.UserDto;
import com.abdullayev.demoshops.models.User;
import com.abdullayev.demoshops.requests.user.UserCreateRequest;
import com.abdullayev.demoshops.requests.user.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    UserDto createUser(UserCreateRequest request);
    UserDto updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertToDto(User user);
    User getAuthenticatedUser();
}
