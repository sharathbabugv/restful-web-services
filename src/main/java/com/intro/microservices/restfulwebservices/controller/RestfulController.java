package com.intro.microservices.restfulwebservices.controller;

import com.intro.microservices.restfulwebservices.dto.User;
import com.intro.microservices.restfulwebservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class RestfulController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @PutMapping
    public User updateUserById(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
    }

}
