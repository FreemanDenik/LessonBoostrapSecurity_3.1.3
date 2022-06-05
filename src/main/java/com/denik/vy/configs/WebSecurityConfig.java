package com.denik.vy.configs;

import com.denik.vy.models.Role;
import com.denik.vy.models.User;
import com.denik.vy.repositories.RoleRepository;
import com.denik.vy.repositories.UserRepository;
import com.denik.vy.services.UserSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginSuccessHandler loginSuccessHandler;
    private final UserSecurityService userSecurityService;
    WebSecurityConfig(LoginSuccessHandler loginSuccessHandler, UserSecurityService userSecurityService){
        this.loginSuccessHandler = loginSuccessHandler;
        this.userSecurityService = userSecurityService;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(loginSuccessHandler).permitAll()
                .and()
                .logout().permitAll();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userSecurityService);
        return provider;
    }
    @Bean
    public String AdminInit(UserRepository userRepository, RoleRepository roleRepository){

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        User adminUser = new User(
                "admin",
                passwordEncoder().encode("12345"),
                "admin@mail.ru",
                Arrays.asList(adminRole, userRole)
        );
        User userUser = new User(
                "user",
                passwordEncoder().encode("12345"),
                "user@mail.ru",
                Arrays.asList(userRole)
        );
        roleRepository.save(adminRole);
        roleRepository.save(userRole);
        userRepository.save(adminUser);
        userRepository.save(userUser);
        return null;
    }

}
