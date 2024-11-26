package ru.yandex.practicum.filmorate.exception;

// Класс BadRequestException наследует от RuntimeException
public class BadRequestException extends RuntimeException {

    // Конструктор класса, который принимает сообщение об ошибке
    public BadRequestException(String message) {
        // Передаёт сообщение родительскому классу RuntimeException
        super(message);
    }
}