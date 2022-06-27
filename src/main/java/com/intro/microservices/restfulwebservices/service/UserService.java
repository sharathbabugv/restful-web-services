package com.intro.microservices.restfulwebservices.service;

import com.intro.microservices.restfulwebservices.common.Messages;
import com.intro.microservices.restfulwebservices.dto.User;
import com.intro.microservices.restfulwebservices.exceptions.UnableToProcessException;
import com.intro.microservices.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        if (user.getId() == null) {
            userRepository.save(user);
        } else {
            throw new UnableToProcessException(Messages.DO_NOT_ENTER_USER_ID);
        }
    }

    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userList.add(user);
        }
        if (!userList.isEmpty()) {
            return userList;
        }
        throw new UnableToProcessException(Messages.NO_USERS_FOUND);
    }

    public User findUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UnableToProcessException(String.format(Messages.NO_USER_FOUND_WITH_ID, id));
    }

    public User updateUser(User user) {
        if (user.getId() != null && userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        } else {
            throw new UnableToProcessException(Messages.USER_ID_NOT_FOUND);
        }
    }

    public void deleteUserById(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UnableToProcessException(Messages.USER_ID_NOT_FOUND);
        }
    }
}
