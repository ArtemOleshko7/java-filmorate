package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validator.ReplaceNameWithLogin;

import javax.validation.constraints.Email; // Импорт аннотации для проверки корректности email
import javax.validation.constraints.NotBlank; // Импорт аннотации для проверки на пустую строку
import javax.validation.constraints.Past; // Импорт аннотации для проверки даты на прошлое
import javax.validation.constraints.Positive; // Импорт аннотации для проверки положительного значения
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Validated // Активирует валидацию для аннотированных полей
@ReplaceNameWithLogin // Использование пользовательской аннотации для валидации
public class User {

    private Set<Long> friends = new HashSet<>(); // Множество для хранения идентификаторов друзей

    @Positive // Указывает, что id должен быть положительным
    private Long id; // Идентификатор пользователя

    private String name;

    @NotBlank(message = "Email is null or blank.") // Проверка, что поле email не пустое
    @Email(message = "Email is not valid.") // Проверка на корректность формата email
    private String email; // Email пользователя

    @Setter(AccessLevel.NONE) // Запрет на авто-сеттер для этого поля
    @NotBlank(message = "Login is null or blank.") // Проверка, что login не пустой
    private String login; // Логин пользователя

    @Past(message = "Birthday in future.") // Проверка, что дата рождения в прошлом
    private LocalDate birthday; // Дата рождения пользователя

    // Переопределенный метод установки login с дополнительной логикой
    public void setLogin(String login) {
        if (login == null) { // Если login null, устанавливаем null
            this.login = null;
        } else if (login.contains(" ")) { // Если login содержит пробелы, устанавливаем пустую строку
            this.login = "";
        } else { // В противном случае, сохраняем login
            this.login = login;
        }
    }
}