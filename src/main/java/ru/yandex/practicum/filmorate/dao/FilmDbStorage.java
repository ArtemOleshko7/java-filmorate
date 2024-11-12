package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j // Аннотация для логирования
@Component // Обозначает, что это компонент Spring
@Qualifier("filmDbStorage") // Определяет имя бина в Spring
@RequiredArgsConstructor // Генерирует конструктор для финальных полей
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate; // Шаблон для работы с JDBC

    @Override
    public Film addFilm(Film film) {
        // SQL запрос для добавления фильма в базу данных
        String sql = "INSERT INTO films (name, description, release_date, duration, rating_id) VALUES (?, ?, ?, ?, ?)";

        // Получаем id рейтинга, если он установлен
        Integer ratingId = (film.getRating() != null) ? film.getRating().ordinal() + 1 : null;

        // Выполняем обновление базы данных
        jdbcTemplate.update(sql, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), ratingId);

        log.info("Фильм добавлен: {}", film); // Логируем информацию о добавленном фильме
        return film; // Возвращаем добавленный фильм
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        // SQL запрос для обновления информации о фильме в базе данных
        String sql = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ? WHERE film_id = ?";

        // Получаем id рейтинга, если он установлен
        Integer ratingId = (updatedFilm.getRating() != null) ? updatedFilm.getRating().ordinal() + 1 : null;

        // Выполняем обновление и проверяем, был ли изменен хоть один ряд
        if (jdbcTemplate.update(sql, updatedFilm.getName(), updatedFilm.getDescription(),
                updatedFilm.getReleaseDate(), updatedFilm.getDuration(), ratingId,
                updatedFilm.getId()) == 0) {
            throw new NotFoundException("Фильм с ID " + updatedFilm.getId() + " не найден."); // Если не найден, выбрасываем исключение
        }

        log.info("Фильм обновлён: {}", updatedFilm); // Логируем информацию об обновленном фильме
        return updatedFilm; // Возвращаем обновленный фильм
    }

    @Override
    public Optional<Film> getById(Integer id) {
        // SQL запрос для получения фильма по его ID
        String sql = "SELECT * FROM films WHERE film_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToFilm, id).stream().findFirst(); // Возвращаем опциональный фильм
    }

    @Override
    public List<Film> getAllFilms() {
        // SQL запрос для получения всех фильмов
        String sql = "SELECT * FROM films";
        return jdbcTemplate.query(sql, this::mapRowToFilm); // Возвращаем список всех фильмов
    }

    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        // Метод для преобразования строки результата в объект Film
        Film film = new Film();
        film.setId(rs.getInt("film_id")); // Устанавливаем ID фильма
        film.setName(rs.getString("name")); // Устанавливаем имя фильма
        film.setDescription(rs.getString("description")); // Устанавливаем описание фильма
        film.setReleaseDate(rs.getDate("release_date").toLocalDate()); // Устанавливаем дату релиза
        film.setDuration(rs.getInt("duration")); // Устанавливаем продолжительность фильма

        // Преобразуем рейтинг из базы данных в значение из перечисления MPA
        int ratingId = rs.getInt("rating_id");
        if (ratingId != 0) {
            film.setRating(Film.MPA.values()[ratingId - 1]); // Устанавливаем рейтинг, если он не нулевой
        } else {
            film.setRating(null); // Если рейтинг нулевой, устанавливаем его как null
        }

        return film; // Возвращаем объект Film
    }
}