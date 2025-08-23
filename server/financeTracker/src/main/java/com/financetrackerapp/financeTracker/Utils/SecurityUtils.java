package com.financetrackerapp.financeTracker.Utils;

import com.financetrackerapp.financeTracker.Entity.Users;
import com.financetrackerapp.financeTracker.Repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    
    private final UsersRepository usersRepository;

    public SecurityUtils(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }

        String username;
        Object principal = authentication.getPrincipal();

        if (principal instanceof Jwt) {
            // Handle JWT authentication
            Jwt jwt = (Jwt) principal;
            username = jwt.getSubject();
        } else if (principal instanceof org.springframework.security.core.userdetails.User) {
            // Handle form-based authentication
            username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        } else if (principal instanceof String) {
            // Handle basic authentication
            username = (String) principal;
        } else {
            throw new IllegalStateException("Unexpected authentication principal type: " + principal.getClass().getName());
        }

        return usersRepository.findByEmail(username)
                .orElseThrow(() -> new SecurityException("User not found with email: " + username));
    }
}
