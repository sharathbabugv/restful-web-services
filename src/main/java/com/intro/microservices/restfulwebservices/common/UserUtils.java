package com.intro.microservices.restfulwebservices.common;

import com.intro.microservices.restfulwebservices.dto.User;
import com.intro.microservices.restfulwebservices.exceptions.UnableToProcessException;

public class UserUtils {

    public static boolean isUserValid(User user) {
        if (user.getFirstName() != null && user.getFirstName().trim().length() > 2 && user.getLastName() != null && user.getLastName().trim().length() > 2) {
            return true;
        }
        throw new UnableToProcessException(Messages.NAME_VALIDATION);
    }
}
