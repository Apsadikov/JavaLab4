package ru.itis.demo.repositories;

import ru.itis.demo.models.User;

import java.util.Optional;

public interface SignUpRepository extends CrudRepository<Long, User> {
    Optional<User> findUserByEmail(String email);

    boolean confirmedEmail(String email, String token);
}
