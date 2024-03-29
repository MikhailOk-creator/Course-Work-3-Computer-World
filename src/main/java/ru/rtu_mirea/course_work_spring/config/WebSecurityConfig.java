package ru.rtu_mirea.course_work_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.rtu_mirea.course_work_spring.entity.Role;
import ru.rtu_mirea.course_work_spring.service.UserDetService;

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
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/images/**").permitAll()
                    .antMatchers("/TheSecondVersion_PastCourseWork/**").permitAll()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/css/*", "/scripts/*").permitAll()
                    .antMatchers("/checkClickedButton/*", "/end", "/addToCart/*", "/cart/**", "/order/**").permitAll()
                    .antMatchers("/", "/test").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/registrationAcc").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/main").permitAll()
                    .antMatchers("/worker/*").hasAuthority(Role.WORKER.getAuthority())
                    .antMatchers("/user/*").permitAll()
                    .antMatchers("/admin/*").hasAuthority(Role.ADMIN.getAuthority())
                    .anyRequest().permitAll()
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

    /*@Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("MikhailOk")
                        .password(encoder().encode("1994Rammstein"))
                        .authorities(Role.ADMIN.getAuthority())
                        .build()
        );
    }*/

    @Bean
    protected BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }
}
