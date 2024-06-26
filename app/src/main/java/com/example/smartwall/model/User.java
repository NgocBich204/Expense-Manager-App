package com.example.smartwall.model;

public class User {

    private String fullName;
    private String email;

    public User(String fullName, String email) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullName, String email, String birthdate) {
        this.fullName = fullName;
        this.email = email;

    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }


}