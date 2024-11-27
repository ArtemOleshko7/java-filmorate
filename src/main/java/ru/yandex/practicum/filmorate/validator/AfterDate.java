package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType; // Импорт для указания местоположения аннотации
import java.lang.annotation.Retention; // Импорт для определения времени жизни аннотации
import java.lang.annotation.RetentionPolicy; // Импорт политики хранения аннотации
import java.lang.annotation.Target; // Импорт для указания, где можно использовать аннотацию

// Указываем, что аннотация может применяться к полям
@Target({ElementType.FIELD})
// Указываем, что аннотация будет доступна в рантайме
@Retention(RetentionPolicy.RUNTIME)
// Определяем собственную аннотацию AfterDate
@Constraint(
        validatedBy = {AfterDateValidator.class} // Указываем класс валидатора
)
public @interface AfterDate {

    // Метод для задания значения, которое будет использоваться в проверке (например, дата для сравнения)
    String value();

    // Метод для задания сообщения по умолчанию, если валидация не проходит
    String message() default "releaseDate is before December 28, 1895.";

    // Метод для группировки аннотации валидации (используется для организации валидаций)
    Class<?>[] groups() default {};

    // Метод для передачи дополнительной информации о валидации
    Class<? extends Payload>[] payload() default {};
}