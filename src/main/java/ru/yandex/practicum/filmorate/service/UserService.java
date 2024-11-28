package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor // Генерирует конструктор с параметрами для обязательных полей
public class UserService {

    private final UserStorage userStorage; // Хранилище данных для пользователей

    // Метод для создания нового пользователя
    public User createUser(User user) {
        return userStorage.createUser(user); // Сохраняет пользователя в хранилище и возвращает его
    }

    // Метод для получения списка всех пользователей
    public List<User> getUsers() {
        return userStorage.getUsers(); // Возвращает список пользователей из хранилища
    }

    // Метод для обновления информации о пользователе
    public User updateUser(User user) {
        return userStorage.updateUser(user); // Обновляет пользователя в хранилище и возвращает обновленного
    }

    // Метод для получения пользователя по его идентификатору
    public User getUserById(Long id) {
        return userStorage.getUserById(id); // Возвращает пользователя по ID из хранилища
    }

    // Метод для добавления друга
    public User addFriend(Long id, Long friendId) {
        return userStorage.addFriend(id, friendId);
    }

    // Метод для удаления друга
    public User deleteFriend(Long id, Long friendId) {
        return userStorage.deleteFriend(id, friendId);
    }

    // Метод для получения списка друзей пользователя
    public List<User> getFriends(Long id) {
        return userStorage.getFriends(id); // Возвращает список друзей по ID пользователя
    }

    // Метод для получения общего списка друзей двух пользователей
    public List<User> getSharedFriendsList(Long id, Long friendId) {
        return userStorage.getSharedFriendsList(id, friendId); // Возвращает общий список друзей по двум ID
    }
}