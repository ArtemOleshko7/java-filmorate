package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat; // для форматирования JSON
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

// Класс ExceptionResponse предназначен для обработки и форматирования информации об исключениях
@Getter
public class ExceptionResponse {
    private final HttpStatus status;
    private final String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // Указывает формат даты при сериализации в JSON
    private final LocalDateTime time = LocalDateTime.now(); // Время, когда было создано это исключение

    public ExceptionResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}