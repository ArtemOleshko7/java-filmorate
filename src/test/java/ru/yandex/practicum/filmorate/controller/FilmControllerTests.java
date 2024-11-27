package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTests {

    // Создаем и инициализируем валидатор для проверки объектов
    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        // Настраиваем валидатор из фабрики
        validator = validatorFactory.usingContext().getValidator();
    }

    // Тест для проверки, что объект Film не является валидным, если все поля пустые (null)
    @Test
    void shouldNotValidateNullFilm() {
        Film film = new Film(); // Создаем пустой объект Film

        // Выполняем валидацию объекта и получаем набор нарушений
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        // Проверяем, что количество нарушений равно 2
        assertEquals(2, violations.size(), "Film is valid.");
    }

    // Тест для проверки, что корректный объект Film валидный
    @Test
    void shouldValidateCorrectFilm() {
        Film film = new Film(); // Создаем новый объект Film
        // Устанавливаем корректные значения для всех полей
        film.setName("Avatar");
        film.setDescription("Avatar description");
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(120);

        // Выполняем валидацию
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        // Проверяем, что нарушений нет
        assertEquals(0, violations.size(), "Film is not valid.");
    }

    // Тест для проверки, что объект Film не валидный, если название (name) равно null
    @Test
    void shouldNotValidateNullTitle() {
        Film film = new Film();
        film.setName(null); // Устанавливаем название как null
        film.setDescription("Avatar description");
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        // Проверяем, что количество нарушений равно 1
        assertEquals(1, violations.size(), "Title is not null.");
    }

    // Тест для проверки, что объект Film не валидный, если название (name) пустое
    @Test
    void shouldNotValidateBlankTitle() {
        Film film = new Film();
        film.setName(""); // Устанавливаем название как пустую строку
        film.setDescription("Avatar description");
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        // Проверяем, что количество нарушений равно 1
        assertEquals(1, violations.size(), "Title is not blank.");
    }

    // Тест для проверки, что объект Film не валидный, если длина описания (description) больше 200 символов
    @Test
    void shouldNotValidateDescriptionLengthMoreThan200Characters() {
        Film film = new Film();
        film.setName("Avatar");
        // Устанавливаем значение описания, превышающее 200 символов
        film.setDescription("a".repeat(202));
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        // Проверяем, что количество нарушений равно 1
        assertEquals(1, violations.size(), "Description length are less than 200 characters.");
    }

    // Тест для проверки, что объект Film не валидный, если дата релиза раньше 28 декабря 1895 года
    @Test
    void shouldNotValidateReleaseDateBefore28December1895() {
        Film film = new Film();
        film.setName("Avatar");
        film.setDescription("Avatar description");
        // Устанавливаем дату релиза до 28 декабря 1895 года
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        // Проверяем, что количество нарушений равно 1
        assertEquals(1, violations.size(), "Release date is valid.");
    }

    // Тест для проверки, что объект Film не валидный, если продолжительность (duration) отрицательная
    @Test
    void shouldNotValidateNegativeDuration() {
        Film film = new Film();
        film.setName("Avatar");
        film.setDescription("Avatar description");
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        // Устанавливаем отрицательную длительность
        film.setDuration(-120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        // Проверяем, что количество нарушений равно 1
        assertEquals(1, violations.size(), "Duration is valid.");
    }
}