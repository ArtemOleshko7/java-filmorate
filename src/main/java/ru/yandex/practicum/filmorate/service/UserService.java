package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User createUser(User user) {
        setDefaultNameIfEmpty(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User updatedUser) {
        setDefaultNameIfEmpty(updatedUser);
        return userStorage.updateUser(updatedUser);
    }

    private void setDefaultNameIfEmpty(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public User getById(int id) {
        return userStorage.getById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден."));
    }

    public Set<Integer> getFriendsIDs(int id) {
        return userStorage.getFriendsIDs(id);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void friend(int firstUserId, int secondUserId) {
        User user = userStorage.getById(firstUserId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + firstUserId + " не найден."));
        User friend = userStorage.getById(secondUserId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + secondUserId + " не найден."));
        user.addFriend(secondUserId);
        friend.addFriend(firstUserId);
        userStorage.updateUser(user);
        userStorage.updateUser(friend);
        log.info("Пользователи с id {} и {} теперь друзья.", firstUserId, secondUserId);
    }

    public void unfriend(int firstUserId1, int secondUserId) {
        User user = userStorage.getById(firstUserId1)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + firstUserId1 + " не найден."));
        User friend = userStorage.getById(secondUserId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + secondUserId + " не найден."));
        user.removeFriend(secondUserId);
        friend.removeFriend(firstUserId1);
        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public List<User> getCommonFriends(int userId1, int userId2) {
        User user = userStorage.getById(userId1)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId1 + " не найден."));
        User user2 = userStorage.getById(userId2)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId2 + " не найден."));
        Set<Integer> commonFriendsIds = user.getFriendsIDs().stream()
                .filter(user2.getFriendsIDs()::contains)
                .collect(Collectors.toSet());

        return commonFriendsIds.stream()
                .map(userStorage::getById)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}