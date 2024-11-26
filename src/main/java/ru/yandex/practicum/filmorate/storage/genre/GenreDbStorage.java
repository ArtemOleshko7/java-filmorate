package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate; // Используется для взаимодействия с базой данных

    // Получение всех жанров
    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM GENRE");
        while (rowSet.next()) {
            Genre genre = Genre.builder()
                    .id(rowSet.getInt("GENRE_ID"))
                    .name(rowSet.getString("GENRE_NAME"))
                    .build();
            genres.add(genre);
        }
        return genres;
    }

    // Получение жанров по идентификатору фильма
    @Override
    public Set<Genre> getGenre(Long id) {
        Set<Genre> genres = new HashSet<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM FILM_GENRE ORDER BY GENRE_ID");
        while (rowSet.next()) {
            // Проверка на соответствие идентификатору фильма
            if (rowSet.getLong("FILM_ID") == id) {
                genres.add(getGenreById(rowSet.getInt("GENRE_ID")));
            }
        }
        return genres;
    }

    // Добавление жанров к фильму
    @Override
    public Film addGenre(Film film) {
        film.getGenres().forEach(genre ->
                jdbcTemplate.update("INSERT INTO FILM_GENRE(GENRE_ID, FILM_ID) VALUES (?, ?)",
                        genre.getId(), film.getId()));
        return film;
    }

    // Обновление жанров для фильма в памяти
    @Override
    public Film updateGenreForFilmInMemory(Film film) {
        if (film.getGenres() == null) {
            return film; // Если жанров нет, вернуть фильм
        }
        // Обновление названий жанров
        film.getGenres().forEach(genre -> genre.setName(getGenreById(genre.getId()).getName()));
        return film;
    }

    // Обновление жанров фильма
    @Override
    public Film updateGenre(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_GENRE WHERE FILM_ID = ?", film.getId()); // Удаление старых жанров
        addGenre(film); // Добавление новых жанров
        return film;
    }

    // Получение жанра по идентификатору
    @Override
    public Genre getGenreById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM GENRE WHERE GENRE_ID = ?", this::rowMapToGenre, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Genre not found"); // Исключение, если жанр не найден
        }
    }

    // Преобразование строки результата в объект Genre
    private Genre rowMapToGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("GENRE_ID"))
                .name(rs.getString("GENRE_NAME"))
                .build();
    }
}