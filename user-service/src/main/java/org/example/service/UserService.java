package org.example.service;

import org.example.dto.UserDto;
import org.example.model.User;

import java.util.List;

public interface UserService {

    User createUser(UserDto dto);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}
