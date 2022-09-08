package com.edgarfrancisco;

import com.edgarfrancisco.security.filter.JwtAccessDeniedHandler;
import com.edgarfrancisco.security.filter.JwtAuthenticationEntryPoint;
import com.edgarfrancisco.security.utility.JWTTokenProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MybooksBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(MybooksBackendApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public JWTTokenProvider jwtTokenProvider() { return new JWTTokenProvider(); }
	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() { return new JwtAuthenticationEntryPoint(); }
	@Bean
	public JwtAccessDeniedHandler jwtAccessDeniedHandler() { return new JwtAccessDeniedHandler(); }

}
