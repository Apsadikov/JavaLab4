package ru.itis.websocket.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.itis.websocket.dto.UserDto;
import ru.itis.websocket.entity.User;
import ru.itis.websocket.repository.UserRepository;
import ru.itis.websocket.util.PasswordEncrypt;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncrypt passwordEncrypt;
    private final Environment environment;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncrypt passwordEncrypt, Environment environment) {
        this.userRepository = userRepository;
        this.passwordEncrypt = passwordEncrypt;
        this.environment = environment;
    }

    @Override
    public void signUp(UserDto userDto) {
        if (!userRepository.findUserByEmail(userDto.getEmail()).isPresent()) {
            userRepository.save(User.builder()
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .passwordHash(passwordEncrypt.getPasswordHash(userDto.getPassword()))
                    .build());
        }
    }

    @Override
    public void signIn(UserDto userDto) {
        Optional<User> userOptional = userRepository.findUserByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncrypt.verifyPassword(userDto.getPassword(), user.getPasswordHash())) {
                Algorithm algorithm = Algorithm.HMAC256(environment.getProperty("secret"));
                String token = JWT.create()
                        .withSubject(String.valueOf(user.getId()))
                        .sign(algorithm);
                userDto.setToken(token);
                userDto.setId(user.getId());
            }
        }
    }

    @Override
    public Optional<UserDto> verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getProperty("secret"));
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            Long userId = Long.valueOf(verifier.verify(token).getSubject());
            Optional<UserDto> optionalUser = findUserById(userId);
            if (optionalUser.isPresent()) {
                optionalUser.get().setToken(token);
                return optionalUser;
            } else {
                return Optional.empty();
            }
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserDto> findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(UserDto::from);
    }
}