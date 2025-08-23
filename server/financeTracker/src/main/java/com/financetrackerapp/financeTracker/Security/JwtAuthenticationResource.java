package com.financetrackerapp.financeTracker.Security;

import com.financetrackerapp.financeTracker.Dto.LoginResponse;
import com.financetrackerapp.financeTracker.Entity.Users;
import com.financetrackerapp.financeTracker.Repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class JwtAuthenticationResource {
    private final JwtEncoder jwtEncoder;
    private final UsersRepository usersRepository;

    public JwtAuthenticationResource(JwtEncoder jwtEncoder, UsersRepository usersRepository) {
        this.jwtEncoder = jwtEncoder;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/authenticate")
    public LoginResponse authenticate(Authentication authentication) {
        String token = createToken(authentication);
        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new LoginResponse(token, user);
    }

    private String createToken(Authentication authentication) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder().issuer("admin")
                .issuedAt(Instant.now()).expiresAt(Instant.now().plusSeconds(60 * 3))
                .subject(authentication.getName()).claim("scope", createClaims(authentication)).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String createClaims(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));
    }
}
