package com.example.ApplicationLogin.LoginController;

//ALL IMPORTS FOR LOGINCONTROLLER , TI IS THE MAIN CLASS

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ApplicationLogin.DTO.BaseRes;
import com.example.ApplicationLogin.DTO.LoginDTO;
import com.example.ApplicationLogin.DTO.UserDTO;
import com.example.ApplicationLogin.Entity.User;
import com.example.ApplicationLogin.Service.LoginService;
import com.example.ApplicationLogin.domain.Role;
import com.example.ApplicationLogin.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping(path = "/save")

    public BaseRes saveUser(@RequestBody UserDTO userDTO)
    {
        return loginService.addUser(userDTO);
    }

    //Create a path for login
    @PostMapping(path = "/login")

    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO)
    {
        LoginResponse loginResponse = loginService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);

    }

  

    //Have put user

//THIS IS CHATGPT CODE





    @GetMapping("/user/login")
    public ResponseEntity<List<User>> getUsers(){
        //here needs to provide an address for URI
        return ResponseEntity.created(URI.create("/api/v1/user/login")).body(loginService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User>saveUser(@RequestBody User user1){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(loginService.saveUser(user1));
    }


    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(loginService.saveRole(role));
    }

    @PostMapping("/role/addtoUser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        loginService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();

    }

    //Creating a get request for refresh token
    @GetMapping("/api/token/refresh")

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            //This is the try block where everything will be present
            try {
                String refresh_token  = authorizationHeader.substring("Bearer ".length()); //here we are removing the space
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                //setting username same as the decoded token
                String username = decodedJWT.getSubject();

                //After getting the username we need to load the user
                User user = loginService.getUser(username);


                //Passing it as a collection
                String getName = "";
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getUser_role().trim().matches(getName))
                        .sign(algorithm);


                //Second is refresh token


                //After refresh token is created


                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            }catch(Exception exception){

                //here you are setting an error
                log.error("Error logging in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());

                //Now creating a status for error
                response.setStatus(FORBIDDEN.value());

                //Now you are sending an error
                //so here the error is a 403 error message that says, that you are forbidden to have access to this

                Map<String, String> error = new HashMap<>();
                String access_token = "";
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new RuntimeException("Refresh token is missing");
        }
    }

}


@Data
class RoleToUserForm{
    private String username;
    private String roleName;

    public String getUsername() {
        return username;
    }

    public String getRoleName() {
        return roleName;
    }


}
