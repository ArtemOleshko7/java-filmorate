package ru.yandex.practicum.filmorate.model;

import lombok.Builder; // Импортирует аннотацию для создания билдера
import lombok.Data; // Импортирует аннотацию для генерации геттеров, сеттеров, equals и других методов

import javax.validation.constraints.NotNull;

@Builder // Позволяет использовать шаблон проектирования "Строитель" для создания объектов Genre
@Data // Генерирует методы toString(), hashCode(), equals(), геттеры и сеттеры для полей
public class Genre implements Comparable<Genre> { // Класс Genre реализует интерфейс Comparable для сортировки

    @NotNull // Валидирует, что id не может быть null
    private int id; // Идентификатор жанра

    @NotNull // Валидирует, что name не может быть null
    private String name; // Название жанра

    @Override // Переопределяет метод compareTo для сортировки жанров по их идентификаторам
    public int compareTo(Genre o) {
        return id - o.getId(); // Сравнение текущего id с id другого жанра
    }
}