package com.project.openbook.controller;

import com.project.openbook.dto.RegisterUserDto;
import com.project.openbook.model.User;
import com.project.openbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("register")
    public User createUser( @RequestBody RegisterUserDto registerUserDto) {
        User user = new User();
        user.setName(registerUserDto.name);
        user.setPassword(passwordEncoder.encode(registerUserDto.password));
        user.setUsername(registerUserDto.username);
        user = this.userRepository.save(user);

        return user;
    }
}
