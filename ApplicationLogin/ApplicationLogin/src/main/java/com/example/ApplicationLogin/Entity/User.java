package com.example.ApplicationLogin.Entity;
import jakarta.persistence.*;


@Entity
@Table(name="users")

public class User {

    @Id

    @Column(name = "user_id", length=45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;

    @Column(name = "user_fullname", length=50)
    private String user_fullname;

    @Column(name = "user_mobile", length=30)
    private String user_mobile;

    @Column(name = "user_email" , length=255)
    private String user_email;

    @Column(name="user_type", length=255)
    private String user_type;

    @Column(name="username", length=255 , unique = true)
    private String username;

    @Column(name = "password", length=255)
    private String password;

    @Column(name = "user_role", length=50)
    private String user_role;


    //Full constructors for the fields
    public User(int user_id, String user_fullname, String user_mobile, String user_email, String user_type, String username, String password, String user_role) {
        this.user_id = user_id;
        this.user_fullname = user_fullname;
        this.user_mobile = user_mobile;
        this.user_email = user_email;
        this.user_type = user_type;
        this.username = username;
        this.password = password;
        this.user_role = user_role;
    }

    //Empty constructor is needed
    public User() {
    }

    public User(int userId, String username, String encode) {
    }

    public User(int userId, String userFullname, String userMobile, String userEmail, String userType, String username, String encode) {
    }

    //Getters and setters needed
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

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

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }



    //Getting all toString() methods


    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_fullname='" + user_fullname + '\'' +
                ", user_mobile='" + user_mobile + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_type='" + user_type + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", user_role='" + user_role + '\'' +
                '}';
    }



}
