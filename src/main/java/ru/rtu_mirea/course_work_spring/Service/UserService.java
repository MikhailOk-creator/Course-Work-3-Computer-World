package ru.rtu_mirea.course_work_spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rtu_mirea.course_work_spring.Model.Role;
import ru.rtu_mirea.course_work_spring.Model.User;
import ru.rtu_mirea.course_work_spring.Repository.UserRepo;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    /**
     * The repository that contains all of system's user records.
     * @see User
     **/
    private final UserRepo repository;

    /** Cryptographic class for encrypting/decrypting user passwords. **/
    private final BCryptPasswordEncoder encoder;
    private final UserDetService details;

    @Autowired
    private MailService mail;

    public UserService(UserRepo repository, BCryptPasswordEncoder encoder, UserDetailsService detailsService, UserDetService details) {
        this.repository = repository;
        this.encoder = encoder;
        this.details = details;
    }

    /**
     * Method that adds a new user to the database.
     * @param user - user, which should be added to the database.
     * @param errors - in case of user creation errors will be written to model and displayed on the page
     * @param role - created user's role ({@link Role}).
     * @param model - MVC class in which attributes are added for display on the page (currentName, currentPassword,
     * currentEmail, errorAcc)
     * @return If errors are empty, a new user is added to the database and the login page is switched.
     * Otherwise the model is filled in and the login page is returned.
     * @see User
     * @see Role
     **/
    public String addUser(@ModelAttribute User user, Errors errors, @RequestParam Role role, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("currentName",user.getName());
            model.addAttribute("currentPassword",user.getPassword());
            model.addAttribute("currentEmail",user.getEmail());

            List<FieldError> list = errors.getFieldErrors();
            for (FieldError f : list) {
                model.addAttribute(f.getField(), f.getDefaultMessage());
            }

            if (details.loadUserByUsername(user.getName()) != null) {
                model.addAttribute("errorAcc", "Пользователь с таким именем уже существует.");
            }
            return "/registration";
        }

        user.setRoles(Collections.singleton(role));
        user.setPassword(encode(user.getPassword()));
        user.setActive(true);
        repository.save(user);

        mail.send("mikhail.okhapkin@yandex.ru", "Spring Boot Course Work Info",
                "Added user " + user.getName());

        return "redirect:/login";
    }

    /**
     * A method that saves the
     * user to the database or updates data on the user, if he is already contained in the database.
     * @param user - user whos
     *            fields must be saved or updated.
     * @see User
     * {@link #repository}
     **/
    public void saveOrUpdate(User user){
        repository.save(user);
    }

    /**
     * A method that returns the user by his name.
     * @param name - user name.
     * @return User with the given name.
     * @see User
     * {@link #repository}
     **/
    public User getUserByName(String name){
        return repository.findUserByName(name);
    }

    /**
     * A method that compares passwords.
     * @param pas1 - first password.
     * @param pas2 - second password.
     * @return Boolean value: true if passwords match.
     * {@link #encoder}
     **/
    public boolean equalsPassword(String pas1, String pas2){
        return encoder.matches(pas1, pas2);
    }

    /**
     * The method that encodes the password.
     * @param pass - source password.
     * @return encoded password.
     * @see #encoder
     **/
    public String encode(String pass){
        return encoder.encode(pass);
    }

    /**
     * A method that compares passwords.
     * @param password - first password.
     * @param currentPassword - second password.
     * @return Boolean value: true if passwords match.
     * @see #encoder
     **/
    public boolean matches(String password, String currentPassword){
        return encoder.matches(password, currentPassword);
    }
}
