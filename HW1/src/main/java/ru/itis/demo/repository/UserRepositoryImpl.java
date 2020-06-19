package ru.itis.demo.repository;

import ru.itis.demo.entity.User;
import ru.itis.demo.repository.mapper.UserJoinRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private Connection connection;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    public Optional<List<User>> findAll() {
        try {
            String sql = "SELECT user.id AS \"user.id\", user.first_name AS \"user.first_name\", " +
                    "user.last_name AS \"user.last_name\", note.id AS \"note.id\", note.text AS \"note.text\"" +
                    "FROM user LEFT JOIN note ON note.user_id = user.id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                List<User> users = new UserJoinRowMapper().mapRows(resultSet);
                return Optional.ofNullable(users);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT user.id AS \"user.id\", user.first_name AS \"user.first_name\", " +
                    "user.last_name AS \"user.last_name\", note.id AS \"note.id\", note.text AS \"note.text\"" +
                    "FROM user LEFT JOIN note ON note.user_id = user.id WHERE user.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new UserJoinRowMapper().mapRow(resultSet);
                return Optional.ofNullable(user);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void save(User entity) {
        try {
            String sql = "INSERT INTO user (first_name, last_name) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setId(keys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try {
            String sql = "DELETE FROM user WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
