package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public User userAddFriend(Integer userId, Integer friendId) {
            inMemoryUserStorage.addFriend(userId, friendId);
            log.debug("Пользователь с id {} добавил пользователя с id {} в друзья. ", userId, friendId);
            return inMemoryUserStorage.getUserForId(userId);
    }

    public User userDeleteFriend(Integer userId, Integer friendId) {
            inMemoryUserStorage.deleteFriend(userId, friendId);
            log.debug("Пользователь с id {} удалил из друзей пользователя с id {}. ", userId, friendId);
            return inMemoryUserStorage.getUserForId(userId);
    }

    public List<User> getFriends(Integer userId, Integer friendId) {
        User user = getUserForId(userId);
        User friend = getUserForId(friendId);

        return user.getFriendIds().stream()
                .filter(friend.getFriendIds()::contains)
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

    public User getUserForId(Integer userId) {
        return inMemoryUserStorage.getUserForId(userId);
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
