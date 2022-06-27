package com.intro.microservices.restfulwebservices.service;

import com.intro.microservices.restfulwebservices.common.Messages;
import com.intro.microservices.restfulwebservices.dto.User;
import com.intro.microservices.restfulwebservices.exceptions.UnableToProcessException;
import com.intro.microservices.restfulwebservices.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void test_userIdEntered_saveUser() {
        User user = new User();
        user.setId(123); // To create a negative scenario, I am not allowing the user to enter the id. User id will be auto generated in the database.
        user.setFirstName("Test");
        user.setLastName("User");

        UnableToProcessException thrown = Assertions
                .assertThrows(UnableToProcessException.class, () -> userService.saveUser(user), Messages.DO_NOT_ENTER_USER_ID);

        Assertions.assertEquals(Messages.DO_NOT_ENTER_USER_ID, thrown.getMessage());

    }

    @Test
    void test_saveUser() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");

        userService.saveUser(user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void test_getAllUser() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");

        User user2 = new User();
        user2.setFirstName("Test2");
        user2.setLastName("User2");

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        Assertions.assertEquals(2, userService.getAllUser().size());
    }

    @Test
    void test_getAllUserWhenNoDataPresent() {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());

        UnableToProcessException thrown = Assertions
                .assertThrows(UnableToProcessException.class, () ->
                        userService.getAllUser(), Messages.NO_USERS_FOUND);

        Assertions.assertEquals(Messages.NO_USERS_FOUND, thrown.getMessage());
    }

    @Test
    void test_findUserByInvalidId() {
        UnableToProcessException thrown = Assertions
                .assertThrows(UnableToProcessException.class, () -> userService.findUserById(123), String.format(Messages.NO_USER_FOUND_WITH_ID, 123));
        Assertions.assertEquals(String.format(Messages.NO_USER_FOUND_WITH_ID, 123), thrown.getMessage());
    }

    @Test
    void test_findUserById() {
        User user = new User();
        user.setId(123);
        user.setFirstName("Test");
        user.setLastName("User");

        Mockito.when(userRepository.findById(123)).thenReturn(Optional.of(user));
        Assertions.assertEquals(123, userService.findUserById(123).getId());
    }
}