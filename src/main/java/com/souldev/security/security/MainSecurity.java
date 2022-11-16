package com.souldev.security.security;

import com.souldev.security.security.jwt.JwtEntryPoint;
import com.souldev.security.security.jwt.JwtTokenFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity {
    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean 
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth/**").permitAll()
        .antMatchers("/persona/listar-personas").permitAll()
        .antMatchers("/persona/listar-persona-resume").permitAll()
        .antMatchers("/persona/listar-persona-aboutMe").permitAll()
        .antMatchers("/curso/listar-cursos").permitAll()
        .antMatchers("/carrera/listar-carreras").permitAll()
        .antMatchers("/persona-curso/listar-cursos-persona").permitAll()
        .antMatchers("/persona-curso/listar-personas-curso").permitAll()
        .antMatchers("/persona-carrera/listar-carreras-persona").permitAll()
        .antMatchers("/persona-carrera/listar-personas-carrera").permitAll()
        .antMatchers("/experiencia/listar-experiencia/").permitAll()
        .antMatchers("/proyecto/listar-proyectos/").permitAll()
        .antMatchers("/referencia/listar-referencias/").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
