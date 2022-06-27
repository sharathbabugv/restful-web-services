package com.intro.microservices.restfulwebservices.service;

import com.intro.microservices.restfulwebservices.common.Messages;
import com.intro.microservices.restfulwebservices.common.UserUtils;
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

    /**
     * Following method will save the user in database.
     *
     * @param user
     */
    public void saveUser(User user) {
        if (UserUtils.isUserValid(user) && user.getId() == null) {
            userRepository.save(user);
        } else {
            throw new UnableToProcessException(Messages.DO_NOT_ENTER_USER_ID);
        }
    }

    /**
     * Retrieves list of all the users from the database
     *
     * @return
     */
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

    /**
     * Finds the user in the database, based on the given user id
     *
     * @param id
     * @return
     */
    public User findUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UnableToProcessException(String.format(Messages.NO_USER_FOUND_WITH_ID, id));
    }

    /**
     * Updates the user in the database.
     *
     * @param user
     * @return
     */
    public User updateUser(User user) {
        if (UserUtils.isUserValid(user) && user.getId() != null && userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        } else {
            throw new UnableToProcessException(Messages.USER_ID_NOT_FOUND);
        }
    }

    /**
     * Deletes the user from the database, based on the given user id
     *
     * @param id
     */
    public void deleteUserById(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UnableToProcessException(Messages.USER_ID_NOT_FOUND);
        }
    }
}
