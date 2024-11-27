package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator; // Импорт интерфейса ConstraintValidator для реализации валидаторов
import javax.validation.ConstraintValidatorContext; // Импорт контекста для валидации
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Класс валидатора, который реализует интерфейс ConstraintValidator
public class AfterDateValidator implements ConstraintValidator<AfterDate, LocalDate> {

    public String value; // Переменная для хранения значения даты из аннотации

    // Форматтер для парсинга строки даты в объект LocalDate
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Метод initialize вызывается перед валидацией и инициализирует валидатор
    @Override
    public void initialize(AfterDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation); // Вызов метода инициализации суперкласса
        this.value = constraintAnnotation.value(); // Сохраняем значение из аннотации
    }

    // Метод isValid выполняет проверку валидации
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        } else {
            return localDate.isAfter(LocalDate.parse(value, formatter)); // Проверка, что дата после заданной даты
        }
    }
}