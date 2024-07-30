//THIS IS FOR SIGNUP

package com.example.ApplicationLogin.DTO;


public class UserDTO {

    private int user_id;
    private String user_fullname;
    private String user_mobile;
    private String user_email;
    private String user_type;
    private String username;
    private String password;
    private String user_role;


    //Creating full constructors
    public UserDTO(int user_id, String user_fullname, String user_mobile, String user_email, String user_type, String username, String password,String user_role) {
        this.user_id = user_id;
        this.user_fullname = user_fullname;
        this.user_mobile = user_mobile;
        this.user_email = user_email;
        this.user_type = user_type;
        this.username = username;
        this.password = password;
        this.user_role = user_role;
    }


    //Creating empty constructor

    public UserDTO() {
    }

    //Create getters and setters

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



    //Create toString() methods function also

    @Override
    public String toString() {
        return "UserDTO{" +
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
