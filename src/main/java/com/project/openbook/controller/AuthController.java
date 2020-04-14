package com.project.openbook.controller;

import com.project.openbook.config.JwtTokenProvider;
import com.project.openbook.dto.LoginUserDto;
import com.project.openbook.dto.RegisterUserDto;
import com.project.openbook.dto.UserDto;
import com.project.openbook.model.User;
import com.project.openbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
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

    @PostMapping("login")
    public ResponseEntity loginUser(@RequestBody LoginUserDto loginUserDto) {
        try {
            String username = loginUserDto.username;
            String password = loginUserDto.password;
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtTokenProvider.createToken(username);
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            Map<Object, Object> model = new HashMap<>();
            model.put("error", "Invalid username/password supplied");
            return ResponseEntity.status(401).body(model);
        }
    }
}
