package ru.itis.security.repository;

import ru.itis.security.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Long, User> {
    Optional<User> findUserByEmail(String email);
}
