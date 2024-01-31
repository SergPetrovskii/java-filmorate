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
        user.setFriendIds(new TreeSet<>());
        users.put(userNextId++, user);
        return user;
    }

    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            log.debug("Пользователь с id={} успешно заменен", user.getId());
            user.setFriendIds(new TreeSet<>());
            users.put(user.getId(), user);
            return user;
        }
        log.debug("Попытка изменить пользователя по не существующему id.");
        throw new ValidationException("Пользователя с данным id нет.");
    }

    public User getUserForId(Integer id) {
        if (users.containsKey(id)) {
            log.debug("Получен пользователь с id={}", id);
            return users.get(id);
        } else {
            throw new UserNotFoundException("Пользователь не найден.");
        }

    }

    public List<User> getFriendsUserForId(Integer id) {
        if (users.containsKey(id)) {
            Set<Integer> userNumberList = users.get(id).getFriendIds();
            List<User> userList = new ArrayList<>();
            for (Integer integer : users.keySet()) {
                if (userNumberList.contains(integer)) {
                    userList.add(users.get(integer));
                }
            }
            log.debug("Получен список друзей пользователя.");
            return userList;
        } else {
            throw new UserNotFoundException("Пользователь не найден.");
        }
    }

    public void addFriend(Integer userId, Integer friendId) {
        if (users.containsKey(userId) && users.containsKey(friendId)) {
            users.get(userId).getFriendIds().add(friendId);
            users.get(friendId).getFriendIds().add(userId);
            log.debug("Пользователь с id {} добавил пользователя с id {} в друзья. ", userId, friendId);
        } else {
            throw new UserNotFoundException("Пользователь с данным id не найден.");
        }
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        if (users.containsKey(userId) && users.containsKey(friendId)) {
            User user = users.get(userId);
            if (!user.getFriendIds().contains(friendId)) {
                users.get(userId).getFriendIds().remove(friendId);
                users.get(friendId).getFriendIds().remove(userId);
                log.debug("Пользователь с id {} удалил из друзей пользователя с id {}. ", userId, friendId);
            }
        } else {
            throw new UserNotFoundException("Пользователь с данным id не найден.");
        }
    }
}
