// package com.dev.loja;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
// import org.springframework.security.crypto.bcrypt.BCrypt;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// @Configuration
// @EnableWebSecurity
// public class SecurityAdministrativo extends WebSecurityConfiguration {

// 	@Autowired
// 	private DataSource dataSource;

//     @Bean
//     public BCryptPasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

// 	@Override
// 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
// 		// neste método que vamos tratar os usuários

// 		auth.inMemoryAuthentication().withUser("user")
// 				.password(new BCryptPasswordEncoder().encode("123")).roles("USER")
//                 .and().withUser("admin")
//                 .password(new BCryptPasswordEncoder()
//                 .encode("admin")).roles("USER", "ADMIN");

//         auth.jdbcAuthentication().dataSource(dataSource)
//             .usersByUsernameQuery(
//                 "select email as username, senha as password, 1 as enable from funcionario where email=?")
//             .authoritiesByUsernameQuery(
//                 "select funcionario.email as username, role.nome as authority from permissoes_funcionario")
//             .passwordEncoder(new BCryptPasswordEncoder());

// 	}

// 	@Override
// 	protected void configure(HttpSecurity http) throws Exception {
// 		http.csrf().disable().authorizeHttpRequests().requestMatchers("/administrativo/entrada/cadastro").permitAll()
// 				.requestMatchers("/administrativo/entrada/listar").hasAnyRole("ADMIN", "USER")
// 				.and().formLogin().loginPage("/login").permitAll().and().logout()
//                 .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/administrativo").and()
//                 .exceptionHandling().accessDeniedPage("/negado");

// 	}

// }