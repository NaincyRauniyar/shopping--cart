package com.springcodework.dreamcart.service.user;

import com.springcodework.dreamcart.model.User;
import com.springcodework.dreamcart.request.CreateUserRequest;
import com.springcodework.dreamcart.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);

    void deleteUser(Long userId);

}
