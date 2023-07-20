package com.dev.loja.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/administrativo").hasAnyRole("SALES", "ADMIN")
            .requestMatchers("/administrativo/clientes/**").hasAnyRole("SALES", "ADMIN")
            .requestMatchers("/administrativo/funcionarios/**").hasRole("ADMIN")
            .requestMatchers("/administrativo/produtos/listar").hasAnyRole("SALES", "ADMIN")
            .requestMatchers("/administrativo/venda/cadastrar").hasAnyRole("SALES", "ADMIN")
            .requestMatchers("/administrativo/venda/listar").hasRole("ADMIN")
            .requestMatchers("/administrativo/despesas/cadastrar").hasRole("ADMIN")
            .requestMatchers("/administrativo/financeiro").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .csrf().disable();
            
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
