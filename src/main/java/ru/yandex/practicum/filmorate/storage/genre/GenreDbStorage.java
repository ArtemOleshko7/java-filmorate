package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        var rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM GENRE");
        while (rowSet.next()) {
            Genre genre = Genre.builder()
                    .id(rowSet.getInt("GENRE_ID"))
                    .name(rowSet.getString("GENRE_NAME"))
                    .build();
            genres.add(genre);
        }
        return genres;
    }

    @Override
    public Genre getGenreById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM GENRE WHERE GENRE_ID = ?", this::rowMapToGenre, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Genre not found");
        }
    }

    private Genre rowMapToGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("GENRE_ID"))
                .name(rs.getString("GENRE_NAME"))
                .build();
    }
}