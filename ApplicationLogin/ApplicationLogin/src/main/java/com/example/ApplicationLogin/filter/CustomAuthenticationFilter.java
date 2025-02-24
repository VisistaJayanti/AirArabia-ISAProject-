package com.example.ApplicationLogin.filter;

//ALL IMPORTS FOR AUTHENTICATION
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ApplicationLogin.Service.impl.User_Impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

        private static final Logger log = LoggerFactory.getLogger(User_Impl.class);
        private final AuthenticationManager authenticationManager;

        public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {

            //passing parameter constructor for authentication manager

            this.authenticationManager = authenticationManager;
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

            //THE CODE FOR GETTING USERNAME AND PASSWORD
            StringBuilder sab = new StringBuilder();
            try {
                BufferedReader reader = request.getReader();
                reader.mark(10000);

                String line;
                do {
                    line = reader.readLine();
                    sab.append(line).append("\n");
                } while (line != null);
                reader.reset();
                // do NOT close the reader here, or you won't be able to get the post data twice
            } catch(IOException e) {
                logger.warn("getPostData couldn't.. get the post data", e);  // This has happened if the request's reader is closed
            }

            String string = sab.toString();

            //TO GET THE USERNAME AND PASSWORD REQUEST


            String username = request.getParameter("username");
            String password = request.getParameter("password");
            log.info("Username is: {}", username);
            log.info("Password is: {}", password);

                //object for token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
                return authenticationManager.authenticate(authenticationToken);


        }


        //FOR PROPER AUTHENTICATION
        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
            super.successfulAuthentication(request, response, chain, authResult);
            User user = (User) authResult.getPrincipal();
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());


            //Two tokens are created
            //One is access token
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURI().toString())
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);


            //Second is refresh token
            String refresh_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                    .withIssuer(request.getRequestURI().toString())
                    .sign(algorithm);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", access_token);
            tokens.put("refresh_token", refresh_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),tokens);

        }
    }




