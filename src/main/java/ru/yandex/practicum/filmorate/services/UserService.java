package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private UserStorage inMemoryUserStorage;

    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User userAddFriend(Integer userId, Integer friendId) { //метод добавления в друзья
        Map<Integer, User> users = inMemoryUserStorage.getMapUsers();
        if (users.containsKey(userId) && users.containsKey(friendId)) {
            inMemoryUserStorage.addFriend(userId, friendId);
            User user = users.get(userId);
            log.debug("Пользователь с id {} добавил пользователя с id {} в друзья. ", userId, friendId);
            return user;
        }
        throw new UserNotFoundException("Пользователь с данным id не найден.");
    }

    public User userDeleteFriend(Integer userId, Integer friendId) { //метод удаления из друзей
        Map<Integer, User> users = inMemoryUserStorage.getMapUsers();
        if (users.containsKey(userId) && users.containsKey(friendId)) {
            User user = users.get(userId);
            if (!user.getFriendVault().contains(friendId)) {
                return user;
            }
            inMemoryUserStorage.deleteFriend(userId, friendId);
            log.debug("Пользователь с id {} удалил из друзей пользователя с id {}. ", userId, friendId);
            return user;
        }
        throw new UserNotFoundException("Пользователь с данным id не найден.");
    }

    public List<User> getFriends(Integer userId, Integer friendId) { //метод получения списка друзей
        User user = getUserForId(userId);
        User friend = getUserForId(friendId);

        return user.getFriendVault().stream()
                .filter(friend.getFriendVault()::contains)
                .map(this::getUserForId).collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    public User createUser(User user) {
        userCheck(user);
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User user) {
        userCheck(user);
        return inMemoryUserStorage.updateUser(user);
    }

    public User getUserForId(Integer UserId) {
        Map<Integer, User> users = inMemoryUserStorage.getMapUsers();
        if (users.containsKey(UserId)) {
            User user = users.get(UserId);
            log.debug("Получен пользователь с id {}", UserId);
            return user;
        }
        throw new UserNotFoundException("Пользователь с данным id не найден.");
    }

    public List<User> getFriendsUserForId(Integer id) {
        return inMemoryUserStorage.getFriendsUserForId(id);
    }

    private void userCheck(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.debug("Имя пользователя не было указанно, по этому использован его логин.");
            user.setName(user.getLogin());
        }
    }
}
