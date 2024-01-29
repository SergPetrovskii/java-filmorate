package ru.yandex.practicum.filmorate.storages;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.models.User;

import javax.validation.ValidationException;
import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private int userNextId = 1;

    private Map<Integer, User> users = new HashMap<>();

    public List<User> getAllUsers() {
        log.debug("Коллекция пользователей получена, текущее количество {}.", users.size());
        return new ArrayList<>(users.values());
    }

    public User createUser(User user) {
        log.debug("Новый пользователь добавлен.");
        user.setId(userNextId);
        user.setFriendVault(new TreeSet<>());
        users.put(userNextId++, user);
        return user;
    }

    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            log.debug("Пользователь с id={} успешно заменен", user.getId());
            user.setFriendVault(new TreeSet<>());
            users.put(user.getId(), user);
            return user;
        }
        log.debug("Попытка изменить пользователя по не существующему id.");
        throw new ValidationException("Пользователя с данным id нет.");
    }

    public Optional<User> getUserForId(Integer id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        log.debug("Получен пользователь с id={}", id);
        return Optional.ofNullable(users.get(id));
    }

    public List<User> getFriendsUserForId(Integer id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        Set<Integer> userNumberList = users.get(id).getFriendVault();
        List<User> userList = new ArrayList<>();
        for (Integer integer : users.keySet()) {
            if (userNumberList.contains(integer)) {
                userList.add(users.get(integer));
            }
        }
        log.debug("Получен список друзей пользователя.");
        return userList;
    }

    public void addFriend(Integer userId, Integer friendId) {
        users.get(userId).getFriendVault().add(friendId);
        users.get(friendId).getFriendVault().add(userId);
    }

    public Map<Integer, User> getMapUsers() {
        return new HashMap<>(users);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        users.get(userId).getFriendVault().remove(friendId);
        users.get(friendId).getFriendVault().remove(userId);
    }
}
