package com.example.ApplicationLogin.Service;

import com.example.ApplicationLogin.DTO.BaseRes;
import com.example.ApplicationLogin.DTO.LoginDTO;
import com.example.ApplicationLogin.DTO.UserDTO;
import com.example.ApplicationLogin.Entity.User;
//import com.example.ApplicationLogin.domain.Role;
import com.example.ApplicationLogin.domain.Role;
import com.example.ApplicationLogin.response.LoginResponse;
import org.springframework.context.annotation.Bean;


import java.util.List;

public interface LoginService {

    //FOR JWT TOKEN
    User saveUser(User user);

    @Bean
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();




    BaseRes addUser(UserDTO userDTO);


    LoginResponse loginUser(LoginDTO loginDTO);


    Role saveRole(Role role);
}
