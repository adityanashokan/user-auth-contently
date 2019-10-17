package com.stackroute.userauthentication.service;

import com.stackroute.userauthentication.exceptions.UserAlreadyExistsExceptions;
import com.stackroute.userauthentication.model.User;

public interface UserService {

    User saveUser(User user) throws UserAlreadyExistsExceptions;

    User findByUsername(String username);
}
