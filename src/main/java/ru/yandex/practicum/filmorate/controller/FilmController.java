package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.ExceptionService;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

// Аннотация для логирования
@Slf4j
// Аннотация для REST-контроллера
@RestController
// Генерация конструктора с обязательными параметрами
@RequiredArgsConstructor
// Базовый путь для всех методов контроллера
@RequestMapping("/films")
public class FilmController {

    // для работы с фильмами
    private final FilmService filmService;
    // для обработки исключений
    private final ExceptionService exceptionService;

    // Получение списка всех фильмов
    @GetMapping
    public List<Film> getFilms() {
        log.info("Поступил запрос на получение списка фильмов");
        return filmService.getFilms();
    }

    // Добавление нового фильма
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Поступил запрос на добавление фильма: {}", film.getName());
        return filmService.addFilm(film);
    }

    // Обновление существующего фильма
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        // Проверка на наличие фильма
        if (filmService.getFilmById(film.getId()) == null) {
            log.error("Film not found");
            exceptionService.throwNotFound();
        }
        log.info("Поступил запрос на обновление фильма: {}", film.getName());
        return filmService.updateFilm(film);
    }

    // Добавление лайка к фильму
    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        // Проверка на наличие фильма и пользователя
        if (filmService.getFilmById(id) == null || filmService.getUserById(userId) == null) {
            log.error("Film or user not found");
            exceptionService.throwNotFound();
        }
        log.info("Поступил запрос на добавление лайка фильму с id: {}", id);
        return filmService.addLike(id, userId);
    }

    // Удаление лайка у фильма
    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        // Проверка на наличие фильма, пользователя и лайка
        if (filmService.getFilmById(id) == null || filmService.getUserById(userId) == null
                || !filmService.getFilmById(id).getLikes().contains(userId)) {
            log.error("Film or user not found, or there is no like to delete");
            exceptionService.throwNotFound();
        }
        log.info("Поступил запрос на удаление лайка у фильма с id: {}", id);
        return filmService.deleteLike(id, userId);
    }

    // Получение фильма по идентификатору
    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        // Проверка на наличие фильма
        if (filmService.getFilmById(id) == null) {
            log.error("Film not found");
            exceptionService.throwNotFound();
        }
        log.info("Поступил запрос на получение фильма с id {}", id);
        return filmService.getFilmById(id);
    }

    // Получение топ популярных фильмов
    @GetMapping("/popular")
    public List<Film> getTop10Films(@RequestParam(defaultValue = "10") int count) {
        // Проверка на положительное значение count
        if (count < 0) {
            log.error("Negative count");
            exceptionService.throwBadRequest("Count is negative: = " + count + ", ожидали положительное");
        }
        log.info("Поступил запрос на получение списка топ {} популярных фильмов", count);
        return filmService.getTopFilms(count);
    }
}