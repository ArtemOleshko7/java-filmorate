package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Like {

    @NotNull
    private final Long id; // Идентификатор лайка

    @NotNull
    private final Long filmId; // Идентификатор фильма, которому поставлен лайк

    @NotNull
    private final Long userId; // Идентификатор пользователя, который поставил лайк
}