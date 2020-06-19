package ru.itis.demo;

import ru.itis.demo.db.DB;
import ru.itis.demo.entity.User;
import ru.itis.demo.repository.UserRepository;
import ru.itis.demo.repository.UserRepositoryImpl;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Connection connection = DB.getConnection();
        UserRepository userRepository = new UserRepositoryImpl(connection);
//        userRepository.delete(4L);
        List<User> users = userRepository.findAll().get();
        users.stream().forEach(user -> {
            System.out.println("User: " + user.getId() + ", " + user.getFirstName());
            user.getNotes().stream().forEach(note -> System.out.println("Note: " + note));
            System.out.println("===========================");
        });
        Optional<User> optionalUser = userRepository.findById(1L);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println(user.getId() + ", " + user.getLastName() + ", " + user.getFirstName());
            user.getNotes().stream().forEach(note -> System.out.println(note));
        }
    }
}
