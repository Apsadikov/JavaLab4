package ru.itis.security.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.itis.security.entity.User;
import ru.itis.security.model.Role;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class UserRepositoryJdbcTemplateImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;
    private BCryptPasswordEncoder passwordEncoder;
    private RowMapper<User> userRowMapper = (row, rowNumber) ->
            User.builder()
                    .id(row.getLong("id"))
                    .passwordHash(row.getString("password_hash"))
                    .email(row.getString("email"))
                    .role(row.getString("role").equals(Role.ADMIN.toString()) ? Role.ADMIN : Role.USER)
                    .build();


    @Autowired
    public UserRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) {
        if (!findUserByEmail(user.getEmail()).isPresent()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO user(email, password_hash) VALUE (?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getEmail());
                statement.setString(2, passwordEncoder.encode(user.getPassword()));
                return statement;
            }, keyHolder);

            user.setId(keyHolder.getKey().longValue());
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, userRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
