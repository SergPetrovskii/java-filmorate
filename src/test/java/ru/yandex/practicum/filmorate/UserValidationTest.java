package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storages.FriendshipStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserValidationTest {

    @Autowired
    private final UserStorage userStorage;

    private final FriendshipStorage friendshipStorage;

    @Test
    public void createUsers() {
        User newUser1 = userStorage.createUser(User.builder()
                .name("dolore")
                .login("dolore")
                .email("msaddaail@mail.ru")
                .birthday(LocalDate.of(1999, 01, 20))
                .build());
        Assertions.assertNotNull(newUser1);
    }

    @Test
    public void getUser() {
        User newUser = userStorage.createUser(User.builder()
                .name("dolore")
                .login("ne12w dosdqdqlore")
                .email("nedsadsadwmail@mail.ru")
                .birthday(LocalDate.of(1999, 01, 20))
                .build());
        Assertions.assertNotNull(newUser);

        User userOptional = userStorage.getUserForId(8);
        Assertions.assertEquals(userOptional, newUser);
    }

    @Test
    public void getUserList() {
        User newUser = userStorage.createUser(User.builder()
                .name("dolowqewewqedsadre")
                .login("nerrrrore")
                .email("sdasdamail@mail.ru")
                .birthday(LocalDate.of(1999, 01, 20))
                .build());
        Assertions.assertNotNull(newUser);

        List<User> userList = userStorage.getAllUsers();
        Assertions.assertNotNull(userList);

    }

    @Test
    public void updateUser() {
        User newUser = userStorage.createUser(User.builder()
                .name("dolore")
                .login("nfffffffew dolore")
                .email("newmailfffffff@mail.ru")
                .birthday(LocalDate.of(1999, 01, 20))
                .build());
        Assertions.assertNotNull(newUser);

        User userUpdate = userStorage.updateUser(User.builder()
                .id(1)
                .name("asdfg")
                .login("new")
                .email("asdfghj@mail.ru")
                .birthday(LocalDate.of(1999, 01,20))
                .build());

        Assertions.assertNotEquals(userUpdate, newUser);
    }

    @Test
    public void addFriendAndGetForID() {
        User newUser = userStorage.createUser(User.builder()
                .name("dolore")
                .login("new dolore")
                .email("newmail@mail.ru")
                .birthday(LocalDate.of(1999, 01, 20))
                .build());
        Assertions.assertNotNull(newUser);

        User userFriend = userStorage.createUser(User.builder()
                .name("dolore Friend")
                .login("dolornd")
                .email("mlsfasa@mail.ru")
                .birthday(LocalDate.of(1999, 01,21))
                .build());

        friendshipStorage.addFriend(1, 2);
        List<User> friendList = friendshipStorage.getFriendsUserForId(1);
        Assertions.assertNotNull(friendList);
    }

    @Test
    public void deleteFriend() {
        User newUser = userStorage.createUser(User.builder()
                .name("dolore")
                .login("nesdaqweww dolore")
                .email("neuygfvcwmail@mail.ru")
                .birthday(LocalDate.of(1999, 01, 20))
                .build());
        Assertions.assertNotNull(newUser);

        User userFriend = userStorage.createUser(User.builder()
                .name("dolore Friend")
                .login("dd")
                .email("m@mail.ru")
                .birthday(LocalDate.of(1999, 01,21))
                .build());

        friendshipStorage.deleteFriend(1, 2);
        List<User> friendList = friendshipStorage.getFriendsUserForId(1);
        Assertions.assertNotEquals(List.of(userFriend), friendList);
    }

    @Test
    public void listCommonFriend() {
        User newUser = userStorage.createUser(User.builder()
                .name("dolore")
                .login("new dolore")
                .email("newmail@mail.ru")
                .birthday(LocalDate.of(1999, 01, 20))
                .build());
        Assertions.assertNotNull(newUser);

        User userFriend2 = userStorage.createUser(User.builder()
                .name("dolore Friend")
                .login("dqwerolore Friend")
                .email("qwertmail@mail.ru")
                .birthday(LocalDate.of(1999, 01,21))
                .build());

        User userFriend = userStorage.createUser(User.builder()
                .name("dolore Friend")
                .login("dolore Friend")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1999, 01,21))
                .build());

        friendshipStorage.addFriend(1, 2);
        friendshipStorage.addFriend(3, 2);

        List<User> listFriend = friendshipStorage.getListFriend(1, 3);

        Assertions.assertEquals(listFriend, List.of(userFriend2));
    }
}
