package ru.itis.demo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.demo.models.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Component
public class SignUpRepositoryJdbcTemplateImpl implements SignUpRepository {
    private JdbcTemplate jdbcTemplate;
    private BCryptPasswordEncoder passwordEncoder;
    private Environment environment;
    private RowMapper<User> userRowMapper = (row, rowNumber) ->
            User.builder()
                    .id(row.getLong("id"))
                    .passwordHash(row.getString("password_hash"))
                    .token(row.getString("token"))
                    .email(row.getString("email"))
                    .isConfirmed(row.getBoolean("is_confirmed"))
                    .build();


    @Autowired
    public SignUpRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
    }

    @Override
    public void save(User user) {
        if (!findUserByEmail(user.getEmail()).isPresent()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String passwordHash = passwordEncoder.encode(user.getPassword() + environment.getProperty("salt"));
            String token = passwordEncoder.encode(user.getPassword() + user.getEmail() + environment.getProperty("salt"));

            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO user(email, password_hash, token) VALUE (?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getEmail());
                statement.setString(2, passwordHash);
                statement.setString(3, token);
                return statement;
            }, keyHolder);

            user.setToken(token);
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

    @Override
    public boolean confirmedEmail(String email, String token) {
        String sql = "UPDATE user SET is_confirmed = 1 WHERE email = ?";
        int affectedRows = jdbcTemplate.update(sql, email);
        return affectedRows != 0;
    }
}
