package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate; // Объект для работы с базой данных

    @Override
    public Set<Long> getLikes(Long id) {
        Set<Long> likes = new HashSet<>(); // Множество для хранения ID пользователей, поставивших лайк
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM LIKES"); // SQL-запрос для получения всех лайков

        while (rowSet.next()) {
            // Проверяем, совпадает ли ID фильма с переданным
            if (rowSet.getLong("FILM_ID") == id) {
                likes.add(rowSet.getLong("USER_ID"));
            }
        }
        return likes;
    }
}