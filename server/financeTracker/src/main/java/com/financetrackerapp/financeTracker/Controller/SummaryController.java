package com.financetrackerapp.financeTracker.Controller;

import com.financetrackerapp.financeTracker.Dto.SummaryStatsDto;
import com.financetrackerapp.financeTracker.Entity.Users;
import com.financetrackerapp.financeTracker.Repository.UsersRepository;
import com.financetrackerapp.financeTracker.Repository.UsersRepository;
import com.financetrackerapp.financeTracker.Service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/summary")
public class SummaryController {

    private final SummaryService summaryService;
    private final UsersRepository userRepository;

    @Autowired
    public SummaryController(SummaryService summaryService, UsersRepository userRepository) {
        this.summaryService = summaryService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getSummary() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            Object principal = authentication.getPrincipal();
            System.out.println("Principal: " + principal);
            
            String username;
            
            if (principal instanceof org.springframework.security.oauth2.jwt.Jwt) {
                // Handle JWT authentication
                org.springframework.security.oauth2.jwt.Jwt jwt = (org.springframework.security.oauth2.jwt.Jwt) principal;
                username = jwt.getSubject(); // The email is stored as the subject in the JWT
                System.out.println("Username: " + username);
            } else if (principal instanceof Users) {
                // If using custom Users class (for non-JWT auth)
                return ResponseEntity.ok(summaryService.getSummaryStats(((Users) principal).getId()));
            } else if (principal instanceof org.springframework.security.core.userdetails.User) {
                // If using Spring Security's default UserDetails
                username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unsupported authentication type: " + principal.getClass().getName());
            }
            
            // Find user by email (which is the username in JWT)
            Users user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));
            System.out.println("User found: " + user.getId());
            SummaryStatsDto summary = summaryService.getSummaryStats(user.getId());
            return ResponseEntity.ok(summary);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing request: " + e.getMessage());
        }
    }
}
