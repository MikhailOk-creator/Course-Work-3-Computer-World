package ru.rtu_mirea.course_work_spring.Service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rtu_mirea.course_work_spring.Model.Product;
import ru.rtu_mirea.course_work_spring.Model.ProductType;
import ru.rtu_mirea.course_work_spring.Model.Role;
import ru.rtu_mirea.course_work_spring.Model.User;
import ru.rtu_mirea.course_work_spring.Repos.ProductRepo;
import ru.rtu_mirea.course_work_spring.Repos.UserRepo;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    @Value("${upload.path}")
    String uploadPath;
    private final ProductRepo repository;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;

    public AdminService(ProductRepo repository, UserRepo userRepo, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    /**
     * Method for adding new product items to the database.
     * @param product - product to be added to the database.
     * @param file - png or jpg format image to be attached to the product.
     * @param errors - in case of an error creating the product there are errors which are returned to the user in the form of warnings.
     * @param redirectAttributes - attributes that are filled in and later given to the user on the page.
     * @see Product
     * @return admin home page with new attributes (added product item)
     **/
    public String addNewPos(Product product, MultipartFile file, Errors errors, RedirectAttributes redirectAttributes) {
        boolean flagOfErrors = false;

        if(errors.hasErrors()){
            flagOfErrors = true;

            redirectAttributes.addFlashAttribute("currentTitle", product.getName());
            redirectAttributes.addFlashAttribute("currentDescription", product.getDescription());
            redirectAttributes.addFlashAttribute("currentNumber", product.getNumber());
            redirectAttributes.addFlashAttribute("currentPrice", product.getPrice());

            List<FieldError> list = errors.getFieldErrors();
            for (FieldError f : list) {
                redirectAttributes.addFlashAttribute(f.getField(), f.getDefaultMessage());
            }
        }

        if(repository.findByName(product.getName()) != null) {
            flagOfErrors = true;
            redirectAttributes.addFlashAttribute("exists", "A product with this name already exists");
        }

        if(!file.getOriginalFilename().equals("")) {
            String uuid = UUID.randomUUID().toString();
            String nameOfFile = uuid + file.getOriginalFilename();
            String filePath = uploadPath + "/" + nameOfFile;

            product.setImageUrl(nameOfFile);
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            flagOfErrors = true;
            redirectAttributes.addFlashAttribute("fileErr", "The file must have a name and must not be empty");
        }

        if (!flagOfErrors)
            repository.save(product);

        return "redirect:/admin";
    }

    /**
     * A method that adds a list of users to the users page.
     * @param model - MVC class in which attributes are added to be displayed on the page (attribute "users" - list of users).
     * @return The page containing all users of the application.
     **/
    public String userList(Model model){
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);

        Role[] roles = Role.values();
        model.addAttribute("userRoles", roles);
        return "userList";
    }

    /**
     * A method that filters products by their type.
     * @param model - MVC class in which attributes are added for display on the page.
     * @param type - product type from the frontend.
     * @param redirectAttributes - attributes that are filled in and then given to the user on the page.
     * @see ProductType
     * @return String that redirects the completed redirectAttributes to the page for display.
     **/
    public String getCheckedListForm(Model model, ProductType type, RedirectAttributes redirectAttributes) {
        List<Product> neededProducts = repository.findAllByType(type);
        redirectAttributes.addFlashAttribute("list", neededProducts);
        return "redirect:/admin";
    }

    /**
     * Метод удаления товара из базы данных по его id, а также удаление изображения товара.
     * @param id - уникальный идентификатор товара, по которому происходит удаление.
     * @see Product
     * @return Строка, которая перенаправляет пользователя на главную страницу.
     **/
    public String deleteProd(Long id){
        String imageUrl = uploadPath + "/" + repository.findById(id).get().getImageUrl();
        repository.deleteById(id);

        //FileUtils.deleteQuietly(new File(imageUrl));
        return "redirect:/admin";
    }



    /**
     * A method that implements the logic of banning / unbanning a user (changes the user's activity in the database).
     * @param id - unique identifier of the user to be banned / unbanned.
     * @return Page with all system users (the status of the user who was banned / unbanned will be updated).
     *  @see User
     **/
    public String changeUserActivity(Long id) {
        User user = userRepo.findById(id).get();
        user.setActive(!user.isActive());
        userRepo.save(user);
        return "redirect:/admin/userList";
    }

    /**
     * Method of adding a new worker to the database.
     * @param user - user to be added to the database.
     * @param errors - - in case of user creation error the errors are returned to the user in the form of warnings.
     * @param redirectAttributes - attributes that are filled in and later given to the user on the page (currentName, currentPassword,
     *                          currentEmail, currentRealName, currentSurname, currentPhone, currentAddress).
     * @return Two outcomes: if the incoming username the field with errors, then errors are written to redirectAttributes and the user
     * page is returned, notifying him about the errors; otherwise - the new worker is added to the database and the main page is returned.
     * @see User
     * @see BCryptPasswordEncoder
     *
     * Translated with www.DeepL.com/Translator (free version)
     **/
    public String addNewWorker(User user, Errors errors, RedirectAttributes redirectAttributes) {
        if(errors.hasErrors()){
            redirectAttributes.addFlashAttribute("currentName", user.getLogin());
            redirectAttributes.addFlashAttribute("currentPassword", user.getPassword());
            redirectAttributes.addFlashAttribute("currentEmail", user.getEmail());
            redirectAttributes.addFlashAttribute("currentRealName", user.getName());
            redirectAttributes.addFlashAttribute("currentSurname", user.getSurname());
            redirectAttributes.addFlashAttribute("currentPhone", user.getPhone());
            redirectAttributes.addFlashAttribute("currentAddress", user.getAddress());

            List<FieldError> list = errors.getFieldErrors();
            for (FieldError f : list) {
                redirectAttributes.addFlashAttribute(f.getField(), f.getDefaultMessage());
            }
        } else {
            user.setRoles(Collections.singleton(Role.WORKER));
            user.setActive(true);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
        }
        return "redirect:/admin";
    }
}
