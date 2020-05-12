package ru.itis.taskmanager.repository;

import ru.itis.taskmanager.entity.UserBoard;

import java.util.List;
import java.util.Optional;

public class UserBoardRepositoryJdbcTemplateImpl implements UserBoardRepository {
    @Override
    public List<UserBoard> findAll() {
        return null;
    }

    @Override
    public Optional<UserBoard> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(UserBoard id) {

    }

    @Override
    public void save(UserBoard entity) {

    }

    public boolean isUserHasBoard(Long boardId) {
        return true;
    }
}
