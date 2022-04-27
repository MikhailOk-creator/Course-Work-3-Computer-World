package ru.rtu_mirea.course_work_spring.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rtu_mirea.course_work_spring.Repos.UserRepo;

/** A class that contains methods for Spring Security to work correctly **/
@Service
public class UserDetService implements UserDetailsService {
    private final UserRepo repo;

    public UserDetService(UserRepo repo) {
        this.repo = repo;
    }

    /**
     * A method that returns the user by his name.
     * @param username - user name.
     * @return User with the passed name.
     * @throws UsernameNotFoundException - if no user was found.
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) repo.findUserByName(username);
    }
}
