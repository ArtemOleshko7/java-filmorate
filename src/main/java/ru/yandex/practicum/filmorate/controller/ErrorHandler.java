package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.ExceptionResponse;

// Аннотация для логирования
@Slf4j
// Аннотация для обработки исключений в REST-контроллерах
@RestControllerAdvice
public class ErrorHandler {

    // Обработчик исключений типа NotFoundException
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND) // Устанавливаем статус 404 для ответа
    public ExceptionResponse handleNotFoundException(final NotFoundException e) {
        // Логируем сообщение об ошибке
        log.error(e.getMessage(), e);
        // Возвращаем ответ с информацией об ошибке
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestException(final BadRequestException e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}