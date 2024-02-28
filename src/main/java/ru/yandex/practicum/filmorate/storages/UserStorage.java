package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserStorage {
    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User getUserForId(Integer id);
}
