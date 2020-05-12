package ru.itis.taskmanager.service;

import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.BoardDto;

import java.util.List;

@Service
public interface BoardService {
    List<BoardDto> getAllBoards(Long userId);

    void save(BoardDto boardDto);
}
