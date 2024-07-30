package com.example.ApplicationLogin;

import com.example.ApplicationLogin.Service.LoginService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class ApplicationLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationLoginApplication.class, args);
	}

	//FOR JWT TOKEN

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	//creating a bean

	@Bean
	CommandLineRunner run(LoginService loginService){
		return args-> {
		};
	}

}

