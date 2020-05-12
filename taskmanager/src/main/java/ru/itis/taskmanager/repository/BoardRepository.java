package ru.itis.taskmanager.repository;

import ru.itis.taskmanager.entity.Board;

import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {
    List<Board> findBoardsByUserId(Long userId);
}
