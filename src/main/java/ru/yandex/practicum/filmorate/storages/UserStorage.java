package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.models.User;
import java.util.List;

public interface UserStorage {
    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    void addFriend(Integer userId, Integer friendId);

        void deleteFriend(Integer userId, Integer friendId);

    User getUserForId(Integer id);

    List<User> getFriendsUserForId(Integer id);
}