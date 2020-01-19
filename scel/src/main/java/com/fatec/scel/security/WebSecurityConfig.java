package com.fatec.scel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/usuarios/cadastrar").hasAnyRole("ADMIN")
		.antMatchers("/livros/cadastrar").hasAnyRole("USUARIO")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login")
		.permitAll()
		.and().logout().permitAll();
	}
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			
			.withUser("carlos").password(pc().encode("123")).roles("ADMIN")
			.and()
			.withUser("flavio").password(pc().encode("123")).roles("USUARIO");
	}
	@Bean
	public BCryptPasswordEncoder pc() {
		return new BCryptPasswordEncoder();
	}
}
