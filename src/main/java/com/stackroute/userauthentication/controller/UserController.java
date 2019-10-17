package com.stackroute.userauthentication.controller;


import com.stackroute.userauthentication.exceptions.InvalidCredentialsException;
import com.stackroute.userauthentication.exceptions.UserAlreadyExistsExceptions;
import com.stackroute.userauthentication.model.User;
import com.stackroute.userauthentication.service.RabbitMQSender;
import com.stackroute.userauthentication.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.Date;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RabbitMQSender rabbitMQSender;

    @Autowired
    public UserController(UserService userService, RabbitMQSender rabbitMQSender) {
        this.userService = userService;
        this.rabbitMQSender = rabbitMQSender;
    }



    @PostMapping(value = "/register")
    public User registerUser(@RequestBody User user) throws UserAlreadyExistsExceptions {

        User newuser = new User();
        newuser.setUsername(user.getUsername());
        newuser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userService.saveUser(newuser);
    }


    @PostMapping(value = "/login")
    public String login(@RequestBody User user) throws ServletException, InvalidCredentialsException {
        String jwtToken = "";


        String username = user.getUsername();
        String password = user.getPassword();

        User matchUser = userService.findByUsername(username);

        if(matchUser == null){
            throw new InvalidCredentialsException();
        }



        if(!BCrypt.checkpw(password, matchUser.getPassword())){
            throw new InvalidCredentialsException();
        }

        jwtToken = Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

        rabbitMQSender.send("Token : " + jwtToken);
        rabbitMQSender.send("User : " + user.getUsername());
        //System.out.println("##################################TOKEN SENT#####################################");
        return jwtToken;
    }

    @GetMapping(value = "/secure")
    public String loginSuccessful(){
        return "LOGIN SUCCESSFUL";
    }
}
