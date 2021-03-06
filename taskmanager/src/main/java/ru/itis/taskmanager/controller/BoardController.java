package ru.itis.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.taskmanager.dto.BoardDto;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.security.details.UserDetailsImpl;
import ru.itis.taskmanager.service.BoardService;

@Controller
@PreAuthorize("isAuthenticated()")
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards/{id}")
    public String board(@PathVariable("id") Long id, Model model) {
        model.addAttribute("boardId", id);
        model.addAttribute("css", "home");
        model.addAttribute("link", "/logout");
        model.addAttribute("linkTitle", "Logout");
        return "board";
    }

    @PostMapping("/boards")
    public String addBoard(Authentication authentication, BoardDto boardDto) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        boardDto.setUser(user);
        boardService.save(boardDto);
        return "redirect:/";
    }
}
