package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private UserStorage userStorage;
    private FriendshipService friendshipService;

    @Autowired
    public UserService(UserDbStorage userStorage, FriendshipService friendshipService) {
        this.userStorage = userStorage;
        this.friendshipService = friendshipService;
    }

    public void userAddFriend(int userId, int friendId) {
        if (userStorage.getUserForId(userId) == null || userStorage.getUserForId(friendId) == null) {
            throw new UserNotFoundException("Пользователь с данным id не найден.");
        } else {
            friendshipService.addFriend(userId, friendId);
            log.debug("Пользователь с id {} добавил пользователя с id {} в друзья. ", userId, friendId);
        }
    }

    public void userDeleteFriend(int userId, int friendId) {
        friendshipService.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(Integer userId, Integer friendId) {
        return friendshipService.getListFriend(userId, friendId);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        userCheck(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User getUserForId(Integer userId) {
        return userStorage.getUserForId(userId);
    }

    public List<User> getFriendsUserForId(Integer id) {
        return friendshipService.getFriendsUserForId(id);
    }

    private void userCheck(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.debug("Имя пользователя не было указанно, поэтому использован его логин.");
            user.setName(user.getLogin());
        }
    }
}
