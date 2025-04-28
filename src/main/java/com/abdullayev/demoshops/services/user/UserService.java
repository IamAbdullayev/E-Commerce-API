package com.abdullayev.demoshops.services.user;

import com.abdullayev.demoshops.dto.UserDto;
import com.abdullayev.demoshops.exceptions.AlreadyExistsException;
import com.abdullayev.demoshops.exceptions.UserNotFoundException;
import com.abdullayev.demoshops.models.User;
import com.abdullayev.demoshops.requests.user.UserCreateRequest;
import com.abdullayev.demoshops.requests.user.UserUpdateRequest;
import com.abdullayev.demoshops.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    @Override
    public UserDto createUser(UserCreateRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(user.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    return convertToDto(userRepository.save(user));
                }).orElseThrow(() -> new AlreadyExistsException("User with this email: \"" + request.getEmail() + "\" already exist!"));
    }

    @Override
    public UserDto updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(excistingUser -> {
                    excistingUser.setFirstName(request.getFirstName());
                    excistingUser.setLastName(request.getLastName());
                    return convertToDto(userRepository.save(excistingUser));
                })
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(
                userRepository::delete,
                () -> { throw new UserNotFoundException("User not found!"); }
        );
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
