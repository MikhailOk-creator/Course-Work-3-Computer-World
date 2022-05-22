package ru.rtu_mirea.course_work_spring.Service;

import org.apache.commons.io.FileUtils;
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
import ru.rtu_mirea.course_work_spring.Repository.ProductRepo;
import ru.rtu_mirea.course_work_spring.Repository.UserRepo;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
     **/
    public String addNewWorker(User user, Errors errors, RedirectAttributes redirectAttributes) {
        if(errors.hasErrors()){
            redirectAttributes.addFlashAttribute("currentName", user.getName());
            redirectAttributes.addFlashAttribute("currentPassword", user.getPassword());
            redirectAttributes.addFlashAttribute("currentEmail", user.getEmail());
            redirectAttributes.addFlashAttribute("currentRealName", user.getRealName());
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
            //user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
        }
        return "redirect:/admin";
    }

    /**
     * Method that returns the page for changing the product image.
     * @param id - unique identifier of the product whose image should be changed.
     * @param model - model - MVC class in which attributes are added for displaying on the page (product - product found in the database by id).
     * @param map - parameters that come from the frontend.
     * @return Page to change product image.
     * @see Product
     */
    public String changeImageForm(Long id, Model model, Map<String, ?> map){
        if(map != null) {
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }
        }
        Product productBD = repository.findById(id).get();
        model.addAttribute("product", productBD);
        return "change_image";
    }

    /**
     * Method for changing the product's image directly.
     * @param id - unique identifier of the product whose image should be changed.
     * @param img - new product image.
     * @param attributes - attributes that are filled in and later given to the user on the page (imgErr).
     * @return Two possible outcomes: if the new image has no name, an attribute is added to attributes,
     * which notifies the user of the error and the product change page is returned; otherwise, the product is updated,
     * the new file replaces the old file and the main admin page is returned.
     * @see Product
     **/
    public String changeImage(Long id, MultipartFile img, RedirectAttributes attributes) {
        boolean flagErrors = false;
        Product changeProduct = repository.findById(id).get();
        String pastUrl = uploadPath + "/" + changeProduct.getImageUrl();

        if(!img.getOriginalFilename().equals("")) {
            String uuid = UUID.randomUUID().toString();
            String nameOfFile = uuid + img.getOriginalFilename();
            String filePath = uploadPath + "/" + nameOfFile;

            changeProduct.setImageUrl(nameOfFile);
            try {
                img.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            repository.save(changeProduct);
            FileUtils.deleteQuietly(new File(pastUrl));
        } else {
            flagErrors = true;
            attributes.addFlashAttribute("imgErr", "Файл должен иметь название и не должен быть пустым");
        }
        if (flagErrors)
            return "redirect:/admin/changeImage/" + id;

        return "redirect:/admin";
    }

    /**
     * A method that fills the change page of a specific product.
     * @param id - unique product identifier.
     * @param model - MVC class in which attributes are added to be displayed on the page
     * (currentProduct - product which is passed by id, types - possible product types).
     * @param attributes - attributes from the front end.
     * @return The page that contains information on the product that is changing.
     * @see Product
     * @see ProductType
     **/
    public String changeProductForm(Long id, Model model, Map<String, ?> attributes) {
        if(attributes != null) {
            for (Map.Entry<String, ?> entry : attributes.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }
        }

        model.addAttribute("currentProduct", repository.findById(id).get());
        model.addAttribute("types", ProductType.values());
        return "change_product";
    }

    public String changeProduct(Product product, RedirectAttributes redirectAttributes, Long id){
        boolean flagOfErrors = false;

        Product dataBaseProduct = repository.findById(id).get();

        if(product.getName().equals("")){
            flagOfErrors = true;
            redirectAttributes.addFlashAttribute("titleErr", "Обязательное поле для заполнения");
        }
        if(product.getDescription().equals("")){
            flagOfErrors = true;
            redirectAttributes.addFlashAttribute("descriptionErr", "Обязательное поле для заполнения");
        }
        if(product.getType().isEmpty()){
            flagOfErrors = true;
            redirectAttributes.addFlashAttribute("selectingTypeErr", "Необходимо выбрать тип товара");
        }
        if(product.getNumber() == null){
            flagOfErrors = true;
            redirectAttributes.addFlashAttribute("numberErr", "Введите количество товара");
        }
        if(product.getPrice() == null){
            flagOfErrors = true;
            redirectAttributes.addFlashAttribute("priceErr", "Введите стоимость товара");
        }

        if (!flagOfErrors){
            dataBaseProduct.setForChange(product.getType(), product.getName(), product.getDescription(), product.getNumber(), product.getPrice());
            repository.save(dataBaseProduct);
            return "redirect:/admin";
        }

        return "redirect:/admin/changeProduct/" + id;
    }
}
