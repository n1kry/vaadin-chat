package com.iongroup.vaadinchat.configuration;

import com.iongroup.vaadinchat.dao.UserEntityDao;
import com.iongroup.vaadinchat.entity.UserEntity;
import com.iongroup.vaadinchat.view.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends VaadinWebSecurity {

    private final UserEntityDao userEntityDao;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) ->
                    authorize
                            .requestMatchers(new AntPathRequestMatcher("/public/**")).anonymous()
            );

        super.configure(http);

        setLoginView(http, LoginView.class);
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .requestMatchers("/webjars/**", "/line-awesome/**","/books/**");
        super.configure(web);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        final UserDetailsService userDetailsService = username -> {
            final UserEntity user = userEntityDao.findByUsername(username);
            final GrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");
            return new User(user.getUsername(), user.getPassword(), Collections.singletonList(userAuthority));
        };
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
