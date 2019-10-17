package com.stackroute.userauthentication.service;


import com.stackroute.userauthentication.exceptions.UserAlreadyExistsExceptions;
import com.stackroute.userauthentication.model.User;
import com.stackroute.userauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) throws UserAlreadyExistsExceptions {
        try {
            return userRepository.save(user);
        } catch (Exception e){
            throw new UserAlreadyExistsExceptions();
        }
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
