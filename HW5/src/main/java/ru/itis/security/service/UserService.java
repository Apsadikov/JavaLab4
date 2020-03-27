package ru.itis.security.service;

import org.springframework.stereotype.Service;
import ru.itis.security.dto.UserDto;

@Service
public interface UserService {
    UserDto login(UserDto userDto);

    UserDto signUp(UserDto userDto);
}
