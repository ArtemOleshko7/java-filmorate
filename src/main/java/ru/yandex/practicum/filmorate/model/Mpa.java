package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull; // Импорт аннотации для валидации полей на null

@Data
@Builder // Позволяет использовать шаблон проектирования "Строитель" для создания объектов класса Mpa
public class Mpa {

    @NotNull
    private Long id; // Идентификатор MPA

    @NotNull
    private String name; // Название MPA
}