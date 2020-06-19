package ru.itis.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class User implements Entity {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Note> notes;
}
