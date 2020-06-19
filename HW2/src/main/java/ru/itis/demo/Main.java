package ru.itis.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.demo.entity.User;
import ru.itis.demo.repository.UserRepository;
import ru.itis.demo.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/notebook?" +
                "useUnicode=true&" +
                "serverTimezone=UTC");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate);

        Optional<List<User>> optionalUsers = userRepository.findAll();
        if (optionalUsers.isPresent()) {
            List<User> users = optionalUsers.get();
            users.forEach(user -> {
                System.out.println("User: " + user.getId() + ", " + user.getFirstName() + " " + user.getLastName());
                user.getNotes().forEach(System.out::println);
                System.out.println("===========================");
            });
        }

//        Optional<User> optionalUser = userRepository.findById(2L);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            System.out.println(user.getId() + ", " + user.getLastName() + ", " + user.getFirstName());
//            user.getNotes().forEach(System.out::println);
//        }

//        User user = User.builder()
//                .firstName("Test")
//                .lastName("Test")
//                .build();
//        userRepository.save(user);
//        System.out.println(user.getId());

//        userRepository.delete(5L);
    }
}
