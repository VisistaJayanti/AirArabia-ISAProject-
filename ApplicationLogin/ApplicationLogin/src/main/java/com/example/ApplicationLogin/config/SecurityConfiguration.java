package com.example.ApplicationLogin.config;

import com.example.ApplicationLogin.filter.CustomAuthenticationFilter;
import com.example.ApplicationLogin.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;
    //
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll()
//                );
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CustomAuthenticationFilter handles the login and token generation
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(http));
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/user/login");

        // CSRF protection is disabled since this is a stateless API (JWT based)
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (no authentication required
                                .requestMatchers("*").permitAll()
//                        .requestMatchers("/api/v1/user/save", "/api/v1/user/login", "/api/token/refresh").permitAll()
                        // Secured endpoints (authentication and specific roles required)
                        .requestMatchers("/api/v1/user").hasAnyAuthority("ROLE_USER")
                        .requestMatchers("/api/v1/user/save").hasAnyAuthority("ROLE_ADMIN")
                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )
                .addFilter(customAuthenticationFilter) // Add custom authentication filter
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // Add custom authorization filter

        return http.build();
    }
}






//THIS IS THE ACTUAL CODE
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(http));
//        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/user/save");
//
//        //Adding this method for ensuring the api is excluded from authorization but not authentication
//
//        //Using this method instead of csrf.disablehttp
//        http
//                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/user/save", "api/v1/user/login", "/api/token/refresh").permitAll()
//                        .requestMatchers("/api/v1/user").hasAnyAuthority("ROLE_USER")
//                        .requestMatchers("/api/v1/user/login").hasAnyAuthority("ROLE_ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .addFilter(customAuthenticationFilter)
//                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//
//        return http.build();
//    }
//}


    //OVERRIDING THESE CONFIGURATIONS
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//    //}
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception
//    }


