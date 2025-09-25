package com.springcodework.dreamcart.service.user;

import com.springcodework.dreamcart.dto.UserDto;
import com.springcodework.dreamcart.exceptions.AlreadyExistsException;
import com.springcodework.dreamcart.exceptions.ResourceNotFoundException;
import com.springcodework.dreamcart.model.User;
import com.springcodework.dreamcart.repository.UserRepository;
import com.springcodework.dreamcart.request.CreateUserRequest;
import com.springcodework.dreamcart.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {   // changed variable name
                    User user = new User();
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistsException("OOPS!! " + request.getEmail() + " already exists!"));
    }


    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete , () ->{
            throw new ResourceNotFoundException("user not found!");
        });

    }

    @Override
    public UserDto convertUserToDto(User user) {
        return null;
    }
}
