package com.example.donfood.configuration;

import com.example.donfood.service.security.AccountUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@Profile("mysql")
@Slf4j
public class SecurityJpaConfig {
    private final AccountUserDetailsService securityService;

    public SecurityJpaConfig(AccountUserDetailsService accountUserDetailsService) {
        this.securityService = accountUserDetailsService;
    }

    public UserDetailsService userDetailsService(){
        return new AccountUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(securityService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api", "/api/login", "/api/ong", "/api/ong/**", "/api/ong/edit/**").permitAll()
                .requestMatchers("/logout","/webjars/**","/api/access_denied","/img/logo1.png","/img/logo2.png").permitAll()
                .requestMatchers("/img/logo3.png", "/h2-console", "/api/registerRestaurant", "/api/registerONG").permitAll()
                .requestMatchers("/api/restaurant","/api/restaurant/**", "/api/restaurant/edit/**", "/api/restaurant/delete/**").hasAuthority("ONG")
                .requestMatchers("/api/donation", "/api/donation/**", "/api/donation/edit/**", "/api/donation/delete/**").hasAuthority("ONG")
                .requestMatchers("/profile").hasAnyAuthority("ONG", "ADMIN", "RESTAURANT")

                .and()
                .formLogin()
                //.loginPage("/api/login")
                //.loginProcessingUrl("/perform_login")
                .successForwardUrl("/api")

                .and()
                .logout()
                .logoutSuccessUrl("/api")

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())

                .and()
                .build();
    }
}
