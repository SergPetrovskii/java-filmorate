package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.models.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserStorage {
    public List<User> getAllUsers();

    public User createUser(User user);

    public User updateUser(User user);

    public void addFriend(Integer userId, Integer friendId);

    public Map<Integer, User> getMapUsers();

    public void deleteFriend(Integer userId, Integer friendId);

    public Optional<User> getUserForId(Integer id);

    public List<User> getFriendsUserForId(Integer id);
}