package ru.itis.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Entity {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Note> notes;
}
