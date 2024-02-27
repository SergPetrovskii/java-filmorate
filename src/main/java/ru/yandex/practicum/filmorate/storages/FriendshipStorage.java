package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface FriendshipStorage {

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    List<User> getFriendsUserForId(Integer id);

    List<User> getListFriend(int userId, int friendId);
}