package com.example.customer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

@EnableWebSecurity
@Configuration
public class CustomerSecurityConfig {

    private UserDetailsService userDetailsService;

    @Autowired

    public CustomerSecurityConfig(UserDetailsService userDetailsService)
    {
        this.userDetailsService=userDetailsService;
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/css/**","/assets/**", "/img/**","/imgs/**", "/js/**", "/fonts/**","/sass/**", "/lib/**","/scss/**","/vendor/**","/less/**","/dist/**","/register/**", "/do-register/**","/fonts/**", "/**", "/address").permitAll()
                        .requestMatchers("/user/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/dashboard").authenticated()
                        .anyRequest().authenticated()
        )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/do_login")
                                .defaultSuccessUrl("/index",true)
                                .failureUrl("/login?error")
                                .permitAll()

                )
                .logout(
                        logout -> logout
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .invalidSessionUrl("/login?logout")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false));
         return http.build();



    }


}
