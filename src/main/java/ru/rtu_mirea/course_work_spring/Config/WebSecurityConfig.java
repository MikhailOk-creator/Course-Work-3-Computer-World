package ru.rtu_mirea.course_work_spring.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.rtu_mirea.course_work_spring.Model.Role;
import ru.rtu_mirea.course_work_spring.Service.UserDetService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetService userService;

    public WebSecurityConfig(UserDetService userService) {
        this.userService = userService;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/checkClickedButton/*", "/end", "/addToCart/*", "/cart/**", "/order/**").permitAll()
                    .antMatchers("/", "/test").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/registrationAcc").permitAll()
                    .antMatchers("/images/*").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/main").permitAll()
                    .antMatchers("/worker/*").hasAuthority(Role.WORKER.getAuthority())
                    .antMatchers("/user/*").permitAll()
                    .antMatchers("/admin/*").hasAuthority(Role.ADMIN.getAuthority())
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(encoder());
    }

    @Bean
    protected BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }
}
