package com.demo.airport.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public BasicAuthenticationEntryPoint authenticationEntryPoint() {
        BasicAuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
        authenticationEntryPoint.setRealmName("airport-service");
        return authenticationEntryPoint;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        http
            .logout(logout -> logout
                .logoutUrl("/basic/basiclogout")
                .addLogoutHandler(new SecurityContextLogoutHandler())
            );

        AuthenticationManagerBuilder auth =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        auth
            .inMemoryAuthentication()
            .withUser("app-user1")
            .password(passwordEncoder().encode("user-pass1"))
            .authorities("ROLE_USER");
        auth
            .inMemoryAuthentication()
            .withUser("app-user2")
            .password(passwordEncoder().encode("user-pass2"))
            .authorities("ROLE_ADMIN");
        return auth.build();

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/api/**").authenticated()
            .and()
            .headers().frameOptions().disable()
            .and()
            .httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint())
            .and()
            .build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${cors.origins}") String corsOrigins) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET"));
        corsConfiguration.setAllowedOrigins(List.of(corsOrigins.split(",")));

        UrlBasedCorsConfigurationSource configurationSource =
            new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/api/**", corsConfiguration);
        return configurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
