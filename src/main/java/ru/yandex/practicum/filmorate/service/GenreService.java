package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDbStorage genreDbStorage; // Хранение данных о жанрах

    // Метод для получения списка всех жанров
    public List<Genre> getGenres() {
        return genreDbStorage.getGenres();
    }

    // Метод для получения жанра по его идентификатору
    public Genre getGenre(int id) {
        return genreDbStorage.getGenreById(id);
    }
}