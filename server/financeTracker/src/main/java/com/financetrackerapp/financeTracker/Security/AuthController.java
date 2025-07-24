package com.financetrackerapp.financeTracker.Security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class AuthController {

    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationResource jwtAuthenticationResource;

    public AuthController(JdbcUserDetailsManager userDetailsManager,
                          PasswordEncoder passwordEncoder,
                          JwtAuthenticationResource jwtAuthenticationResource) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationResource = jwtAuthenticationResource;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        if (userDetailsManager.userExists(userDto.getEmail())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        UserDetails user = User.builder()
                .username(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles("USER")
                .build();

        userDetailsManager.createUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    
}