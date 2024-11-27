package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// Класс валидатора, который реализует интерфейс ConstraintValidator
public class ReplaceNameWithLoginValidator implements ConstraintValidator<ReplaceNameWithLogin, User> {

    // Метод isValid выполняет проверку валидации
    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        // Проверяем, если имя пользователя равно null или пустое
        if (user.getName() == null || user.getName().isBlank()) {
            // Если имя пустое, заменяем его на логин пользователя
            user.setName(user.getLogin());
        }
        // Возвращаем true, указывая, что валидация прошла успешно
        return true;
    }
}