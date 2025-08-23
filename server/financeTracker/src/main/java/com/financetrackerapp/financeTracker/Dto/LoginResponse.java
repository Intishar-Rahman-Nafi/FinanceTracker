package com.financetrackerapp.financeTracker.Dto;

import com.financetrackerapp.financeTracker.Entity.Users;

public class LoginResponse {
    private String token;
    private UserDto user;

    public LoginResponse(String token, Users user) {
        this.token = token;
        this.user = new UserDto(user);
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public static class UserDto {
        private Long id;
        private String email;
        private String name;
        private String role;
        private String currencyPreference;

        public UserDto(Users user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
            this.role = user.getRole();
            this.currencyPreference = user.getCurrencyPreference();
        }

        // Getters and setters
        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        public String getCurrencyPreference() {
            return currencyPreference;
        }
    }
}
