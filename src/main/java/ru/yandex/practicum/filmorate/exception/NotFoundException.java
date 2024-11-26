package ru.yandex.practicum.filmorate.exception; // Определяет пакет, в котором находится этот класс

// Класс NotFoundException наследует от RuntimeException,
// что позволяет использовать его как исключение времени выполнения
public class NotFoundException extends RuntimeException {

    // Конструктор класса принимает строковое сообщение об ошибке
    public NotFoundException(String message) {
        // Передает сообщение родительскому классу RuntimeException
        super(message);
    }
}