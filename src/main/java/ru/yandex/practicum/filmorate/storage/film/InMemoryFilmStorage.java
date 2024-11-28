package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component // Обозначает, что класс является компонентом Spring
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>(); // Хранилище фильмов (ID -> Film)
    private Long filmId = 1L; // Генерация уникальных ID для фильмов

    // Получение списка всех фильмов
    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values()); // Возвращает список значений из хранилища
    }

    // Добавление нового фильма
    @Override
    public Film addFilm(Film film) {
        film.setId(filmId++); // Установка нового ID для фильма
        film.setRating(film.getLikes().size()); // Установка рейтинга на основе количества лайков
        films.put(film.getId(), film); // Сохранение фильма в хранилище
        log.info("Фильм {} добавлен", film.getName()); // Логирование события
        return film;
    }

    // Обновление существующего фильма
    @Override
    public Film updateFilm(Film film) {
        film.setRating(film.getLikes().size()); // Обновление рейтинга
        films.put(film.getId(), film); // Сохранение обновлённого фильма
        log.info("Фильм c id {} обновлен", film.getId());
        return film;
    }

    @Override
    public Film getFilmById(Long id) {
        return films.get(id);
    }

    @Override
    public Film addLike(Long id, Long userId) {
        films.get(id).getLikes().add(userId); // Добавление ID пользователя в список лайков
        films.get(id).setRating(films.get(id).getLikes().size()); // Обновление рейтинга
        log.info("Пользователь с id: {} поставил лайк фильму с id: {}", userId, id);
        return films.get(id);
    }

    @Override
    public Film deleteLike(Long id, Long userId) {
        films.get(id).getLikes().remove(userId); // Удаление ID пользователя из списка лайков
        films.get(id).setRating(films.get(id).getLikes().size()); // Обновление рейтинга
        log.info("Пользователю с id: {} больше не нравится фильм с id: {}", userId, id);
        return films.get(id);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return getFilms().stream()
                .sorted((film, t1) -> t1.getRating() - film.getRating()) // Сортировка по рейтингу
                .limit(count) // Ограничение количества фильмов
                .collect(Collectors.toList()); // Сбор в список
    }
}