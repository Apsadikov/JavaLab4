package ru.itis.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.security.model.Role;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private Long id;

    private String email;

    private String passwordHash;

    private String password;

    private Role role;
}
