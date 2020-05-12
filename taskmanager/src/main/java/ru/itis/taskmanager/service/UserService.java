package ru.itis.taskmanager.service;

import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.UserDto;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> findUsers(String email);

    UserDto signUp(UserDto userDto);
}
