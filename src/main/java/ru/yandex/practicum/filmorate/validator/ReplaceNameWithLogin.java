package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint; // Импорт аннотации Constraint для валидации
import javax.validation.Payload; // Импорт Payload для дополнительной информации о валидации
import java.lang.annotation.ElementType; // Импорт для указания местоположения аннотации
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Указываем, что аннотация может применяться к классам (типам)
@Target(ElementType.TYPE)
// Указываем, что аннотация будет доступна в рантайме
@Retention(RetentionPolicy.RUNTIME)
// Определяем собственную аннотацию ReplaceNameWithLogin
@Constraint(
        validatedBy = {ReplaceNameWithLoginValidator.class} // Указываем класс валидатора
)
public @interface ReplaceNameWithLogin {

    // Метод для задания сообщения по умолчанию, если валидация не проходит
    String message() default "Null or blank name replaced with login.";

    // Метод для группировки аннотации валидации (используется для организации валидаций)
    Class<?>[] groups() default {};

    // Метод для передачи дополнительной информации о валидации
    Class<? extends Payload>[] payload() default {};
}