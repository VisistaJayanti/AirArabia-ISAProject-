package com.example.ApplicationLogin.response;

public class LoginResponse {
    String message;
    Boolean status;

    //generate constructors

    public LoginResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    //generate empty constructors

    public LoginResponse() {
    }

    //generate getters and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    //to string methods also

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}

