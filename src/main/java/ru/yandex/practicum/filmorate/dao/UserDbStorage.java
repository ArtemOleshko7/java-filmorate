package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@Qualifier("userDbStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate; // для работы с базой данных

    @Override
    public User createUser(User user) {
        // SQL запрос для добавления нового пользователя
        String sql = "INSERT INTO users (name, email, login, birthday) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        log.info("Пользователь добавлен: {}", user); // Логирование добавления пользователя
        return user; // Возвращаем добавленного пользователя
    }

    @Override
    public User updateUser(User updatedUser) {
        // SQL запрос для обновления существующего пользователя
        String sql = "UPDATE users SET name = ?, email = ?, login = ?, birthday = ? WHERE user_id = ?";
        // Выполняем обновление и проверяем, был ли обновлён какой-либо ряд
        if (jdbcTemplate.update(sql, updatedUser.getName(), updatedUser.getEmail(),
                updatedUser.getLogin(), updatedUser.getBirthday(), updatedUser.getId()) == 0) {
            throw new NotFoundException("Пользователь с ID " + updatedUser.getId() + " не найден."); // Генерируем исключение, если пользователь не найден
        }
        log.info("Пользователь обновлён: {}", updatedUser); // Логирование обновления пользователя
        return updatedUser; // Возвращаем обновлённого пользователя
    }

    @Override
    public Optional<User> getById(Integer id) {
        // SQL запрос для получения пользователя по его ID
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, id).stream().findFirst(); // Возвращаем пользователя как Optional
    }

    @Override
    public List<User> getAllUsers() {
        // SQL запрос для получения всех пользователей
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, this::mapRowToUser); // Возвращаем список пользователей
    }

    // Метод для отображения строки ResultSet в объект User
    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(); // Создаём новый объект User
        user.setId(rs.getInt("user_id")); // Устанавливаем ID пользователя
        user.setName(rs.getString("name")); // Устанавливаем имя пользователя
        user.setEmail(rs.getString("email")); // Устанавливаем email пользователя
        user.setLogin(rs.getString("login")); // Устанавливаем логин пользователя
        user.setBirthday(rs.getDate("birthday").toLocalDate()); // Устанавливаем дату рождения пользователя
        return user; // Возвращаем объект User
    }

    @Override
    public Set<Integer> getFriendsIDs(int id) {
        // SQL запрос для получения ID друзей указанного пользователя
        String sql = "SELECT friend_id FROM friends WHERE user_id = ?";
        List<Integer> friendsIds = jdbcTemplate.queryForList(sql, Integer.class, id); // Получаем список ID друзей

        // Логирование наличия друзей у пользователя
        if (friendsIds.isEmpty()) {
            log.info("Пользователь с ID {} не имеет друзей.", id);
        } else {
            log.info("Друзья пользователя с ID {}: {}", id, friendsIds);
        }

        return new HashSet<>(friendsIds); // Возвращаем уникальные ID друзей в виде множества
    }

    @Override
    public boolean isUserExist(int id) {
        // SQL запрос для проверки существования пользователя по его ID
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0; // Возвращаем true, если пользователь существует
    }
}