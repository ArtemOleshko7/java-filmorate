package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // Обозначает, что это тестовый класс Spring
@AutoConfigureTestDatabase // Автоматически настраивает встраиваемую базу данных для тестов
@RequiredArgsConstructor(onConstructor_ = @Autowired) // Генерирует конструктор с зависимостями
class FilmDbStorageTests {
    private final FilmDbStorage filmStorage; // Хранение фильмов

    @BeforeEach
    public void setUp() {
        // Создаем тестовый фильм и добавляем его в хранилище
        Film film = new Film();
        film.setId(1);
        film.setName("Test Film");
        film.setDescription("Test description");
        film.setReleaseDate(LocalDate.of(2020, 1, 1));
        film.setDuration(120);
        filmStorage.addFilm(film);
    }

    @Test
    public void testFindFilmById() {
        // Ищем фильм по id и проверяем, что он найден
        Optional<Film> filmOptional = filmStorage.getById(1);
        assertThat(filmOptional)
                .isPresent() // Убедиться, что фильм присутствует
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1) // Проверка id
                );
    }

    @Test
    public void testAddFilm() {
        // Создаем новый фильм
        Film newFilm = new Film();
        newFilm.setName("New Film");
        newFilm.setDescription("New description");
        newFilm.setReleaseDate(LocalDate.of(2021, 5, 15));
        newFilm.setDuration(90);

        // Добавляем новый фильм и проверяем его свойства
        Film createdFilm = filmStorage.addFilm(newFilm);
        assertThat(createdFilm).isNotNull(); // Проверка на не-null
        assertThat(createdFilm.getId()).isGreaterThan(1); // Проверка, что id больше 1
        assertThat(createdFilm.getName()).isEqualTo("New Film"); // Проверка имени
    }

    @Test
    public void testUpdateFilm() {
        // Обновляем существующий фильм
        Film filmToUpdate = new Film();
        filmToUpdate.setId(1);
        filmToUpdate.setName("Updated Film");
        filmToUpdate.setDescription("Updated description");
        filmToUpdate.setReleaseDate(LocalDate.of(2020, 1, 1));
        filmToUpdate.setDuration(150);

        // Сохраняем обновления
        filmStorage.updateFilm(filmToUpdate);

        // Проверяем, что обновленные данные сохранены верно
        Optional<Film> updatedFilmOptional = filmStorage.getById(1);
        assertThat(updatedFilmOptional)
                .isPresent() // Убедиться, что фильм присутствует
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "Updated Film") // Проверка имени
                );
    }
}