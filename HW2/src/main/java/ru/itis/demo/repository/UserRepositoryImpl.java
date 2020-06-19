package ru.itis.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.demo.entity.User;
import ru.itis.demo.repository.mapper.UserJoinRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate template) {
        jdbcTemplate = template;
    }

    public Optional<List<User>> findAll() {
        String sql = "SELECT user.id AS \"user.id\", user.first_name AS \"user.first_name\", " +
                "user.last_name AS \"user.last_name\", note.id AS \"note.id\", note.text AS \"note.text\", note.user_id AS \"note.user_id\"" +
                "FROM user LEFT JOIN note ON note.user_id = user.id";
        List<User> users = jdbcTemplate.query(sql, resultSet ->
                resultSet.next() ? new UserJoinRowMapper().mapRows(resultSet) : null);
        return users == null ? Optional.empty() : Optional.of(users);
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT user.id AS \"user.id\", user.first_name AS \"user.first_name\", " +
                "user.last_name AS \"user.last_name\", note.id AS \"note.id\", note.text AS \"note.text\", note.user_id AS \"note.user_id\"" +
                "FROM user LEFT JOIN note ON note.user_id = user.id WHERE user.id = ?";
        User user = jdbcTemplate.query(sql, new Object[]{id}, resultSet ->
                resultSet.next() ? new UserJoinRowMapper().mapRow(resultSet) : null);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO user (first_name, last_name) VALUES (?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            return statement;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
