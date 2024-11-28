package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.validation.annotation.Validated; // Импортирует аннотацию для валидации
import ru.yandex.practicum.filmorate.validator.AfterDate;

import javax.validation.constraints.NotBlank; // Импортирует аннотацию для проверки на пустоту
import javax.validation.constraints.Positive; // Импортирует аннотацию для проверки положительности
import javax.validation.constraints.Size; // Импортирует аннотацию для проверки длины строки
import java.time.LocalDate;
import java.util.Comparator; // Импортирует интерфейс для сравнения объектов
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet; // Импортирует TreeSet из Java Collections

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor // Генерирует конструктор без аргументов
@Validated
public class Film {

    private Set<Long> likes = new HashSet<>(); // Множество идентификаторов лайков

    private Set<Genre> genres = new TreeSet<>(Comparator.comparingInt(Genre::getId)); // Множество жанров, отсортированных по ID

    private Long id; // Идентификатор фильма

    @NotBlank(message = "Title is null or blank.") // Валидирует, что название не пустое
    private String name; // Название фильма

    @Size(max = 200, message = "Description length is more than 200 characters.") // Валидирует, что описание не превышает 200 символов
    private String description; // Описание фильма

    @AfterDate("28-12-1895") // Пользовательская аннотация для проверки даты релиза
    private LocalDate releaseDate; // Дата релиза фильма

    @Positive(message = "Duration is negative.") // Валидирует, что продолжительность положительна
    private Integer duration; // Продолжительность фильма в минутах

    private Integer rating;

    private Mpa mpa; // Объект Mpa, соответствующий фильму
}