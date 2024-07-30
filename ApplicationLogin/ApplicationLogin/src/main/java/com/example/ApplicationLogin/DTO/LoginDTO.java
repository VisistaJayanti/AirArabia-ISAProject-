package com.example.ApplicationLogin.DTO;

public class LoginDTO {

    private String username;
    private String password;


    //generate filled constructors
    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }


    //generate empty consructors

    public LoginDTO() {
    }


    //Generate getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //Generate string methods

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
