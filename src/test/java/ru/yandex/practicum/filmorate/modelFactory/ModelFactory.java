package ru.yandex.practicum.filmorate.modelFactory;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;

public class ModelFactory {

    // Виток (volatile) переменная для обеспечения корректной работы с многопоточностью
    private static volatile ModelFactory factory;

    // Приватный конструктор, чтобы предотвратить создание экземпляров класса извне
    private ModelFactory() {
    }

    // Метод для получения единственного экземпляра ModelFactory
    public static ModelFactory getInstance() {
        ModelFactory result = factory;

        // Проверяем, и если есть, возвращаем уже созданный экземпляр
        if (result != null) {
            return result;
        }
        synchronized (ModelFactory.class) {
            // Двойная проверка блокировки
            if (factory == null) {
                factory = new ModelFactory(); // Создание нового экземпляра
            }
            return factory;
        }
    }

    // Метод для создания нового пользователя (User)
    public User createUser() {
        User user = new User();
        user.setName("user"); // Установим имя пользователя
        user.setLogin("login"); // Установим логин
        user.setEmail("user@mail.ru"); // Установим email
        user.setBirthday(LocalDate.of(2000, 1, 1)); // Установим дату рождения
        user.setFriends(new HashSet<>()); // Инициализация коллекции друзей
        return user;
    }

    // Метод для создания нового фильма (Film)
    public Film createFilm() {
        Film film = new Film();
        film.setName("film"); // Установим имя фильма
        film.setDescription("film description"); // Установим описание
        film.setReleaseDate(LocalDate.of(2000, 1, 1)); // Установим дату релиза
        film.setDuration(180); // Установим продолжительность
        film.setGenres(new HashSet<>()); // Инициализация коллекции жанров
        film.setLikes(new HashSet<>()); // Инициализация коллекции лайков
        film.setMpa(Mpa.builder() // Установим информацию о рейтинге MPA
                .id(5L)
                .name("NC-17")
                .build());
        return film;
    }

    // Метод для изменения данных пользователя (User)
    public void setNewUserData(User user) {
        user.setName(user.getName() + Math.random()); // Изменение имени с добавлением случайного числа
        user.setLogin(user.getName() + user.getLogin()); // Изменение логина
        user.setEmail(user.getLogin() + "@mail.ru"); // Изменение email
    }

    // Метод для изменения имени фильма (Film)
    public void setFilmName(Film film) {
        film.setName(film.getName() + " " + Math.random()); // Изменение имени фильма с добавлением случайного числа
    }
}