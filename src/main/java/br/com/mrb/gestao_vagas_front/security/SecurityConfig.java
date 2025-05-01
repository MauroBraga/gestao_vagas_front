package br.com.mrb.gestao_vagas_front.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->{
                    authorizeRequests
                            .requestMatchers("/candidate/login", "/candidate/signIn", "/candidate/create").permitAll()
                            .anyRequest().authenticated();
                }).formLogin(form -> form.loginPage("/candidate/login"));
        return http.build();
    }
}
