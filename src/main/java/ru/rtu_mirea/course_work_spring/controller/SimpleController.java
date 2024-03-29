package ru.rtu_mirea.course_work_spring.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import ru.rtu_mirea.course_work_spring.entity.Product;
import ru.rtu_mirea.course_work_spring.entity.ProductType;
import ru.rtu_mirea.course_work_spring.entity.Role;
import ru.rtu_mirea.course_work_spring.entity.User;
import ru.rtu_mirea.course_work_spring.repo.ProductRepo;
import ru.rtu_mirea.course_work_spring.service.CartService;
import ru.rtu_mirea.course_work_spring.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Controller
public class SimpleController {
    private final UserService service;
    private final ProductRepo productRepo;  //change this
    private final CartService cartService;

    public SimpleController(UserService service, ProductRepo productRepo, CartService cartService) {
        this.service = service;
        this.productRepo = productRepo;
        this.cartService = cartService;
    }

    @GetMapping("/registration")
    public String registrationForm() {
        return "registration";
    }

    @GetMapping("/registrationAcc")
    public String addUser(@ModelAttribute @Valid User user,
                          Errors errors,
                          @RequestParam Role role,
                          Model model) {
        return service.addUser(user, errors, role, model);
    }

    @GetMapping
    public String afterLogin(@CookieValue(value = "cart", required = false) String cart,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             Model model){
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser.getAuthorities().contains(Role.PR5)) {
            checkCookies(response);
            return "redirect:/pr5";
        }

        model.addAttribute("cartSize", cartService.getSize(cart, response));

        if(RequestContextUtils.getInputFlashMap(request) != null){
            Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }
        } else {
            model.addAttribute("list", productRepo.findAll());
        }
        ProductType[] productType = ProductType.values();
        model.addAttribute("types", productType);

        model.addAttribute("isAuthorized", authUser.getPrincipal()!="anonymousUser");
        model.addAttribute("isAdmin", authUser.getAuthorities().contains(Role.ADMIN));
        model.addAttribute("isWorker", authUser.getAuthorities().contains(Role.WORKER));

        return "index";
    }

    private void checkCookies(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String translation = "";
        String backgroundColor = "";
        String name = "";

        translation = "ru";
        Cookie cookie1 = new Cookie("translation", translation);
        cookie1.setPath("/pr5");
        cookie1.setMaxAge(86400);
        response.addCookie(cookie1);
        response.setContentType("text/plain");


        backgroundColor = "light";
        Cookie cookie2 = new Cookie("backgroundColor", backgroundColor);
        cookie2.setMaxAge(86400);
        cookie2.setPath("/pr5");
        response.addCookie(cookie2);
        response.setContentType("text/plain");

        name = authentication.getName();
        Cookie cookie3 = new Cookie("name", name);
        cookie3.setPath("/pr5");
        cookie3.setMaxAge(86400);
        response.addCookie(cookie3);
        response.setContentType("text/plain");
    }


    @GetMapping("/checkClickedButton/{type}")
    public String checkType(Model model, @PathVariable(name = "type") ProductType type, RedirectAttributes redirectAttributes){
        List<Product> neededProducts = productRepo.findAllByType(type);
        redirectAttributes.addFlashAttribute("list", neededProducts);
        return "redirect:/";
    }

    @PostMapping("/addToCart/{id}")
    public String addToCart(@CookieValue(value = "cart", required = false) String cart,
                            @PathVariable("id") Long id,
                            HttpServletResponse response){
        return cartService.add(cart, id, response);
    }
}
