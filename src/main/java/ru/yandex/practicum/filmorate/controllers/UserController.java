package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIDException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
            return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void userAddFriend(@PathVariable("id") final Integer userId, @PathVariable("friendId") final Integer friendId) {
        userService.userAddFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void userDeleteFriend(@PathVariable("id") final Integer userId, @PathVariable("friendId") final Integer friendId) {
        userService.userDeleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getListFriend(@PathVariable("id") final Integer userId,
    @PathVariable("otherId") final Integer friendId) {
        return userService.getFriends(userId, friendId);
    }

    @GetMapping("/{id}")
    public User getUserForId(@PathVariable("id") Integer id) {
        return userService.getUserForId(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsUserForId(@PathVariable("id") Integer id) {
        return userService.getFriendsUserForId(id);
    }
}