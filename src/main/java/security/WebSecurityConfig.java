/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
 




/**
 *
 * @author veritas-estagio
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
 @Autowired
 ComercialUserDetailsService comercialUserDetailsService;
    
    
@Override
protected void configure(HttpSecurity http) throws Exception{
    http.authorizeRequests().anyRequest().authenticated().and().formLogin();
}
    
@Override
protected void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(comercialUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    
} 
}
