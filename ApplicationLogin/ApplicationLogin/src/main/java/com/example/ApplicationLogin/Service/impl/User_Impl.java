package com.example.ApplicationLogin.Service.impl;

//IMPORTS FOR JWT TOKEN
import com.example.ApplicationLogin.DTO.BaseRes;
import com.example.ApplicationLogin.DTO.LoginDTO;
import com.example.ApplicationLogin.DTO.UserDTO;
import com.example.ApplicationLogin.Entity.User;
import com.example.ApplicationLogin.Repo.UserRepo;
import com.example.ApplicationLogin.Service.LoginService;
import com.example.ApplicationLogin.domain.Role;
import com.example.ApplicationLogin.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class User_Impl implements LoginService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(User_Impl.class);

    @Autowired
    public UserRepo userRepo;



    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;



    //ADDING THE METHODS
    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return List.of();
    }

    //END OF THE METHODS

    @Override
    public BaseRes addUser(UserDTO userDTO) {

        //Here we are creating new object and setting it
        User user = new User(
                userDTO.getUser_id(),
                userDTO.getUser_fullname(),
                userDTO.getUser_mobile(),
                userDTO.getUser_email(),
                userDTO.getUser_type(),
                userDTO.getUsername(),
                bCryptPasswordEncoder.encode(userDTO.getPassword())

        );
        boolean success = true;
        String msg = "USER ADDED SUCCESSFULLY";
        try {
            userRepo.save(user);
        } catch (Exception e) {  // Improved generic exception handling
            success = false;
            msg = "An error occurred: " + e.getMessage();
            logger.error("An error occurred", e);  // Log error
        }

//        return user.getUser_fullname();

        BaseRes res = new BaseRes();
        res.setSuccess(success);
        res.setMessage(msg);

        return res;
    }

    //Implement the method that is responsible for login users
    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {

        String msg = "";
        User user = userRepo.findByUsername(loginDTO.getUsername());

        //Condition for user
        if (user != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user.getPassword();
            Boolean isPwdRight = bCryptPasswordEncoder.matches(password, encodedPassword);

            if (isPwdRight) {
                Optional<User> user1 = userRepo.findOneByUsernameAndPassword(loginDTO.getUsername(), encodedPassword);
                if (user1.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("Password does not match", false);
            }
        } else {
            return new LoginResponse("Username does not exist", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}





