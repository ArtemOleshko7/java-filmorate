package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service; // Импорт аннотации для объявления сервисного компонента Spring
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class FilmService {

    // Объявление зависимостей с использованием инъекции через конструктор
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;

    // Получение пользователя по ID
    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    // Получение списка всех фильмов
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    // Добавление нового фильма в хранилище
    public Film addFilm(Film film) {
        Film film1 = filmStorage.addFilm(film); // Сохранение фильма
        mpaDbStorage.addToFilm(film); // Добавление информации о MPA
        genreDbStorage.addGenre(film); // Добавление жанров к фильму
        return film1; // Возвращение сохраненного фильма
    }

    // Обновление информации о фильме
    public Film updateFilm(Film film) {
        mpaDbStorage.addToFilm(film); // Обновление MPA информации
        genreDbStorage.updateGenre(film); // Обновление жанров
        genreDbStorage.updateGenreForFilmInMemory(film); // Обновление жанров в памяти
        film.setGenres(new TreeSet<>(film.getGenres())); // Установка уникальных жанров
        return filmStorage.updateFilm(film); // Сохранение обновленного фильма
    }

    // Получение фильма по ID
    public Film getFilmById(Long id) {
        return filmStorage.getFilmById(id);
    }

    // Добавление лайка к фильму
    public Film addLike(Long id, Long userId) {
        return filmStorage.addLike(id, userId);
    }

    // Удаление лайка у фильма
    public Film deleteLike(Long id, Long userId) {
        return filmStorage.deleteLike(id, userId);
    }

    // Получение топ-фильмов по количеству
    public List<Film> getTopFilms(int count) {
        return filmStorage.getTopFilms(count);
    }
}