package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.List;

// Аннотация для логирования
@Slf4j
// Аннотация для REST-контроллера, который будет обрабатывать запросы о жанрах
@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
public class GenreController {

    // для работы с жанрами
    private final GenreDbStorage genreDbStorage;

    // Получение списка всех жанров
    @GetMapping
    public List<Genre> getGenresList() {
        log.info("Поступил запрос на получение списка жанров.");
        return genreDbStorage.getGenres();
    }

    // Получение жанра по идентификатору
    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable int id) {
        log.info("Поступил запрос на получение жанра по id: {}", id);
        return genreDbStorage.getGenreById(id);
    }
}